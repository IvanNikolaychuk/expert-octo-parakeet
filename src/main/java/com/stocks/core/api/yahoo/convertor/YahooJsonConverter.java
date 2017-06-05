package com.stocks.core.api.yahoo.convertor;

import com.google.gson.*;
import com.stocks.core.api.dto.StockData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class YahooJsonConverter {
    public static List<StockData> convert(String json) {
        JsonObject root = new JsonParser().parse(json).getAsJsonObject();

        JsonObject financeData = extractFinanceData(root);
        List<Date> dates = extractDates(root);
        List<BigDecimal> opens = extract(financeData, "open");
        List<BigDecimal> closes = extract(financeData, "close");
        List<BigDecimal> highs = extract(financeData, "high");
        List<BigDecimal> lows = extract(financeData, "low");
        List<BigDecimal> volumes = extract(financeData, "volume");

        List<StockData> stockDataList = new ArrayList<>();

        for (int i = 0; i < dates.size(); i++) {
            StockData stockData = new StockData();
            stockData.date = dates.get(i);
            stockData.open = opens.get(i);
            stockData.close = closes.get(i);
            stockData.low = lows.get(i);
            stockData.high = highs.get(i);
            stockData.volume = volumes.get(i);

            stockDataList.add(stockData);
        }

        return stockDataList;
    }

    private static List<Date> extractDates(JsonObject root) {
        List<Date> dates = new ArrayList<>();

        JsonArray calendarsJson = root.get("chart").getAsJsonObject()
                .get("result").getAsJsonArray().get(0)
                .getAsJsonObject().get("timestamp").getAsJsonArray();


        for (int i = 0; i < calendarsJson.size(); i++) {
            dates.add(new Date(calendarsJson.get(i).getAsLong() * 1000));
        }

        return dates;
    }


    private static List<BigDecimal> extract(JsonObject financeData, String elemName) {
        List<BigDecimal> elements = new ArrayList<>();

        JsonArray jsonArray = financeData.get(elemName).getAsJsonArray();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement jsonElement = jsonArray.get(i);
            if (jsonElement.getClass() == JsonNull.class) {
                elements.add(BigDecimal.valueOf(-1000));
            } else {
                elements.add(jsonElement.getAsBigDecimal());
            }
        }

        return elements;
    }

    private static JsonObject extractFinanceData(JsonObject root) {
        return root.get("chart").getAsJsonObject()
                .get("result").getAsJsonArray()
                .get(0).getAsJsonObject()
                .get("indicators").getAsJsonObject()
                .get("quote").getAsJsonArray()
                .get(0).getAsJsonObject();
    }

}
