package com.stocks.livermor.strategy.book;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.excel.ExcelWriter;
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
import static com.stocks.livermor.entity.Signal.DOWN_TREND_IS_OVER_BECAUSE_PRICE_JUMPS_FROM_NEAR_DOWN_TREND_POINT;
import static com.stocks.livermor.entity.Signal.UPPER_TREND_IS_OVER_BECAUSE_LAST_UPPER_PIVOT_POINT_IS_BROKEN_WEAK;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.strategy.book.CheckingMechanism.*;

public class KeyPriceTest {

    @Before
    public void init() {
        clear();

        Record firstNoDate = newRecord(325.65, DOWN_TREND, true);
        firstNoDate.setDate(NULL_DATE);

        Record secondNoDate = newRecord(335.39, NATURAL_RALLY, true);
        secondNoDate.setDate(NULL_DATE);

        Record thirdNoDate = newRecord(327.48, NATURAL_REACTION, false);
        thirdNoDate.setDate(NULL_DATE);

        Record forthNoDate = newRecord(337.3, NATURAL_RALLY, false);
        forthNoDate.setDate(NULL_DATE);

        Record fifthNoDate = newRecord(326.11, NATURAL_REACTION, false);
        fifthNoDate.setDate(NULL_DATE);

        addToRecordHolder(firstNoDate);
        addToRecordHolder(secondNoDate);
        addToRecordHolder(thirdNoDate);
        addToRecordHolder(forthNoDate);
        addToRecordHolder(fifthNoDate);
    }


    @Test
    public void test() throws Exception {
        firstQuarter();
        secondQuarter();
        thirdQuarter();
        fourthQuarter();
//        allQuarters2016();
        processAndSaveRecordsFromDatabase();
        new ExcelWriter().createTable(getRecordsHolder());
    }

    public void processAndSaveRecordsFromDatabase() {
        List<Candle> rosnCandles = filter2016(new CompanyDao().getByName("ROSN.ME").getCandles());
        List<Candle> gazpCandles = filter2016(new CompanyDao().getByName("GAZP.ME").getCandles());
        rosnCandles.sort(Comparator.comparing(Candle::getDate));
        gazpCandles.sort(Comparator.comparing(Candle::getDate));

        for (int i = 0; i < rosnCandles.size(); i++) {
            final Candle rosnCandle = rosnCandles.get(i);
            final Candle gazpCandle = gazpCandles.get(i);

            Record record = new Record(gazpCandle.getDate().getTime(), (rosnCandle.getClose().add(gazpCandle.getClose())).doubleValue());
            processWithNoCheck(record);
        }

        RecordDao recordDao = new RecordDao();
        for (Record record : getRecordsHolder().getRecords()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(record.getDate());
            if (calendar.get(Calendar.YEAR) >= 2016) {
                record.setTicker("GAZP.ME_ROSN.ME");
                recordDao.saveOrUpdate(record);
            }
        }
    }

    private void allQuarters2016() {
        processAndCheckNext(385.91, NONE, null, false, null);
        processAndCheckNext(389.65, NONE, null, false, null);
        processAndCheckNext(388.34, NONE, null, false, null);
        processAndCheckNext(372.43, DOWN_TREND, _11b, false, null);
        processAndCheckNext(373.44, NONE, null, false, null);
        processAndCheckNext(370.9, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(368.51, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(357.2, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(356.6, DOWN_TREND, _12_down, true, null);
        processAndCheckNext(362.03, NONE, null, false, null);
        processAndCheckNext(357.73, NONE, null, false, null);
        processAndCheckNext(365.25, NATURAL_RALLY, _6c, false, null);
        processAndCheckNext(382.8, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(388.0, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(387.79, NONE, null, false, null);
        processAndCheckNext(398.4, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(403.85, UPPER_TREND, _5a, false, null);
        processAndCheckNext(409.05, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(403.7, NONE, null, false, null);
        processAndCheckNext(404.3, NONE, null, false, null);
        processAndCheckNext(404.11, NONE, null, false, null);
        processAndCheckNext(418.07, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(415.98, NONE, null, false, null);
        processAndCheckNext(409.55, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(401.3, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(398.53, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(395.34, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(402.0, NONE, null, false, null);
        processAndCheckNext(404.43, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(413.25, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(417.0, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(421.35, UPPER_TREND, _6d3, false, null);
        processAndCheckNext(415.3, NONE, null, false, null);
        processAndCheckNext(413.54, NONE, null, false, null);
        processAndCheckNext(424.9, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(418.85, NONE, null, false, null);
        processAndCheckNext(417.47, NONE, null, false, null);
        processAndCheckNext(421.97, NONE, null, false, null);
        processAndCheckNext(429.0, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(431.9, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(427.14, NONE, null, false, null);
        processAndCheckNext(438.75, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(448.41, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(451.15, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(443.43, NONE, null, false, null);
        processAndCheckNext(448.49, NONE, null, false, null);
        processAndCheckNext(445.9, NONE, null, false, null);
        processAndCheckNext(444.25, NONE, null, false, null);
        processAndCheckNext(443.14, NONE, null, false, null);
        processAndCheckNext(446.0, NONE, null, false, null);
        processAndCheckNext(456.67, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(466.2, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(462.9, NONE, null, false, null);
        processAndCheckNext(467.67, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(455.2, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(450.6, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(450.54, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(447.6, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(441.1, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(455.6, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(452.95, NONE, null, false, null);
        processAndCheckNext(449.0, NONE, null, false, null);
        processAndCheckNext(455.88, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(450.54, NONE, null, false, null);
        processAndCheckNext(453.25, NONE, null, false, null);
        processAndCheckNext(450.69, NONE, null, false, null);
        processAndCheckNext(462.2, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(472.3, UPPER_TREND, _6d3, false, null);
        processAndCheckNext(468.9, NONE, null, false, null);
        processAndCheckNext(475.42, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(464.03, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(457.74, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(450.2, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(467.7, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(484.0, UPPER_TREND, _6d3, false, null);
        processAndCheckNext(484.8, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(483.3, NONE, null, false, null);
        processAndCheckNext(487.2, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(494.85, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(495.94, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(506.5, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(519.47, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(508.4, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(497.0, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(488.27, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(480.35, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(484.05, NONE, null, false, null);
        processAndCheckNext(478.03, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(482.5, NONE, null, false, null);
        processAndCheckNext(480.67, NONE, null, false, null);
        processAndCheckNext(475.68, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(481.6, NONE, null, false, null);
        processAndCheckNext(478.33, NONE, null, false, null);
        processAndCheckNext(471.35, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(457.41, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(463.35, NONE, null, false, null);
        processAndCheckNext(464.55, NONE, null, false, null);
        processAndCheckNext(469.02, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(470.48, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(471.6, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(461.5, SECONDARY_REACTION, _6h, false, null);
        processAndCheckNext(457.7, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(455.35, NATURAL_REACTION, _6h3, false, null);
        processAndCheckNext(458.16, NONE, null, false, null);
        processAndCheckNext(470.25, SECONDARY_RALLY, _6g, false, null);
        processAndCheckNext(476.6, NATURAL_RALLY, _6g3, false, null);
        processAndCheckNext(489.45, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(488.48, NONE, null, false, null);
        processAndCheckNext(480.29, NONE, null, false, null);
        processAndCheckNext(466.64, SECONDARY_REACTION, _6h, false, null);
        processAndCheckNext(464.42, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(455.8, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(459.97, NONE, null, false, null);
        processAndCheckNext(478.97, SECONDARY_RALLY, _6g, false, null);
        processAndCheckNext(484.49, SECONDARY_RALLY, _12_secondary_rally, false, null);
        processAndCheckNext(480.75, NONE, null, false, null);
        processAndCheckNext(492.2, NATURAL_RALLY, _6g3, false, null);
        processAndCheckNext(474.9, SECONDARY_REACTION, _6h, false, null);
        processAndCheckNext(466.57, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(466.24, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(471.5, NONE, null, false, null);
        processAndCheckNext(469.51, NONE, null, false, null);
        processAndCheckNext(472.5, NONE, null, false, null);
        processAndCheckNext(472.8, NONE, null, false, null);
        processAndCheckNext(470.35, NONE, null, false, null);
        processAndCheckNext(460.49, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(461.27, NONE, null, false, null);
        processAndCheckNext(465.27, NONE, null, false, null);
        processAndCheckNext(469.75, SECONDARY_RALLY, _6g, false, null);
        processAndCheckNext(481.52, SECONDARY_RALLY, _12_secondary_rally, false, null);
        processAndCheckNext(480.1, NONE, null, false, null);
        processAndCheckNext(480.7, NONE, null, false, null);
        processAndCheckNext(482.37, SECONDARY_RALLY, _12_secondary_rally, false, null);
        processAndCheckNext(479.54, NONE, null, false, null);
        processAndCheckNext(468.98, SECONDARY_REACTION, _6h, false, null);
        processAndCheckNext(468.31, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(474.4, NONE, null, false, null);
        processAndCheckNext(474.9, NONE, null, false, null);
        processAndCheckNext(474.7, NONE, null, false, null);
        processAndCheckNext(470.69, NONE, null, false, null);
        processAndCheckNext(469.4, NONE, null, false, null);
        processAndCheckNext(473.9, NONE, null, false, null);
        processAndCheckNext(462.8, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(462.53, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(451.85, DOWN_TREND, _5b, true, null);
        processAndCheckNext(457.29, NONE, null, false, null);
        processAndCheckNext(465.7, NATURAL_RALLY, _6c, false, null);
        processAndCheckNext(465.4, NONE, null, false, null);
        processAndCheckNext(466.47, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(467.24, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(464.0, NONE, null, false, null);
        processAndCheckNext(462.24, NONE, null, false, null);
        processAndCheckNext(468.68, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(477.0, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(481.49, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(483.23, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(490.25, NATURAL_RALLY, _12_rally, true, null);
        processAndCheckNext(485.85, NONE, null, false, null);
        processAndCheckNext(489.55, NONE, null, false, null);
//        processAndCheckNext(488.37, NONE, null, false);
//        processAndCheckNext(487.2, NONE, null, false);
//        processAndCheckNext(483.62, NONE, null, false);
//        processAndCheckNext(486.75, NONE, null, false);
        processAndCheckNext(486.8, NONE, null, false, null);
        processAndCheckNext(483.59, NONE, null, false, null);
        processAndCheckNext(479.65, NATURAL_REACTION, _6b, false, null);
        processAndCheckNext(476.29, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(485.45, NONE, null, false, null);
        processAndCheckNext(489.65, SECONDARY_RALLY, _6g, false, null);
        processAndCheckNext(499.4, UPPER_TREND, _5a, false, null);
        processAndCheckNext(507.08, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(506.2, NONE, null, false, null);
        processAndCheckNext(500.94, NONE, null, false, null);
        processAndCheckNext(505.93, NONE, null, false, null);
        processAndCheckNext(502.35, NONE, null, false, null);
//        processAndCheckNext(506.5, NONE, null, false);
//        processAndCheckNext(505.5, NONE, null, false);
//        processAndCheckNext(497.32, NONE, null, false);
//        processAndCheckNext(497.79, NONE, null, false);
        processAndCheckNext(495.8, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(496.25, NONE, null, false, null);
        processAndCheckNext(497.15, NONE, null, false, null);
        processAndCheckNext(492.75, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(485.61, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(474.92, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(475.39, NONE, null, false, null);
        processAndCheckNext(484.51, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(477.7, NONE, null, false, null);
        processAndCheckNext(481.37, NONE, null, false, null);
        processAndCheckNext(482.83, NONE, null, false, null);
        processAndCheckNext(476.5, NONE, null, false, null);
        processAndCheckNext(479.3, NONE, null, false, null);
        processAndCheckNext(477.6, NONE, null, false, null);
        processAndCheckNext(500.1, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(501.03, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(501.22, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(496.87, NONE, null, false, null);
        processAndCheckNext(490.05, SECONDARY_REACTION, _6h, false, null);
        processAndCheckNext(483.57, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(481.56, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(480.59, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(477.52, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(479.26, NONE, null, false, null);
        processAndCheckNext(482.8, NONE, null, false, null);
        processAndCheckNext(484.0, NONE, null, false, null);
        processAndCheckNext(482.92, NONE, null, false, null);
        processAndCheckNext(481.74, NONE, null, false, null);
        processAndCheckNext(484.9, NONE, null, false, null);
        processAndCheckNext(486.49, NONE, null, false, null);
        processAndCheckNext(496.78, SECONDARY_RALLY, _6g, false, null);
        processAndCheckNext(488.96, NONE, null, false, null);
        processAndCheckNext(481.9, SECONDARY_REACTION, _6h, false, null);
        processAndCheckNext(479.5, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(479.82, NONE, null, false, null);
        processAndCheckNext(493.22, SECONDARY_RALLY, _6g, false, null);
        processAndCheckNext(498.45, SECONDARY_RALLY, _12_secondary_rally, false, null);
        processAndCheckNext(489.98, NONE, null, false, null);
        processAndCheckNext(483.96, SECONDARY_REACTION, _6h, false, null);
        processAndCheckNext(483.9, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(484.75, NONE, null, false, null);
        processAndCheckNext(485.85, NONE, null, false, null);
        processAndCheckNext(483.96, NONE, null, false, null);
        processAndCheckNext(490.15, NONE, null, false, null);
        processAndCheckNext(491.8, NONE, null, false, null);
        processAndCheckNext(496.94, SECONDARY_RALLY, _6g, false, null);
        processAndCheckNext(497.66, SECONDARY_RALLY, _12_secondary_rally, false, null);
        processAndCheckNext(492.81, NONE, null, false, null);
        processAndCheckNext(485.54, SECONDARY_REACTION, _6h, false, null);
        processAndCheckNext(484.5, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(489.0, NONE, null, false, null);
        processAndCheckNext(491.31, NONE, null, false, null);
        processAndCheckNext(492.35, NONE, null, false, null);
        processAndCheckNext(505.28, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(504.15, NONE, null, false, null);
        processAndCheckNext(508.2, UPPER_TREND, _6d3, false, null);
        processAndCheckNext(528.9, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(523.79, NONE, null, false, null);
        processAndCheckNext(546.78, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(556.75, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(560.25, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(558.7, NONE, null, false, null);
        processAndCheckNext(566.7, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(557.2, NONE, null, false, null);
        processAndCheckNext(555.45, NONE, null, false, null);
        processAndCheckNext(552.71, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(541.87, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(531.9, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(537.6, NONE, null, false, null);
        processAndCheckNext(548.38, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(549.2, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(551.42, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(557.35, NATURAL_RALLY, _12_rally, false, null);
    }

    private void firstQuarter() {
        processAndCheckNext(330.7, NONE, null, false, DOWN_TREND_IS_OVER_BECAUSE_PRICE_JUMPS_FROM_NEAR_DOWN_TREND_POINT);
        processAndCheckNext(339.22, UPPER_TREND, _5a, false, null);
        processAndCheckNext(361.41, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(348.3, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(344.22, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(347.54, NONE, null, false, null);
        processAndCheckNext(355.8, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(366.9, UPPER_TREND, _6d3, false, null);
        processAndCheckNext(375.6, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(373.41, NONE, null, false, null);
        processAndCheckNext(372.75, NONE, null, false, null);
        processAndCheckNext(383.4, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(393.3, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(396.73, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(385.25, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(389.9, NONE, null, false, null);
        processAndCheckNext(379.2, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(371.55, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(373.17, NONE, null, false, null);
        processAndCheckNext(368.73, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(378.7, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(381.6, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(398.6, UPPER_TREND, _6d3, false, null);
        processAndCheckNext(412.6, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(406.3, NONE, null, false, null);
        processAndCheckNext(406.36, NONE, null, false, null);
        processAndCheckNext(415.9, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(431.75, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(452.6, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(439.89, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(435.15, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(444.45, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(436.65, NONE, null, false, null);
        processAndCheckNext(432.0, NATURAL_REACTION, _6b, false, null);
        processAndCheckNext(423.64, DOWN_TREND, _5b, false, null);
        processAndCheckNext(414.15, DOWN_TREND, _12_down, true, null);
        processAndCheckNext(415.5, NONE, null, false, null);
        processAndCheckNext(418.8, NONE, null, false, null);
        processAndCheckNext(425.2, NATURAL_RALLY, _6c, false, null);
        processAndCheckNext(436.0, NATURAL_RALLY, _12_rally, true, null);
        processAndCheckNext(418.95, NATURAL_REACTION, _6b, false, null);
        processAndCheckNext(424.4, NONE, null, false, null);
        processAndCheckNext(415.62, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(401.05, DOWN_TREND, _6b3, false, null);
        processAndCheckNext(397.44, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(392.4, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(383.1, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(376.45, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(376.7, NONE, null, false, null);
        processAndCheckNext(383.65, NONE, null, false, null);
        processAndCheckNext(373.86, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(375.3, NONE, null, false, null);
        processAndCheckNext(374.56, NONE, null, false, null);
        processAndCheckNext(381.15, NONE, null, false, null);
        processAndCheckNext(377.6, NONE, null, false, null);
        processAndCheckNext(369.3, DOWN_TREND, _12_down, true, null);
        processAndCheckNext(372.13, NONE, null, false, null);
        processAndCheckNext(384.5, NATURAL_RALLY, _6c, false, null);
        processAndCheckNext(391.05, NATURAL_RALLY, _12_rally, false, null);
    }

    private void secondQuarter() {
        processAndCheckNext(404.35, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(406.45, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(407.64, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(411.85, NATURAL_RALLY, _12_rally, true, null);
        processAndCheckNext(410.3, NONE, null, false, null);
        processAndCheckNext(410.97, NONE, null, false, null);
        processAndCheckNext(406.65, NONE, null, false, null);
        processAndCheckNext(404.95, NONE, null, false, null);
        // and some more nones
        processAndCheckNext(393.9, NATURAL_REACTION, _6b, false, null);
        processAndCheckNext(404.5, SECONDARY_RALLY, _6g, false, null);
        processAndCheckNext(411.65, SECONDARY_RALLY, _12_secondary_rally, false, null);
        processAndCheckNext(405.3, NONE, null, false, null);
        processAndCheckNext(414.46, NATURAL_RALLY, _6g3, false, null);
        processAndCheckNext(415.05, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(403.2, SECONDARY_REACTION, _6h, false, null);
        processAndCheckNext(399.25, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(412.09, SECONDARY_RALLY, _6g, false, null);
        processAndCheckNext(408.0, NONE, null, false, null);
        processAndCheckNext(409.3, NONE, null, false, null);
        processAndCheckNext(407.5, NONE, null, false, null);
        processAndCheckNext(410.0, NONE, null, false, null);
        processAndCheckNext(417.83, UPPER_TREND, _5a, true, null);
        processAndCheckNext(415.15, NONE, null, false, null);
        processAndCheckNext(407.35, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(414.97, NONE, null, false, null);
        processAndCheckNext(412.55, NONE, null, false, null);
        processAndCheckNext(404.19, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(407.5, NONE, null, false, null);
        processAndCheckNext(410.43, NONE, null, false, null);
        processAndCheckNext(408.0, NONE, null, false, null);
        processAndCheckNext(402.1, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(392.25, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(392.81, NONE, null, false, null);
        processAndCheckNext(398.7, NONE, null, false, null);
        processAndCheckNext(391.29, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(390.4, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(395.16, NONE, null, false, null);
        processAndCheckNext(391.91, NONE, null, false, null);
        processAndCheckNext(373.0, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(375.4, NONE, null, false, null);
        // some more nones..
        processAndCheckNext(379.15, NONE, null, false, null);
        processAndCheckNext(382.26, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(391.17, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(391.6, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(390.6, NONE, null, false, null);
        processAndCheckNext(388.35, NONE, null, false, null);
        processAndCheckNext(391.7, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(393.5, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(392.15, NONE, null, false, null);
        processAndCheckNext(397.15, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(400.1, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(396.23, NONE, null, false, null);
        processAndCheckNext(392.73, NONE, null, false, null);
        processAndCheckNext(382.25, SECONDARY_REACTION, _6h, false, null);
        processAndCheckNext(375.08, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(373.24, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(377.85, NONE, null, false, null);
    }

    private void thirdQuarter() {
        processAndCheckNext(377.5, NONE, null, false, null);
        processAndCheckNext(377.0, NONE, null, false, null);
        processAndCheckNext(372.73, NATURAL_REACTION, _6h3, false, null);
        processAndCheckNext(365.8, DOWN_TREND, _5b, false, null);
        processAndCheckNext(363.0, DOWN_TREND, _12_down, false, null);
        processAndCheckNext(360.5, DOWN_TREND, _12_down, true, null);
        processAndCheckNext(367.37, NONE, null, false, null);
        processAndCheckNext(370.9, NATURAL_RALLY, _6c, false, null);
        processAndCheckNext(376.77, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(375.3, NONE, null, false, null);
        processAndCheckNext(373.2, NONE, null, false, null);
        processAndCheckNext(378.1, NATURAL_RALLY, _12_rally, true, null);
        processAndCheckNext(375.6, NONE, null, false, null);
        processAndCheckNext(368.98, NATURAL_REACTION, _6b, false, null);
        processAndCheckNext(368.46, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(367.75, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(361.57, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(357.01, DOWN_TREND, _6b3, false, null);
        processAndCheckNext(355.01, DOWN_TREND, _12_down, true, null);
        processAndCheckNext(357.95, NONE, null, false, null);
        processAndCheckNext(367.7, NATURAL_RALLY, _6c, false, null);
        processAndCheckNext(369.9, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(379.2, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(374.5, NONE, null, false, null);
        processAndCheckNext(374.94, NONE, null, false, null);
        processAndCheckNext(379.93, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(374.53, NONE, null, false, null);
        processAndCheckNext(376.89, NONE, null, false, null);
        processAndCheckNext(378.35, NONE, null, false, null);
        processAndCheckNext(377.47, NONE, null, false, null);
        processAndCheckNext(382.99, UPPER_TREND, _5a, false, null);
        processAndCheckNext(387.6, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(391.4, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(389.4, NONE, null, false, null);
        processAndCheckNext(385.9, NONE, null, false, null);
        processAndCheckNext(381.85, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(383.34, NONE, null, false, null);
        processAndCheckNext(375.54, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(362.6, NATURAL_REACTION, _12_reaction, true, null);

        processAndCheckNext(375.8, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(380.51, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(390.1, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(392.95, UPPER_TREND, _6d3, false, null);
        processAndCheckNext(393.29, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(387.2, NONE, null, false, null);
        processAndCheckNext(391.57, NONE, null, false, null);
        processAndCheckNext(394.45, UPPER_TREND, _12_upper, false, null);
        // a lot of nones
        processAndCheckNext(395.65, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(395.5, NONE, null, false, null);
        processAndCheckNext(388.5, NONE, null, false, null);
        processAndCheckNext(381.2, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(371.3, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(369.45, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(372.25, NONE, null, false, null);
        processAndCheckNext(367.55, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(377.5, NATURAL_RALLY, _6d, false, null);
    }

    private void fourthQuarter() {
        processAndCheckNext(366.1, NATURAL_REACTION, _6b, false, null);
        processAndCheckNext(380.8, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(386.37, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(391.15, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(392.8, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(410.81, UPPER_TREND, _6d3, true, null);
        processAndCheckNext(404.7, NONE, null, false, null);
        processAndCheckNext(404.3, NONE, null, false, null);
        processAndCheckNext(402.86, NONE, null, false, null);
        processAndCheckNext(401.01, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(394.4, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(391.77, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(391.15, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(391.29, NONE, null, false, null);
        processAndCheckNext(392.65, NONE, null, false, null);
        processAndCheckNext(392.44, NONE, null, false, null);
        processAndCheckNext(389.0, NATURAL_REACTION, _12_reaction, true, null);
        processAndCheckNext(390.0, NONE, null, false, null);
        // more nones
        processAndCheckNext(393.85, NONE, null, false, null);
        processAndCheckNext(400.0, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(412.4, UPPER_TREND, _6d3, true, null);
        processAndCheckNext(410.97, NONE, null, false, null);
        processAndCheckNext(402.5, SECONDARY_REACTION, _6aa, false, UPPER_TREND_IS_OVER_BECAUSE_LAST_UPPER_PIVOT_POINT_IS_BROKEN_WEAK);
        processAndCheckNext(398.9, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(393.13, SECONDARY_REACTION, _12_secondary_reaction, false, null);
        processAndCheckNext(401.0, NATURAL_RALLY, _6d, false, null);
        processAndCheckNext(399.65, NONE, null, false, null);
        processAndCheckNext(396.7, NONE, null, false, null);
        processAndCheckNext(400.71, NONE, null, false, null);
        processAndCheckNext(414.25, UPPER_TREND, _6d3, false, null);
        processAndCheckNext(425.67, UPPER_TREND, _12_upper, false, null);
        processAndCheckNext(419.0, NONE, null, false, null);
        processAndCheckNext(420.95, NONE, null, false, null);
        processAndCheckNext(426.8, UPPER_TREND, _12_upper, true, null);
        processAndCheckNext(411.4, NATURAL_REACTION, _6a, false, null);
        processAndCheckNext(415.54, NONE, null, false, null);
        processAndCheckNext(414.05, NONE, null, false, null);
        processAndCheckNext(408.85, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(403.0, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(402.12, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(395.35, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(397.6, NONE, null, false, null);
        processAndCheckNext(389.15, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(378.5, DOWN_TREND, _5b, false, null);
        processAndCheckNext(379.3, NONE, null, false, null);
        processAndCheckNext(383.5, NONE, null, false, null);
        processAndCheckNext(384.6, NONE, null, false, null);
        processAndCheckNext(378.65, NONE, null, false, null);
        processAndCheckNext(376.49, DOWN_TREND, _12_down, true, null);
        processAndCheckNext(384.1, NATURAL_RALLY, _6c, false, null);
        processAndCheckNext(384.6, NATURAL_RALLY, _12_rally, false, null);
        processAndCheckNext(396.6, NATURAL_RALLY, _12_rally, true, null);
        processAndCheckNext(383.0, NATURAL_REACTION, _6b, false, null);
        processAndCheckNext(377.1, NATURAL_REACTION, _12_reaction, false, null);
        processAndCheckNext(381.4, NONE, null, false, DOWN_TREND_IS_OVER_BECAUSE_PRICE_JUMPS_FROM_NEAR_DOWN_TREND_POINT);
        processAndCheckNext(388.95, SECONDARY_RALLY, _6g, false, null);
        processAndCheckNext(387.6, NONE, null, false, null);
        processAndCheckNext(380.28, SECONDARY_REACTION, _6h, false, null);
        processAndCheckNext(392.49, SECONDARY_RALLY, _6g, false, DOWN_TREND_IS_OVER_BECAUSE_PRICE_JUMPS_FROM_NEAR_DOWN_TREND_POINT);
        processAndCheckNext(389.34, NONE, null, false, null);
    }
}
