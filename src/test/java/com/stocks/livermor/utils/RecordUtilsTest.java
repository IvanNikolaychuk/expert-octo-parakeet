package com.stocks.livermor.utils;

import com.stocks.livermor.entity.Record;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static com.stocks.livermor.utils.RecordUtils.CHANGE_MEASURE;
import static com.stocks.livermor.utils.RecordUtils.ChangeMeasure.PERCENTAGE;
import static com.stocks.livermor.utils.RecordUtils.ChangeMeasure.POINTS;
import static com.stocks.livermor.utils.RecordUtils.strongReaction;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RecordUtilsTest {
    @Test
    public void strong_reaction_when_perc_change_is_more_than_2_perc() {
        CHANGE_MEASURE = PERCENTAGE;
        assertTrue(strongReaction(record(100), record(95)));
    }

    @Test
    public void strong_reaction_when_perc_change_is_less_than_2_perc() {
        CHANGE_MEASURE = PERCENTAGE;
        assertFalse(strongReaction(record(100), record(99)));
    }

    @Test
    public void strong_reaction_when_perc_change_is_more_than_6_points() {
        CHANGE_MEASURE = POINTS;
        assertTrue(strongReaction(record(100), record(94)));
    }

    @Test
    public void strong_reaction_when_perc_change_is_less_than_6_points() {
        CHANGE_MEASURE = POINTS;
        assertFalse(strongReaction(record(100), record(99)));
    }

    private Record record(int price) {
        return new Record(new Date(), price);
    }

}
