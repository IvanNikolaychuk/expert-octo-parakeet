package com.stocks.livermor.utils;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.State;

import java.util.List;

import static com.stocks.livermor.Constants.BASIC_CHANGE_PERCENTAGE;
import static com.stocks.livermor.entity.Signal.*;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordUtils.*;
import static com.stocks.livermor.utils.Trend.DOWN;
import static com.stocks.livermor.utils.Trend.UP;

public class RecordsSignageSearcher {

    public static void searchForSignals(RecordsHolder recordsHolder, Record newRecord) {
        if (recordsHolder.lastWithState().getState() != UPPER_TREND
                && recordsHolder.lastWithState().getState() != DOWN_TREND
                && newRecord.getState() != UPPER_TREND && newRecord.getState() != DOWN_TREND) {
            searchForSignalsOnJumpingFromUnbrokenUpperTrend(recordsHolder, newRecord);
            searchForSignalsOnJumpingFromUnbrokenDownTrend(recordsHolder, newRecord);
        }
        searchForSignalsOnWeakDownTrend(recordsHolder, newRecord);
        searchForSignalsOnWeakUpperTrend(recordsHolder, newRecord);
    }

    private static void searchForSignalsOnWeakUpperTrend(RecordsHolder recordsHolder, Record newRecord) {
        if (recordsHolder.currentTrend() != UP
                || recordsHolder.lastWithState().getState() != UPPER_TREND
                || newRecord.getState() == UPPER_TREND
                || !newRecord.hasState()) return;

        List<Record> allRecords = recordsHolder.getPivotPoints().getRecords();
        allRecords.sort(new RecordsHolder.ByDateComparator().reversed());

        Record lastUpperTrend = null;
        Record beforeLastUpperTrend = null;

        for (Record record : allRecords) {
            final State state = record.getState();
            if (state == UPPER_TREND) {
                if (lastUpperTrend == null) lastUpperTrend = record;
                else if (beforeLastUpperTrend == null) beforeLastUpperTrend = record;
            }
            if (state == DOWN_TREND || state == NATURAL_RALLY) break;
        }

        if (lastUpperTrend == null || beforeLastUpperTrend == null) return;
        if (percentageChange(lastUpperTrend, beforeLastUpperTrend) <= BASIC_CHANGE_PERCENTAGE)
            newRecord.setSignal(UPPER_TREND_IS_OVER_BECAUSE_LAST_UPPER_PIVOT_POINT_IS_BROKEN_WEAK);

    }

    private static void searchForSignalsOnWeakDownTrend(RecordsHolder recordsHolder, Record newRecord) {
        if (recordsHolder.currentTrend() != DOWN) return;
        if (recordsHolder.lastWithState().getState() != DOWN_TREND) return;
        if (newRecord.getState() == DOWN_TREND || !newRecord.hasState()) return;

        List<Record> allRecords = recordsHolder.getPivotPoints().getRecords();
        allRecords.sort(new RecordsHolder.ByDateComparator().reversed());

        Record lastDownTrend = null;
        Record beforeLastDownTrend = null;

        for (Record record : allRecords) {
            final State state = record.getState();
            if (state == DOWN_TREND) {
                if (lastDownTrend == null) lastDownTrend = record;
                else if (beforeLastDownTrend == null) beforeLastDownTrend = record;
            }
            if (state == UPPER_TREND || state == NATURAL_REACTION) break;
        }

        if (lastDownTrend == null || beforeLastDownTrend == null) return;
        if (percentageChange(beforeLastDownTrend, lastDownTrend) <= BASIC_CHANGE_PERCENTAGE)
            newRecord.setSignal(DOWN_TREND_IS_OVER_BECAUSE_LAST_DOWN_PIVOT_POINT_IS_BROKEN_WEAK);

    }

    private static void searchForSignalsOnJumpingFromUnbrokenUpperTrend(RecordsHolder recordsHolder, Record newRecord) {
        final boolean rallyIsOver = newRecord.getState() != NATURAL_RALLY;
        if (!rallyIsOver || recordsHolder.currentTrend() != UP) return;

        final Record lastUpTrendPivotPoint = recordsHolder.getPivotPoints().last(UPPER_TREND);
        final Record lastRally = recordsHolder.lastWithState();

        boolean rallyIsCloseToDownTrend = percentageChange(lastUpTrendPivotPoint, lastRally) <= BASIC_CHANGE_PERCENTAGE;
        boolean reactionAfterRally = anyReaction(lastRally, newRecord);

        if (rallyIsCloseToDownTrend && reactionAfterRally && !recordsHolder.last().hasSignal())
            newRecord.setSignal(UPPER_TREND_IS_OVER_BECAUSE_PRICE_JUMPS_FROM_NEAR_UPPER_TREND_POINT);
    }

    private static void searchForSignalsOnJumpingFromUnbrokenDownTrend(RecordsHolder recordsHolder, Record newRecord) {
        final boolean reactionIsOver = newRecord.getState() != NATURAL_REACTION;
        if (!reactionIsOver || recordsHolder.currentTrend() != DOWN) return;

        final Record lastDownTrendPivotPoint = recordsHolder.getPivotPoints().last(DOWN_TREND);
        final Record lastReaction = recordsHolder.lastWithState();

        boolean reactionIsCloseToDownTrend = percentageChange(lastReaction, lastDownTrendPivotPoint) <= BASIC_CHANGE_PERCENTAGE;
        boolean rallyAfterReaction = anyRally(lastReaction, newRecord);

        if (reactionIsCloseToDownTrend && rallyAfterReaction && !recordsHolder.last().hasSignal())
            newRecord.setSignal(DOWN_TREND_IS_OVER_BECAUSE_PRICE_JUMPS_FROM_NEAR_DOWN_TREND_POINT);
    }
}
