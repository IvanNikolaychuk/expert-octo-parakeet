package com.stocks.livermor.excel;

import com.stocks.livermor.entity.LivermorPair;
import com.stocks.livermor.entity.LivermorSingleCompany;
import com.stocks.livermor.entity.Record;
import com.stocks.livermor.utils.RecordsHolder;
import com.stocks.technical.core.db.dao.LivermorSingleCompanyDao;
import com.stocks.technical.core.db.dao.RecordDao;

import java.util.List;

import static com.stocks.livermor.utils.RecordsFilter.byYear;

public class LivermorVisualizer {
    private ExcelWriter excelWriter;
    private RecordDao recordDao;

    public LivermorVisualizer() throws Exception {
        excelWriter = new ExcelWriter();
        recordDao = new RecordDao();
    }

    public void visualize(LivermorPair livermorPair, int beginYear) throws Exception {
        List<Record> firstRecords = byYear(beginYear, recordDao.getAll(livermorPair.getFirstTicker()));
        excelWriter.createTable(new RecordsHolder(firstRecords));
        excelWriter.next();

        List<Record> secondRecords = byYear(beginYear, recordDao.getAll(livermorPair.getSecondTicker()));
        excelWriter.createTable(new RecordsHolder(secondRecords));
        excelWriter.next();

        List<Record> keyPriceRecords = byYear(beginYear, recordDao.getAll(livermorPair.getKeyPriceTicker()));
        excelWriter.createTable(new RecordsHolder(keyPriceRecords));
    }

    public void visualize(LivermorSingleCompany livermorSingleCompany, int beginYear) throws Exception {
        List<Record> firstRecords = byYear(beginYear, recordDao.getAll(livermorSingleCompany.getTicker()));
        excelWriter.createTable(new RecordsHolder(firstRecords));
    }

    public static void main(String[] args) throws Exception {
//        final LivermorPair livermorPair = new LivermorPairDao().getByKeyTicker("GM_F");
//        new LivermorVisualizer().visualize(livermorPair, 2015);

        LivermorSingleCompany livermorSingleCompany = new LivermorSingleCompanyDao().getByKeyTicker("V");
        new LivermorVisualizer().visualize(livermorSingleCompany, 2015);
    }
}
