package com.tasks.analyzer.drafts.algorithms;

import com.core.db.entity.Candle;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static com.tasks.helpers.CandleHelper.createTodaysCandle;
import static com.tasks.helpers.CandleHelper.createYesterdaysCandle;

public class GapFallCandleAlgorithmTest {

    @Test
    public void strongFallDetected_slightFall() {
        Candle yesterdays = createYesterdaysCandle(BigDecimal.valueOf(100), BigDecimal.valueOf(50));
        Candle todays = createTodaysCandle(BigDecimal.valueOf(50), BigDecimal.valueOf(60));

        Assert.assertFalse(StrongGapFallCandleAlgorithm.isStrongGapFallCandle(yesterdays, todays));
    }

    @Test
    public void strongFallDetected_sharpFall() {
        Candle yesterdays = createYesterdaysCandle(BigDecimal.valueOf(100), BigDecimal.valueOf(100));
        Candle todays = createTodaysCandle(BigDecimal.valueOf(50), BigDecimal.valueOf(25));
        todays.setTrend(Candle.Trend.DOWN);
        Assert.assertTrue(StrongGapFallCandleAlgorithm.isStrongGapFallCandle(yesterdays, todays));
    }

    @Test
    public void strongFallDetected_andGrowthAfter_NotStrongFall() {
        Candle yesterdays = createYesterdaysCandle(BigDecimal.valueOf(100), BigDecimal.valueOf(100));
        Candle todays = createTodaysCandle(BigDecimal.valueOf(50), BigDecimal.valueOf(55));
        todays.setTrend(Candle.Trend.UP);
        Assert.assertFalse(StrongGapFallCandleAlgorithm.isStrongGapFallCandle(yesterdays, todays));
    }

    @Test
    public void noStrongFall_WhenNoFall() {
        Candle yesterdays = createYesterdaysCandle(BigDecimal.valueOf(100), BigDecimal.valueOf(100));
        Candle todays = createTodaysCandle(BigDecimal.valueOf(100), BigDecimal.valueOf(100));

        Assert.assertFalse(StrongGapFallCandleAlgorithm.isStrongGapFallCandle(yesterdays, todays));
    }
}
