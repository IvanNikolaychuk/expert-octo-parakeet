package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.RecordsHolder;
import org.junit.Assert;
import org.junit.Test;

import static com.stocks.livermor.entity.State.NONE;
import static com.stocks.livermor.entity.State.UPPER_TREND;
import static com.stocks.livermor.utils.RecordFactory.getTodays;
import static com.stocks.livermor.utils.RecordFactory.getYestredays;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class UpperTrendStrategyTest {

    @Test
    public void when_new_price_is_grater_than_prev_it_is_recorded_as_upper_trend() {
        RecordsHolder recordsHolder = new RecordsHolder(getYestredays(UPPER_TREND, false, 100));
        Record newRecord = getTodays(NONE, 104);

        new UpperTrendStrategy().process(recordsHolder, newRecord);
        Assert.assertEquals(newRecord.getState(), UPPER_TREND);
    }

    @Test
    public void when_strong_reaction_prev_record_is_marked_as_pivot_point() {
        Record prevRecord = getYestredays(UPPER_TREND, false, 100);
        Record newRecord = getTodays(NONE, 50);
        RecordsHolder recordsHolder = new RecordsHolder(prevRecord);

        new UpperTrendStrategy().process(recordsHolder, newRecord);
        assertTrue(prevRecord.isPivotPoint());
        assertNotEquals(newRecord.getState(), NONE);
    }
}
