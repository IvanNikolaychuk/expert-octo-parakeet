package com.stocks.livermor.utils;

import com.stocks.livermor.entity.Record;
import org.junit.Test;

import java.util.Date;

import static com.stocks.livermor.utils.RecordUtils.MovementType.*;
import static com.stocks.livermor.utils.RecordUtils.getMovementType;
import static org.junit.Assert.assertEquals;

public class RecordUtilsTest {

    @Test
    public void strong_rally() {
        assertEquals(STRONG_RALLY, getMovementType(record(100), record(120)));
    }

    @Test
    public void rally() {
        assertEquals(RALLY, getMovementType(record(200), record(203)));
    }

    @Test
    public void none() {
        assertEquals(NONE, getMovementType(record(200), record(201)));
    }

    @Test
    public void strong_reaction() {
        assertEquals(STRONG_REACTION, getMovementType(record(200), record(150)));
    }


    private Record record(double price) {
        return new Record(new Date(), price);
    }

}
