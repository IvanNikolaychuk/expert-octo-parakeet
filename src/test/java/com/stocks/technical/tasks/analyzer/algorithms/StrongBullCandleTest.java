package com.stocks.technical.tasks.analyzer.algorithms;

import com.stocks.technical.core.api.helpers.Constants;
import com.stocks.technical.core.db.entity.Candle;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static com.stocks.technical.core.db.entity.Candle.Trend.DOWN;
import static com.stocks.technical.tasks.helpers.CandleHelper.*;
import static java.math.BigDecimal.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StrongBullCandleTest {

    @Test
    public void negativeTrend_NotStrongBull() {
        Candle target = createTodaysCandle();
        target.setTrend(DOWN);

        assertFalse(StrongBullCandleAlgorithm.isStrongBullCandle(target));
    }

    @Test
    public void candleIsStrongBull() {
        Candle candleBefore = createYesterdaysCandleWithUpperTrend(valueOf(2), valueOf(2));
        Candle target = createTodaysCandleWithUpperTrend(valueOf(2), valueOf(12));
        target.setLowerShadow(BigDecimal.valueOf(1));
        target.setUpperShadow(BigDecimal.valueOf(1));

        assertTrue(StrongBullCandleAlgorithm.isStrongBullCandle(target));
    }

    @Test
    public void shadowsAreLow() {
        Candle candle = createYesterdaysCandleWithUpperTrend(ONE, valueOf(2));
        candle.setUpperShadow(valueOf(10));
        candle.setLowerShadow(valueOf(10));

        assertFalse(StrongBullCandleAlgorithm.shadowsAreLow(candle));
    }

    @Test
    public void shadowPercentagesAreCalculatedCorrectly() {
        Assert.assertEquals(StrongBullCandleAlgorithm.calculateShadowPercentage(valueOf(100), TEN), TEN);
    }

    @Test
    public void percentageProfitIsBig() {
        BigDecimal open = valueOf(100);
        BigDecimal close = open.add(Constants.MIN_ACCEPTED_GROW_PERCENTAGE_FOR_STRONG_BULL_CANDLE).subtract(ONE);

        Candle target = createTodaysCandleWithUpperTrend(valueOf(100), close);

        assertFalse(StrongBullCandleAlgorithm.percentageProfitIsBig(target));
    }

}
