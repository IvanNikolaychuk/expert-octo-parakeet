package com.jobs.converter;

import com.core.api.dto.StockData;
import com.core.db.entity.Candle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class StockDataToCandleConverter {
    public static Candle convert(StockData stockData) {
        Candle candle = new Candle();

        candle.setDate(stockData.getDate());
        candle.setOpen(stockData.getOpen());
        candle.setClose(stockData.getClose());
        candle.setHigh(stockData.getHigh());
        candle.setLow(stockData.getLow());
        candle.setVolume(stockData.getVolume());

        return candle;
    }

    public static List<Candle> convert(List<StockData> stockDataList) {
        List<Candle> candles = new ArrayList<>();

        for (StockData stockData : stockDataList) {
            candles.add(convert(stockData));
        }

        return candles;
    }
}
