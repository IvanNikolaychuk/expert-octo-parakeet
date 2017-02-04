package com.tasks.once;

import com.core.api.dto.StockData;
import com.core.db.dao.CompanyDao;
import com.core.db.dao.InvestmentPeriodDataDao;
import com.core.db.dao.StatisticDataDao;
import com.core.db.entity.company.Company;
import com.core.db.entity.statistic.CommonStatisticData;
import com.core.service.StockService;
import com.tasks.utils.converter.StockDataToCandleConverter;

import java.util.List;

import static com.tasks.once.data.CompanyStaticDataHolder.getAllCompanies;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class CreateCompaniesTask {

    public void execute() {
        clearPrevious();

        for (Company company : getAllCompanies()) {
            List<StockData> stockDataList = new StockService().queryStockDataStartingFromLastYear(company);

            company.addCandles(StockDataToCandleConverter.convert(stockDataList));
            company.setCommonStatisticData(new CommonStatisticData(company));

            new CompanyDao().saveOrUpdate(company);
        }
    }

    private void clearPrevious() {
        new CompanyDao().clearAll();
        new StatisticDataDao().clearAll();
        new InvestmentPeriodDataDao().clearAll();
    }


    public static void main(String[] args) {
        new CreateCompaniesTask().execute();
    }

}
