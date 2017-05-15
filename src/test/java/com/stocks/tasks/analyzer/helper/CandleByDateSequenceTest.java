package com.stocks.tasks.analyzer.helper;

import com.stocks.core.db.entity.Candle;
import com.stocks.tasks.analyzer.helpers.CandleByDateSequence;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.stocks.tasks.helpers.CandleHelper.createTodaysCandle;
import static com.stocks.tasks.helpers.CandleHelper.createYesterdaysCandle;
import static java.util.Collections.singletonList;
import static org.junit.Assert.*;

public class CandleByDateSequenceTest {

    @Test(expected = IllegalStateException.class)
    public void exceptionIsThrownWhenTryingToSetCandleThatDoesntExistInSequence() {
        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(singletonList(createTodaysCandle()));
        candleByDateSequence.setCurrent(createYesterdaysCandle());
    }

    @Test
    public void currentCandle() {
        Candle todays = createTodaysCandle();
        Candle yestredays = createYesterdaysCandle();

        List<Candle> candles = Arrays.asList(yestredays, todays);
        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(candles);

        Assert.assertTrue(candleByDateSequence.hasNext());

        candleByDateSequence.setCurrent(todays);
        Assert.assertTrue(candleByDateSequence.hasNext());
        Assert.assertEquals(candleByDateSequence.getCurrent(), todays);
        Assert.assertEquals(candleByDateSequence.next(), todays);
        Assert.assertFalse(candleByDateSequence.hasNext());
    }

    @Test
    public void sequenceHasNoElementsWhenCreatedWithEmptyList() {
        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(new ArrayList<>());
        assertFalse(candleByDateSequence.hasNext());
    }

    @Test
    public void testIteration_AllCandlesAreSortedByDate() {
        Candle first = createTodaysCandle();
        Candle second = createYesterdaysCandle();

        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(Arrays.asList(first, second));

        assertTrue(candleByDateSequence.hasNext());
        assertEquals(candleByDateSequence.next(), second);

        assertTrue(candleByDateSequence.hasNext());
        assertEquals(candleByDateSequence.next(), first);

        assertFalse(candleByDateSequence.hasNext());
    }
}
