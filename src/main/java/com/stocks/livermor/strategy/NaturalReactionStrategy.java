package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.Trend;
import com.stocks.livermor.utils.RecordsHolder;

import static com.stocks.livermor.constants.Constants.Rule.*;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.entity.Trend.DOWN;
import static com.stocks.livermor.utils.RecordUtils.anyReaction;
import static com.stocks.livermor.utils.RecordUtils.strongRally;
import static com.stocks.livermor.utils.RecordsHolder.NULL_OBJECT;
import static org.springframework.util.Assert.isTrue;

public class NaturalReactionStrategy implements StateProcessor {
    @Override
    public void process(RecordsHolder recordsHolder, Record newRecord) {
        isTrue(recordsHolder.lastWithState().getState() == NATURAL_REACTION);

        checkPriceIsLowerThanLastInDownTrend(recordsHolder, newRecord);
        checkPriceIsLowerThanLastLastPivotPointInNaturalReaction(recordsHolder, newRecord);

        checkPriceIsHigherThanLastInUpperTrend(recordsHolder, newRecord);
        checkStrongRally(recordsHolder, newRecord);

        setStateIfNotYet(newRecord, recordsHolder.lastWithState());
    }

    /**
     * Выставляется только в том случае, если у записи еще нет stat'а.
     */
    private void setStateIfNotYet(Record newRecord, Record lastRecord) {
        if (newRecord.getState() == null) {
            if (newRecord.getPrice() < lastRecord.getPrice())
                newRecord.setStateAndRule(NATURAL_REACTION, _12_reaction);
            else
                newRecord.setState(NONE);
        }
    }

    private void checkStrongRally(RecordsHolder recordsHolder, Record newRecord) {
        if (strongRally(recordsHolder.lastWithState(), newRecord)) {
            Record lastRally = recordsHolder.last(NATURAL_RALLY);
            if (lastRally != NULL_OBJECT && newRecord.getPrice() <= lastRally.getPrice())
                newRecord.setStateAndRule(SECONDARY_RALLY, _6g);
            else {
                markAsPicotPointIfNeeded(recordsHolder.currentTrend(), newRecord);
                newRecord.setStateAndRule(NATURAL_RALLY, _6d);
            }
        }
    }

    private void checkPriceIsLowerThanLastLastPivotPointInNaturalReaction(RecordsHolder recordsHolder, Record newRecord) {
        Record lastReactionPivotPoint = recordsHolder.getPivotPoints().lastPivotPointRecord(NATURAL_REACTION);
        if (lastReactionPivotPoint == NULL_OBJECT) return;

        if (anyReaction(lastReactionPivotPoint, newRecord))
            newRecord.setStateAndRule(DOWN_TREND, _5b);
    }

    private void checkPriceIsHigherThanLastInUpperTrend(RecordsHolder recordsHolder, Record newRecord) {
        Record lastUpperTrend = recordsHolder.last(UPPER_TREND);
        if (lastUpperTrend == NULL_OBJECT) return;

        if (newRecord.getPrice() > lastUpperTrend.getPrice()) {
            markAsPicotPointIfNeeded(recordsHolder.currentTrend(), newRecord);
            newRecord.setStateAndRule(UPPER_TREND, _11a);
        }
    }

    private void checkPriceIsLowerThanLastInDownTrend(RecordsHolder recordsHolder, Record newRecord) {
        Record lastDownTrend = recordsHolder.last(DOWN_TREND);
        if (lastDownTrend == NULL_OBJECT) return;

        if (newRecord.getPrice() < lastDownTrend.getPrice())
            newRecord.setStateAndRule(DOWN_TREND, _6b);
    }

    private void markAsPicotPointIfNeeded(Trend trend, Record newRecord) {
        if (trend == DOWN)
            newRecord.markAsPivotPoint();
    }
}