package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.utils.RecordsHolder;
import org.springframework.util.Assert;

import static com.stocks.livermor.Constants.Rule.*;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordUtils.anyRally;
import static com.stocks.livermor.utils.RecordUtils.strongReaction;
import static com.stocks.livermor.utils.RecordsHolder.NULL_OBJECT;

public class SecondaryRallyStrategy implements StateProcessor {

    @Override
    public void process(RecordsHolder recordsHolder, Record newRecord) {
        final Record last = recordsHolder.lastWithState();
        Assert.isTrue(last.getState() == SECONDARY_RALLY);

        checkPriceIsLowerThanLastInDownTrend(recordsHolder, newRecord);
        if (newRecord.hasState()) return;

        checkPriceIsHigherThanLastInNaturalRally(recordsHolder, newRecord);
        if (newRecord.hasState()) return;

        checkPriceIsHigherThanLastPivotPointInNaturalRally(recordsHolder, newRecord);
        if (newRecord.hasState()) return;

        checkStrongReaction(recordsHolder, newRecord);
        if (newRecord.hasState()) return;

        setStateIfNotYet(newRecord, last);
    }

    private void checkPriceIsLowerThanLastInDownTrend(RecordsHolder recordsHolder, Record newRecord) {
        Record lastDownTrend = recordsHolder.last(DOWN_TREND);
        if (lastDownTrend == NULL_OBJECT) return;

        if (newRecord.getPrice() < lastDownTrend.getPrice()) {
            newRecord.setStateAndRule(DOWN_TREND, _11b);
        }
    }

    private void checkStrongReaction(RecordsHolder recordsHolder, Record newRecord) {
        if (strongReaction(recordsHolder.lastWithState(), newRecord)) {
            Record lastReaction = recordsHolder.last(NATURAL_REACTION);
            if (lastReaction != NULL_OBJECT && newRecord.getPrice() >= lastReaction.getPrice())
                newRecord.setStateAndRule(SECONDARY_REACTION, _6h);
            else
                newRecord.setStateAndRule(NATURAL_REACTION, _6b);
        }
    }

    private void checkPriceIsHigherThanLastPivotPointInNaturalRally(RecordsHolder recordsHolder, Record newRecord) {
        Record lastRallyPivotPoint = recordsHolder.getPivotPoints().last(NATURAL_RALLY);
        if (lastRallyPivotPoint == NULL_OBJECT) return;
        if (!recordsHolder.getPivotPoints().getSupportAndResistance().contains(lastRallyPivotPoint)) return;

        if (anyRally(lastRallyPivotPoint, newRecord))
            newRecord.setStateAndRule(UPPER_TREND, _5a);
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
        else
            newRecord.setState(NONE);
    }
}
