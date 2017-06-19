package com.stocks.technical.tasks.once;

import com.stocks.technical.core.api.dto.StockData;
import com.stocks.technical.core.db.dao.CompanyDao;
import com.stocks.technical.core.db.entity.company.Company;
import com.stocks.technical.core.db.entity.statistic.VolumeStatisticData;
import com.stocks.technical.core.service.StockService;
import com.stocks.technical.tasks.utils.converter.StockDataToCandleConverter;

import java.util.List;

import static com.stocks.technical.tasks.once.data.CompanyStaticDataHolder.getAllCompanies;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class CreateCompaniesTask {

    public void execute() {
//        clearPrevious();

        for (Company company : getAllCompanies()) {
            List<StockData> stockDataList = new StockService().queryStocksSince2015Yahoo(company);

            company.addCandles(StockDataToCandleConverter.convert(stockDataList));
            company.setCommonStatisticData(new VolumeStatisticData(company));

            new CompanyDao().saveOrUpdate(company);
        }
    }



    public static void main(String[] args) {
        new CreateCompaniesTask().execute();
    }

}
