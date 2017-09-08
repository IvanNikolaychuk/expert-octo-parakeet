package com.stocks.technical.tasks.daily;

import com.stocks.technical.core.api.dto.StockData;
import com.stocks.technical.core.db.dao.CompanyDao;
import com.stocks.technical.core.db.entity.Candle;
import com.stocks.technical.core.db.entity.company.Company;
import com.stocks.technical.core.db.service.CompanyService;
import com.stocks.technical.tasks.utils.CandleUtils;
import com.stocks.technical.tasks.utils.converter.StockDataToCandleConverter;
import com.stocks.technical.tasks.utils.filters.CandlesFilter;
import javafx.util.Pair;

import java.util.Calendar;
import java.util.List;

import static com.stocks.technical.core.api.YahooApi.query;
import static java.util.Calendar.*;

public class RecentDataObtainTask {

    public void execute() {
        CompanyDao companyDao = new CompanyDao();
        List<Company> companies = companyDao.getAll();

        for (Company company : companies) {
            Candle mostRecentCandle = CandleUtils.sort(company.getCandles(), new CandlesFilter.RecentDateFirstComparator()).get(0);

            Calendar firstDateWithNoCandle = getInstance();
            firstDateWithNoCandle.setTime(mostRecentCandle.getDate().getTime());
//            firstDateWithNoCandle.add(DATE, 1);

            Calendar yesterday = getInstance();
            yesterday.set(yesterday.get(YEAR), yesterday.get(MONTH), yesterday.get(DAY_OF_MONTH), 23, 0);
            yesterday.add(DAY_OF_MONTH, -1);

            List<StockData> stockDataList = query(company.getName(), new Pair<>(firstDateWithNoCandle.getTime(), yesterday.getTime()));
            if (stockDataList.isEmpty()) {
                System.out.println("No data for period: " + firstDateWithNoCandle + " - " + yesterday + " for company " + company.getName());
                continue;
            }

            new CompanyService().addCandles(company, StockDataToCandleConverter.convert(stockDataList));
        }
    }

    public static void main(String[] args) {
        new RecentDataObtainTask().execute();
    }

}
