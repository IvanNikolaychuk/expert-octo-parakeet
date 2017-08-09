package com.stocks.livermor.utils;

import com.stocks.livermor.entity.Record;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.stocks.livermor.utils.RecordHelper.getYestredays;
import static org.junit.Assert.assertEquals;

public class RecordContextTest {

    @Test
    public void when_creating_records_are_sorted_by_date_reversed() {
        Record yesterdays = getYestredays();

        List<Record> records = Arrays.asList(new Record(new Date(), 1), yesterdays);
        records = new RecordsHolder(records).getRecords();

        assertEquals(records.get(0), yesterdays);
    }



}
