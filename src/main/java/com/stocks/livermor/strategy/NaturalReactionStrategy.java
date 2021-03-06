package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.State;
import com.stocks.livermor.utils.RecordsHolder;

import static com.stocks.livermor.Constants.Rule;
import static com.stocks.livermor.Constants.Rule.*;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordPriceMovementUtils.*;
import static com.stocks.livermor.utils.RecordUtils.strongRally;
import static com.stocks.livermor.utils.RecordsSignageSearcher.searchForSignals;
import static com.stocks.livermor.utils.Trend.UP;
import static org.springframework.util.Assert.isTrue;

public class NaturalReactionStrategy implements StateProcessor {

    @Override
    public void process(RecordsHolder recordsHolder, Record newRecord) {
        processState(recordsHolder, newRecord);
        searchForSignals(recordsHolder, newRecord);
    }

    private void processState(RecordsHolder recordsHolder, Record newRecord) {
        Record last = recordsHolder.lastWithState();
        isTrue(last.getState() == NATURAL_REACTION);

        if (downTrendPivotPointIsBroken(recordsHolder, newRecord))
            newRecord.setStateAndRule(DOWN_TREND, _6b3);

        if (reactionPivotPointIsBroken(recordsHolder, newRecord))
            newRecord.setStateAndRule(DOWN_TREND, _5b);

        if (upperTrendPivotPointIsBroken(recordsHolder, newRecord)) {
            markAsPivotPointIfNeeded(recordsHolder);
            newRecord.setStateAndRule(UPPER_TREND, _11a);
        }

        if (strongRally(recordsHolder.lastWithState(), newRecord)) {
            if (priceIsLowerThanLastNaturalRally(recordsHolder, newRecord))
                newRecord.setStateAndRule(SECONDARY_RALLY, _6g);
            else {
                markAsPivotPointIfNeeded(recordsHolder);
                final State newState = rallyPivotPointIsBroken(recordsHolder, newRecord) ? UPPER_TREND : NATURAL_RALLY;
                final Rule rule = rallyPivotPointIsBroken(recordsHolder, newRecord) ? _5a : _6d;
                newRecord.setStateAndRule(newState, rule);
            }
        }

        if (newRecord.getPrice() < last.getPrice())
            newRecord.setStateAndRule(NATURAL_REACTION, _12_reaction);
    }

    private void markAsPivotPointIfNeeded(RecordsHolder recordsHolder) {
        if (recordsHolder.currentTrend() == UP && recordsHolder.getPivotPoints().last().getState() != NATURAL_REACTION)
            recordsHolder.lastWithState().markAsPivotPoint();
    }
}