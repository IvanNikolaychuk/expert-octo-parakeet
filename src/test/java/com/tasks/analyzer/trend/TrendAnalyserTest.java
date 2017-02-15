package com.tasks.analyzer.trend;

import com.core.db.entity.Candle;
import com.tasks.analyzer.helpers.CandleByDateSequence;
import com.tasks.helpers.CandleHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static com.tasks.analyzer.trend.Movement.BACK;
import static com.tasks.analyzer.trend.Movement.FORWARD;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;

public class TrendAnalyserTest {
    @Test
    public void afterTrendReturnsNullWhenNoDataMovement() {
        Candle yestredays = CandleHelper.createYesterdaysCandle(ONE, ONE);
        Candle todays = CandleHelper.createTodaysCandle(ONE, ONE);
        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(Arrays.asList(yestredays, todays));

        TrendData trendData = new TrendAnalyser().analyseTrend(yestredays, candleByDateSequence, FORWARD);

        Assert.assertNull(trendData.getNumberOfDays());
        Assert.assertNull(trendData.getPercentageProfit());
    }


    @Test
    public void afterTrendReturnsPositiveStatisticWhenPriceMovingUp() {
        Candle yestredays = CandleHelper.createYesterdaysCandle(ONE, ONE);
        Candle todays = CandleHelper.createTodaysCandle(ONE, ONE.multiply(valueOf(2)));
        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(Arrays.asList(yestredays, todays));

        TrendData trendData = new TrendAnalyser().analyseTrend(yestredays, candleByDateSequence, FORWARD);

        Assert.assertEquals(2, trendData.getNumberOfDays().intValue());
        Assert.assertEquals(valueOf(100), trendData.getPercentageProfit());
    }

    @Test
    public void beforeTrendReturnsPositiveStatisticWhenPriceMovingUp() {
        Candle yestredays = CandleHelper.createYesterdaysCandle(ONE, ONE);
        Candle todays = CandleHelper.createTodaysCandle(TEN, TEN);
        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(Arrays.asList(yestredays, todays));
        candleByDateSequence.setCurrent(todays);

        TrendData trendData = new TrendAnalyser().analyseTrend(todays, candleByDateSequence, BACK);

        Assert.assertEquals(1, trendData.getNumberOfDays().intValue());
        Assert.assertEquals(valueOf(900), trendData.getPercentageProfit());
    }

    @Test
    public void beforeTrendReturnsNegativeStatisticWhenPriceMovingDown() {
        Candle yestredays = CandleHelper.createYesterdaysCandle(TEN, valueOf(9));
        Candle todays = CandleHelper.createTodaysCandle(ONE, ONE);
        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(Arrays.asList(yestredays, todays));
        candleByDateSequence.setCurrent(todays);

        TrendData trendData = new TrendAnalyser().analyseTrend(todays, candleByDateSequence, BACK);

        Assert.assertEquals(1, trendData.getNumberOfDays().intValue());
        Assert.assertEquals(valueOf(90).negate(), trendData.getPercentageProfit());
    }

    @Test
    public void afterTrendReturnsNegativeStatisticWhenPriceMovingDown() {
        Candle yestredays = CandleHelper.createYesterdaysCandle(TEN, TEN);
        Candle todays = CandleHelper.createTodaysCandle(valueOf(2), valueOf(2));
        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(Arrays.asList(yestredays, todays));

        TrendData trendData = new TrendAnalyser().analyseTrend(yestredays, candleByDateSequence, FORWARD);

        Assert.assertEquals(2, trendData.getNumberOfDays().intValue());
        Assert.assertEquals(valueOf(80).negate(), trendData.getPercentageProfit());
    }

}
