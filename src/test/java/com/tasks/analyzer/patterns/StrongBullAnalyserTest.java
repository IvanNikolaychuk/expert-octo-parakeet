package com.tasks.analyzer.patterns;

import com.core.db.entity.Candle;
import com.core.db.entity.statistic.StrongBullStatisticData;
import com.tasks.analyzer.drafts.helpers.CandleByDateSequence;
import com.tasks.helpers.CandleHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static com.tasks.analyzer.patterns.StrongBullStatisticDataAnalyser.Movement;
import static com.tasks.analyzer.patterns.StrongBullStatisticDataAnalyser.Movement.BACK;
import static com.tasks.analyzer.patterns.StrongBullStatisticDataAnalyser.Movement.FORWARD;
import static com.tasks.utils.TimeUtils.subtractDaysFromToday;
import static java.math.BigDecimal.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

public class StrongBullAnalyserTest {
    private StrongBullStatisticDataAnalyser strongBullStatisticDataAnalyser;

    @Before
    public void setUp() {
        strongBullStatisticDataAnalyser = new StrongBullStatisticDataAnalyser();
    }

    @Test
    public void afterTrendReturnsNullWhenNoDataMovement() {
        Candle yestredays = CandleHelper.createYesterdaysCandle(ONE, ONE);
        Candle todays = CandleHelper.createTodaysCandle(ONE, ONE);
        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(Arrays.asList(yestredays, todays));

        StrongBullStatisticData strongBullStatisticData = new StrongBullStatisticData();

        strongBullStatisticDataAnalyser.analyseTrend(yestredays, candleByDateSequence, strongBullStatisticData, Movement.FORWARD);

        Assert.assertNull(strongBullStatisticData.getAfterDays());
        Assert.assertNull(strongBullStatisticData.getAfterPercentageProfit());
    }


    @Test
    public void afterTrendReturnsPositiveStatisticWhenPriceMovingUp() {
        Candle yestredays = CandleHelper.createYesterdaysCandle(ONE, ONE);
        Candle todays = CandleHelper.createTodaysCandle(ONE, ONE.multiply(valueOf(2)));
        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(Arrays.asList(yestredays, todays));

        StrongBullStatisticData strongBullStatisticData = new StrongBullStatisticData();

        strongBullStatisticDataAnalyser.analyseTrend(yestredays, candleByDateSequence, strongBullStatisticData, Movement.FORWARD);

        Assert.assertEquals(2, strongBullStatisticData.getAfterDays().intValue());
        Assert.assertEquals(valueOf(100), strongBullStatisticData.getAfterPercentageProfit());
    }

    @Test
    public void beforeTrendReturnsPositiveStatisticWhenPriceMovingUp() {
        Candle yestredays = CandleHelper.createYesterdaysCandle(ONE, ONE);
        Candle todays = CandleHelper.createTodaysCandle(TEN, TEN);
        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(Arrays.asList(yestredays, todays));
        candleByDateSequence.setCurrent(todays);

        StrongBullStatisticData strongBullStatisticData = new StrongBullStatisticData();

        strongBullStatisticDataAnalyser.analyseTrend(todays, candleByDateSequence, strongBullStatisticData, BACK);

        Assert.assertEquals(1, strongBullStatisticData.getBeforeDays().intValue());
        Assert.assertEquals(valueOf(900), strongBullStatisticData.getBeforePercentageProfit());
    }

    @Test
    public void beforeTrendReturnsNegativeStatisticWhenPriceMovingDown() {
        Candle yestredays = CandleHelper.createYesterdaysCandle(TEN, valueOf(9));
        Candle todays = CandleHelper.createTodaysCandle(ONE, ONE);
        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(Arrays.asList(yestredays, todays));
        candleByDateSequence.setCurrent(todays);

        StrongBullStatisticData strongBullStatisticData = new StrongBullStatisticData();

        strongBullStatisticDataAnalyser.analyseTrend(todays, candleByDateSequence, strongBullStatisticData, BACK);

        Assert.assertEquals(1, strongBullStatisticData.getBeforeDays().intValue());
        Assert.assertEquals(valueOf(90).negate(), strongBullStatisticData.getBeforePercentageProfit());
    }

    @Test
    public void afterTrendReturnsNegativeStatisticWhenPriceMovingDown() {
        Candle yestredays = CandleHelper.createYesterdaysCandle(TEN, TEN);
        Candle todays = CandleHelper.createTodaysCandle(valueOf(2), valueOf(2));
        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(Arrays.asList(yestredays, todays));

        StrongBullStatisticData strongBullStatisticData = new StrongBullStatisticData();

        strongBullStatisticDataAnalyser.analyseTrend(yestredays, candleByDateSequence, strongBullStatisticData, Movement.FORWARD);

        Assert.assertEquals(2, strongBullStatisticData.getAfterDays().intValue());
        Assert.assertEquals(valueOf(80).negate(), strongBullStatisticData.getAfterPercentageProfit());
    }


    @Test
    public void analyseBeginsFromNeighboringCandles() {
        Candle todays = CandleHelper.createTodaysCandle();
        Candle yesterdaysStrongBull = CandleHelper.createYesterdaysCandle();
        Candle twoDaysAgo = new Candle();
        twoDaysAgo.setDate(subtractDaysFromToday(2));

        strongBullStatisticDataAnalyser = mock(StrongBullStatisticDataAnalyser.class);
        doCallRealMethod().when(strongBullStatisticDataAnalyser).analyse(anyList(), anyList());
        strongBullStatisticDataAnalyser.analyse(Arrays.asList(twoDaysAgo, yesterdaysStrongBull, todays), Arrays.asList(yesterdaysStrongBull));
        Mockito.verify(strongBullStatisticDataAnalyser).analyseTrend(eq(todays), any(), any(), eq(FORWARD));
        Mockito.verify(strongBullStatisticDataAnalyser).analyseTrend(eq(twoDaysAgo), any(), any(), eq(BACK));
    }
}
