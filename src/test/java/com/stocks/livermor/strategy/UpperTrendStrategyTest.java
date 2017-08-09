package com.stocks.livermor.strategy;

import com.stocks.livermor.utils.RecordFactory;
import com.stocks.livermor.utils.RecordsHolder;
import org.junit.Test;

import java.util.Arrays;

public class UpperTrendStrategyTest {
    @Test
    public void when_new_price_is_grater_than_prev_it_is_recorded_as_upper_trend() {
        RecordsHolder recordsHolder = new RecordsHolder(Arrays.asList(RecordFactory.getYestredays()));
    }
}
