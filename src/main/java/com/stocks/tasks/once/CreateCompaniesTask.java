package com.stocks.tasks.once;

import com.stocks.core.api.yahoo.dto.StockData;
import com.stocks.core.db.dao.CompanyDao;
import com.stocks.core.db.dao.InvestmentPeriodDataDao;
import com.stocks.core.db.dao.VolumeStatisticDataDao;
import com.stocks.core.db.entity.company.Company;
import com.stocks.core.db.entity.statistic.VolumeStatisticData;
import com.stocks.core.service.StockService;
import com.stocks.tasks.utils.converter.StockDataToCandleConverter;

import java.util.List;

import static com.stocks.tasks.once.data.CompanyStaticDataHolder.getAllCompanies;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class CreateCompaniesTask {

    public void execute() {
//        clearPrevious();

        for (Company company : getAllCompanies()) {
            List<StockData> stockDataList = new StockService().queryStocksSince2015(company);

            company.addCandles(StockDataToCandleConverter.convert(stockDataList));
            company.setCommonStatisticData(new VolumeStatisticData(company));

            new CompanyDao().saveOrUpdate(company);
        }
    }

    private void clearPrevious() {
        new CompanyDao().clearAll();
        new VolumeStatisticDataDao().clearAll();
        new InvestmentPeriodDataDao().clearAll();
    }


    public static void main(String[] args) {
        new CreateCompaniesTask().execute();
    }

}
