package com.stocks.livermor;

import com.stocks.livermor.entity.LivermorPair;
import com.stocks.livermor.entity.Record;
import com.stocks.livermor.strategy.StateProcessor;
import com.stocks.livermor.strategy.factory.StrategyPicker;
import com.stocks.livermor.utils.RecordsHolder;
import com.stocks.livermor.utils.RecordsHolder.ByDateComparator;
import com.stocks.technical.core.db.dao.CompanyDao;
import com.stocks.technical.core.db.dao.LivermorPairDao;
import com.stocks.technical.core.db.dao.RecordDao;
import com.stocks.technical.core.db.entity.Candle;
import com.stocks.technical.core.db.entity.company.Company;
import com.stocks.technical.tasks.daily.RecentDataObtainTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Executor {
    private final StrategyPicker strategyPicker;
    private static final RecordDao recordDao = new RecordDao();
    private static final Executor executor = new Executor(new StrategyPicker());
    private static final LivermorPairDao livermorPairDao = new LivermorPairDao();

    public Executor(StrategyPicker strategyPicker) {
        this.strategyPicker = strategyPicker;
    }

    public void process(RecordsHolder recordsHolder, Record current) {
        final StateProcessor stateProcessor = strategyPicker.pick(recordsHolder.lastWithState());
        stateProcessor.process(recordsHolder, current);
        recordsHolder.add(current);
    }

    public static void main(String[] args) {
        new RecentDataObtainTask().execute();
        processNewRecords();
        processKeyPrices();
    }

    private static void processNewRecords() {
        for (Company company : new CompanyDao().getAll()) {
            RecordsHolder recordsHolder = new RecordsHolder(recordDao.getAll(company.getName()));
            for (Candle candle : filterNewCandles(company)) {
                Record record = new Record(candle.getDate().getTime(), candle.getClose().doubleValue());
                executor.process(recordsHolder, record);
            }

            saveOrUpdateNewRecords(recordsHolder);
        }
    }

    private static void processKeyPrices() {

        for (LivermorPair livermorPair : livermorPairDao.getAll()) {
            final List<Record> firstTickerRecords = recordDao.getAll(livermorPair.getFirstTicker());
            firstTickerRecords.sort(new ByDateComparator().reversed());
            final List<Record> secondTickerRecords = recordDao.getAll(livermorPair.getSecondTicker());
            secondTickerRecords.sort(new ByDateComparator().reversed());


            List<Record> keyPriceRecords = recordDao.getAll(livermorPair.getKeyPriceTicker());
            keyPriceRecords.sort(new ByDateComparator().reversed());
            RecordsHolder keyPriceRecordsHolder = new RecordsHolder(keyPriceRecords);

            final Date oldestKeyPriceRecordDate = keyPriceRecords.get(0).getDate();
            for (int i = 0; i < firstTickerRecords.size(); i++) {
                Record firstRecord = firstTickerRecords.get(i);
                Record secondRecord = secondTickerRecords.get(i);
                if (!firstRecord.getDate().equals(secondRecord.getDate()))
                    throw new IllegalStateException("Dates are not eq.");
                if (firstRecord.getDate().compareTo(oldestKeyPriceRecordDate) <= 0) break;

                final Record newKeyPriceRecord = new Record(firstRecord.getDate(), firstRecord.getPrice() + secondRecord.getPrice());
                executor.process(keyPriceRecordsHolder, newKeyPriceRecord);
            }
            
            saveOrUpdateNewRecords(keyPriceRecordsHolder);
        }
    }

    private static List<Candle> filterNewCandles(Company company) {
        List<Record> allRecords = new RecordDao().getAll(company.getName());
        allRecords.sort(new ByDateComparator().reversed());

        final Calendar oldestRecordDate = Calendar.getInstance();
        oldestRecordDate.setTime(allRecords.get(0).getDate());

        List<Candle> newCandles = new ArrayList<>();

        for (Candle candle : company.getCandles()) {
            if (candle.getDate().compareTo(oldestRecordDate) > 0) {
                newCandles.add(candle);
            }
        }

        return newCandles;
    }

    private static void saveOrUpdateNewRecords(RecordsHolder recordsHolder) {
        for (Record record : recordsHolder.getNewRecords()) {
            recordDao.saveOrUpdate(record);
        }
    }
}
