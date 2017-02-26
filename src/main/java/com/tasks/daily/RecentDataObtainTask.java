package com.tasks.daily;

import com.core.api.QuandlApi;
import com.core.api.YahooApi;
import com.core.api.yahoo.dto.StockData;
import com.core.api.yahoo.gererator.RequestGenerator;
import com.core.api.yahoo.helpers.Constants;
import com.core.api.yahoo.helpers.Period;
import com.core.db.dao.CompanyDao;
import com.core.db.entity.Candle;
import com.core.db.entity.company.Company;
import com.tasks.utils.CandleUtils;
import com.tasks.utils.converter.StockDataToCandleConverter;
import com.tasks.utils.filters.CandlesFilter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.core.api.QuandlApi.query;
import static com.core.api.yahoo.helpers.Period.Date.forDate;
import static com.core.api.yahoo.helpers.Period.Date.january;
import static com.tasks.utils.TimeUtils.isSameDay;

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
