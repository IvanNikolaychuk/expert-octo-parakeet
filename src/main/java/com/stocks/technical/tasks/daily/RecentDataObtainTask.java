package com.stocks.technical.tasks.daily;

import com.stocks.technical.core.api.dto.StockData;
import com.stocks.technical.core.api.helpers.Period;
import com.stocks.technical.core.db.dao.CompanyDao;
import com.stocks.technical.core.db.entity.Candle;
import com.stocks.technical.core.db.entity.company.Company;
import com.stocks.technical.tasks.utils.CandleUtils;
import com.stocks.technical.tasks.utils.converter.StockDataToCandleConverter;
import com.stocks.technical.tasks.utils.filters.CandlesFilter;

import java.util.Calendar;
import java.util.List;

import static com.stocks.technical.core.api.QuandlApi.query;
import static com.stocks.technical.core.api.helpers.Period.Date.forDate;
import static com.stocks.technical.core.api.helpers.Period.Date.january;

public class RecentDataObtainTask {

    public void execute() {
        CompanyDao companyDao = new CompanyDao();
        List<Company> companies = companyDao.getAll();

        for (Company company : companies) {
            Candle mostRecentCandle = CandleUtils.sort(company.getCandles(), new CandlesFilter.RecentDateFirstComparator()).get(0);

            Calendar firstDateWithNoCandle = Calendar.getInstance();
            firstDateWithNoCandle.setTime(mostRecentCandle.getDate().getTime());
            firstDateWithNoCandle.add(Calendar.DATE, 1);

            Calendar now = Calendar.getInstance();
            List<StockData> stockDataList = query(company.getName(), Period.of(forDate(firstDateWithNoCandle), forDate(now)));
            if (stockDataList.isEmpty()) {
                System.out.println("No data for period: " + firstDateWithNoCandle + " - " + now + " for company " + company.getName());
                continue;
            }

            company.getCandles().addAll(StockDataToCandleConverter.convert(stockDataList));
            companyDao.saveOrUpdate(company);
        }
    }

}
