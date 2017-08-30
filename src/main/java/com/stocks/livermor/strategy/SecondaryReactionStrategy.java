package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.utils.RecordsHolder;
import org.springframework.util.Assert;

import static com.stocks.livermor.Constants.Rule.*;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordUtils.anyRally;
import static com.stocks.livermor.utils.RecordUtils.anyReaction;
import static com.stocks.livermor.utils.RecordUtils.strongRally;
import static com.stocks.livermor.utils.RecordsHolder.NULL_OBJECT;
import static com.stocks.livermor.utils.Trend.UP;

public class SecondaryReactionStrategy implements StateProcessor {

    @Override
    public void process(RecordsHolder recordsHolder, Record newRecord) {
        final Record last = recordsHolder.lastWithState();
        Assert.isTrue(last.getState() == SECONDARY_REACTION);

        checkPriceIsHigherThanLastInUpperTrend(recordsHolder, newRecord);
        if (newRecord.hasState()) return;
        checkStrongRally(recordsHolder, newRecord);
        if (newRecord.hasState()) return;

        checkPriceIsLowerThanLastPivotPointInNaturalReaction(recordsHolder, newRecord);
        if (newRecord.hasState()) return;
        checkPriceIsLowerThanLastInNaturalReaction(recordsHolder, newRecord);
        if (newRecord.hasState()) return;

        setStateIfNotYet(newRecord, last);
    }

    private void checkPriceIsHigherThanLastInUpperTrend(RecordsHolder recordsHolder, Record newRecord) {
        Record lastUpperTrend = recordsHolder.last(UPPER_TREND);
        if (lastUpperTrend == NULL_OBJECT) return;

        if (newRecord.getPrice() > lastUpperTrend.getPrice())
            if (recordsHolder.currentTrend() == UP)
                newRecord.setStateAndRule(UPPER_TREND, _11a);
            else if (anyRally(lastUpperTrend, newRecord))
                newRecord.setStateAndRule(UPPER_TREND, _11a);
    }

    private void checkStrongRally(RecordsHolder recordsHolder, Record newRecord) {
        if (strongRally(recordsHolder.lastWithState(), newRecord)) {
            Record lastRally = recordsHolder.last(NATURAL_RALLY);
            if (lastRally != NULL_OBJECT && newRecord.getPrice() <= lastRally.getPrice())
                newRecord.setStateAndRule(SECONDARY_RALLY, _6g);
            else {
                newRecord.setStateAndRule(NATURAL_RALLY, _6d);
            }
        }
    }

    private void checkPriceIsLowerThanLastPivotPointInNaturalReaction(RecordsHolder recordsHolder, Record newRecord) {
        Record lastReactionPivotPoint = recordsHolder.getPivotPoints().last(NATURAL_REACTION);
        if (lastReactionPivotPoint == NULL_OBJECT) return;
        if (!recordsHolder.getPivotPoints().getSupportAndResistance().contains(lastReactionPivotPoint)) return;

        if (anyReaction(lastReactionPivotPoint, newRecord))
            newRecord.setStateAndRule(DOWN_TREND, _5b);
    }

    private void checkPriceIsLowerThanLastInNaturalReaction(RecordsHolder recordsHolder, Record newRecord) {
        Record lastReaction = recordsHolder.last(NATURAL_REACTION);
        if (lastReaction != NULL_OBJECT) {
            if (newRecord.getPrice() < lastReaction.getPrice()) {
                newRecord.setStateAndRule(NATURAL_REACTION, _6h3);
            }
        }
    }

    private void setStateIfNotYet(Record newRecord, Record lastRecord) {
        if (newRecord.getState() != null) return;

        if (newRecord.getPrice() < lastRecord.getPrice())
            newRecord.setStateAndRule(SECONDARY_REACTION, _12_secondary_reaction);
        else
            newRecord.setState(NONE);
    }
}
