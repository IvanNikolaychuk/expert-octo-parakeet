package com.stocks.livermor.strategy;

import com.stocks.livermor.Constants;
import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.State;
import com.stocks.livermor.utils.RecordsHolder;

import static com.stocks.livermor.Constants.Rule.*;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordPriceMovementUtils.*;
import static com.stocks.livermor.utils.RecordUtils.strongReaction;
import static com.stocks.livermor.utils.RecordsHolder.NULL_OBJECT;
import static com.stocks.livermor.utils.Trend.DOWN;
import static org.springframework.util.Assert.isTrue;

public class NaturalRallyStrategy implements StateProcessor {

    @Override
    public void process(RecordsHolder recordsHolder, Record newRecord) {
        final Record last = recordsHolder.lastWithState();
        isTrue(last.getState() == NATURAL_RALLY);

        if (upperTrendPivotPointIsBroken(recordsHolder, newRecord))
            newRecord.setStateAndRule(UPPER_TREND, _6d3);

        if (rallyPivotPointIsBroken(recordsHolder, newRecord))
            newRecord.setStateAndRule(UPPER_TREND, _5a);

        if (downTrendPivotPointIsBroken(recordsHolder, newRecord)) {
            markAsPivotPointIfNeeded(recordsHolder);
            newRecord.setStateAndRule(DOWN_TREND, _11b);
        }

        checkStrongReaction(recordsHolder, newRecord);

        if (newRecord.getPrice() > last.getPrice())
            newRecord.setStateAndRule(NATURAL_RALLY, _12_rally);
    }

    private void checkStrongReaction(RecordsHolder recordsHolder, Record newRecord) {
        if (strongReaction(recordsHolder.lastWithState(), newRecord)) {
            Record lastReaction = recordsHolder.last(NATURAL_REACTION);
            if (lastReaction != NULL_OBJECT && newRecord.getPrice() >= lastReaction.getPrice()
                    && recordsHolder.getPivotPoints().isAfterSupportOrResistance(lastReaction)
                    // пишем во вторичную только если в текущем тренде была естественная реакция.
                    && recordsHolder.getStates().contains(NATURAL_REACTION))
                newRecord.setStateAndRule(SECONDARY_REACTION, _6h);
            else {
                markAsPivotPointIfNeeded(recordsHolder);
                final State newState = reactionPivotPointIsBroken(recordsHolder, newRecord) ? DOWN_TREND : NATURAL_REACTION;
                final Constants.Rule rule = reactionPivotPointIsBroken(recordsHolder, newRecord) ? _5b : _6b;
                newRecord.setStateAndRule(newState, rule);
            }
        }
    }

    private void markAsPivotPointIfNeeded(RecordsHolder recordsHolder) {
        if (recordsHolder.currentTrend() == DOWN && recordsHolder.getPivotPoints().last().getState() != NATURAL_RALLY)
            recordsHolder.lastWithState().markAsPivotPoint();
    }
}
