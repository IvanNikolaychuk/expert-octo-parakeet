package com.tasks.utils.filters;

import com.core.db.entity.Candle;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class HighVolumeFirstComparatorTest {

    @Test
    public void candlesAreOrderedCorrectly() {
        Candle highVolume = candleWithVolume(100);
        Candle lowVolume = candleWithVolume(10);
        Candle avgVolume = candleWithVolume(50);

        List<Candle> candles = Arrays.asList(highVolume, avgVolume, lowVolume);

        candles.sort(new CandlesFilter.HighVolumeFirstComparator());

        Assert.assertEquals(candles.get(0), highVolume);
        Assert.assertEquals(candles.get(1), avgVolume);
        Assert.assertEquals(candles.get(2), lowVolume);
    }

    public static Candle candleWithVolume(double volume) {
        Candle candle = new Candle();
        candle.setVolume(BigDecimal.valueOf(volume));

        return candle;
    }
}
