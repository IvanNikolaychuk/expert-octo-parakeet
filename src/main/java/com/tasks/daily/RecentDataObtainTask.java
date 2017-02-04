package com.tasks.daily;

import com.core.api.YahooApi;
import com.core.api.dto.StockData;
import com.core.api.gererator.RequestGenerator;
import com.core.api.helpers.Constants;
import com.core.api.helpers.Period;
import com.core.db.dao.CompanyDao;
import com.core.db.entity.Candle;
import com.core.db.entity.company.Company;
import com.tasks.utils.converter.StockDataToCandleConverter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.tasks.utils.TimeUtils.isSameDay;

public class RecentDataObtainTask {

    public void execute() {
        CompanyDao companyDao = new CompanyDao();
        List<Company> companies = companyDao.getAll();

        Calendar calendar = yesterday();

        for (Company company : companies) {
            if (companyHasDataForThisDay(company, calendar)) {
                System.out.println("Company has data for " + calendar.getTime() + ". No data would be added.");
                continue;
            }

            final String url = RequestGenerator.generateFor(company.getName(),
                    Period.of(forDate(calendar), forDate(calendar)));

            StockData stockData = YahooApi.querySingle(url);
            if (stockData == null) {
                System.out.println("No stock data for " + forDate(calendar).toString());
                break;
            }

            company.addCandle(StockDataToCandleConverter.convert(stockData));
            companyDao.saveOrUpdate(company);
        }
    }

    private boolean companyHasDataForThisDay(Company company, Calendar calendar) {
        List<Candle> candles = company.getCandles();

        for (Candle candle : candles) {
            if (isSameDay(candle.getDate(), calendar)) {
                return true;
            }
        }
        return false;
    }

    private Calendar yesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);

        return calendar;
    }

    private Period.Date forDate(Calendar calendar) {
        return Period.Date.build(Constants.CURRENT_YEAR, calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE));
    }
}
