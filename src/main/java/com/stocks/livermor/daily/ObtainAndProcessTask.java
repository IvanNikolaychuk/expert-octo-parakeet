package com.stocks.livermor.daily;

import com.stocks.livermor.Executor;
import com.stocks.livermor.entity.LivermorPair;
import com.stocks.livermor.entity.Record;
import com.stocks.livermor.strategy.factory.StrategyPicker;
import com.stocks.livermor.utils.RecordsHolder;
import com.stocks.technical.core.db.dao.CompanyDao;
import com.stocks.technical.core.db.dao.LivermorPairDao;
import com.stocks.technical.core.db.dao.RecordDao;
import com.stocks.technical.core.db.entity.Candle;
import com.stocks.technical.core.db.entity.company.Company;
import com.stocks.technical.tasks.daily.RecentDataObtainTask;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_DOWN;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class ObtainAndProcessTask {
    private static final RecordDao recordDao = new RecordDao();
    private static final Executor executor = new Executor(new StrategyPicker());
    private static final LivermorPairDao livermorPairDao = new LivermorPairDao();

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
                record.setTicker(company.getName());
                executor.process(recordsHolder, record);
            }

            saveOrUpdateNewRecords(recordsHolder);
        }
    }

    private static void processKeyPrices() {
        for (LivermorPair livermorPair : livermorPairDao.getAll()) {
            final List<Record> firstTickerRecords = recordDao.getAll(livermorPair.getFirstTicker());
            firstTickerRecords.sort(new RecordsHolder.ByDateComparator());
            final List<Record> secondTickerRecords = recordDao.getAll(livermorPair.getSecondTicker());
            secondTickerRecords.sort(new RecordsHolder.ByDateComparator());


            List<Record> keyPriceRecords = recordDao.getAll(livermorPair.getKeyPriceTicker());
            keyPriceRecords.sort(new RecordsHolder.ByDateComparator().reversed());
            final Date oldestKeyPriceRecordDate = keyPriceRecords.get(0).getDate();

            RecordsHolder keyPriceRecordsHolder = new RecordsHolder(keyPriceRecords);
            for (int i = 0; i < firstTickerRecords.size(); i++) {
                Record firstRecord = firstTickerRecords.get(i);
                Record secondRecord = secondTickerRecords.get(i);
                if (!sameDate(firstRecord, secondRecord))
                    throw new IllegalStateException("Dates are not eq.");

                if (firstRecord.getDate().compareTo(oldestKeyPriceRecordDate) <= 0) continue;

                BigDecimal keyPrice = new BigDecimal(firstRecord.getPrice() + secondRecord.getPrice()).setScale(2, ROUND_HALF_DOWN);
                final Record newKeyPriceRecord = new Record(firstRecord.getDate(), keyPrice.doubleValue());
                newKeyPriceRecord.setTicker(livermorPair.getKeyPriceTicker());
                executor.process(keyPriceRecordsHolder, newKeyPriceRecord);
            }

            saveOrUpdateNewRecords(keyPriceRecordsHolder);
        }
    }

    private static boolean sameDate(Record firstRecord, Record secondRecord) {
        final Calendar firstDate = Calendar.getInstance();
        firstDate.setTime(firstRecord.getDate());

        final Calendar secondDate = Calendar.getInstance();
        secondDate.setTime(secondRecord.getDate());

        return firstDate.get(YEAR) == secondDate.get(YEAR) &&
                firstDate.get(MONTH) == secondDate.get(MONTH) &&
                firstDate.get(DAY_OF_MONTH) == secondDate.get(DAY_OF_MONTH);
    }

    private static List<Candle> filterNewCandles(Company company) {
        List<Record> allRecords = new RecordDao().getAll(company.getName());
        allRecords.sort(new RecordsHolder.ByDateComparator().reversed());

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
