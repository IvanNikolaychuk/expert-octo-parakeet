package com.stocks.technical.tasks.once;

import com.stocks.technical.core.api.dto.StockData;
import com.stocks.technical.core.db.dao.CompanyDao;
import com.stocks.technical.core.db.entity.company.Company;
import com.stocks.technical.core.service.StockService;
import com.stocks.technical.tasks.utils.converter.StockDataToCandleConverter;

import java.util.Calendar;
import java.util.List;

import static com.stocks.technical.tasks.once.data.CompanyStaticDataHolder.getAllCompanies;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class CreateCompaniesTask {

    public void execute() {
//        clearPrevious();

        for (Company company :  getAllCompanies()) {
            Calendar date2016 = Calendar.getInstance();
            date2016.set(2016, Calendar.JANUARY, 1);
            List<StockData> stockDataList = new StockService().queryStocksSinceYahoo(company, date2016);

            company.addCandles(StockDataToCandleConverter.convert(stockDataList));

            new CompanyDao().saveOrUpdate(company);
        }
    }



    public static void main(String[] args) {
        new CreateCompaniesTask().execute();
    }

}
