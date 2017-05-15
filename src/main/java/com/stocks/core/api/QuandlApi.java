package com.stocks.core.api;

import com.quandl.api.java.InvalidTokenException;
import com.quandl.api.java.QDataset;
import com.quandl.api.java.QEntry;
import com.quandl.api.java.QuandlConnection;
import com.stocks.core.api.yahoo.dto.StockData;
import com.stocks.core.api.yahoo.helpers.Period;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;

public class QuandlApi {
    private static final int OPEN = 1;
    private static final int HIGH = 2;
    private static final int LOW = 3;
    private static final int CLOSE = 4;
    private static final int VOLUME = 5;

    public static List<StockData> query(String companyName, Period period) {
        QuandlConnection q;
        try {
            q = QuandlConnection.getFullConnection("BxyFSeF3zsXjZbVErHyZ");
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
