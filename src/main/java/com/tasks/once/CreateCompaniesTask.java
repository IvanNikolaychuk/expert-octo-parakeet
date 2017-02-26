package com.tasks.once;

import com.core.api.yahoo.dto.StockData;
import com.core.db.dao.CompanyDao;
import com.core.db.dao.InvestmentPeriodDataDao;
import com.core.db.dao.VolumeStatisticDataDao;
import com.core.db.entity.company.Company;
import com.core.db.entity.statistic.VolumeStatisticData;
import com.core.service.StockService;
import com.tasks.utils.converter.StockDataToCandleConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.tasks.once.data.CompanyStaticDataHolder.getAllCompanies;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class CreateCompaniesTask {

    public void execute() {
//        clearPrevious();

        for (Company company : getAllCompanies()) {
            List<StockData> stockDataList = new StockService().queryStocksSince2014(company);

            company.addCandles(StockDataToCandleConverter.convert(stockDataList));
            company.setCommonStatisticData(new VolumeStatisticData(company, new ArrayList<Calendar>()));

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
