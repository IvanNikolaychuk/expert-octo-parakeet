package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.utils.RecordsHolder;

import static com.stocks.livermor.Constants.Rule.*;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordUtils.anyRally;
import static com.stocks.livermor.utils.RecordUtils.anyReaction;
import static com.stocks.livermor.utils.RecordUtils.strongRally;
import static com.stocks.livermor.utils.RecordsHolder.NULL_OBJECT;
import static com.stocks.livermor.utils.Trend.DOWN;
import static com.stocks.livermor.utils.Trend.UP;
import static org.springframework.util.Assert.isTrue;

public class NaturalReactionStrategy implements StateProcessor {
    @Override
    public void process(RecordsHolder recordsHolder, Record newRecord) {
        isTrue(recordsHolder.lastWithState().getState() == NATURAL_REACTION);

        checkPriceIsLowerThanLastInDownTrend(recordsHolder, newRecord);
        if (newRecord.hasState()) return;
        checkPriceIsLowerThanLastLastPivotPointInNaturalReaction(recordsHolder, newRecord);
        if (newRecord.hasState()) return;

        checkPriceIsHigherThanLastInUpperTrend(recordsHolder, newRecord);
        if (newRecord.hasState()) return;
        checkStrongRally(recordsHolder, newRecord);
        if (newRecord.hasState()) return;

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
            if (lastRally != NULL_OBJECT && newRecord.getPrice() <= lastRally.getPrice() &&
                    recordsHolder.getPivotPoints().isAfterSupportOrResistence(lastRally))
                newRecord.setStateAndRule(SECONDARY_RALLY, _6g);
            else {
                markAsPicotPointIfNeeded(recordsHolder);
                newRecord.setStateAndRule(NATURAL_RALLY, _6d);
            }
        }
    }

    private void checkPriceIsLowerThanLastLastPivotPointInNaturalReaction(RecordsHolder recordsHolder, Record newRecord) {
        Record lastReactionPivotPoint = recordsHolder.getPivotPoints().last(NATURAL_REACTION);
        if (lastReactionPivotPoint == NULL_OBJECT) return;
        if (!recordsHolder.getPivotPoints().getSupportAndResistance().contains(lastReactionPivotPoint)) return;

        if (anyReaction(lastReactionPivotPoint, newRecord))
            newRecord.setStateAndRule(DOWN_TREND, _5b);
    }

    private void checkPriceIsHigherThanLastInUpperTrend(RecordsHolder recordsHolder, Record newRecord) {
        Record lastUpperTrend = recordsHolder.last(UPPER_TREND);
        if (lastUpperTrend == NULL_OBJECT) return;

        if (newRecord.getPrice() > lastUpperTrend.getPrice()) {
            markAsPicotPointIfNeeded(recordsHolder);
            if (recordsHolder.currentTrend() == UP)
                newRecord.setStateAndRule(UPPER_TREND, _11a);
            else if (anyRally(lastUpperTrend, newRecord))
                newRecord.setStateAndRule(UPPER_TREND, _11a);
        }
    }

    private void checkPriceIsLowerThanLastInDownTrend(RecordsHolder recordsHolder, Record newRecord) {
        Record lastDownTrend = recordsHolder.last(DOWN_TREND);
        if (lastDownTrend == NULL_OBJECT) return;

        if (newRecord.getPrice() < lastDownTrend.getPrice()) {
            // если тренд меняется, нужна реакция как минимум на 1% во избежани ложных пробитий.
            if (recordsHolder.currentTrend() == DOWN) {
                newRecord.setStateAndRule(DOWN_TREND, _6b3);
            } else if (anyReaction(lastDownTrend, newRecord))
                newRecord.setStateAndRule(DOWN_TREND, _6b3);
        }
    }

    private void markAsPicotPointIfNeeded(RecordsHolder recordsHolder) {
        if (recordsHolder.currentTrend() == UP && recordsHolder.getPivotPoints().last().getState() != NATURAL_REACTION)
            recordsHolder.lastWithState().markAsPivotPoint();
    }
}