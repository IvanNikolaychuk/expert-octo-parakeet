package com.stocks.livermor.strategy.book;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.excel.ExcelWriter;
import com.stocks.technical.core.db.dao.CompanyDao;
import com.stocks.technical.core.db.entity.Candle;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

import static com.stocks.livermor.Constants.NULL_DATE;
import static com.stocks.livermor.Constants.Rule.*;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.strategy.book.CheckingMechanism.*;
import static com.stocks.livermor.utils.RecordUtils.CHANGE_MEASURE;
import static com.stocks.livermor.utils.RecordUtils.ChangeMeasure.PERCENTAGE;

public class RosnTest {

    @Before
    public void init() {
        clear();
        CHANGE_MEASURE = PERCENTAGE;
        Record firstNoDate = newRecord(201.8, UPPER_TREND, true);
        firstNoDate.setDate(NULL_DATE);
        Record secondNoDate = newRecord(195.8, NATURAL_REACTION, true);
        secondNoDate.setDate(NULL_DATE);
        addToRecordHolder(firstNoDate);
        addToRecordHolder(secondNoDate);
    }

    @Test
    public void test() throws Exception {
        firstQuarter();
        secondQuarter();
        thirdQuarter();
        fourthQuarter();

        List<Candle> candles = filter2016(new CompanyDao().getByName("ROSN.ME").getCandles());
        candles.sort(Comparator.comparing(Candle::getDate));

        for (Candle candle : candles) {
            Record record = new Record(candle.getDate().getTime(), candle.getClose().doubleValue());
            processWithNoCheck(record);
        }

//        RecordDao recordDao = new RecordDao();
//        for(Record record : getRecordsHolder().getRecords()) {
//            recordDao.save(record);
//        }


        new ExcelWriter().createTable(getRecordsHolder());
    }


    private void firstQuarter() {
        processAndCheckNext(200.3, NATURAL_RALLY, _6d, false);
        processAndCheckNext(214.95, UPPER_TREND, _6d3, true);
        processAndCheckNext(206.6, NATURAL_REACTION, _6a, false);
        processAndCheckNext(204.0, NATURAL_REACTION, _12_reaction, true);
        processAndCheckNext(209.5, NATURAL_RALLY, _6d, false);
        processAndCheckNext(219.9, UPPER_TREND, _6d3, false);
        processAndCheckNext(226.0, UPPER_TREND, _12_upper, false);
        processAndCheckNext(225.3, NONE, null, false);
        processAndCheckNext(227.0, UPPER_TREND, _12_upper, false);
        processAndCheckNext(234.6, UPPER_TREND, _12_upper, false);
        processAndCheckNext(241.8, UPPER_TREND, _12_upper, false);
        processAndCheckNext(243.9, UPPER_TREND, _12_upper, true);
        processAndCheckNext(237.25, NATURAL_REACTION, _6a, false);
        processAndCheckNext(239.0, NONE, null, false);
        processAndCheckNext(231.5, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(226.55, NATURAL_REACTION, _12_reaction, true);
        processAndCheckNext(238.35, NATURAL_RALLY, _6d, false);
        processAndCheckNext(239.0, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(251.0, UPPER_TREND, _6d3, false);
        processAndCheckNext(261.0, UPPER_TREND, _12_upper, false);
        processAndCheckNext(265.35, UPPER_TREND, _12_upper, false);
        processAndCheckNext(274.05, UPPER_TREND, _12_upper, false);
        processAndCheckNext(290.2, UPPER_TREND, _12_upper, true);
        processAndCheckNext(279.7, NATURAL_REACTION, _6a, false);
        processAndCheckNext(275.95, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(281.45, NONE, null, false);
        processAndCheckNext(275.75, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(274.0, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(268.65, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(263.45, NATURAL_REACTION, _12_reaction, true);
        processAndCheckNext(269.7, NATURAL_RALLY, _6d, false);
        processAndCheckNext(277.0, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(264.95, SECONDARY_REACTION, _6h, false);
        processAndCheckNext(263.5, SECONDARY_REACTION, _12_secondary_reaction, false);
        processAndCheckNext(253.0, DOWN_TREND, _5b, false);
        processAndCheckNext(251.45, DOWN_TREND, _12_down, false);
        processAndCheckNext(247.7, DOWN_TREND, _12_down, false);
        processAndCheckNext(241.3, DOWN_TREND, _12_down, false);
        processAndCheckNext(236.7, DOWN_TREND, _12_down, true);
        processAndCheckNext(237.0, NONE, null, false);
        processAndCheckNext(243.4, NATURAL_RALLY, _6c, true);
        processAndCheckNext(237.05, NATURAL_REACTION, _6b, false);
        processAndCheckNext(244.0, NATURAL_RALLY, _6d, false);
        processAndCheckNext(242.7, NONE, null, false);
        processAndCheckNext(237.1, SECONDARY_REACTION, _6h, false);
        processAndCheckNext(247.5, NATURAL_RALLY, _6d, false);
        processAndCheckNext(252.15, UPPER_TREND, _5a, false);
    }

    private void secondQuarter() {
        processAndCheckNext(261.35, UPPER_TREND, _12_upper, false);
        processAndCheckNext(262.55, UPPER_TREND, _12_upper, false);
        processAndCheckNext(263.7, UPPER_TREND, _12_upper, false);
        processAndCheckNext(266.15, UPPER_TREND, _12_upper, true);
        processAndCheckNext(258.55, NATURAL_REACTION, _6a, false);
        processAndCheckNext(256.95, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(255.5, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(249.5, NATURAL_REACTION, _12_reaction, true);
        processAndCheckNext(254.4, NONE, null, false);
        processAndCheckNext(259.25, NATURAL_RALLY, _6d, false);
        processAndCheckNext(254.4, NONE, null, false);
        processAndCheckNext(260.5, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(259.7, NONE, null, false);
        processAndCheckNext(252.4, SECONDARY_REACTION, _6h, false);
        processAndCheckNext(249.1, NATURAL_REACTION, _6h3, false);
        processAndCheckNext(258.2, SECONDARY_RALLY, _6g, false);
        processAndCheckNext(256.5, NONE, null, false);
        processAndCheckNext(261.5, NATURAL_RALLY, _6g3, false);
        processAndCheckNext(259.9, NONE, null, false);
        processAndCheckNext(255.25, SECONDARY_REACTION, _6h, false);
        processAndCheckNext(259.45, NONE, null, false);
        processAndCheckNext(258.35, NONE, null, false);
        processAndCheckNext(252.45, SECONDARY_REACTION, _12_secondary_reaction, false);
        processAndCheckNext(255.7, NONE, null, false);
        processAndCheckNext(257.45, NONE, null, false);
        processAndCheckNext(256.1, NONE, null, false);
        processAndCheckNext(252.25, SECONDARY_REACTION, _12_secondary_reaction, false);
        processAndCheckNext(245.9, DOWN_TREND, _5b, false);
        processAndCheckNext(245.65, DOWN_TREND, _12_down, false);
        processAndCheckNext(249.9, NONE, null, false);
        processAndCheckNext(244.6, DOWN_TREND, _12_down, false);
        processAndCheckNext(245.15, NONE, null, false);
        processAndCheckNext(247.5, NONE, null, false);
        processAndCheckNext(245.8, NONE, null, false);
        processAndCheckNext(234.0, DOWN_TREND, _12_down, false);
        processAndCheckNext(236.0, NONE, null, false);
        processAndCheckNext(237.5, NONE, null, false);
        processAndCheckNext(233.1, DOWN_TREND, _12_down, true);
        processAndCheckNext(236.25, NONE, null, false);
        processAndCheckNext(236.75, NONE, null, false);
        processAndCheckNext(239.6, NATURAL_RALLY, _6c, false);
        processAndCheckNext(245.0, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(246.0, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(245.2, NONE, null, false);
        processAndCheckNext(243.4, NONE, null, false);
        processAndCheckNext(245.7, NONE, null, false);
        processAndCheckNext(245.9, NONE, null, false);
        processAndCheckNext(245.0, NONE, null, false);
        processAndCheckNext(250.0, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(251.6, NATURAL_RALLY, _12_rally, true);
        processAndCheckNext(250.0, NONE, null, false);
        processAndCheckNext(246.05, NATURAL_REACTION, _6b, false);
        processAndCheckNext(238.55, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(229.9, DOWN_TREND, _6b3, false);
        processAndCheckNext(229.65, DOWN_TREND, _12_down, false);
        processAndCheckNext(232.0, NONE, null, false);
    }

    private void thirdQuarter() {
        processAndCheckNext(231.2, NONE, null, false);
        processAndCheckNext(228.4, DOWN_TREND, _12_down, false);
        processAndCheckNext(222.8, DOWN_TREND, _12_down, false);
        processAndCheckNext(220.5, DOWN_TREND, _12_down, false);
        processAndCheckNext(219.0, DOWN_TREND, _12_down, true);
        processAndCheckNext(223.6, NATURAL_RALLY, _6c, false);
        processAndCheckNext(227.0, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(231.5, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(228.8, NONE, null, false);
        processAndCheckNext(230.5, NONE, null, false);
        processAndCheckNext(233.9, NATURAL_RALLY, _12_rally, true);
        processAndCheckNext(232.0, NONE, null, false);
        processAndCheckNext(227.2, NATURAL_REACTION, _6b, false);
        processAndCheckNext(227.05, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(226.95, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(223.2, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(220.6, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(220.5, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(222.9, NONE, null, false);
        processAndCheckNext(229.0, SECONDARY_RALLY, _6g, false);
        processAndCheckNext(230.1, SECONDARY_RALLY, _12_secondary_rally, false);
        processAndCheckNext(236.7, UPPER_TREND, _5a, false);
        processAndCheckNext(233.05, NONE, null, false);
        processAndCheckNext(233.05, NONE, null, false);
        processAndCheckNext(236.15, NONE, null, false);
        processAndCheckNext(233.65, NONE, null, false);
        processAndCheckNext(235.55, NONE, null, false);
        processAndCheckNext(236.85, UPPER_TREND, _12_upper, false);
        processAndCheckNext(235.5, NONE, null, false);
        processAndCheckNext(240.0, UPPER_TREND, _12_upper, false);
        processAndCheckNext(243.9, UPPER_TREND, _12_upper, false);
        processAndCheckNext(246.6, UPPER_TREND, _12_upper, true);
        processAndCheckNext(245.15, NONE, null, false);
        processAndCheckNext(245.25, NONE, null, false);
        processAndCheckNext(241.05, NATURAL_REACTION, _6a, false);
        processAndCheckNext(240.7, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(235.25, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(227.25, NATURAL_REACTION, _12_reaction, true);
        processAndCheckNext(235.4, NATURAL_RALLY, _6d, false);
        processAndCheckNext(240.0, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(245.5, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(246.35, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(245.1, NONE, null, false);
        processAndCheckNext(242.7, NONE, null, false);
        processAndCheckNext(249.0, UPPER_TREND, _6d3, false);
        processAndCheckNext(249.15, UPPER_TREND, _12_upper, false);
        processAndCheckNext(244.35, NONE, null, false);
        processAndCheckNext(246.4, NONE, null, false);
        processAndCheckNext(248.05, NONE, null, false);
        processAndCheckNext(249.5, UPPER_TREND, _12_upper, false);
        processAndCheckNext(247.2, NONE, null, false);
        processAndCheckNext(245.9, NONE, null, false);
        processAndCheckNext(246.0, NONE, null, false);
        processAndCheckNext(246.0, NONE, null, false);
        processAndCheckNext(251.5, UPPER_TREND, _12_upper, false);
        processAndCheckNext(252.85, UPPER_TREND, _12_upper, false);
        processAndCheckNext(256.0, UPPER_TREND, _12_upper, true);
        processAndCheckNext(249.1, NATURAL_REACTION, _6a, false);
        processAndCheckNext(244.8, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(237.0, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(237.25, NONE, null, false);
        processAndCheckNext(238.4, NONE, null, false);
        processAndCheckNext(236.65, NATURAL_REACTION, _12_reaction, true);
        processAndCheckNext(239.7, NONE, null, false);
        processAndCheckNext(242.95, NATURAL_RALLY, _6d, false);
    }

    private void fourthQuarter() {
        processAndCheckNext(237.9, SECONDARY_REACTION, _6h, false);
        processAndCheckNext(234.6, NATURAL_REACTION, _6h3, false);
        processAndCheckNext(244.0, NATURAL_RALLY, _6d, false);
        processAndCheckNext(248.5, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(251.15, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(253.0, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(267.0, UPPER_TREND, _6d3, true);
        processAndCheckNext(263.0, NONE, null, false);
        processAndCheckNext(262.5, NONE, null, false);
        processAndCheckNext(260.85, NATURAL_REACTION, _6a, false);
        processAndCheckNext(258.4, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(253.4, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(251.5, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(250.75, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(251.8, NONE, null, false);
        processAndCheckNext(253.4, NONE, null, false);
        processAndCheckNext(253.5, NONE, null, false);
        processAndCheckNext(250.5, NATURAL_REACTION, _12_reaction, true);
        processAndCheckNext(251.5, NONE, null, false);
        processAndCheckNext(253.8, NONE, null, false);
        processAndCheckNext(255.45, NONE, null, false);
        processAndCheckNext(258.1, NATURAL_RALLY, _6d, false);
        processAndCheckNext(262.2, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(270.65, UPPER_TREND, _6d3, true);
        processAndCheckNext(270.0, NONE, null, false);
        processAndCheckNext(264.75, NATURAL_REACTION, _6a, false);
        processAndCheckNext(262.4, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(257.55, NATURAL_REACTION, _12_reaction, true);
        processAndCheckNext(266.0, NATURAL_RALLY, _6d, false);
        processAndCheckNext(263.05, NONE, null, false);
        processAndCheckNext(260.97, NONE, null, false);
        processAndCheckNext(263.5, NONE, null, false);
        processAndCheckNext(271.5, UPPER_TREND, _6d3, false);
        processAndCheckNext(278.2, UPPER_TREND, _12_upper, true);
        processAndCheckNext(271.5, NATURAL_REACTION, _6a, false);
        processAndCheckNext(272.9, NONE, null, false);
        processAndCheckNext(274.8, NONE, null, false);
        processAndCheckNext(265.75, NATURAL_REACTION, _12_reaction, true);
        processAndCheckNext(271.55, NATURAL_RALLY, _6d, false);
        processAndCheckNext(270.85, NONE, null, false);
        processAndCheckNext(268.2, NONE, null, false);
        processAndCheckNext(265.0, NATURAL_REACTION, _6b, false);
        processAndCheckNext(264.7, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(258.5, DOWN_TREND, _5b, false);
        processAndCheckNext(257.6, DOWN_TREND, _12_down, false);
        processAndCheckNext(251.45, DOWN_TREND, _12_down, false);
        processAndCheckNext(244.0, DOWN_TREND, _12_down, true);
        processAndCheckNext(244.65, NONE, null, false);
        processAndCheckNext(247.7, NONE, null, false);
        processAndCheckNext(249.0, NATURAL_RALLY, _6c, false);
        processAndCheckNext(244.85, NONE, null, false);
        processAndCheckNext(244.9, NONE, null, false);
        processAndCheckNext(249.7, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(250.1, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(258.7, NATURAL_RALLY, _12_rally, true);
        processAndCheckNext(250.75, NATURAL_REACTION, _6b, false);
        processAndCheckNext(245.6, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(248.2, NONE, null, false);
        processAndCheckNext(253.65, SECONDARY_RALLY, _6g, false);
        processAndCheckNext(251.6, NONE, null, false);
        processAndCheckNext(245.8, SECONDARY_REACTION, _6h, false);
        processAndCheckNext(246.0, NONE, null, false);
        processAndCheckNext(255.0, SECONDARY_RALLY, _6g, false);
        processAndCheckNext(253.25, NONE, null, false);
    }

}
