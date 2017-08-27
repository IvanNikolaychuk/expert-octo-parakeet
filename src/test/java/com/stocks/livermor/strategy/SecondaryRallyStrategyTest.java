package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.RecordsHolder;
import org.junit.Test;

import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordFactory.get2DaysAgo;
import static com.stocks.livermor.utils.RecordFactory.getTodays;
import static com.stocks.livermor.utils.RecordFactory.getYestredays;
import static org.junit.Assert.assertEquals;

public class SecondaryRallyStrategyTest {

    @Test
    public void price_is_higher_than_last_in_natural_rally() {
        Record todays = getTodays(NONE, 120);
        Record twoDaysAgo = get2DaysAgo(NATURAL_RALLY, false, 119);
        Record yesterdays = getYestredays(SECONDARY_RALLY);

        RecordsHolder recordsHolder = new RecordsHolder(twoDaysAgo, yesterdays);
        new SecondaryRallyStrategy().process(recordsHolder, todays);

        assertEquals(todays.getState(), NATURAL_RALLY);
    }

}
