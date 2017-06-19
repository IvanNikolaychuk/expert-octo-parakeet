package com.stocks.technical.tasks.utils.converter;

import com.stocks.technical.core.api.dto.StockData;
import com.stocks.technical.core.db.entity.Candle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.stocks.technical.core.db.entity.Candle.Trend.*;
import static com.stocks.technical.tasks.utils.CandleUtils.calculatePercentageProfit;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class StockDataToCandleConverter {
    public static Candle convert(StockData stockData) {
        Candle candle = new Candle();

        candle.setVolume(stockData.getVolume());
        Calendar candleDate = Calendar.getInstance();
        candleDate.setTime(stockData.getDate());
        candle.setDate(candleDate);
        candle.setOpen(stockData.getOpen());
        candle.setClose(stockData.getClose());
        candle.setLow(stockData.getLow());
        candle.setHigh(stockData.getHigh());

        fillAnalyticsData(stockData, candle);

        return candle;
    }

    private static void fillAnalyticsData(StockData stockData, Candle candle) {
        BigDecimal body = stockData.getClose().subtract(stockData.getOpen());

        if (body.compareTo(BigDecimal.ZERO) == -1) {
            candle.setBody(body.negate());
            candle.setTrend(DOWN);
            candle.setUpperShadow(candle.getHigh().subtract(candle.getOpen()));
            candle.setLowerShadow(candle.getClose().subtract(candle.getLow()));
        } else {
            candle.setBody(body);
            candle.setTrend(UP);
            candle.setUpperShadow(candle.getHigh().subtract(candle.getClose()));
            candle.setLowerShadow(candle.getOpen().subtract(candle.getLow()));
        }

        candle.setPercentageProfit(calculatePercentageProfit(candle));
    }

    public static List<Candle> convert(List<StockData> stockDataList) {
        List<Candle> candles = new ArrayList<>();

        for (StockData stockData : stockDataList) {
            candles.add(convert(stockData));
        }

        return candles;
    }
}
