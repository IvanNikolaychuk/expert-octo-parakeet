package com.tasks.utils;

import com.core.db.entity.Candle;
import com.tasks.utils.filters.CandlesFilter;
import javafx.util.Pair;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;

public class CandleUtils {
    public static BigDecimal calculateTotalProfit(List<Candle> candles) {
        candles.sort(new CandlesFilter.OldDateFirstComparator());
        final Candle firstCandle = candles.get(0);
        final Candle lastCandle = candles.get(candles.size() - 1);

        return lastCandle.getClose().subtract(firstCandle.getOpen());
    }

    public static BigDecimal calculatePercentageProfit(Pair<Candle, Candle> candlePair) {
        List<Candle> candles = asList(candlePair.getKey(), candlePair.getValue());
        candles.sort(new CandlesFilter.OldDateFirstComparator());

        BigDecimal totalProfit = calculateTotalProfit(candles);

        return totalProfit.multiply(valueOf(100)).divide(getFirst(candles).getOpen(), RoundingMode.HALF_UP);
    }

    public static Candle getFirst(List<Candle> candles) {
        return candles.get(0);
    }

    public static Candle getLast(List<Candle> candles) {
        return candles.get(candles.size() - 1);
    }

}
