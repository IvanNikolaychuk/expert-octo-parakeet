package com.stocks.core.service;

import com.stocks.core.api.QuandlApi;
import com.stocks.core.api.yahoo.dto.StockData;
import com.stocks.core.api.yahoo.helpers.Period;
import com.stocks.core.db.entity.company.Company;

import java.util.Calendar;
import java.util.List;

import static com.stocks.core.api.yahoo.helpers.Period.Date.forDate;
import static com.stocks.core.api.yahoo.helpers.Period.Date.november;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class StockService {

    public List<StockData> queryStocksSince2015(Company company) {
        Calendar now = Calendar.getInstance();

        return QuandlApi.query(company.getName(), Period.of(november(2015, 1), forDate(now)));
    }
}
