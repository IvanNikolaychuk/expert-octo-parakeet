package com.jobs.analyzer.filters;

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

        candles.sort(new ByDateComparator());

        List<Candle> mostRecent = new ArrayList<>(Constants.RECENT_CANDLES_NUMBER);

        for (int i = 0; i < Constants.RECENT_CANDLES_NUMBER; i++) {
            mostRecent.add(candles.get(i));
        }

        return mostRecent;
    }

    public static List<Candle> filter(List<Candle> candles, int year) {
        List<Candle> filtered = new ArrayList<>();

        for (Candle candle : candles) {
            Calendar date = candle.getCalendar();
            if (date.get(Calendar.YEAR) == year) {
                filtered.add(candle);
            }
        }

        return filtered;
    }

    public static class ByDateComparator implements Comparator<Candle> {

        @Override
        public int compare(Candle first, Candle second) {
            return first.getDate().compareTo(second.getDate());
        }
    }
}
