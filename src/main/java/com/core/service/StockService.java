package com.core.service;

import com.core.api.YahooApi;
import com.core.api.dto.StockData;
import com.core.api.gererator.RequestGenerator;
import com.core.api.helpers.Constants;
import com.core.api.helpers.Period;
import com.core.db.entity.company.Company;

import java.util.ArrayList;
import java.util.List;

import static com.core.api.helpers.Constants.CURRENT_YEAR;
import static com.core.api.helpers.Period.Date.*;
import static com.core.api.helpers.Period.Date.december;
import static com.core.api.helpers.Period.Date.february;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class StockService {

    public List<StockData> queryStockDataStartingFromLastYear(Company company) {
        StockService stockService = new StockService();
        List<StockData> listOfStocks = stockService.queryStocks(company, CURRENT_YEAR - 1);
        listOfStocks.addAll(stockService.queryStocks(company, CURRENT_YEAR));

        return listOfStocks;
    }

    public List<StockData> queryStocks(Company company, int year) {
        List<StockData> listOfStocks = new ArrayList<>();

        String url = RequestGenerator.generateFor(company.getName(), Period.of(january(year, 1), may(year, 1)));
        listOfStocks.addAll(YahooApi.query(url));

        url = RequestGenerator.generateFor(company.getName(), Period.of(may(year, 2), august(year, 1)));
        listOfStocks.addAll(YahooApi.query(url));

        url = RequestGenerator.generateFor(company.getName(), Period.of(august(year, 2), november(year, 1)));
        listOfStocks.addAll(YahooApi.query(url));

        url = RequestGenerator.generateFor(company.getName(), Period.of(november(year, 2), december(year, 31)));
        listOfStocks.addAll(YahooApi.query(url));

        return listOfStocks;
    }
}
