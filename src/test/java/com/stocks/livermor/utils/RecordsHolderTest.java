package com.stocks.livermor.utils;

import com.stocks.livermor.entity.Record;
import org.junit.Test;

import java.util.List;

import static com.stocks.livermor.entity.State.DOWN_TREND;
import static com.stocks.livermor.utils.RecordFactory.getTodays;
import static com.stocks.livermor.utils.RecordFactory.getYestredays;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class RecordsHolderTest {

    @Test
    public void last_record_of_particular_type_is_obtained_when_exist() {
        Record mostRecent = getTodays(DOWN_TREND);
        Record old = getYestredays(DOWN_TREND);

        List<Record> records = asList(old, mostRecent);
        Record last = new RecordsHolder(records).last(DOWN_TREND);

        assertEquals(last, mostRecent);
    }
}
