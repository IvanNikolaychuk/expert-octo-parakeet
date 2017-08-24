package com.stocks.livermor.strategy.book;

import com.stocks.livermor.entity.Record;
import org.junit.Before;
import org.junit.Test;

import static com.stocks.livermor.constants.Constants.NULL_DATE;
import static com.stocks.livermor.constants.Constants.Rule.*;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.strategy.book.CheckingMechanism.*;
import static com.stocks.livermor.utils.RecordUtils.CHANGE_MEASURE;
import static com.stocks.livermor.utils.RecordUtils.ChangeMeasure.PERCENTAGE;

public class KeyPriceTest {

    @Before
    public void init() {
        clear();
        CHANGE_MEASURE = PERCENTAGE;

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
//        new ExcelWriter().createTable(getRecordsHolder());
    }

    private void firstQuarter() {
        processAndCheckNext(330.7, NONE, null, false);
        processAndCheckNext(339.22, NATURAL_RALLY, _6d, false);
        processAndCheckNext(361.41, UPPER_TREND, _5a, true);
        processAndCheckNext(348.3, NATURAL_REACTION, _6a, false);
        processAndCheckNext(344.22, NATURAL_REACTION, _12_reaction, true);
        processAndCheckNext(347.54, NONE, null, false);
        processAndCheckNext(355.8, NATURAL_RALLY, _6d, false);
        processAndCheckNext(366.9, UPPER_TREND, _6d3, false);
        processAndCheckNext(375.6, UPPER_TREND, _12_upper, false);
        processAndCheckNext(373.41, NONE, null, false);
        processAndCheckNext(372.75, NONE, null, false);
        processAndCheckNext(383.4, UPPER_TREND, _12_upper, false);
        processAndCheckNext(393.3, UPPER_TREND, _12_upper, false);
        processAndCheckNext(396.73, UPPER_TREND, _12_upper, true);
        processAndCheckNext(385.25, NATURAL_REACTION, _6a, false);
        processAndCheckNext(389.9, NONE, null, false);
        processAndCheckNext(379.2, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(371.55, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(373.17, NONE, null, false);
        processAndCheckNext(368.73, NATURAL_REACTION, _12_reaction, true);
        processAndCheckNext(378.7, NATURAL_RALLY, _6d, false);
        processAndCheckNext(381.6, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(398.6, UPPER_TREND, _6d3, false);
        processAndCheckNext(412.6, UPPER_TREND, _12_upper, false);
        processAndCheckNext(406.3, NONE, null, false);
        processAndCheckNext(406.36, NONE, null, false);
        processAndCheckNext(415.9, UPPER_TREND, _12_upper, false);
        processAndCheckNext(431.75, UPPER_TREND, _12_upper, false);
        processAndCheckNext(452.6, UPPER_TREND, _12_upper, true);
        processAndCheckNext(439.89, NATURAL_REACTION, _6a, false);
        processAndCheckNext(435.15, NATURAL_REACTION, _12_reaction, true);
        processAndCheckNext(444.45, NATURAL_RALLY, _6d, false);
        processAndCheckNext(436.65, NONE, null, false);
        processAndCheckNext(432.0, NATURAL_REACTION, _6b, false);
        processAndCheckNext(423.64, DOWN_TREND, _5b, false);
        processAndCheckNext(414.15, DOWN_TREND, _12_down, true);
        processAndCheckNext(415.5, NONE, null, false);
        processAndCheckNext(418.8, NONE, null, false);
        processAndCheckNext(425.2, NATURAL_RALLY, _6c, false);
        processAndCheckNext(436.0, NATURAL_RALLY, _12_rally, true);
        processAndCheckNext(418.95, NATURAL_REACTION, _6b, false);
        processAndCheckNext(424.4, NONE, null, false);
        processAndCheckNext(415.62, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(401.05, DOWN_TREND, _6b3, false);
        processAndCheckNext(397.44, DOWN_TREND, _12_down, false);
        processAndCheckNext(392.4, DOWN_TREND, _12_down, false);
        processAndCheckNext(383.1, DOWN_TREND, _12_down, false);
        processAndCheckNext(376.45, DOWN_TREND, _12_down, false);
        processAndCheckNext(376.7, NONE, null, false);
        processAndCheckNext(383.65, NONE, null, false);
        processAndCheckNext(373.86, DOWN_TREND, _12_down, false);
        processAndCheckNext(375.3, NONE, null, false);
        processAndCheckNext(374.56, NONE, null, false);
        processAndCheckNext(381.15, NONE, null, false);
        processAndCheckNext(377.6, NONE, null, false);
        processAndCheckNext(369.3, DOWN_TREND, _12_down, true);
        processAndCheckNext(372.13, NONE, null, false);
        processAndCheckNext(384.5, NATURAL_RALLY, _6c, false);
        processAndCheckNext(391.05, NATURAL_RALLY, _12_rally, false);
    }

    private void secondQuarter() {
        processAndCheckNext(404.35, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(406.45, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(407.64, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(411.85, NATURAL_RALLY, _12_rally, true);
        processAndCheckNext(410.3, NONE, null, false);
        processAndCheckNext(410.97, NONE, null, false);
        processAndCheckNext(406.65, NONE, null, false);
        processAndCheckNext(404.95, NONE, null, false);
        // and some more nones
        processAndCheckNext(393.9, NATURAL_REACTION, _6b, false);
        processAndCheckNext(404.5, SECONDARY_RALLY, _6g, false);
        processAndCheckNext(411.65, SECONDARY_RALLY, _12_secondary_rally, false);
        processAndCheckNext(405.3, NONE, null, false);
        processAndCheckNext(414.46, NATURAL_RALLY, _6g3, false);
        processAndCheckNext(415.05, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(403.2, SECONDARY_REACTION, _6h, false);
        processAndCheckNext(399.25, SECONDARY_REACTION, _12_secondary_reaction, false);
        processAndCheckNext(412.09, SECONDARY_RALLY, _6g, false);
        processAndCheckNext(408.0, NONE, null, false);
        processAndCheckNext(409.3, NONE, null, false);
        processAndCheckNext(407.5, NONE, null, false);
        processAndCheckNext(410.0, NONE, null, false);
        processAndCheckNext(417.83, UPPER_TREND, _5a, true);
        processAndCheckNext(415.15, NONE, null, false);
        processAndCheckNext(407.35, NATURAL_REACTION, _6a, false);
        processAndCheckNext(414.97, NONE, null, false);
        processAndCheckNext(412.55, NONE, null, false);
        processAndCheckNext(404.19, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(407.5, NONE, null, false);
        processAndCheckNext(410.43, NONE, null, false);
        processAndCheckNext(408.0, NONE, null, false);
        processAndCheckNext(402.1, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(392.25, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(392.81, NONE, null, false);
        processAndCheckNext(398.7, NONE, null, false);
        processAndCheckNext(391.29, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(390.4, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(395.16, NONE, null, false);
        processAndCheckNext(391.91, NONE, null, false);
        processAndCheckNext(373.0, NATURAL_REACTION, _12_reaction, true);
        processAndCheckNext(375.4, NONE, null, false);
        // some more nones..
        processAndCheckNext(379.15, NONE, null, false);
        processAndCheckNext(382.26, NATURAL_RALLY, _6d, false);
        processAndCheckNext(391.17, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(391.6, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(390.6, NONE, null, false);
        processAndCheckNext(388.35, NONE, null, false);
        processAndCheckNext(391.7, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(393.5, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(392.15, NONE, null, false);
        processAndCheckNext(397.15, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(400.1, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(396.23, NONE, null, false);
        processAndCheckNext(392.73, NONE, null, false);
        processAndCheckNext(382.25, SECONDARY_REACTION, _6h, false);
        processAndCheckNext(375.08, SECONDARY_REACTION, _12_secondary_reaction, false);
        processAndCheckNext(373.24, SECONDARY_REACTION, _12_secondary_reaction, false);
        processAndCheckNext(377.85, NONE, null, false);
    }

    private void thirdQuarter() {
        processAndCheckNext(377.5, NONE, null, false);
        processAndCheckNext(377.0, NONE, null, false);
        processAndCheckNext(372.73, NATURAL_REACTION, _6h3, false);
        processAndCheckNext(365.8, DOWN_TREND, _6b3, false);
        processAndCheckNext(363.0, DOWN_TREND, _12_down, false);
        processAndCheckNext(360.5, DOWN_TREND, _12_down, true);
        processAndCheckNext(367.37, NONE, null, false);
        processAndCheckNext(370.9, NATURAL_RALLY, _6c, false);
        processAndCheckNext(376.77, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(375.3, NONE, null, false);
        processAndCheckNext(373.2, NONE, null, false);
        processAndCheckNext(378.1, NATURAL_RALLY, _12_rally, true);
        processAndCheckNext(375.6, NONE, null, false);
        processAndCheckNext(368.98, NATURAL_REACTION, _6b, false);
        processAndCheckNext(368.46, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(367.75, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(361.57, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(357.01, DOWN_TREND, _6b3, false);
        processAndCheckNext(355.01, DOWN_TREND, _12_down, true);
        processAndCheckNext(357.95, NONE, null, false);
        processAndCheckNext(367.7, NATURAL_RALLY, _6c, false);
        processAndCheckNext(369.9, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(379.2, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(374.5, NONE, null, false);
        processAndCheckNext(374.94, NONE, null, false);
        processAndCheckNext(379.93, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(374.53, NONE, null, false);
        processAndCheckNext(376.89, NONE, null, false);
        processAndCheckNext(378.35, NONE, null, false);
        processAndCheckNext(377.47, NONE, null, false);
        processAndCheckNext(382.99, UPPER_TREND, _5a, false);
        processAndCheckNext(387.6, UPPER_TREND, _12_upper, false);
        processAndCheckNext(391.4, UPPER_TREND, _12_upper, true);
        processAndCheckNext(389.4, NONE, null, false);
        processAndCheckNext(385.9, NONE, null, false);
        processAndCheckNext(381.85, NATURAL_REACTION, _6a, false);
        processAndCheckNext(383.34, NONE, null, false);
        processAndCheckNext(375.54, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(362.6, NATURAL_REACTION, _12_reaction, true);

        processAndCheckNext(375.8, NATURAL_RALLY, _6d, false);
        processAndCheckNext(380.51, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(390.1, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(392.95, UPPER_TREND, _6d3, false);
        processAndCheckNext(393.29, UPPER_TREND, _12_upper, false);
        processAndCheckNext(387.2, NONE, null, false);
        processAndCheckNext(391.57, NONE, null, false);
        processAndCheckNext(394.45, UPPER_TREND, _12_upper, false);
        // a lot of nones
        processAndCheckNext(395.65, UPPER_TREND, _12_upper, true);
        processAndCheckNext(395.5, NONE, null, false);
        processAndCheckNext(388.5, NONE, null, false);
        processAndCheckNext(381.2, NATURAL_REACTION, _6a, false);
        processAndCheckNext(371.3, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(369.45, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(372.25, NONE, null, false);
        processAndCheckNext(367.55, NATURAL_REACTION, _12_reaction, true);
        processAndCheckNext(377.5, NATURAL_RALLY, _6d, false);
    }

    private void fourthQuarter() {
        processAndCheckNext(366.1, NATURAL_REACTION, _6b, false);
        processAndCheckNext(380.8, NATURAL_RALLY, _6d, false);
        processAndCheckNext(386.37, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(391.15, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(392.8, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(410.81, UPPER_TREND, _6d3, true);
        processAndCheckNext(404.7, NONE, null, false);
        processAndCheckNext(404.3, NONE, null, false);
        processAndCheckNext(402.86, NONE, null, false);
        processAndCheckNext(401.01, NATURAL_REACTION, _6a, false);
        processAndCheckNext(394.4, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(391.77, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(391.15, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(391.29, NONE, null, false);
        processAndCheckNext(392.65, NONE, null, false);
        processAndCheckNext(392.44, NONE, null, false);
        processAndCheckNext(389.0, NATURAL_REACTION, _12_reaction, true);
        processAndCheckNext(390.0, NONE, null, false);
        // more nones
        processAndCheckNext(393.85, NONE, null, false);
        processAndCheckNext(400.0, NATURAL_RALLY, _6d, false);
        processAndCheckNext(412.4, UPPER_TREND, _6d3, true);
        processAndCheckNext(410.97, NONE, null, false);
        processAndCheckNext(402.5, SECONDARY_REACTION, _6aa, false);
        processAndCheckNext(398.9, SECONDARY_REACTION, _12_secondary_reaction, false);
        processAndCheckNext(393.13, SECONDARY_REACTION, _12_secondary_reaction, false);
        processAndCheckNext(401.0, NATURAL_RALLY, _6d, false);
        processAndCheckNext(399.65, NONE, null, false);
        processAndCheckNext(396.7, NONE, null, false);
        processAndCheckNext(400.71, NONE, null, false);
        processAndCheckNext(414.25, UPPER_TREND, _6d3, false);
        processAndCheckNext(425.67, UPPER_TREND, _12_upper, false);
        processAndCheckNext(419.0, NONE, null, false);
        processAndCheckNext(420.95, NONE, null, false);
        processAndCheckNext(426.8, UPPER_TREND, _12_upper, true);
        processAndCheckNext(411.4, NATURAL_REACTION, _6a, false);
        processAndCheckNext(415.54, NONE, null, false);
        processAndCheckNext(414.05, NONE, null, false);
        processAndCheckNext(408.85, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(403.0, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(402.12, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(395.35, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(397.6, NONE, null, false);
        processAndCheckNext(389.15, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(378.5, DOWN_TREND, _5b, false);
        processAndCheckNext(379.3, NONE, null, false);
        processAndCheckNext(383.5, NONE, null, false);
        processAndCheckNext(384.6, NONE, null, false);
        processAndCheckNext(378.65, NONE, null, false);
        processAndCheckNext(376.49, DOWN_TREND, _12_down, true);
        processAndCheckNext(384.1, NATURAL_RALLY, _6c, false);
        processAndCheckNext(384.6, NATURAL_RALLY, _12_rally, false);
        processAndCheckNext(396.6, NATURAL_RALLY, _12_rally, true);
        processAndCheckNext(383.0, NATURAL_REACTION, _6b, false);
        processAndCheckNext(377.1, NATURAL_REACTION, _12_reaction, false);
        processAndCheckNext(381.4, NONE, null, false);
        processAndCheckNext(388.95, SECONDARY_RALLY, _6g, false);
        processAndCheckNext(387.6, NONE, null, false);
        processAndCheckNext(380.28, SECONDARY_REACTION, _6h, false);
        processAndCheckNext(392.49, SECONDARY_RALLY, _6g, false);
        processAndCheckNext(389.34, NONE, null, false);

    }
}
