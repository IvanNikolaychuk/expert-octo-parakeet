package com.stocks.livermor.strategy;

import com.stocks.livermor.Constants;
import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.State;
import com.stocks.livermor.utils.RecordsHolder;
import org.springframework.util.Assert;

import static com.stocks.livermor.Constants.Rule.*;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordPriceMovementUtils.rallyPivotPointIsBroken;
import static com.stocks.livermor.utils.RecordPriceMovementUtils.reactionPivotPointIsBroken;
import static com.stocks.livermor.utils.RecordUtils.anyReaction;
import static com.stocks.livermor.utils.RecordUtils.strongReaction;
import static com.stocks.livermor.utils.RecordsHolder.NULL_OBJECT;
import static com.stocks.livermor.utils.Trend.DOWN;

public class SecondaryRallyStrategy implements StateProcessor {

    @Override
    public void process(RecordsHolder recordsHolder, Record newRecord) {
        final Record last = recordsHolder.lastWithState();
        Assert.isTrue(last.getState() == SECONDARY_RALLY);

        checkPriceIsLowerThanLastInDownTrend(recordsHolder, newRecord);
        if (newRecord.hasState()) return;
        checkStrongReaction(recordsHolder, newRecord);
        if (newRecord.hasState()) return;

        if (rallyPivotPointIsBroken(recordsHolder, newRecord)) {
            newRecord.setStateAndRule(UPPER_TREND, _5a);
            return;
        }
        checkPriceIsHigherThanLastInNaturalRally(recordsHolder, newRecord);
        if (newRecord.hasState()) return;

        setStateIfNotYet(newRecord, last);
    }

    private void checkPriceIsLowerThanLastInDownTrend(RecordsHolder recordsHolder, Record newRecord) {
        Record lastDownTrend = recordsHolder.last(DOWN_TREND);
        if (lastDownTrend == NULL_OBJECT) return;

        if (newRecord.getPrice() < lastDownTrend.getPrice()) {
            if (recordsHolder.currentTrend() == DOWN)
                newRecord.setStateAndRule(DOWN_TREND, _11b);
            else if (anyReaction(lastDownTrend, newRecord))
                newRecord.setStateAndRule(DOWN_TREND, _11b);
        }
    }

    private void checkStrongReaction(RecordsHolder recordsHolder, Record newRecord) {
        if (strongReaction(recordsHolder.lastWithState(), newRecord)) {
            Record lastReaction = recordsHolder.last(NATURAL_REACTION);
            if (lastReaction != NULL_OBJECT && newRecord.getPrice() >= lastReaction.getPrice()
                    && recordsHolder.getPivotPoints().isAfterSupportOrResistence(lastReaction)
                    // пишем во вторичную только если в текущем тренде была естественная реакция.
                    && recordsHolder.getStates().contains(NATURAL_REACTION))
                newRecord.setStateAndRule(SECONDARY_REACTION, _6h);
            else {
                final State newState = reactionPivotPointIsBroken(recordsHolder, newRecord) ? DOWN_TREND : NATURAL_REACTION;
                final Constants.Rule rule = reactionPivotPointIsBroken(recordsHolder, newRecord) ? _5b : _6b;
                newRecord.setStateAndRule(newState, rule);
            }
        }
    }

    private void checkPriceIsHigherThanLastInNaturalRally(RecordsHolder recordsHolder, Record newRecord) {
        Record lastRally = recordsHolder.last(NATURAL_RALLY);
        if (lastRally != NULL_OBJECT) {
            if (newRecord.getPrice() > lastRally.getPrice()) {
                newRecord.setStateAndRule(NATURAL_RALLY, _6g3);
            }
        }
    }


    private void setStateIfNotYet(Record newRecord, Record lastRecord) {
        if (newRecord.getState() != null) return;

        if (newRecord.getPrice() > lastRecord.getPrice())
            newRecord.setStateAndRule(SECONDARY_RALLY, _12_secondary_rally);
    }
}
