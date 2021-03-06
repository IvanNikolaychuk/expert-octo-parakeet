package com.stocks.technical.core.api;

import com.stocks.technical.core.api.quandl.java.InvalidTokenException;
import com.stocks.technical.core.api.quandl.java.QDataset;
import com.stocks.technical.core.api.quandl.java.QEntry;
import com.stocks.technical.core.api.quandl.java.QuandlConnection;
import com.stocks.technical.core.api.dto.StockData;
import com.stocks.technical.core.api.helpers.Period;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;

public class QuandlApi {
    public static final String AUTH_TOKEN = "BxyFSeF3zsXjZbVErHyZ";

    private static final int OPEN = 1;
    private static final int HIGH = 2;
    private static final int LOW = 3;
    private static final int CLOSE = 4;
    private static final int VOLUME = 5;

    public static List<StockData> query(String companyName, Period period) {
        QuandlConnection q;
        try {
            q = QuandlConnection.getFullConnection(AUTH_TOKEN);
        } catch (InvalidTokenException e) {
            throw new IllegalStateException(e);
        }
        QDataset dataset = q.getDatasetBetweenDates("WIKI/" + companyName,
                period.getStartDate().toString(),
                period.getEndDate().toString());

        return toStockData(dataset);
    }

    private static List<StockData> toStockData(QDataset dataset) {
        final List<StockData> stockDatasSet = new ArrayList<>();

        for(QEntry qEntry : dataset.getDataset()) {
            List<String> data = qEntry.getRow();

            StockData stockData = new StockData();
            stockData.setDate(toDate(qEntry.getDate()));
            stockData.setOpen(new BigDecimal(data.get(OPEN)));
            stockData.setLow(new BigDecimal(data.get(LOW)));
            stockData.setHigh(new BigDecimal(data.get(HIGH)));
            stockData.setVolume(new BigDecimal(data.get(VOLUME)));
            stockData.setClose(new BigDecimal(data.get(CLOSE)));

            stockDatasSet.add(stockData);
        }

        return stockDatasSet;
    }

    private static Date toDate(String date) {
        String[] dateParts = date.split("-");
        if (dateParts.length != 3) {
            throw  new IllegalArgumentException("Has more than 2 '-': " + date);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(parseInt(dateParts[0]), parseInt(dateParts[1]) - 1, parseInt(dateParts[2]), 0, 0, 0);

        return calendar.getTime();
    }
}
