package com.tasks.utils.filters;

import com.core.api.helpers.Constants;
import com.core.db.entity.Candle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class CandlesFilter {

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

    public static List<Candle> filter(List<Candle> candles, int year) {
        List<Candle> filtered = new ArrayList<>();

        for (Candle candle : candles) {
            Calendar date = candle.getDate();
            if (date.get(Calendar.YEAR) == year) {
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
}
