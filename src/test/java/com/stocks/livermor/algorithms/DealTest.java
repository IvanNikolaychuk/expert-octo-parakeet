package com.stocks.livermor.algorithms;

import org.junit.Test;

import static com.stocks.livermor.algorithms.Deal.Type.LONG;
import static com.stocks.livermor.algorithms.Deal.Type.SHORT;
import static org.junit.Assert.assertEquals;

public class DealTest {

    @Test
    public void testShortProfit() {
        Deal deal = new Deal(100, SHORT, "");
        deal.close(99);

        assertEquals(deal.computeProfit(), 1, 001d);
    }

    @Test
    public void testShortLoss() {
        Deal deal = new Deal(100, SHORT, "");
        deal.close(101);

        assertEquals(deal.computeProfit(), -1, 001d);
    }

    @Test
    public void testLongProfit() {
        Deal deal = new Deal(100, LONG, "");
        deal.close(101);

        assertEquals(deal.computeProfit(), 1, 001d);
    }

    @Test
    public void testLongLoss() {
        Deal deal = new Deal(100, LONG,"");
        deal.close(99);

        assertEquals(deal.computeProfit(), -1, 001d);
    }
}
