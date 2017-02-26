package com.core.service;

import com.core.api.QuandlApi;
import com.core.api.yahoo.dto.StockData;
import com.core.api.yahoo.helpers.Period;
import com.core.db.entity.company.Company;

import java.util.Calendar;
import java.util.List;

import static com.core.api.yahoo.helpers.Period.Date.*;
import static com.core.api.yahoo.helpers.Period.Date.december;
import static com.core.api.yahoo.helpers.Period.Date.february;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class StockService {

    public List<StockData> queryStocksSince2014(Company company) {
        Calendar now = Calendar.getInstance();

        return QuandlApi.query(company.getName(), Period.of(january(2014, 1), forDate(now)));
    }
}
