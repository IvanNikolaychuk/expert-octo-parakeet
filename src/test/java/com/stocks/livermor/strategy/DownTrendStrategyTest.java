package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.utils.RecordsHolder;
import org.junit.Assert;
import org.junit.Test;

import static com.stocks.livermor.entity.State.DOWN_TREND;
import static com.stocks.livermor.entity.State.NONE;
import static com.stocks.livermor.utils.RecordFactory.getTodays;
import static com.stocks.livermor.utils.RecordFactory.getYestredays;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class DownTrendStrategyTest {

    @Test
    public void when_new_price_is_lower_than_prev_it_is_recorded_as_down_trend() {
        RecordsHolder recordsHolder = new RecordsHolder(singletonList(getYestredays(DOWN_TREND, false, 100)));
        Record newRecord = getTodays(NONE, 80);

        new DownTrendStrategy().execute(recordsHolder, newRecord);
        Assert.assertEquals(newRecord.getState(), DOWN_TREND);
    }

    @Test
    public void when_strong_rally_prev_record_is_marked_as_pivot_point() {
        Record prevRecord = getYestredays(DOWN_TREND, false, 100);
        Record newRecord = getTodays(NONE, 150);
        RecordsHolder recordsHolder = new RecordsHolder(singletonList(prevRecord));

        new DownTrendStrategy().execute(recordsHolder, newRecord);
        assertTrue(prevRecord.isPivotPoint());
        assertNotEquals(newRecord.getState(), NONE);
    }
}
