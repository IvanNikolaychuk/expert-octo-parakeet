package com.stocks.tasks.analyzer.algorithms;

import com.stocks.core.db.entity.Candle;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static com.stocks.tasks.analyzer.algorithms.StrongGapFallCandleAlgorithm.*;
import static com.stocks.tasks.analyzer.algorithms.StrongGapRiseCandleAlgorithm.isStrongGapGrowCandle;
import static com.stocks.tasks.helpers.CandleHelper.createTodaysCandle;
import static com.stocks.tasks.helpers.CandleHelper.createYesterdaysCandle;

public class GapFallCandleAlgorithmTest {

    @Test
    public void strongFallDetected_slightFall() {
        Candle yesterdays = createYesterdaysCandle(BigDecimal.valueOf(100), BigDecimal.valueOf(50));
        Candle todays = createTodaysCandle(BigDecimal.valueOf(50), BigDecimal.valueOf(60));

        Assert.assertFalse(isStrongGapFallCandle(yesterdays, todays));
    }

    @Test
    public void strongGrowDetected_slightGrow() {
        Candle yesterdays = createYesterdaysCandle(BigDecimal.valueOf(50), BigDecimal.valueOf(100));
        Candle todays = createTodaysCandle(BigDecimal.valueOf(100), BigDecimal.valueOf(105));

        Assert.assertFalse(isStrongGapGrowCandle(yesterdays, todays));
    }


    @Test
    public void strongFallDetected_sharpFall() {
        Candle yesterdays = createYesterdaysCandle(BigDecimal.valueOf(100), BigDecimal.valueOf(100));
        Candle todays = createTodaysCandle(BigDecimal.valueOf(50), BigDecimal.valueOf(25));
        todays.setTrend(Candle.Trend.DOWN);
        Assert.assertTrue(isStrongGapFallCandle(yesterdays, todays));
    }

    @Test
    public void strongGrowDetected_sharpGrow() {
        Candle yesterdays = createYesterdaysCandle(BigDecimal.valueOf(100), BigDecimal.valueOf(100));
        Candle todays = createTodaysCandle(BigDecimal.valueOf(150), BigDecimal.valueOf(175));
        todays.setTrend(Candle.Trend.UP);
        Assert.assertTrue(isStrongGapGrowCandle(yesterdays, todays));
    }

    @Test
    public void strongFallDetected_andGrowthAfter_NotStrongFall() {
        Candle yesterdays = createYesterdaysCandle(BigDecimal.valueOf(100), BigDecimal.valueOf(100));
        Candle todays = createTodaysCandle(BigDecimal.valueOf(50), BigDecimal.valueOf(55));
        todays.setTrend(Candle.Trend.UP);
        Assert.assertFalse(isStrongGapFallCandle(yesterdays, todays));
    }

    @Test
    public void noStrongFall_WhenNoFall() {
        Candle yesterdays = createYesterdaysCandle(BigDecimal.valueOf(100), BigDecimal.valueOf(100));
        Candle todays = createTodaysCandle(BigDecimal.valueOf(100), BigDecimal.valueOf(100));

        Assert.assertFalse(isStrongGapFallCandle(yesterdays, todays));
    }
}
