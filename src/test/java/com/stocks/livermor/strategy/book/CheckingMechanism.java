package com.stocks.livermor.strategy.book;

import com.stocks.livermor.Executor;
import com.stocks.livermor.Constants;
import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.State;
import com.stocks.livermor.strategy.book.utils.DateGenerator;
import com.stocks.livermor.strategy.factory.StrategyPicker;
import com.stocks.livermor.utils.RecordsHolder;
import com.stocks.technical.core.db.entity.Candle;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CheckingMechanism {
    private static final int PP_CHECKS_COUNTER = 5;

    private static RecordsHolder recordsHolder = new RecordsHolder();
    private static Executor executor = new Executor(new StrategyPicker());
    private static Map<Record, Integer> recordToPpChecksCounterMap = new HashMap<>();
    private static Map<Record, Integer> recordToNotPpChecksCounterMap = new HashMap<>();

    public static void processWithNoCheck(Record record) {
        executor.process(recordsHolder, record);
    }

    public static void processAndCheckNext(double price, State expectedState, Constants.Rule expectedRule, boolean shouldBePivotPoint) {
        Record newRecord = newRecord(price);
        executor.process(recordsHolder, newRecord);

        assertEquals(newRecord.getState(), expectedState);
        if (expectedRule != null) {
            assertEquals(newRecord.getExplanation(), expectedRule.getExplanation());
        }
        checkPivotPoints();
        if (shouldBePivotPoint)
            recordToPpChecksCounterMap.put(newRecord, 0);
        else
            recordToNotPpChecksCounterMap.put(newRecord, 0);
    }

    static List<Candle> filter2016(List<Candle> candles) {
        List<Candle> filtered = new ArrayList<>();

        for (Candle candle : candles) {
            if (candle.getDate().get(Calendar.YEAR) == 2016) {
                filtered.add(candle);
            }
        }
        return filtered;
    }

    public static void addToRecordHolder(Record record) {
        recordsHolder.add(record);
    }

    public static void clear() {
        recordsHolder = new RecordsHolder();
        recordToPpChecksCounterMap = new HashMap<>();
        recordToNotPpChecksCounterMap = new HashMap<>();
    }

    public static RecordsHolder getRecordsHolder() {
        return recordsHolder;
    }

    private static void checkPivotPoints() {
        for (Map.Entry<Record, Integer> recordToPPchecksCounter : recordToPpChecksCounterMap.entrySet()) {
            recordToPPchecksCounter.setValue(recordToPPchecksCounter.getValue() + 1);
            if (recordToPPchecksCounter.getValue() == PP_CHECKS_COUNTER) {
                assertTrue(recordToPPchecksCounter.getKey().isPivotPoint());
            }
        }

        for (Map.Entry<Record, Integer> recordToPPchecksCounter : recordToNotPpChecksCounterMap.entrySet()) {
            recordToPPchecksCounter.setValue(recordToPPchecksCounter.getValue() + 1);
            if (recordToPPchecksCounter.getValue() == PP_CHECKS_COUNTER) {
                assertFalse(recordToPPchecksCounter.getKey().isPivotPoint());
            }
        }
    }


    public static Record newRecord(double price) {
        return new Record(DateGenerator.next(), price);
    }


    public static Record newRecord(double price, State state, boolean isPivotPoint) {
        Record record = newRecord(price);
        record.setState(state);
        record.setPivotPoint(isPivotPoint);
        return record;
    }
}
