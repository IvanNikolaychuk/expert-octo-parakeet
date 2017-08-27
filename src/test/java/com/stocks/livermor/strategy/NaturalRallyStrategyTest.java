package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.utils.RecordsHolder;
import org.junit.Test;

import static com.stocks.livermor.constants.Constants.Rule.*;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordFactory.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class NaturalRallyStrategyTest {

    @Test
    public void record_in_upper_trend_when_price_is_higher_than_last_upper_trend_record() {
        Record newRecord = getTodays(null, 100d);
        Record lastUpperTrend = get2DaysAgo(UPPER_TREND, false, 90d);
        Record lastRecorded = getYestredays(NATURAL_RALLY);

        new NaturalRallyStrategy().process(new RecordsHolder(lastUpperTrend, lastRecorded), newRecord);

        assertEquals(newRecord.getState(), UPPER_TREND);
        assertEquals(newRecord.getExplanation(), _6d3.getExplanation());
    }

    @Test
    public void record_in_upper_trend_when_price_is_higher_than_last_natural_rally_pivot_point() {
        Record newRecord = getTodays(null, 100d);
        Record lastRallyPivotPoint = get2DaysAgo(NATURAL_RALLY, true, 90d);
        Record oldestPivotPoint = get3DaysAgo(UPPER_TREND, true, 95d);
        Record lastRecord = getYestredays(NATURAL_RALLY);

        new NaturalRallyStrategy().process(new RecordsHolder(lastRallyPivotPoint, lastRecord, oldestPivotPoint), newRecord);

        assertEquals(newRecord.getState(), UPPER_TREND);
        assertEquals(newRecord.getExplanation(), _6d3.getExplanation());
    }

    @Test
    public void record_in_down_trend_when_price_is_lower_than_last_down_trend_record() {
        Record newRecord = getTodays(null, 100d);
        Record lastDownTrend = get2DaysAgo(DOWN_TREND, true, 101d);
        Record lastRecord = getYestredays(NATURAL_RALLY);

        new NaturalRallyStrategy().process(new RecordsHolder(lastDownTrend, lastRecord), newRecord);

        assertEquals(newRecord.getState(), DOWN_TREND);
        assertEquals(newRecord.getExplanation(), _11b.getExplanation());
    }

    @Test
    public void record_in_natural_reaction_when_strong_reaction() {
        Record newRecord = getTodays(null, 100d);
        Record lastRecord = getYestredays(NATURAL_RALLY, 110d);

        new NaturalRallyStrategy().process(new RecordsHolder(lastRecord), newRecord);

        assertEquals(newRecord.getState(), NATURAL_REACTION);
        assertEquals(newRecord.getExplanation(), _6b.getExplanation());
        assertFalse(newRecord.isPivotPoint());
    }

    @Test
    public void record_in_secondary_reaction_when_new_price_is_bigger_than_last_natural_reaction_price() {
        Record newRecord = getTodays(null, 101d);
        Record lastNaturalReaction = get2DaysAgo(NATURAL_REACTION, false, 100d);
        Record lastRecord = getYestredays(NATURAL_RALLY, 110d);

        new NaturalRallyStrategy().process(new RecordsHolder(lastNaturalReaction, lastRecord), newRecord);

        assertEquals(newRecord.getState(), SECONDARY_REACTION);
        assertEquals(newRecord.getExplanation(), _6h.getExplanation());
    }
}
