package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.utils.RecordsHolder;
import org.junit.Test;

import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordFactory.getTodays;
import static com.stocks.livermor.utils.RecordFactory.getYestredays;
import static org.junit.Assert.assertEquals;

public class SecondaryRallyStrategyTest {

    @Test
    public void price_is_higher_than_last_in_natural_rally() {
        Record todays = getTodays(NONE, 120);
        Record yesterdays = getYestredays(NATURAL_RALLY, false, 119);

        RecordsHolder recordsHolder = new RecordsHolder(yesterdays);
        new SecondaryRallyStrategy().execute(recordsHolder, todays);

        assertEquals(todays.getState(), NATURAL_RALLY);
    }

    @Test
    public void price_is_higher_than_last_pivot_point_in_natural_rally() {
        Record todays = getTodays(NONE, 120);
        Record yesterdays = getYestredays(NATURAL_RALLY, true, 110);

        RecordsHolder recordsHolder = new RecordsHolder(yesterdays);
        new SecondaryRallyStrategy().execute(recordsHolder, todays);

        assertEquals(todays.getState(), UPPER_TREND);
    }
}
