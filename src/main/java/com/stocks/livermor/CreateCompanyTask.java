package com.stocks.livermor;

import com.stocks.livermor.entity.LivermorPair;
import com.stocks.livermor.entity.Record;
import com.stocks.technical.core.api.dto.StockData;
import com.stocks.technical.core.db.dao.CompanyDao;
import com.stocks.technical.core.db.entity.company.BusinessType;
import com.stocks.technical.core.db.entity.company.Company;
import com.stocks.technical.core.db.entity.company.StockCurrency;
import com.stocks.technical.core.service.StockService;
import com.stocks.technical.tasks.utils.converter.StockDataToCandleConverter;

import java.util.Calendar;
import java.util.List;

import static com.stocks.livermor.entity.State.DOWN_TREND;
import static com.stocks.technical.core.db.entity.company.BusinessType.AUTO_PRODUCTION;

public class CreateCompanyTask {
    public static void main(String[] args) {
        Calendar date = Calendar.getInstance();
        date.set(2016, Calendar.FEBRUARY, 11);

//        create("F", AUTO_PRODUCTION, DOLLAR, date);
//        create("GM", AUTO_PRODUCTION, DOLLAR, date);

        Record first = new Record(date.getTime(), 26.90);
        first.setState(DOWN_TREND);
        first.markAsPivotPoint();
        first.setTicker("GM");

        Record second = new Record(date.getTime(), 11.17);
        second.setState(DOWN_TREND);
        second.markAsPivotPoint();
        second.setTicker("F");

        Record key = new Record(date.getTime(), second.getPrice() + first.getPrice());
        key.setState(DOWN_TREND);
        key.markAsPivotPoint();
        key.setTicker(first.getTicker() + "_" + second.getTicker());

        LivermorPair livermorPair = new LivermorPair();
        livermorPair.setKeyPriceTicker(key.getTicker());
        livermorPair.setFirstTicker(first.getTicker());
        livermorPair.setSecondTicker(second.getTicker());
        livermorPair.setBusinessType(AUTO_PRODUCTION);

//        for(Company company : new CompanyDao().getAll()) {
//            new LivermorSingleCompanyDao().save(new LivermorSingleCompany(company.getName()));
//        }
//        new RecordDao().save(first);
//        new RecordDao().save(second);
//        new RecordDao().save(key);
//        new LivermorPairDao().save(livermorPair);
    }

    public static void create(String companyName, BusinessType businessType, StockCurrency stockCurrency,
                              Calendar firstCandleDate) {
        Company company = new Company(companyName, businessType, stockCurrency);

        List<StockData> stockDataList = new StockService().queryStocksSinceYahoo(company, firstCandleDate);
        company.addCandles(StockDataToCandleConverter.convert(stockDataList));
        new CompanyDao().saveOrUpdate(company);
    }

}
