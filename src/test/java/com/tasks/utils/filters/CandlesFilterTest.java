package com.tasks.utils.filters;

import com.core.api.helpers.Constants;
import com.core.db.entity.Candle;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static com.tasks.utils.TimeUtils.subtractDaysFromToday;
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

        for (int index = 0; index < mostRecentCandles.size(); index++) {
            int candlerDate = mostRecentCandles.get(index).getDate().get(Calendar.DATE);

            Assert.assertEquals(candlerDate, subtractDaysFromToday(index).get(Calendar.DATE));
        }
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
