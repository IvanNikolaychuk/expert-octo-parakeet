package com.stocks.livermor;

import com.stocks.technical.core.api.dto.StockData;
import com.stocks.technical.core.db.dao.CompanyDao;
import com.stocks.technical.core.db.entity.company.BusinessType;
import com.stocks.technical.core.db.entity.company.Company;
import com.stocks.technical.core.db.entity.company.StockCurrency;
import com.stocks.technical.core.service.StockService;
import com.stocks.technical.tasks.utils.converter.StockDataToCandleConverter;

import java.util.Calendar;
import java.util.List;

public class CreateCompanyTask {
    public static void main(String[] args) {
//        create("C", BANKING, DOLLAR);
//        create("BAC", BANKING, DOLLAR);

        Calendar date = Calendar.getInstance();
        date.set(2016, Calendar.FEBRUARY, 11);

        CompanyDao companyDao = new CompanyDao();
//        Record record = new Record(date.getTime(), 34.98 + 11.16);
//        record.setState(DOWN_TREND);
//        record.markAsPivotPoint();
//        record.setTicker("C_BAC");
//
//        new RecordDao().save(record);
    }

    public static void create(String companyName, BusinessType businessType, StockCurrency stockCurrency,
                              Calendar firstCandleDate) {
        Company company = new Company(companyName, businessType, stockCurrency);

        List<StockData> stockDataList = new StockService().queryStocksSinceYahoo(company, firstCandleDate);
        company.addCandles(StockDataToCandleConverter.convert(stockDataList));
        new CompanyDao().saveOrUpdate(company);
    }

}
