package com.stocks.tasks.analyzer.helper;

import com.stocks.core.db.entity.Candle;
import com.stocks.tasks.analyzer.helpers.CandleByDateSequence;
import org.junit.Assert;
import org.junit.Test;

import static com.stocks.tasks.helpers.CandleHelper.createTodaysCandle;
import static com.stocks.tasks.helpers.CandleHelper.createYesterdaysCandle;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CandleByDateSequenceTest {

    @Test(expected = IllegalStateException.class)
    public void exception_is_thrown_when_set_current_candle_that_is_not_in_sequence() {
        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(singletonList(createTodaysCandle()));
        candleByDateSequence.setCurrent(createYesterdaysCandle());
    }


    @Test
    public void testFlow() {
        Candle yesterdays = createYesterdaysCandle();
        Candle todays = createTodaysCandle();

        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(yesterdays, todays);

        assertEquals(yesterdays, candleByDateSequence.getCurrent());
        assertTrue(candleByDateSequence.hasNext());

        assertEquals(todays, candleByDateSequence.next());
        assertFalse(candleByDateSequence.hasNext());
        assertEquals(todays, candleByDateSequence.getCurrent());
    }


}
