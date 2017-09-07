package com.stocks.livermor.utils;

import com.stocks.livermor.entity.Record;

import static com.stocks.livermor.Constants.BASIC_CHANGE_PERCENTAGE;
import static com.stocks.livermor.entity.Signal.DOWN_TREND_IS_OVER;
import static com.stocks.livermor.entity.Signal.UPPER_TREND_IS_OVER;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordUtils.anyRally;
import static com.stocks.livermor.utils.RecordUtils.anyReaction;
import static com.stocks.livermor.utils.RecordUtils.percentageChange;
import static com.stocks.livermor.utils.Trend.DOWN;
import static com.stocks.livermor.utils.Trend.UP;

public class RecordsSignageSearcher {

    public static void searchForSignals(RecordsHolder recordsHolder, Record newRecord) {
        searchForSignalsOnUpperTrendAndRally(recordsHolder, newRecord);
        searchForSignalsOnDownTrendAndReaction(recordsHolder, newRecord);
    }

    private static void searchForSignalsOnUpperTrendAndRally(RecordsHolder recordsHolder, Record newRecord) {
        final boolean rallyIsOver = newRecord.getState() != NATURAL_RALLY;
        if (!rallyIsOver || recordsHolder.currentTrend() != UP) return;

        final Record lastUpTrendPivotPoint = recordsHolder.getPivotPoints().last(UPPER_TREND);
        final Record lastRally = recordsHolder.lastWithState();

        boolean rallyIsCloseToDownTrend = percentageChange(lastUpTrendPivotPoint, lastRally) <= BASIC_CHANGE_PERCENTAGE;
        boolean reactionAfterRally = anyReaction(lastRally, newRecord);

        if (rallyIsCloseToDownTrend && reactionAfterRally && !recordsHolder.last().hasSignal())
            newRecord.setSignal(UPPER_TREND_IS_OVER);
    }

    private static void searchForSignalsOnDownTrendAndReaction(RecordsHolder recordsHolder, Record newRecord) {
        final boolean reactionIsOver = newRecord.getState() != NATURAL_REACTION;
        if (!reactionIsOver || recordsHolder.currentTrend() != DOWN) return;

        final Record lastDownTrendPivotPoint = recordsHolder.getPivotPoints().last(DOWN_TREND);
        final Record lastReaction = recordsHolder.lastWithState();

        boolean reactionIsCloseToDownTrend = percentageChange(lastReaction, lastDownTrendPivotPoint) <= BASIC_CHANGE_PERCENTAGE;
        boolean rallyAfterReaction = anyRally(lastReaction, newRecord);

        if (reactionIsCloseToDownTrend && rallyAfterReaction && !recordsHolder.last().hasSignal())
            newRecord.setSignal(DOWN_TREND_IS_OVER);
    }
}
