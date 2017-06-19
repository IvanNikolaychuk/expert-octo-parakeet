package com.stocks.technical.core.api.xignite.converter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stocks.technical.core.api.dto.StockData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class XigniteJsonConverter {
    public static List<StockData> convert(String json) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/dd/yyyy");

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        JsonArray quotes = jsonObject.get("GlobalQuotes").getAsJsonArray();

        List<StockData> stockDataList = new ArrayList<>();
        if (quotes.size() == 0) {
            System.err.println("No data! " + json);
            return new ArrayList<>();
        }
        for (int i = 0; i < quotes.size(); i++) {
            StockData stockData = new StockData();
            JsonObject jsonStockData = quotes.get(i).getAsJsonObject();

            final String date = jsonStockData.get("Date").getAsString();
            try {
                stockData.date = simpleDateFormat.parse(date);
            } catch (ParseException e) {
                System.err.println("CANNOT PARSE DATE " + date);
                System.exit(0);
            }
            stockData.open = jsonStockData.get("Open").getAsBigDecimal();
            stockData.close = jsonStockData.get("LastClose").getAsBigDecimal();
            stockData.high = jsonStockData.get("High").getAsBigDecimal();
            stockData.low = jsonStockData.get("Low").getAsBigDecimal();
            stockData.volume = jsonStockData.get("Volume").getAsBigDecimal();
            stockDataList.add(stockData);
        }

        return stockDataList;
    }
}
