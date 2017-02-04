package com.tasks.analyzer.filters;

import com.core.api.helpers.Constants;
import com.core.db.entity.Candle;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class CandlesFilterTest {

    @Test
    public void allCandlesAreReturned_WhenThereAreALittleOfCandles() {
        List<Candle> candles = generateCandles(Constants.RECENT_CANDLES_NUMBER);
        List<Candle> mostRecentCandles = CandlesFilter.filterMostRecent(candles);

        assertEquals(candles, mostRecentCandles);
    }

    @Test
    public void candlesAreFilteredByDay_WhenThereAreALotOfCandles() {
        List<Candle> candles = generateCandles(Constants.RECENT_CANDLES_NUMBER * 3);

        List<Candle> mostRecentCandles = CandlesFilter.filterMostRecent(candles);

        assertEquals(mostRecentCandles.size(), Constants.RECENT_CANDLES_NUMBER);

        for (int i = 0; i < mostRecentCandles.size(); i++) {
            int candlerDate = mostRecentCandles.get(i).getCalendar().get(Calendar.DATE);

            Calendar expectedDate = Calendar.getInstance();
            expectedDate.setTime(subtractDays(new Date(), i));

            Assert.assertEquals(candlerDate, expectedDate.get(Calendar.DATE));
        }
    }

    private List<Candle> generateCandles(int numberOfCandles) {
        List<Candle> candles = new ArrayList<>(numberOfCandles);

        Date today = new Date();
        for (int counter = 0; counter < numberOfCandles; counter++) {
            Candle candle = new Candle();
            candle.setDate(subtractDays(today, counter));
            candles.add(candle);
        }

        Collections.shuffle(candles);

        return candles;
    }

    private Date subtractDays(Date date, int numberOfDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, numberOfDays * (-1));

        return calendar.getTime();
    }
}
