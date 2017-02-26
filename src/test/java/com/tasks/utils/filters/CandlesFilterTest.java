package com.tasks.utils.filters;

import com.core.api.yahoo.helpers.Constants;
import com.core.db.entity.Candle;
import org.junit.Test;

import java.util.*;

import static com.tasks.utils.TimeUtils.subtractDaysFromToday;
import static com.tasks.utils.TimeUtils.today;
import static com.tasks.utils.TimeUtils.yesterday;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

        for (int index = 0; index < mostRecentCandles.size(); index++) {
            int candlerDate = mostRecentCandles.get(index).getDate().get(Calendar.DATE);

            assertEquals(candlerDate, subtractDaysFromToday(index).get(Calendar.DATE));
        }
    }

    @Test
    public void candlesAreFilteredByPeriod() {
        Candle todaysCandle = new Candle();
        todaysCandle.setDate(today());
        Candle candleFromLastWeek = new Candle();
        candleFromLastWeek.setDate(subtractDaysFromToday(7));

        List<Candle> candles = Arrays.asList(todaysCandle, candleFromLastWeek);
        List<Candle> filtered = CandlesFilter.filterByDate(candles, yesterday(), today());

        assertEquals(filtered.size(), 1);
        assertTrue(filtered.contains(todaysCandle));
    }

    private List<Candle> generateCandles(int numberOfCandles) {
        List<Candle> candles = new ArrayList<>(numberOfCandles);

        for (int counter = 0; counter < numberOfCandles; counter++) {
            Candle candle = new Candle();
            candle.setDate(subtractDaysFromToday(counter));
            candles.add(candle);
        }

        Collections.shuffle(candles);

        return candles;
    }


}
