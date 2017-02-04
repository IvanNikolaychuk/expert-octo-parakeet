package com.tasks.once;

import com.core.api.dto.StockData;
import com.core.db.dao.CompanyDao;
import com.core.db.dao.StatisticDataDao;
import com.core.db.entity.company.Company;
import com.core.db.entity.statistic.StatisticData;
import com.core.service.StockService;
import com.tasks.utils.converter.StockDataToCandleConverter;

import java.util.ArrayList;
import java.util.List;

import static com.tasks.once.data.CompanyStaticDataHolder.getAllCompanies;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class CreateCompaniesTask {

    public void execute() {
        StockService stockService = new StockService();
        CompanyDao companyDao = new CompanyDao();

        companyDao.clearAll();
        new StatisticDataDao().clearAll();

        for (Company company : getAllCompanies()) {
            List<StockData> stockDataList = stockService.queryStockDataStartingFromLastYear(company);
            company.addCandles(StockDataToCandleConverter.convert(stockDataList));
            company.setStatisticData(new StatisticData(company));
            companyDao.saveOrUpdate(company);
        }
    }



    public static void main(String[] args) {
        new CreateCompaniesTask().execute();
    }

}
