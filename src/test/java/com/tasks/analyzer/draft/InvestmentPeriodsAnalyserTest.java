package com.tasks.analyzer.draft;

import com.core.db.entity.Candle;
import com.core.db.entity.company.Company;
import com.core.db.entity.statistic.InvestmentPeriodData;
import com.tasks.analyzer.draft.InvestmentPeriodsAnalyser;
import com.tasks.utils.TimeUtils;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static com.core.api.helpers.Constants.MAX_DAYS_INVESTING;
import static com.core.db.entity.Candle.Trend.UP;
import static com.core.db.entity.company.BusinessType.PHARMACEUTICS;
import static com.core.db.entity.company.StockCurrency.DOLLAR;
import static com.tasks.utils.TimeUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class InvestmentPeriodsAnalyserTest {
    private InvestmentPeriodsAnalyser investmentPeriodsAnalyser;

    @Before
    public void setUp() throws Exception {
        investmentPeriodsAnalyser = new InvestmentPeriodsAnalyser();
    }

    @Test
    public void thereIsOnlyOneCombinationWhenNumberOfStocksIsLessThanMaxDaysInvestment() {
        List<Candle> candles = createCandles(MAX_DAYS_INVESTING - 5);

        Map<Pair<Calendar, Calendar>, List<Candle>> possiblePeriodCombinations
                = investmentPeriodsAnalyser.createPossibleCombinations(candles);

        assertEquals(possiblePeriodCombinations.size(), 1);

        for (Map.Entry<Pair<Calendar, Calendar>, List<Candle>> periodCandlesEntry : possiblePeriodCombinations.entrySet()) {
            Pair<Calendar, Calendar> period = periodCandlesEntry.getKey();
            assertTrue(isSameDay(subtractDaysFromToday(candles.size() - 1), period.getKey()));
            assertTrue(isSameDay(today(), period.getValue()));
            assertEquals(candles, periodCandlesEntry.getValue());
        }
    }

    @Test
    public void mostSuccessfulPeriodOpportunityTwoCandlesThatAreGoingDown_NullReturned() {
        Candle first = createCandle(subtractDaysFromToday(1), BigDecimal.ONE, BigDecimal.ONE);
        Candle second = createCandle(today(), BigDecimal.ZERO, BigDecimal.ZERO);

        Pair<Candle, Candle> pairs = investmentPeriodsAnalyser.findLongestSuccessfulPeriodOpportunity(Arrays.asList(first, second));

        Assert.assertNull(pairs);
    }

    @Test
    public void mostSuccessfulPeriodOpportunityTwoCandlesThatAreGoingUp() {
        Candle first = createCandle(subtractDaysFromToday(1), BigDecimal.ZERO, BigDecimal.ZERO);
        Candle second = createCandle(today(), BigDecimal.ONE, BigDecimal.ONE);

        Pair<Candle, Candle> pairs =
                investmentPeriodsAnalyser.findLongestSuccessfulPeriodOpportunity(Arrays.asList(second, first));

       Assert.assertEquals(pairs, new Pair<>(first, second));
    }

    @Test
    public void mostSuccessfulPeriodsAreCalculatedCorrectly() {
        Company company = new Company("Dummy", PHARMACEUTICS, DOLLAR);
        Map<Pair<Calendar, Calendar>, List<Candle>> periodCandles = new HashMap<>();

        List<Candle> candlesFirstPeriod = Arrays.asList(
                createCandle(subtractDaysFromToday(3), BigDecimal.valueOf(20), BigDecimal.valueOf(20)),
                createCandle(subtractDaysFromToday(2), BigDecimal.valueOf(10), BigDecimal.valueOf(10)),
                createCandle(subtractDaysFromToday(1), BigDecimal.valueOf(30), BigDecimal.valueOf(30)),
                createCandle(today(), BigDecimal.valueOf(40), BigDecimal.valueOf(40))
        );

        List<Candle> candlesSecondPeriod = Arrays.asList(
                createCandle(subtractDaysFromToday(4), BigDecimal.valueOf(10), BigDecimal.valueOf(10)),
                createCandle(subtractDaysFromToday(3), BigDecimal.valueOf(5), BigDecimal.valueOf(5)),
                createCandle(subtractDaysFromToday(2), BigDecimal.valueOf(20), BigDecimal.valueOf(20)),
                createCandle(subtractDaysFromToday(1), BigDecimal.valueOf(10), BigDecimal.valueOf(10))
        );

        periodCandles.put(new Pair<>(subtractDaysFromToday(3), today()), candlesFirstPeriod);
        periodCandles.put(new Pair<>(subtractDaysFromToday(4), subtractDaysFromToday(1)),
                candlesSecondPeriod);

        Set<InvestmentPeriodData> investmentPeriodDataList =
                investmentPeriodsAnalyser.computeMostSuccessfulPeriods(periodCandles, company.getName());

        assertEquals(investmentPeriodDataList.size(), 2);
        InvestmentPeriodData expectedFirstPeriodData =
                new InvestmentPeriodData("Dummy", subtractDaysFromToday(2), today(),
                        BigDecimal.valueOf(300), UP);

        InvestmentPeriodData expectedSecondPeriodData =
                new InvestmentPeriodData("Dummy", subtractDaysFromToday(3),
                        subtractDaysFromToday(2), BigDecimal.valueOf(300), UP);

        assertTrue(investmentPeriodDataList.contains(expectedFirstPeriodData));
        assertTrue(investmentPeriodDataList.contains(expectedSecondPeriodData));
    }

    @Test
    public void mostSuccessfulPeriodOpportunityTwoCandlesThatAreGoingUpLongestPeriod() {
        Candle todaysCandle = createCandle(today(), BigDecimal.TEN, BigDecimal.TEN);
        Candle yesterdaysCandle = createCandle(subtractDaysFromToday(1), BigDecimal.ONE, BigDecimal.ONE);
        Candle oldestCandle = createCandle(subtractDaysFromToday(2), BigDecimal.ZERO, BigDecimal.ZERO);

        Pair<Candle, Candle> pairs =
                investmentPeriodsAnalyser.findLongestSuccessfulPeriodOpportunity(Arrays.asList(todaysCandle, oldestCandle, yesterdaysCandle));

        Assert.assertEquals(pairs, new Pair<>(oldestCandle, todaysCandle));
    }

    private Candle createCandle(Calendar date, BigDecimal open, BigDecimal close) {
        Candle candle = new Candle();
        candle.setOpen(open);
        candle.setClose(close);
        candle.setDate(date);

        return candle;
    }

    @Test
    public void combinationsAreComputedCorrectlyWhenNumberOfStocksIsBiggerThanMaxDaysInvestment() {
        final int numberOfCandles = MAX_DAYS_INVESTING * 22;
        List<Candle> candles = createCandles(numberOfCandles);

        Map<Pair<Calendar, Calendar>, List<Candle>> possiblePeriodCombinations
                = investmentPeriodsAnalyser.createPossibleCombinations(candles);

        assertEquals(possiblePeriodCombinations.size(), numberOfCandles - MAX_DAYS_INVESTING + 1);
        assertNoDataContradictions(possiblePeriodCombinations);
        assertThereIsCandleForEachDay(possiblePeriodCombinations, candles);

    }

    private void assertThereIsCandleForEachDay(Map<Pair<Calendar, Calendar>, List<Candle>> possiblePeriodCombinations, List<Candle> candles) {
        Calendar shouldBeOldest = subtractDaysFromToday(candles.size());
        Calendar shouldBeYoungest = today();


        for(; isSameDay(shouldBeYoungest, shouldBeOldest); shouldBeYoungest.add(Calendar.DATE, 1)) {
            boolean candleExists = false;
            for(Pair<Calendar, Calendar> pairs : possiblePeriodCombinations.keySet()) {
                if (isSameDay(pairs.getKey(), shouldBeYoungest) || isSameDay(pairs.getKey(), shouldBeOldest)) {
                    candleExists = true;
                }
            }

            assertTrue(candleExists);
        }
    }


    private void assertNoDataContradictions(Map<Pair<Calendar, Calendar>, List<Candle>> possiblePeriodCombinations) {
        for (Map.Entry<Pair<Calendar, Calendar>, List<Candle>> periodCandlesEntry : possiblePeriodCombinations.entrySet()) {
            final Calendar from = periodCandlesEntry.getKey().getKey();
            final Calendar to = periodCandlesEntry.getKey().getValue();
            assertTrue(from.compareTo(to) < 0);

            for (Candle candle : periodCandlesEntry.getValue()) {
                Calendar calendarDate = candle.getDate();
                assertTrue(TimeUtils.between(calendarDate, from, to));
            }
        }
    }

    private List<Candle> createCandles(int numberOfCandles) {
        List<Candle> candles = new ArrayList<>(numberOfCandles);

        for (int index = 0; index < numberOfCandles; index++) {
            Candle candle = new Candle();
            candle.setDate(subtractDaysFromToday(index));
            candles.add(candle);
        }

        return candles;
    }
}
