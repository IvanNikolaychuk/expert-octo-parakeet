package com.stocks.tasks.utils;

import com.stocks.core.db.entity.Candle;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import static com.stocks.tasks.helpers.CandleHelper.createTodaysCandle;
import static com.stocks.tasks.utils.CandleUtils.getCandlesForMonthAndYear;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CandleUtilsTest {

    @Test
    public void calculateTotalGrowthNegativeTrend() {
        Candle first = createTodaysCandle(BigDecimal.TEN, BigDecimal.TEN);
        Candle second = createTodaysCandle(BigDecimal.TEN, BigDecimal.ZERO);

        List<Candle> candles = asList(first, second);
        assertEquals(CandleUtils.calculateProfit(candles), BigDecimal.TEN.negate());
    }

    @Test
    public void calculateTotalGrowthPositiveTrend() {
        Candle first = createTodaysCandle(BigDecimal.ZERO, BigDecimal.ONE);
        Candle second = createTodaysCandle(BigDecimal.ONE, BigDecimal.TEN);

        List<Candle> candles = asList(first, second);
        assertEquals(CandleUtils.calculateProfit(candles), BigDecimal.TEN);
    }

    @Test
    public void calculateTotalPercentageGrowthPositiveTrend() {
        Candle first = createTodaysCandle(BigDecimal.valueOf(100), BigDecimal.valueOf(100));
        Candle second = createTodaysCandle(BigDecimal.valueOf(90), BigDecimal.valueOf(120));

        assertEquals(CandleUtils.calculatePercentageProfit(asList(first, second)), BigDecimal.valueOf(20));
    }

    @Test
    public void calculateTotalPercentageGrowthPositiveTrend_threeCandles() {
        Candle first = createTodaysCandle(BigDecimal.valueOf(100), BigDecimal.valueOf(100));
        Candle second = createTodaysCandle(BigDecimal.valueOf(90), BigDecimal.valueOf(120));
        Candle third = createTodaysCandle(BigDecimal.valueOf(90), BigDecimal.valueOf(150));

        assertEquals(CandleUtils.calculatePercentageProfit(asList(first, second, third)), BigDecimal.valueOf(50));
    }

    @Test
    public void calculateTotalPercentageGrowthNegativeTrend() {
        Candle first = createTodaysCandle(BigDecimal.valueOf(100), BigDecimal.valueOf(100));
        Candle second = createTodaysCandle(BigDecimal.valueOf(90), BigDecimal.valueOf(80));

        assertEquals(CandleUtils.calculatePercentageProfit(asList(first, second)), BigDecimal.valueOf(20).negate());
    }

    @Test
    public void calculateTotalPercentageGrowthOneCandle() {
        Candle candle = createTodaysCandle(BigDecimal.ONE, BigDecimal.TEN);

        assertEquals(CandleUtils.calculatePercentageProfit(candle), BigDecimal.valueOf(900));
    }

    @Test
    public void firstAndLastMonthCandlesAreFoundCorrectlyWhenDataExistsOnlyForCurrentMonth() {
        Candle firstCandle = buildCandle(2017, Calendar.FEBRUARY, 1);
        firstCandle.setId(1);
        Candle secondCandle = buildCandle(2017, Calendar.FEBRUARY, 2);
        secondCandle.setId(2);
        List<Candle> filtered = getCandlesForMonthAndYear(asList(firstCandle, secondCandle), Calendar.FEBRUARY, 2017);

        assertEquals(filtered.size(), 2);
        assertTrue(filtered.contains(firstCandle));
        assertTrue(filtered.contains(secondCandle));
    }

    @Test
    public void firstAndLastMonthCandlesAreFoundCorrectlyWhenDataExistsOnlyForSeveralMonths() {
        Candle firstCandle = buildCandle(2017, Calendar.FEBRUARY, 1);
        Candle secondCandle = buildCandle(2017, Calendar.FEBRUARY, 2);
        Candle thirdCandle = buildCandle(2017, Calendar.MARCH, 2);

        List<Candle> filtered = getCandlesForMonthAndYear(asList(thirdCandle, firstCandle, secondCandle), Calendar.FEBRUARY, 2017);
        assertEquals(filtered.size(), 2);
        assertTrue(filtered.contains(firstCandle));
        assertTrue(filtered.contains(secondCandle));
    }

    public Candle buildCandle(int year, int month, int day) {
        Candle candle = new Candle();
        Calendar date = Calendar.getInstance();
        date.set(year, month, day);
        candle.setDate(date);

        return candle;
    }
}
