package com.tasks.utils;

import com.core.db.entity.Candle;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.tasks.utils.TimeUtils.today;

public class CandleUtilsTest {

    @Test
    public void calculateTotalGrowthNegativeTrend() {
        Candle first = createCandle(BigDecimal.TEN, BigDecimal.TEN);
        Candle second = createCandle(BigDecimal.TEN, BigDecimal.ZERO);

        List<Candle> candles = Arrays.asList(first, second);
        Assert.assertEquals(CandleUtils.calculateTotalProfit(candles), BigDecimal.TEN.negate());
    }

    @Test
    public void calculateTotalGrowthPositiveTrend() {
        Candle first = createCandle(BigDecimal.ZERO, BigDecimal.ONE);
        Candle second = createCandle(BigDecimal.ONE, BigDecimal.TEN);

        List<Candle> candles = Arrays.asList(first, second);
        Assert.assertEquals(CandleUtils.calculateTotalProfit(candles), BigDecimal.TEN);
    }

    @Test
    public void calculateTotalPercentageGrowthPositiveTrend() {
        Candle first = createCandle(BigDecimal.valueOf(100), BigDecimal.valueOf(100));
        Candle second = createCandle(BigDecimal.valueOf(90), BigDecimal.valueOf(120));

        Pair<Candle, Candle> candlePair = new Pair<>(first, second);
        Assert.assertEquals(CandleUtils.calculatePercentageProfit(candlePair), BigDecimal.valueOf(20));
    }

    @Test
    public void calculateTotalPercentageGrowthNegativeTrend() {
        Candle first = createCandle(BigDecimal.valueOf(100), BigDecimal.valueOf(100));
        Candle second = createCandle(BigDecimal.valueOf(90), BigDecimal.valueOf(80));

        Pair<Candle, Candle> candlePair = new Pair<>(first, second);
        Assert.assertEquals(CandleUtils.calculatePercentageProfit(candlePair), BigDecimal.valueOf(20).negate());
    }

    private Candle createCandle(BigDecimal open, BigDecimal close) {
        Candle candle = new Candle();
        candle.setOpen(open);
        candle.setClose(close);
        candle.setDate(today());

        return candle;
    }
}
