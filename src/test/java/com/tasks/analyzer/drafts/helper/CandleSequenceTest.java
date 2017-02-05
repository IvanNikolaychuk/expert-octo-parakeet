package com.tasks.analyzer.drafts.helper;

import com.core.db.entity.Candle;
import com.tasks.analyzer.drafts.helpers.CandleSequence;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class CandleSequenceTest {

    @Test
    public void sequenceHasNoElementsWhenCreatedWithEmptyList() {
        CandleSequence candleSequence = new CandleSequence(new ArrayList<>());
        assertFalse(candleSequence.hasNext());
    }

    @Test
    public void testIteration() {
        Candle first = new Candle();
        Candle second = new Candle();

        CandleSequence candleSequence = new CandleSequence(Arrays.asList(first, second));

        assertTrue(candleSequence.hasNext());
        assertEquals(candleSequence.next(), first);

        assertTrue(candleSequence.hasNext());
        assertEquals(candleSequence.next(), second);

        assertFalse(candleSequence.hasNext());
    }

    @Test
    public void firstCandleIsNeverStrungBull() {

    }

    @Test
    public void candleIsNeverStrungBull() {

    }
}
