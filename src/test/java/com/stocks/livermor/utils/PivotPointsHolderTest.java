package com.stocks.livermor.utils;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.State;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static com.stocks.livermor.entity.PivotPointType.DOWN_TREND;
import static com.stocks.livermor.entity.PivotPointType.NONE;
import static com.stocks.livermor.entity.PivotPointType.UPPER_TREND;
import static com.stocks.livermor.utils.RecordHelper.get2DaysAgo;
import static com.stocks.livermor.utils.RecordHelper.getTodays;
import static com.stocks.livermor.utils.RecordHelper.getYestredays;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PivotPointsHolderTest {

    @Test
    public void when_no_pp_exist_list_is_empty() {
        List<Record> record = new PivotPointsHolder(singletonList(getTodays(NONE)))
                .lastRecords(DOWN_TREND);

        Assert.assertTrue(record.isEmpty());
    }


    @Test
    public void whe_pp_exist_list_is_not_empty() {
        Record lastRecord = getTodays(DOWN_TREND);
        Record yesterdays = getYestredays(DOWN_TREND);

        List<Record> lastDownTrendRecords = new PivotPointsHolder(asList(lastRecord, yesterdays))
                .lastRecords(DOWN_TREND);

        assertEquals(lastDownTrendRecords.get(0), yesterdays);
        assertEquals(lastDownTrendRecords.get(1), lastRecord);
    }

    @Test
    public void rule_6aa_doesnt_apply_when_record_is_not_in_upper_trend() {
        PivotPointsHolder pivotPointsHolder = new PivotPointsHolder(new ArrayList<>());
        assertFalse(pivotPointsHolder.check6aaRuleWhenReactionOccurred(getTodays(DOWN_TREND)));
    }

    @Test
    public void rule_6aa_doesnt_apply_when_no_records_in_upper_trend() {
        PivotPointsHolder pivotPointsHolder = new PivotPointsHolder(new ArrayList<>());
        assertFalse(pivotPointsHolder.check6aaRuleWhenReactionOccurred(getTodays(State.UPPER_TREND)));
    }

    @Test
    public void rule_6aa_doesnt_apply_due_to_last_record_price() {
        Record prevUpper = getYestredays(UPPER_TREND, 100);
        Record last = getTodays(State.UPPER_TREND, 120);

        PivotPointsHolder pivotPointsHolder = new PivotPointsHolder(singletonList(prevUpper));
        assertFalse(pivotPointsHolder.check6aaRuleWhenReactionOccurred(last));
    }


    @Test
    public void rule_6aa_doesnt_apply_due_to_down_trend_between_records() {
        Record prevUpper = get2DaysAgo(UPPER_TREND, 100);
        Record prevDown = getYestredays(DOWN_TREND, 100);
        Record last = getTodays(State.UPPER_TREND, 100.5);

        PivotPointsHolder pivotPointsHolder = new PivotPointsHolder(asList(prevUpper, prevDown));
        assertFalse(pivotPointsHolder.check6aaRuleWhenReactionOccurred(last));
    }

    @Test
    public void rule_6aa_applies() {
        Record prevUpper = getYestredays(UPPER_TREND, 100);
        Record prevDown = get2DaysAgo(DOWN_TREND, 100);
        Record last = getTodays(State.UPPER_TREND, 100.5);

        PivotPointsHolder pivotPointsHolder = new PivotPointsHolder(asList(prevUpper, prevDown));
        assertTrue(pivotPointsHolder.check6aaRuleWhenReactionOccurred(last));
    }

}
