package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.utils.RecordsHolder;
import org.junit.Test;

import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordFactory.get2DaysAgo;
import static com.stocks.livermor.utils.RecordFactory.getTodays;
import static com.stocks.livermor.utils.RecordFactory.getYestredays;
import static org.junit.Assert.assertEquals;

public class SecondaryReactionStrategyTest {

    @Test
    public void price_is_lower_than_last_in_natural_reaction() {
        Record todays = getTodays(NONE, 100);
        Record twoDaysAgo = get2DaysAgo(NATURAL_REACTION, false, 119);
        Record yesterdays = getYestredays(SECONDARY_REACTION, false, 101);

        RecordsHolder recordsHolder = new RecordsHolder(twoDaysAgo, yesterdays);
        new SecondaryReactionStrategy().process(recordsHolder, todays);

        assertEquals(todays.getState(), NATURAL_REACTION);
    }
}
