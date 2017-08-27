package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.RecordsHolder;
import org.junit.Test;

import static com.stocks.livermor.constants.Constants.Rule.*;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordFactory.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class NaturalReactionStrategyTest {

    @Test
    public void record_in_upper_trend_when_price_is_bigger_than_last_upper_trend_record() {
        Record newRecord = getTodays(null, 100d);
        Record lastUpperTrend = get2DaysAgo(UPPER_TREND, false, 90d);
        Record lastRecorded = getYestredays(NATURAL_REACTION, 99d);

        new NaturalReactionStrategy().process(new RecordsHolder(lastUpperTrend, lastRecorded), newRecord);

        assertEquals(newRecord.getState(), UPPER_TREND);
        assertEquals(newRecord.getExplanation(), _11a.getExplanation());
    }

    @Test
    public void record_in_natural_rally_when_strong_rally() {
        Record newRecord = getTodays(null, 110d);
        Record lastRecord = getYestredays(NATURAL_REACTION, 100d);

        new NaturalReactionStrategy().process(new RecordsHolder(lastRecord), newRecord);

        assertEquals(newRecord.getState(), NATURAL_RALLY);
        assertEquals(newRecord.getExplanation(), _6d.getExplanation());
        assertFalse(newRecord.isPivotPoint());
    }

    @Test
    public void record_in_secondary_rally_when_new_price_is_smaller_than_last_natural_rally_price() {
        Record newRecord = getTodays(null, 110d);
        Record lastNaturalReaction = get2DaysAgo(NATURAL_RALLY, false, 111d);
        Record lastRecord = getYestredays(NATURAL_REACTION, 100d);

        new NaturalReactionStrategy().process(new RecordsHolder(lastNaturalReaction, lastRecord), newRecord);

        assertEquals(newRecord.getState(), SECONDARY_RALLY);
        assertEquals(newRecord.getExplanation(), _6g.getExplanation());
    }
}
