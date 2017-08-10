package com.stocks.livermor.utils;

import com.stocks.livermor.entity.Record;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static com.stocks.livermor.entity.State.DOWN_TREND;
import static com.stocks.livermor.entity.State.UPPER_TREND;
import static com.stocks.livermor.utils.RecordFactory.*;
import static com.stocks.livermor.utils.RecordsHolder.NULL_OBJECT;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.*;

public class PivotPointsHolderTest {

    @Test
    public void when_no_pp_exist_list_is_empty() {
        Record record = new PivotPointsHolder(singletonList(getTodays(DOWN_TREND, false)))
                .lastPivotPointRecord(DOWN_TREND);

        Assert.assertEquals(record, NULL_OBJECT);
    }

    @Test
    public void whe_pp_exist_list_is_not_empty() {
        Record lastRecord = getTodays(DOWN_TREND, true);
        Record yesterdays = getYestredays(true);
        yesterdays.setState(DOWN_TREND);

        Record lastDownTrendRecords = new PivotPointsHolder(asList(lastRecord, yesterdays))
                .lastPivotPointRecord(DOWN_TREND);

        assertEquals(lastDownTrendRecords, lastRecord);
    }

    @Test
    public void rule_6aa_doesnt_apply_when_record_is_not_in_upper_trend() {
        PivotPointsHolder pivotPointsHolder = new PivotPointsHolder(new ArrayList<>());
        assertFalse(pivotPointsHolder.check6aaRuleWhenReactionOccurred(getTodays(DOWN_TREND)));
    }

    @Test
    public void rule_6aa_doesnt_apply_when_no_records_in_upper_trend() {
        PivotPointsHolder pivotPointsHolder = new PivotPointsHolder(new ArrayList<>());
        assertFalse(pivotPointsHolder.check6aaRuleWhenReactionOccurred(getTodays(UPPER_TREND)));
    }

    @Test
    public void rule_6aa_doesnt_apply_due_to_last_record_price() {
        Record prevUpper = getYestredays(UPPER_TREND, true, 100);
        Record last = getTodays(UPPER_TREND, 120);

        PivotPointsHolder pivotPointsHolder = new PivotPointsHolder(singletonList(prevUpper));
        assertFalse(pivotPointsHolder.check6aaRuleWhenReactionOccurred(last));
    }


    @Test
    public void rule_6aa_doesnt_apply_due_to_down_trend_between_records() {
        Record prevUpper = get2DaysAgo(true, 100);
        prevUpper.setState(UPPER_TREND);
        Record prevDown = getYestredays(DOWN_TREND, true, 100);
        Record last = getTodays(UPPER_TREND, 100.5);

        PivotPointsHolder pivotPointsHolder = new PivotPointsHolder(asList(prevUpper, prevDown));
        assertFalse(pivotPointsHolder.check6aaRuleWhenReactionOccurred(last));
    }

    @Test
    public void rule_6aa_applies() {
        Record prevUpper = getYestredays(UPPER_TREND, true, 100);
        Record prevDown = get2DaysAgo(true, 100);
        prevDown.setState(DOWN_TREND);
        Record last = getTodays(UPPER_TREND, 100.5);

        PivotPointsHolder pivotPointsHolder = new PivotPointsHolder(asList(prevUpper, prevDown));
        assertTrue(pivotPointsHolder.check6aaRuleWhenReactionOccurred(last));
    }

    @Test
    public void rule_6cc_doesnt_apply_when_record_is_not_in_down_trend() {
        PivotPointsHolder pivotPointsHolder = new PivotPointsHolder(new ArrayList<>());
        assertFalse(pivotPointsHolder.check6ccRuleWhenReactionOccurred(getTodays(UPPER_TREND)));
    }

    @Test
    public void rule_6cc_doesnt_apply_when_no_records_in_down_trend() {
        PivotPointsHolder pivotPointsHolder = new PivotPointsHolder(new ArrayList<>());
        assertFalse(pivotPointsHolder.check6ccRuleWhenReactionOccurred(getTodays(DOWN_TREND)));
    }

    @Test
    public void rule_6cc_doesnt_apply_due_to_last_record_price() {
        Record prevUpper = getYestredays(DOWN_TREND, true, 120);
        Record last = getTodays(DOWN_TREND, 100);

        PivotPointsHolder pivotPointsHolder = new PivotPointsHolder(singletonList(prevUpper));
        assertFalse(pivotPointsHolder.check6ccRuleWhenReactionOccurred(last));
    }


    @Test
    public void rule_6cc_doesnt_apply_due_to_up_trend_between_records() {
        Record prevDown = get2DaysAgo(true, 100);
        prevDown.setState(DOWN_TREND);
        Record prevUp = getYestredays(UPPER_TREND, true, 100);
        Record last = getTodays(DOWN_TREND, 100.5);

        PivotPointsHolder pivotPointsHolder = new PivotPointsHolder(asList(prevDown, prevUp));
        assertFalse(pivotPointsHolder.check6ccRuleWhenReactionOccurred(last));
    }

    @Test
    public void rule_6cc_applies() {
        Record prevUpper = get2DaysAgo(true, 120);
        prevUpper.setState(UPPER_TREND);
        Record prevDown = getYestredays(DOWN_TREND, true, 100);
        Record last = getTodays(DOWN_TREND, 99.5);

        PivotPointsHolder pivotPointsHolder = new PivotPointsHolder(asList(prevUpper, prevDown));
        assertTrue(pivotPointsHolder.check6ccRuleWhenReactionOccurred(last));
    }

}
