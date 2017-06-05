package com.stocks.core.service;

import com.stocks.core.api.QuandlApi;
import com.stocks.core.api.YahooApi;
import com.stocks.core.api.helpers.Period;
import com.stocks.core.api.dto.StockData;
import com.stocks.core.db.entity.company.Company;
import javafx.util.Pair;

import java.util.Calendar;
import java.util.List;

import static com.stocks.core.api.helpers.Period.Date.forDate;
import static com.stocks.core.api.helpers.Period.Date.november;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class StockService {
    private final Calendar now = Calendar.getInstance();

    public List<StockData> queryStocksSince2015(Company company) {
        return QuandlApi.query(company.getName(), Period.of(november(2015, 1), forDate(now)));
    }

    public List<StockData> queryStocksSince2015Yahoo(Company company) {
        Calendar from = Calendar.getInstance();
        from.set(2015, Calendar.MAY, 30);
        Calendar to = Calendar.getInstance();
        to.set(now.get(YEAR), now.get(MONTH), now.get(DAY_OF_MONTH));

        return YahooApi.query(company.getName(), new Pair<>(from.getTime(), to.getTime()));
    }
}
