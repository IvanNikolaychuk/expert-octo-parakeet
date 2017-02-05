package com.tasks.utils;

import com.core.db.entity.Candle;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.tasks.helpers.CandleHelper.createTodaysCandle;

public class CandleUtilsTest {

    @Test
    public void calculateTotalGrowthNegativeTrend() {
        Candle first = createTodaysCandle(BigDecimal.TEN, BigDecimal.TEN);
        Candle second = createTodaysCandle(BigDecimal.TEN, BigDecimal.ZERO);

        List<Candle> candles = Arrays.asList(first, second);
        Assert.assertEquals(CandleUtils.calculateTotalProfit(candles), BigDecimal.TEN.negate());
    }

    @Test
    public void calculateTotalGrowthPositiveTrend() {
        Candle first = createTodaysCandle(BigDecimal.ZERO, BigDecimal.ONE);
        Candle second = createTodaysCandle(BigDecimal.ONE, BigDecimal.TEN);

        List<Candle> candles = Arrays.asList(first, second);
        Assert.assertEquals(CandleUtils.calculateTotalProfit(candles), BigDecimal.TEN);
    }

    @Test
    public void calculateTotalPercentageGrowthPositiveTrend() {
        Candle first = createTodaysCandle(BigDecimal.valueOf(100), BigDecimal.valueOf(100));
        Candle second = createTodaysCandle(BigDecimal.valueOf(90), BigDecimal.valueOf(120));

        Assert.assertEquals(CandleUtils.calculatePercentageProfit(Arrays.asList(first, second)), BigDecimal.valueOf(20));
    }

    @Test
    public void calculateTotalPercentageGrowthPositiveTrend_threeCandles() {
        Candle first = createTodaysCandle(BigDecimal.valueOf(100), BigDecimal.valueOf(100));
        Candle second = createTodaysCandle(BigDecimal.valueOf(90), BigDecimal.valueOf(120));
        Candle third = createTodaysCandle(BigDecimal.valueOf(90), BigDecimal.valueOf(150));

        Assert.assertEquals(CandleUtils.calculatePercentageProfit(Arrays.asList(first, second, third)), BigDecimal.valueOf(50));
    }

    @Test
    public void calculateTotalPercentageGrowthNegativeTrend() {
        Candle first = createTodaysCandle(BigDecimal.valueOf(100), BigDecimal.valueOf(100));
        Candle second = createTodaysCandle(BigDecimal.valueOf(90), BigDecimal.valueOf(80));

        Assert.assertEquals(CandleUtils.calculatePercentageProfit(Arrays.asList(first, second)), BigDecimal.valueOf(20).negate());
    }

    @Test
    public void calculateTotalPercentageGrowthOneCandle() {
        Candle candle = createTodaysCandle(BigDecimal.ONE, BigDecimal.TEN);

        Assert.assertEquals(CandleUtils.calculatePercentageProfit(candle), BigDecimal.valueOf(900));
    }

}
