package com.stocks.livermor.strategy.book;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.excel.ExcelWriter;
import com.stocks.livermor.utils.RecordsHolder;
import com.stocks.technical.core.db.dao.CompanyDao;
import com.stocks.technical.core.db.dao.RecordDao;
import com.stocks.technical.core.db.entity.Candle;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import static com.stocks.livermor.Constants.NULL_DATE;
import static com.stocks.livermor.Constants.Rule.*;
import static com.stocks.livermor.entity.Signal.*;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.strategy.book.CheckingMechanism.*;

public class RosnTest {

    @Before
    public void init() {
        clear();
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
        allQuarters2016();
//        processAndSaveRecordsFromDatabase();
        new ExcelWriter().createTable(new RecordsHolder(getRecordsHolder().getRecords()));
    }

    public void processAndSaveRecordsFromDatabase() {
        List<Candle> gazpCandles = filter2016(new CompanyDao().getByName("ROSN.ME").getCandles());
        gazpCandles.sort(Comparator.comparing(Candle::getDate));

        for (Candle candle : gazpCandles) {
            Record record = new Record(candle.getDate().getTime(), candle.getClose().doubleValue());
            processWithNoCheck(record);
        }

        RecordDao recordDao = new RecordDao();
        for (Record record : getRecordsHolder().getRecords()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(record.getDate());
            if (calendar.get(Calendar.YEAR) >= 2016) {
                record.setTicker("ROSN.ME");
                recordDao.saveOrUpdate(record);
            }
        }
    }

    private void allQuarters2016() {
        processAndCheckNext(251.0, NONE, null, false, null);
        processAndCheckNext(253.2, NONE, null, false, null);
        processAndCheckNext(252.4, NONE, null, false, null);
        processAndCheckNext(241.05, DOWN_TREND, _11b, false, null);
        processAndCheckNext(239.9, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(239.6, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(237.65, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(232.1, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(232.0, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(236.05, NONE, null, false, null);
        processAndCheckNext(231.9, DOWN_TREND, _12_down, true, null);
        processAndCheckNext(237.5, NATURAL_RALLY, _6c, false, null);
        processAndCheckNext(250.4, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(255.0, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(255.9, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(264.4, UPPER_TREND, _5a, false, null);
        processAndCheckNext(267.35, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(272.45, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(269.8, NONE, null, false, null);
        processAndCheckNext(271.8, NONE, null, false, null);
        processAndCheckNext(273.1, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(283.0, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(281.55, NONE, null, false, null);
        processAndCheckNext(276.05, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(268.6, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(265.5, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(264.85, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(270.7, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(271.0, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(277.4, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(279.9, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(283.35, UPPER_TREND, _6d3, true, null);
        processAndCheckNext(278.4, NONE, null, false, null);
        processAndCheckNext(276.95, SECONDARY_REACTION, _6aa, false, UPPER_TREND_IS_OVER_BECAUSE_LAST_UPPER_PIVOT_POINT_IS_BROKEN_WEAK);
        processAndCheckNext(285.0, UPPER_TREND, _11a, false, null);
        processAndCheckNext(281.7, NONE, null, false, null);
        processAndCheckNext(279.5, NONE, null, false, null);
        processAndCheckNext(283.3, NONE, null, false, null);
        processAndCheckNext(287.6, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(289.9, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(287.0, NONE, null, false, null);
        processAndCheckNext(294.25, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(302.25, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(301.0, NONE, null, false, null);
        processAndCheckNext(296.0, NATURAL_REACTION, _6a, true, null);
//        processAndCheckNext(301.55,NONE,null,false);
//        processAndCheckNext(301.6,NONE,null,false);
//        processAndCheckNext(301.05,NONE,null,false);
//        processAndCheckNext(300.6,NONE,null,false);
        processAndCheckNext(301.25, NONE, null, false, null);
        processAndCheckNext(309.4, UPPER_TREND, _11a, false, null);
        processAndCheckNext(315.55, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(313.0, NONE, null, false, null);
        processAndCheckNext(318.05, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(309.2, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(304.8, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(304.5, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(302.5, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(296.4, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(307.8, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(305.2, NONE, null, false, null);
        processAndCheckNext(301.8, NONE, null, false, null);
        processAndCheckNext(309.5, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(306.2, NONE, null, false, null);
        processAndCheckNext(309.05, NONE, null, false, null);
        processAndCheckNext(306.8, NONE, null, false, null);
        processAndCheckNext(316.5, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(325.0, UPPER_TREND, _6d3, true, null);
        processAndCheckNext(320.0, NONE, null, false, null);
        processAndCheckNext(322.3, NONE, null, false, null);
        processAndCheckNext(313.6, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(309.75, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(303.9, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(311.75, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(322.05, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(323.0, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(322.1, NONE, null, false, null);
        processAndCheckNext(326.9, UPPER_TREND, _6d3, false, null);
        processAndCheckNext(331.85, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(334.95, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(341.75, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(351.0, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(346.0, NONE, null, false, null);
        processAndCheckNext(336.5, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(329.0, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(323.5, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(324.6, NONE, null, false, null);
        processAndCheckNext(319.0, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(322.65, NONE, null, false, null);
        processAndCheckNext(322.6, NONE, null, false, null);
        processAndCheckNext(322.1, NONE, null, false, null);
        processAndCheckNext(329.5, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(330.0, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(324.65, NONE, null, false, null);
        processAndCheckNext(313.1, DOWN_TREND, _5b, true, null);
        processAndCheckNext(317.0, NONE, null, false, null);
        processAndCheckNext(318.0, NONE, null, false, null);
        processAndCheckNext(320.15, NATURAL_RALLY, _6c, false, null);
        processAndCheckNext(320.6, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(322.0, NATURAL_RALLY, _12_rally, true, null);
        processAndCheckNext(316.0, NONE, null, false, null);
        processAndCheckNext(313.0, DOWN_TREND, _11b, false, null);
        processAndCheckNext(311.35, DOWN_TREND, _12_down, true, null);
        processAndCheckNext(314.75, NONE, null, false, null);
        processAndCheckNext(323.85, SECONDARY_RALLY, _6cc, false, DOWN_TREND_IS_OVER_BECAUSE_LAST_DOWN_PIVOT_POINT_IS_BROKEN_WEAK);
        processAndCheckNext(328.85, UPPER_TREND, _5a, false, null);
        processAndCheckNext(342.5, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(343.5, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(337.85, NONE, null, false, null);
        processAndCheckNext(324.8, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(322.85, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(316.3, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(320.65, NONE, null, false, null);
        processAndCheckNext(336.0, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(340.95, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(337.75, NONE, null, false, null);
        processAndCheckNext(348.0, UPPER_TREND, _6d3, true, null);
        processAndCheckNext(333.65, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(328.05, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(327.0, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(331.5, NONE, null, false, null);
        processAndCheckNext(330.0, NONE, null, false, null);
        processAndCheckNext(332.45, NONE, null, false, null);
        processAndCheckNext(331.2, NONE, null, false, null);
        processAndCheckNext(329.4, NONE, null, false, null);
        processAndCheckNext(320.5, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(320.9, NONE, null, false, null);
        processAndCheckNext(325.0, NONE, null, false, null);
        processAndCheckNext(328.35, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(339.4, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(336.5, NONE, null, false, null);
        processAndCheckNext(336.5, NONE, null, false, null);
        processAndCheckNext(335.0, NONE, null, false, null);
        processAndCheckNext(333.7, NONE, null, false, null);
        processAndCheckNext(329.5, SECONDARY_REACTION, _6h, false, null);
        processAndCheckNext(329.05, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(334.3, NONE, null, false, null);
        processAndCheckNext(334.3, NONE, null, false, null);
        processAndCheckNext(334.0, NONE, null, false, null);
        processAndCheckNext(330.0, NONE, null, false, null);
        processAndCheckNext(329.9, NONE, null, false, null);
        processAndCheckNext(334.0, NONE, null, false, null);
        processAndCheckNext(325.5, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(325.6, NONE, null, false, null);
        processAndCheckNext(317.35, NATURAL_REACTION, _6h3, false, null);
        processAndCheckNext(323.25, NONE, null, false, null);
        processAndCheckNext(329.0, SECONDARY_RALLY, _6g, false, null);
        processAndCheckNext(329.9, SECONDARY_RALLY, _12_secondary_rally, false, null);
        processAndCheckNext(329.0, NONE, null, false, null);
        processAndCheckNext(329.9, NONE, null, false, null);
        processAndCheckNext(326.6, NONE, null, false, null);
        processAndCheckNext(325.05, NONE, null, false, null);
        processAndCheckNext(330.85, SECONDARY_RALLY, _12_secondary_rally, false, null);
        processAndCheckNext(339.0, SECONDARY_RALLY, _12_secondary_rally, false, null);
        processAndCheckNext(343.5, NATURAL_RALLY, _6g3, false, null);
        processAndCheckNext(345.55, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(351.85, UPPER_TREND, _6d3, true, null);
        processAndCheckNext(349.05, NONE, null, false, null);
        processAndCheckNext(351.75, NONE, null, false, null);
        processAndCheckNext(351.0, NONE, null, false, null);
//        processAndCheckNext(350.2,NONE,null,false);
//        processAndCheckNext(348.2,NONE,null,false);
//        processAndCheckNext(350.55,NONE,null,false);
//        processAndCheckNext(350.6,NONE,null,false);
        processAndCheckNext(348.0, NONE, null, false, null);
        processAndCheckNext(344.7, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(341.3, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(349.9, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(353.6, UPPER_TREND, _6d3, false, null);
        processAndCheckNext(358.0, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(364.0, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(364.75, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(362.4, NONE, null, false, null);
        processAndCheckNext(367.0, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(363.9, NONE, null, false, null);
        processAndCheckNext(367.15, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(367.9, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(360.85, NONE, null, false, null);
        processAndCheckNext(361.2, NONE, null, false, null);
        processAndCheckNext(358.8, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(360.0, NONE, null, false, null);
        processAndCheckNext(360.45, NONE, null, false, null);
        processAndCheckNext(356.05, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(350.35, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(341.0, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(341.3, NONE, null, false, null);
        processAndCheckNext(349.2, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(342.8, NONE, null, false, null);
        processAndCheckNext(346.0, NONE, null, false, null);
        processAndCheckNext(346.6, NONE, null, false, null);
        processAndCheckNext(341.7, SECONDARY_REACTION, _6h, false, null);
        processAndCheckNext(343.4, NONE, null, false, null);
        processAndCheckNext(341.5, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(361.0, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(362.05, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(363.15, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(359.4, NONE, null, false, null);
        processAndCheckNext(353.85, SECONDARY_REACTION, _6h, false, null);
        processAndCheckNext(348.0, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(345.75, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(345.1, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(342.8, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(344.3, NONE, null, false, null);
        processAndCheckNext(347.75, NONE, null, false, null);
        processAndCheckNext(348.7, NONE, null, false, null);
        processAndCheckNext(347.6, NONE, null, false, null);
        processAndCheckNext(346.7, NONE, null, false, null);
        processAndCheckNext(349.9, SECONDARY_RALLY, _6g, false, null);
        processAndCheckNext(347.65, NONE, null, false, null);
        processAndCheckNext(354.8, SECONDARY_RALLY, _12_secondary_rally, false, null);
        processAndCheckNext(348.15, NONE, null, false, null);
        processAndCheckNext(342.0, SECONDARY_REACTION, _6h, false, null);
        processAndCheckNext(341.0, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(340.5, NATURAL_REACTION, _6h3, false, null);
        processAndCheckNext(347.6, SECONDARY_RALLY, _6g, false, null);
        processAndCheckNext(351.05, SECONDARY_RALLY, _12_secondary_rally, false, null);
        processAndCheckNext(342.1, SECONDARY_REACTION, _6h, false, null);
        processAndCheckNext(337.7, NATURAL_REACTION, _6h3, false, null);
        processAndCheckNext(338.0, NONE, null, false, null);
        processAndCheckNext(339.25, NONE, null, false, null);
        processAndCheckNext(338.15, NONE, null, false, null);
        processAndCheckNext(337.0, DOWN_TREND, _5b, true, null);
        processAndCheckNext(340.55, NONE, null, false, null);
        processAndCheckNext(341.8, NONE, null, false, null);
        processAndCheckNext(347.45, NATURAL_RALLY, _6c, true, null);
        processAndCheckNext(346.55, NONE, null, false, null);
        processAndCheckNext(341.5, NONE, null, false, null);
        processAndCheckNext(336.6, DOWN_TREND, _11b, true, null);
        processAndCheckNext(336.75, NONE, null, false, null);
        processAndCheckNext(340.2, NONE, null, false, null);
        processAndCheckNext(338.5, NONE, null, false, null);
        processAndCheckNext(340.0, NONE, null, false, null);
        processAndCheckNext(351.5, UPPER_TREND, _5a, false, DOWN_TREND_IS_OVER_BECAUSE_LAST_DOWN_PIVOT_POINT_IS_BROKEN_WEAK);
        processAndCheckNext(352.65, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(356.25, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(375.85, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(370.8, NONE, null, false, null);
        processAndCheckNext(388.15, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(397.8, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(403.05, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(400.45, NONE, null, false, null);
        processAndCheckNext(410.5, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(402.6, NONE, null, false, null);
        processAndCheckNext(400.15, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(399.65, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(391.6, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(382.85, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(388.65, NONE, null, false, null);
        processAndCheckNext(398.0, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(398.2, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(398.8, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(402.8, NATURAL_RALLY, _12_rally, false, null);
    }


    private void firstQuarter() {
        processAndCheckNext(200.3, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(214.95, UPPER_TREND, _6d3, true, null);
        processAndCheckNext(206.6, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(204.0, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(209.5, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(219.9, UPPER_TREND, _6d3, false, null);
        processAndCheckNext(226.0, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(225.3, NONE, null, false, null);
        processAndCheckNext(227.0, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(234.6, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(241.8, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(243.9, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(237.25, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(239.0, NONE, null, false, null);
        processAndCheckNext(231.5, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(226.55, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(238.35, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(239.0, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(251.0, UPPER_TREND, _6d3, false, null);
        processAndCheckNext(261.0, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(265.35, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(274.05, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(290.2, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(279.7, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(275.95, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(281.45, NONE, null, false, null);
        processAndCheckNext(275.75, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(274.0, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(268.65, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(263.45, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(269.7, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(277.0, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(264.95, SECONDARY_REACTION, _6h, false, null);
        processAndCheckNext(263.5, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(253.0, DOWN_TREND, _5b, false, null);
        processAndCheckNext(251.45, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(247.7, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(241.3, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(236.7, DOWN_TREND, _12_down, true, null);
        processAndCheckNext(237.0, NONE, null, false, null);
        processAndCheckNext(243.4, NATURAL_RALLY, _6c, true, null);
        processAndCheckNext(237.05, NATURAL_REACTION, _6b, false, null);
        processAndCheckNext(239.3, NONE, null, false, null);
        processAndCheckNext(239.95, NONE, null, false, DOWN_TREND_IS_OVER_BECAUSE_PRICE_JUMPS_FROM_NEAR_DOWN_TREND_POINT);
        processAndCheckNext(244.0, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(242.7, NONE, null, false, null);
        processAndCheckNext(237.1, SECONDARY_REACTION, _6h, false, null);
        processAndCheckNext(247.5, UPPER_TREND, _5a, false, null);
        processAndCheckNext(252.15, UPPER_TREND, _12_upper, false, null);
    }

    private void secondQuarter() {
        processAndCheckNext(261.35, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(262.55, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(263.7, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(266.15, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(258.55, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(256.95, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(255.5, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(249.5, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(254.4, NONE, null, false, null);
        processAndCheckNext(259.25, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(254.4, NONE, null, false, null);
        processAndCheckNext(260.5, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(259.7, NONE, null, false, null);
        processAndCheckNext(252.4, SECONDARY_REACTION, _6h, false, null);
        processAndCheckNext(249.1, NATURAL_REACTION, _6h3, false, null);
        processAndCheckNext(258.2, SECONDARY_RALLY, _6g, false, null);
        processAndCheckNext(256.5, NONE, null, false, null);
        processAndCheckNext(261.5, NATURAL_RALLY, _6g3, false, null);
        processAndCheckNext(259.9, NONE, null, false, null);
        processAndCheckNext(255.25, SECONDARY_REACTION, _6h, false, null);
        processAndCheckNext(259.45, NONE, null, false, null);
        processAndCheckNext(258.35, NONE, null, false, null);
        processAndCheckNext(252.45, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(255.7, NONE, null, false, null);
        processAndCheckNext(257.45, NONE, null, false, null);
        processAndCheckNext(256.1, NONE, null, false, null);
        processAndCheckNext(252.25, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(245.9, DOWN_TREND, _5b, false, null);
        processAndCheckNext(245.65, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(249.9, NONE, null, false, null);
        processAndCheckNext(244.6, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(245.15, NONE, null, false, null);
        processAndCheckNext(247.5, NONE, null, false, null);
        processAndCheckNext(245.8, NONE, null, false, null);
        processAndCheckNext(234.0, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(236.0, NONE, null, false, null);
        processAndCheckNext(237.5, NONE, null, false, null);
        processAndCheckNext(233.1, DOWN_TREND, _12_down, true, null);
        processAndCheckNext(236.25, NONE, null, false, null);
        processAndCheckNext(236.75, NONE, null, false, null);
        processAndCheckNext(239.6, NATURAL_RALLY, _6c, false, null);
        processAndCheckNext(245.0, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(246.0, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(245.2, NONE, null, false, null);
        processAndCheckNext(243.4, NONE, null, false, null);
        processAndCheckNext(245.7, NONE, null, false, null);
        processAndCheckNext(245.9, NONE, null, false, null);
        processAndCheckNext(245.0, NONE, null, false, null);
        processAndCheckNext(250.0, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(251.6, NATURAL_RALLY, _12_rally, true, null);
        processAndCheckNext(250.0, NONE, null, false, null);
        processAndCheckNext(246.05, NATURAL_REACTION, _6b, false, null);
        processAndCheckNext(238.55, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(229.9, DOWN_TREND, _6b3, false, null);
        processAndCheckNext(229.65, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(232.0, NONE, null, false, null);
    }

    private void thirdQuarter() {
        processAndCheckNext(231.2, NONE, null, false, null);
        processAndCheckNext(228.4, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(222.8, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(220.5, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(219.0, DOWN_TREND, _12_down, true, null);
        processAndCheckNext(223.6, NATURAL_RALLY, _6c, false, null);
        processAndCheckNext(227.0, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(231.5, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(228.8, NONE, null, false, null);
        processAndCheckNext(230.5, NONE, null, false, null);
        processAndCheckNext(233.9, NATURAL_RALLY, _12_rally, true, null);
        processAndCheckNext(232.0, NONE, null, false, null);
        processAndCheckNext(227.2, NATURAL_REACTION, _6b, false, null);
        processAndCheckNext(227.05, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(226.95, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(223.2, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(220.6, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(220.5, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(222.9, NONE, null, false, DOWN_TREND_IS_OVER_BECAUSE_PRICE_JUMPS_FROM_NEAR_DOWN_TREND_POINT);
        processAndCheckNext(229.0, SECONDARY_RALLY, _6g, false, null);
        processAndCheckNext(230.1, SECONDARY_RALLY, _12_secondary_rally, false, null);
        processAndCheckNext(236.7, UPPER_TREND, _5a, false, null);
        processAndCheckNext(233.05, NONE, null, false, null);
        processAndCheckNext(233.05, NONE, null, false, null);
        processAndCheckNext(236.15, NONE, null, false, null);
        processAndCheckNext(233.65, NONE, null, false, null);
        processAndCheckNext(235.55, NONE, null, false, null);
        processAndCheckNext(236.85, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(235.5, NONE, null, false, null);
        processAndCheckNext(240.0, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(243.9, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(246.6, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(245.15, NONE, null, false, null);
        processAndCheckNext(245.25, NONE, null, false, null);
        processAndCheckNext(241.05, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(240.7, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(235.25, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(227.25, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(235.4, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(240.0, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(245.5, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(246.35, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(245.1, NONE, null, false, null);
        processAndCheckNext(242.7, NONE, null, false, UPPER_TREND_IS_OVER_BECAUSE_PRICE_JUMPS_FROM_NEAR_UPPER_TREND_POINT);
        processAndCheckNext(249.0, UPPER_TREND, _6d3, false, null);
        processAndCheckNext(249.15, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(244.35, NONE, null, false, null);
        processAndCheckNext(246.4, NONE, null, false, null);
        processAndCheckNext(248.05, NONE, null, false, null);
        processAndCheckNext(249.5, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(247.2, NONE, null, false, null);
        processAndCheckNext(245.9, NONE, null, false, null);
        processAndCheckNext(246.0, NONE, null, false, null);
        processAndCheckNext(246.0, NONE, null, false, null);
        processAndCheckNext(251.5, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(252.85, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(256.0, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(249.1, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(244.8, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(237.0, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(237.25, NONE, null, false, null);
        processAndCheckNext(238.4, NONE, null, false, null);
        processAndCheckNext(236.65, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(239.7, NONE, null, false, null);
        processAndCheckNext(242.95, NATURAL_RALLY, _6d, false, null);
    }

    private void fourthQuarter() {
        processAndCheckNext(237.9, SECONDARY_REACTION, _6h, false, null);
        processAndCheckNext(234.6, NATURAL_REACTION, _6h3, false, null);
        processAndCheckNext(244.0, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(248.5, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(251.15, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(253.0, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(267.0, UPPER_TREND, _6d3, true, null);
        processAndCheckNext(263.0, NONE, null, false, null);
        processAndCheckNext(262.5, NONE, null, false, null);
        processAndCheckNext(260.85, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(258.4, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(253.4, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(251.5, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(250.75, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(251.8, NONE, null, false, null);
        processAndCheckNext(253.4, NONE, null, false, null);
        processAndCheckNext(253.5, NONE, null, false, null);
        processAndCheckNext(250.5, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(251.5, NONE, null, false, null);
        processAndCheckNext(253.8, NONE, null, false, null);
        processAndCheckNext(255.45, NONE, null, false, null);
        processAndCheckNext(258.1, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(262.2, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(270.65, UPPER_TREND, _6d3, true, null);
        processAndCheckNext(270.0, NONE, null, false, null);
        processAndCheckNext(264.75, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(262.4, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(257.55, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(266.0, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(263.05, NONE, null, false, null);
        processAndCheckNext(260.97, NONE, null, false, null);
        processAndCheckNext(263.5, NONE, null, false, null);
        processAndCheckNext(271.5, UPPER_TREND, _6d3, false, null);
        processAndCheckNext(278.2, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(271.5, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(272.9, NONE, null, false, null);
        processAndCheckNext(274.8, NONE, null, false, null);
        processAndCheckNext(265.75, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(271.55, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(270.85, NONE, null, false, null);
        processAndCheckNext(268.2, NONE, null, false, null);
        processAndCheckNext(265.0, NATURAL_REACTION, _6b, false, null);
        processAndCheckNext(264.7, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(258.5, DOWN_TREND, _5b, false, null);
        processAndCheckNext(257.6, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(251.45, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(244.0, DOWN_TREND, _12_down, true, null);
        processAndCheckNext(244.65, NONE, null, false, null);
        processAndCheckNext(247.7, NONE, null, false, null);
        processAndCheckNext(249.0, NATURAL_RALLY, _6c, false, null);
        processAndCheckNext(244.85, NONE, null, false, null);
        processAndCheckNext(244.9, NONE, null, false, null);
        processAndCheckNext(249.7, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(250.1, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(258.7, NATURAL_RALLY, _12_rally, true, null);
        processAndCheckNext(250.75, NATURAL_REACTION, _6b, false, null);
        processAndCheckNext(245.6, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(248.2, NONE, null, false, DOWN_TREND_IS_OVER_BECAUSE_PRICE_JUMPS_FROM_NEAR_DOWN_TREND_POINT);
        processAndCheckNext(253.65, SECONDARY_RALLY, _6g, false, null);
        processAndCheckNext(251.6, NONE, null, false, null);
        processAndCheckNext(245.8, SECONDARY_REACTION, _6h, false, null);
        processAndCheckNext(246.0, NONE, null, false, null);
        processAndCheckNext(255.0, SECONDARY_RALLY, _6g, false, DOWN_TREND_IS_OVER_BECAUSE_PRICE_JUMPS_FROM_NEAR_DOWN_TREND_POINT);
        processAndCheckNext(253.25, NONE, null, false, null);
    }

}
