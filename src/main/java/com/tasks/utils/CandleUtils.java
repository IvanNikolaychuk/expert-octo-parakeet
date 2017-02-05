package com.tasks.utils;

import com.core.db.entity.Candle;
import com.tasks.utils.filters.CandlesFilter;
import javafx.util.Pair;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class CandleUtils {
    public static BigDecimal calculateTotalProfit(List<Candle> candles) {
        candles.sort(new CandlesFilter.OldDateFirstComparator());
        final Candle firstCandle = getFirst(candles);
        final Candle lastCandle = getLast(candles);

        return lastCandle.getClose().subtract(firstCandle.getOpen());
    }

    public static BigDecimal calculatePercentageProfit(List<Candle> candles) {
        candles.sort(new CandlesFilter.OldDateFirstComparator());

        BigDecimal totalProfit = calculateTotalProfit(candles);

        return totalProfit
                .multiply(valueOf(100))
                .divide(getFirst(candles).getOpen(), RoundingMode.HALF_UP);
    }

    public static BigDecimal calculatePercentageProfit(Candle candle) {
        List<Candle> candles = singletonList(candle);
        return calculateTotalProfit(candles)
                .multiply(valueOf(100))
                .divide(getFirst(candles).getOpen(), RoundingMode.HALF_UP);
    }

    public static List<Candle> sort(List<Candle> candles, Comparator<Candle> comparator) {
        candles.sort(comparator);
        return candles;
    }

    public static Candle getFirst(List<Candle> candles) {
        return candles.get(0);
    }

    public static Candle getLast(List<Candle> candles) {
        return candles.get(candles.size() - 1);
    }

}
