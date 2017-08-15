package com.stocks.livermor.strategy.book;

import com.stocks.livermor.Executor;
import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.State;
import com.stocks.livermor.strategy.book.utils.DateGenerator;
import com.stocks.livermor.strategy.factory.StrategyPicker;
import com.stocks.livermor.utils.RecordsHolder;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.stocks.livermor.constants.Constants.Rule;
import static com.stocks.livermor.constants.Constants.Rule.*;
import static com.stocks.livermor.constants.Constants.Rule._12_secondary_reaction;
import static com.stocks.livermor.constants.Constants.Rule._6h;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.entity.State.NONE;
import static com.stocks.livermor.entity.State.SECONDARY_REACTION;
import static com.stocks.livermor.utils.RecordUtils.CHANGE_MEASURE;
import static com.stocks.livermor.utils.RecordUtils.ChangeMeasure.PERCENTAGE;
import static org.junit.Assert.*;

public class GazpTest {
    private static final int PP_CHECKS_COUNTER = 5;
    private Map<Record, Integer> recordToPpChecksCounterMap;
    private Map<Record, Integer> recordToNotPpChecksCounterMap;

    private Executor executor = new Executor(new StrategyPicker());
    private RecordsHolder recordsHolder = new RecordsHolder();

    @Before
    public void init() {
        CHANGE_MEASURE = PERCENTAGE;
        recordsHolder.add(newRecord(130.29, DOWN_TREND, true));
        recordsHolder.add(newRecord(138.7, NATURAL_RALLY, true));
        recordToPpChecksCounterMap = new HashMap<>();
        recordToNotPpChecksCounterMap = new HashMap<>();
    }

    @Test
    public void test() {
        firstQuarter();
        secondQuarter();
        thirdQuarter();
        fourthQuarter();
    }

    private void fourthQuarter() {
        processAndCheckNext(132.9, NONE, null, false);
        processAndCheckNext(131.5, NATURAL_REACTION, _6b, false);

        // TODO: спросить, непомнятно почему в записях делается запись из реакции в восходящий тренд напрямую (05.10)
        processAndCheckNext(136.8, NATURAL_RALLY, _6d, false);

        processAndCheckNext(137.87, UPPER_TREND, _5a, false);
        processAndCheckNext(140.0, UPPER_TREND, _12_upper, false);
        processAndCheckNext(139.8, NONE, null, false);
        processAndCheckNext(143.81, UPPER_TREND, _12_upper, true);
        processAndCheckNext(141.7, NONE, null, false);
        processAndCheckNext(141.8, NONE, null, false);
        processAndCheckNext(142.01, NONE, null, false);
        // and some more nones.
        processAndCheckNext(140.27, NATURAL_REACTION, _6a, false);
        processAndCheckNext(140.4, NONE, null, false);
        processAndCheckNext(139.49, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(139.25, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(138.94, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(138.5, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(138.5, NONE, null, false);
        processAndCheckNext(138.1, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(136.11, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(135.75, NATURAL_REACTION, _12_reaction, true);
        processAndCheckNext(137.8, NONE, null, false);
        processAndCheckNext(141.75, NATURAL_RALLY, _6d, false);
        processAndCheckNext(140.97, NONE, null, false);
        processAndCheckNext(137.75, SECONDARY_REACTION, _6h, false);
        processAndCheckNext(135.58, NATURAL_REACTION, _6h3, false);
        // TODO: спросить, должна быть пивотной точкой (по записям нет). Рассмотреть кейс, когда в тренде пробивается точка без "отката" в противоположную сторону. Это 11.11
        processAndCheckNext(135.0, NATURAL_REACTION, _12_reaction, true);
        processAndCheckNext(136.6, NONE, null, false);
        processAndCheckNext(136.0, NONE, null, false);
        processAndCheckNext(137.21, NONE, null, false);
        processAndCheckNext(142.75, NATURAL_RALLY, _6d, false);
        processAndCheckNext(147.47, UPPER_TREND, _6d3, false);
        processAndCheckNext(147.5, UPPER_TREND, _12_upper, false);
        processAndCheckNext(148.05, UPPER_TREND, _12_upper, false);
        processAndCheckNext(152.0, UPPER_TREND, _12_upper, true);
        processAndCheckNext(145.65, NATURAL_REACTION, _6a, false);
        processAndCheckNext(143.99, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(143.2, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(140.65, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(138.0, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(137.42, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(136.85, NATURAL_REACTION, _12_reaction, false);
        // TODO: по записям это натуральное ралли, хотя эта цифра меньше последней записи в ралли. Это 03.12
        processAndCheckNext(140.0, SECONDARY_RALLY, _6g, false);
        processAndCheckNext(137.7, NONE, null, false);
        processAndCheckNext(134.5, NATURAL_REACTION, _6b, false);
        processAndCheckNext(134.65, NONE, null, false);
        processAndCheckNext(135.81, NONE, null, false);
        processAndCheckNext(135.6, NONE, null, false);
        processAndCheckNext(133.8, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(131.59, DOWN_TREND, _5b, true);
        processAndCheckNext(134.4, NATURAL_RALLY, _6c, false);
        processAndCheckNext(134.5, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(137.9, NATURAL_RALLY, _12_rally, true);
        processAndCheckNext(132.25, NATURAL_REACTION, _6b, false);
        processAndCheckNext(131.5, DOWN_TREND, _6b3, true);
        processAndCheckNext(133.2, NONE, null, false);
        // TODO: по записям не так, хотя вроде все логично
        processAndCheckNext(135.3, SECONDARY_RALLY, _6cc, false);
        processAndCheckNext(136.0, SECONDARY_RALLY, _12_secondary_rally, false);
        processAndCheckNext(134.48, NONE, null, false);
        processAndCheckNext(134.97, NONE, null, false);
        processAndCheckNext(137.49, SECONDARY_RALLY, _12_secondary_rally, false);
        processAndCheckNext(136.09, NONE, null, false);

    }

    private void thirdQuarter() {
        processAndCheckNext(145.5, NONE, null, false);
        processAndCheckNext(145.8, NONE, null, false);
        processAndCheckNext(144.33, NONE, null, false);
        processAndCheckNext(143.0, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(142.5, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(141.5, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(143.77, NONE, null, false);
        processAndCheckNext(143.9, NONE, null, false);
        processAndCheckNext(145.27, SECONDARY_RALLY, _6g, false);
        processAndCheckNext(146.5, SECONDARY_RALLY, _12_secondary_rally, false);
        processAndCheckNext(142.7, SECONDARY_REACTION, _6h, false);
        processAndCheckNext(144.2, NONE, null, false);
        processAndCheckNext(143.6, NONE, null, false);
        processAndCheckNext(141.78, SECONDARY_REACTION, _12_secondary_reaction, false);
        processAndCheckNext(141.41, NATURAL_REACTION, _6h3, false);
        processAndCheckNext(140.8, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(138.37, DOWN_TREND, _6b3, false);
        processAndCheckNext(136.41, DOWN_TREND, _12_down, false);
        processAndCheckNext(134.51, DOWN_TREND, _12_down, true);
        processAndCheckNext(135.05, NONE, null, false);
        processAndCheckNext(138.7, NATURAL_RALLY, _6c, false);
        processAndCheckNext(139.8, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(142.5, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(141.45, NONE, null, false);
        processAndCheckNext(141.89, NONE, null, false);
        processAndCheckNext(143.78, NATURAL_RALLY, _12_rally, true);

        // вообще по записям тут 140.88
        processAndCheckNext(140.79, NATURAL_REACTION, _6b, false);
        processAndCheckNext(141.34, NONE, null, false);
        processAndCheckNext(141.5, NONE, null, false);
        processAndCheckNext(141.97, NONE, null, false);
        processAndCheckNext(142.99, NONE, null, false);
        processAndCheckNext(143.7, SECONDARY_RALLY, _6g, false);
        // TODO: спросить, должна быть пивотной точкой (по записям нет). Рассмотреть кейс, когда в тренде пробивается точка без "отката" в противоположную сторону.
        processAndCheckNext(144.8, NATURAL_RALLY, _6g3, true);
        processAndCheckNext(144.25, NONE, null, false);
        processAndCheckNext(142.65, NONE, null, false);
        // вообще по записям тут 140.8 (но это ошибка)
        processAndCheckNext(140.78, NATURAL_REACTION, _6b, false);
        processAndCheckNext(142.64, NONE, null, false);
        processAndCheckNext(140.29, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(135.35, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(140.4, SECONDARY_RALLY, _6g, false);
        processAndCheckNext(140.51, SECONDARY_RALLY, _12_secondary_rally, false);
        processAndCheckNext(144.6, SECONDARY_RALLY, _12_secondary_rally, false);
        processAndCheckNext(146.6, UPPER_TREND, _5a, false);
        processAndCheckNext(148.19, UPPER_TREND, _12_upper, true);
        processAndCheckNext(144.5, NATURAL_REACTION, _6a, false);
        processAndCheckNext(142.57, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(145.3, NONE, null, false);
        processAndCheckNext(142.8, NONE, null, false);
        processAndCheckNext(141.67, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(143.21, NONE, null, false);
        processAndCheckNext(144.22, NONE, null, false);
        processAndCheckNext(142.99, NONE, null, false);
        processAndCheckNext(142.4, NONE, null, false);
        // and some more none here
        processAndCheckNext(139.5, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(139.45, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(136.4, NATURAL_REACTION, _12_reaction, false);

        // TODO: спросить, по-идеи цена ниже последнего down-trend'a (23.09), но в записях это продолжение реакции.
        processAndCheckNext(134.3, DOWN_TREND, _6b3, false);

        processAndCheckNext(132.2, DOWN_TREND, _12_down, false);
        processAndCheckNext(133.85, NONE, null, false);
        processAndCheckNext(130.9, DOWN_TREND, _12_down, true);
        processAndCheckNext(133.2, NONE, null, false);
        processAndCheckNext(134.55, NATURAL_RALLY, _6c, true);
    }

    private void secondQuarter() {
        processAndCheckNext(143.0, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(143.9, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(143.94, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(145.7, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(146.2, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(147.27, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(148.1, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(148.0, NONE, null, false);
        processAndCheckNext(148.8, NATURAL_RALLY, _12_rally, true);
        processAndCheckNext(144.4, NATURAL_REACTION, _6b, false);
        processAndCheckNext(150.1, NATURAL_RALLY, _6d, false);
        processAndCheckNext(152.4, UPPER_TREND, _5a, true);
        processAndCheckNext(149.3, NATURAL_REACTION, _6a, true);
        processAndCheckNext(153.96, UPPER_TREND, _11a, false);
        processAndCheckNext(155.35, UPPER_TREND, _12_upper, true);
        processAndCheckNext(150.8, NATURAL_REACTION, _6a, false);
        processAndCheckNext(150.15, NATURAL_REACTION, _12_reaction, true);
        processAndCheckNext(153.89, NATURAL_RALLY, _6d, false);
        processAndCheckNext(152.05, NONE, null, false);
        processAndCheckNext(152.79, NONE, null, false);
        processAndCheckNext(152.50, NONE, null, false);
        processAndCheckNext(153.50, NONE, null, false);
        processAndCheckNext(156.33, UPPER_TREND, _6d3, true);
        processAndCheckNext(155.25, NONE, null, false);
        processAndCheckNext(152.1, SECONDARY_REACTION, _6aa, false);

        //TODO: ask (05.08)
        processAndCheckNext(155.52, NATURAL_RALLY, _6d, false);

        processAndCheckNext(155.25, NONE, null, false);
        processAndCheckNext(151.74, SECONDARY_REACTION, _6h, false);
        processAndCheckNext(151.8, NONE, null, false);
        processAndCheckNext(152.98, NONE, null, false);
        processAndCheckNext(151.9, NONE, null, false);
        processAndCheckNext(149.85, NATURAL_REACTION, _6h3, false);
        processAndCheckNext(146.35, DOWN_TREND, _5b, false);
        processAndCheckNext(147.16, NONE, null, false);
        processAndCheckNext(148.8, NONE, null, false);
        processAndCheckNext(146.69, NONE, null, false);
        processAndCheckNext(145.25, DOWN_TREND, _12_down, false);
        processAndCheckNext(147.66, NONE, null, false);
        processAndCheckNext(146.11, NONE, null, false);
        processAndCheckNext(139.0, DOWN_TREND, _12_down, true);
        processAndCheckNext(139.4, NONE, null, false);
        processAndCheckNext(140.75, NONE, null, false);
        processAndCheckNext(140.71, NONE, null, false);
        processAndCheckNext(140.2, NONE, null, false);
        processAndCheckNext(142.4, NATURAL_RALLY, _6c, false);
        processAndCheckNext(142.66, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(146.17, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(145.6, NONE, null, false);
        processAndCheckNext(145.4, NONE, null, false);
        processAndCheckNext(144.95, NONE, null, false);
        processAndCheckNext(146.0, NONE, null, false);
        processAndCheckNext(147.6, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(147.15, NONE, null, false);
        processAndCheckNext(147.15, NONE, null, false);
        processAndCheckNext(148.5, NATURAL_RALLY, _12_rally, true);
        processAndCheckNext(146.25, NONE, null, false);
        processAndCheckNext(146.68, NONE, null, false);
        processAndCheckNext(143.7, NATURAL_REACTION, _6b, false);
        processAndCheckNext(145.18, NONE, null, false);
        processAndCheckNext(143.59, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(145.85, NONE, null, false);
    }

    private void firstQuarter() {
        processAndCheckNext(130.31, NATURAL_REACTION, _6b, false);
        processAndCheckNext(133.95, SECONDARY_RALLY, _6g, false);
        processAndCheckNext(138.92, NATURAL_RALLY, _6g3, false);
        processAndCheckNext(146.46, UPPER_TREND, _5a, true);
        processAndCheckNext(141.7, NATURAL_REACTION, _6a, false);
        processAndCheckNext(140.22, NATURAL_REACTION, _12_reaction, true);
        processAndCheckNext(142.64, NONE, null, false);
        processAndCheckNext(146.3, NATURAL_RALLY, _6d, false);
        processAndCheckNext(147.0, UPPER_TREND, _6d3, false);
        processAndCheckNext(149.6, UPPER_TREND, _12_upper, true);
        processAndCheckNext(148.11, NONE, null, false);
        processAndCheckNext(145.75, NATURAL_REACTION, _6a, true);
        processAndCheckNext(148.8, NATURAL_RALLY, _6d, false);
        processAndCheckNext(151.5, UPPER_TREND, _6d3, false);
        processAndCheckNext(152.83, UPPER_TREND, _12_upper, true);
        processAndCheckNext(148.0, NATURAL_REACTION, _6a, false);
        processAndCheckNext(150.9, NONE, null, false);
        processAndCheckNext(147.7, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(145.0, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(143.82, DOWN_TREND, _5b, false);
        processAndCheckNext(138.18, DOWN_TREND, _12_down, true);
        processAndCheckNext(140.35, NONE, null, false);
        processAndCheckNext(142.6, NATURAL_RALLY, _6c, false);
        processAndCheckNext(147.6, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(151.6, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(149.3, NONE, null, false);
        processAndCheckNext(149.31, NONE, null, false);
        processAndCheckNext(150.55, NONE, null, false);
        processAndCheckNext(157.7, UPPER_TREND, _6d3, false);
        processAndCheckNext(162.4, UPPER_TREND, _12_upper, false);
        processAndCheckNext(160.19, NONE, null, false);
        processAndCheckNext(159.2, NONE, null, false);
        processAndCheckNext(163.0, UPPER_TREND, _12_upper, true);
        processAndCheckNext(160.9, NONE, null, false);
        processAndCheckNext(158.0, NATURAL_REACTION, _6a, false);
        processAndCheckNext(154.99, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(150.7, NATURAL_REACTION, _12_reaction, true);
        processAndCheckNext(151.65, NONE, null, false);
        processAndCheckNext(152.95, NONE, null, false);
        processAndCheckNext(155.55, NATURAL_RALLY, _6d, false);
        processAndCheckNext(159.0, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(154.0, SECONDARY_REACTION, _6h, false);
        processAndCheckNext(154.8, NONE, null, false);
        processAndCheckNext(152.12, SECONDARY_REACTION, _12_secondary_reaction, false);
        processAndCheckNext(148.05, DOWN_TREND, _5b, false);
        processAndCheckNext(145.99, DOWN_TREND, _12_down, false);
        processAndCheckNext(144.7, DOWN_TREND, _12_down, false);
        processAndCheckNext(141.8, DOWN_TREND, _12_down, false);
        processAndCheckNext(139.75, DOWN_TREND, _12_down, false);
        processAndCheckNext(139.7, DOWN_TREND, _12_down, false);
        processAndCheckNext(140.25, NONE, null, false);
        processAndCheckNext(136.81, DOWN_TREND, _12_down, false);
        processAndCheckNext(136.0, DOWN_TREND, _12_down, false);
        processAndCheckNext(134.61, DOWN_TREND, _12_down, false);
        processAndCheckNext(137.15, NONE, null, false);
        processAndCheckNext(134.9, NONE, null, false);
        processAndCheckNext(132.2, DOWN_TREND, _12_down, true);
        processAndCheckNext(134.88, NATURAL_RALLY, _6c, false);
        processAndCheckNext(137.0, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(138.9, NATURAL_RALLY, _12_rally, false);
    }

    public void processAndCheckNext(double price, State expectedState, Rule expectedRule, boolean shouldBePivotPoint) {
        Record newRecord = newRecord(price);
        executor.process(recordsHolder, newRecord);

        assertEquals(newRecord.getState(), expectedState);
        assertEquals(newRecord.getRule(), expectedRule);
        checkPivotPoints();
        if (shouldBePivotPoint)
            recordToPpChecksCounterMap.put(newRecord, 0);
        else
            recordToNotPpChecksCounterMap.put(newRecord, 0);
    }

    private void checkPivotPoints() {
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


    public Record newRecord(double price) {
        return new Record(DateGenerator.next(), price);
    }


    public Record newRecord(double price, State state, boolean isPivotPoint) {
        Record record = newRecord(price);
        record.setState(state);
        record.setPivotPoint(isPivotPoint);
        return record;
    }

}
