package com.stocks.technical.tasks.utils;

import com.stocks.technical.core.db.entity.Candle;
import com.stocks.technical.tasks.utils.filters.CandlesFilter.OldDateFirstComparator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static java.math.BigDecimal.valueOf;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.Collections.singletonList;

public class CandleUtils {
    public static BigDecimal calculateProfit(List<Candle> candles) {
        candles.sort(new OldDateFirstComparator());
        final Candle firstCandle = getFirst(candles);
        final Candle lastCandle = getLast(candles);

        return lastCandle.getClose().subtract(firstCandle.getOpen());
    }

    public static BigDecimal calculatePercentageProfit(List<Candle> candles) {
        candles.sort(new OldDateFirstComparator());

        BigDecimal totalProfit = calculateProfit(candles);

        return totalProfit
                .multiply(valueOf(100))
                .divide(getFirst(candles).getOpen(), RoundingMode.HALF_UP);
    }


    public static BigDecimal calculateGapPercentageProfit(List<Candle> candles) {
        if (candles.size() != 2) {
            throw new IllegalStateException("For gap calculation there should be erectly 2 candles");
        }
        candles.sort(new OldDateFirstComparator());

        BigDecimal totalProfit = calculateGapProfit(candles);

        return totalProfit
                .multiply(valueOf(100))
                .divide(getFirst(candles).getClose(), RoundingMode.HALF_UP);
    }

    private static BigDecimal calculateGapProfit(List<Candle> candles) {
        candles.sort(new OldDateFirstComparator());
        final Candle firstCandle = getFirst(candles);
        final Candle lastCandle = getLast(candles);

        return lastCandle.getOpen().subtract(firstCandle.getClose());
    }

    public static BigDecimal calculatePercentageProfit(Candle candle) {
        List<Candle> candles = singletonList(candle);

        return calculateProfit(candles)
                .multiply(valueOf(100))
                .divide(getFirst(candles).getOpen(), RoundingMode.HALF_UP);
    }


    public static BigDecimal calculatePercentageProfit(BigDecimal open, BigDecimal close) {

        return close.subtract(open)
                .multiply(valueOf(100))
                .divide(open, RoundingMode.HALF_UP);
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

    static List<Candle> getCandlesForMonthAndYear(List<Candle> candles, int month, int year) {
        candles = sort(candles, new OldDateFirstComparator());

        List<Candle> filtered = new ArrayList<>();

        for(Candle candle : candles) {
            Calendar candleDate = candle.getDate();
            if (candleDate.get(MONTH) ==  month && candleDate.get(YEAR) == year) {
                filtered.add(candle);
            }
        }


        if (filtered.isEmpty()) {
            throw new IllegalStateException("No first candle was found in month: " + month + " and year " + year);
        }
        filtered.sort(new OldDateFirstComparator());
        return filtered;
    }
}
