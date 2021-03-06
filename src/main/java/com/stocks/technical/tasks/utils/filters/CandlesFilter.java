package com.stocks.technical.tasks.utils.filters;

import com.stocks.technical.core.api.helpers.Constants;
import com.stocks.technical.core.db.entity.Candle;
import com.stocks.technical.tasks.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class CandlesFilter {

    public static List<Candle> filterByPattern(List<Candle> candles, Candle.Pattern... patterns) {
        List<Candle> filtered = new ArrayList<>();

        for (Candle candle : candles) {
            for (Candle.Pattern pattern : patterns) {
                if (candle.getPattern() == pattern) {
                    filtered.add(candle);
                    break;
                }
            }
        }

        return filtered;
    }

    public static List<Candle> filterMostRecent(List<Candle> candles) {
        if (candles.size() <= Constants.RECENT_CANDLES_NUMBER) {
            return candles;
        }

        candles.sort(new RecentDateFirstComparator());

        List<Candle> mostRecent = new ArrayList<>(Constants.RECENT_CANDLES_NUMBER);

        for (int i = 0; i < Constants.RECENT_CANDLES_NUMBER; i++) {
            mostRecent.add(candles.get(i));
        }

        return mostRecent;
    }

    public static List<Candle> filterByYear(List<Candle> candles, int year) {
        List<Candle> filtered = new ArrayList<>();

        for (Candle candle : candles) {
            Calendar date = candle.getDate();
            if (date.get(Calendar.YEAR) == year) {
                filtered.add(candle);
            }
        }

        return filtered;
    }

    public static List<Candle> filterByDate(List<Candle> candles, Calendar from, Calendar to) {
        List<Candle> filtered = new ArrayList<>();

        for (Candle candle : candles) {
            if (TimeUtils.between(candle.getDate(), from, to)) {
                filtered.add(candle);
            }
        }

        return filtered;
    }

    public static class RecentDateFirstComparator implements Comparator<Candle> {

        @Override
        public int compare(Candle first, Candle second) {
            return second.getDate().compareTo(first.getDate());
        }
    }

    public static class OldDateFirstComparator implements Comparator<Candle> {

        @Override
        public int compare(Candle first, Candle second) {
            return (-1) * new RecentDateFirstComparator().compare(first, second);
        }
    }

    public static class HighVolumeFirstComparator implements Comparator<Candle> {

        @Override
        public int compare(Candle first, Candle second) {
            return second.getVolume().compareTo(first.getVolume());
        }
    }
}
