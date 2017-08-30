package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.utils.RecordsHolder;

import static com.stocks.livermor.Constants.Rule.*;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordUtils.anyRally;
import static com.stocks.livermor.utils.RecordUtils.anyReaction;
import static com.stocks.livermor.utils.RecordUtils.strongReaction;
import static com.stocks.livermor.utils.RecordsHolder.NULL_OBJECT;
import static com.stocks.livermor.utils.Trend.DOWN;
import static com.stocks.livermor.utils.Trend.UP;
import static org.springframework.util.Assert.isTrue;

public class NaturalRallyStrategy implements StateProcessor {

    @Override
    public void process(RecordsHolder recordsHolder, Record newRecord) {
        isTrue(recordsHolder.lastWithState().getState() == NATURAL_RALLY);

        checkPriceIsHigherThanLastInUpperTrend(recordsHolder, newRecord);
        if (newRecord.hasState()) return;
        checkPriceIsHigherThanLastLastPivotPointInNaturalRally(recordsHolder, newRecord);
        if (newRecord.hasState()) return;

        checkPriceIsLowerThanLastInDownTrend(recordsHolder, newRecord);
        if (newRecord.hasState()) return;
        checkStrongReaction(recordsHolder, newRecord);
        if (newRecord.hasState()) return;

        setStateIfNotYet(newRecord, recordsHolder.lastWithState());
    }

    /**
     * Выставляется только в том случае, если у записи еще нет stat'а.
     */
    private void setStateIfNotYet(Record newRecord, Record lastRecord) {
        if (newRecord.getState() == null) {
            if (newRecord.getPrice() > lastRecord.getPrice())
                newRecord.setStateAndRule(NATURAL_RALLY, _12_rally);
            else
                newRecord.setState(NONE);
        }
    }

    private void checkStrongReaction(RecordsHolder recordsHolder, Record newRecord) {
        if (strongReaction(recordsHolder.lastWithState(), newRecord)) {
            Record lastReaction = recordsHolder.last(NATURAL_REACTION);
            if (lastReaction != NULL_OBJECT && newRecord.getPrice() >= lastReaction.getPrice())
                newRecord.setStateAndRule(SECONDARY_REACTION, _6h);
            else {
                markAsPicotPointIfNeeded(recordsHolder);
                newRecord.setStateAndRule(NATURAL_REACTION, _6b);
            }
        }
    }

    private void checkPriceIsHigherThanLastLastPivotPointInNaturalRally(RecordsHolder recordsHolder, Record newRecord) {
        Record lastRallyPivotPoint = recordsHolder.getPivotPoints().last(NATURAL_RALLY);
        if (lastRallyPivotPoint == NULL_OBJECT) return;
        if (!recordsHolder.getPivotPoints().getSupportAndResistance().contains(lastRallyPivotPoint)) return;

        if (anyRally(lastRallyPivotPoint, newRecord))
            newRecord.setStateAndRule(UPPER_TREND, _5a);
    }

    private void checkPriceIsHigherThanLastInUpperTrend(RecordsHolder recordsHolder, Record newRecord) {
        Record lastUpperTrend = recordsHolder.last(UPPER_TREND);
        if (lastUpperTrend == NULL_OBJECT) return;

        if (newRecord.getPrice() > lastUpperTrend.getPrice()) {
            if (recordsHolder.currentTrend() == UP)
                newRecord.setStateAndRule(UPPER_TREND, _6d3);
            else if (anyRally(lastUpperTrend, newRecord))
                newRecord.setStateAndRule(UPPER_TREND, _6d3);
        }
    }

    private void checkPriceIsLowerThanLastInDownTrend(RecordsHolder recordsHolder, Record newRecord) {
        Record lastDownTrend = recordsHolder.last(DOWN_TREND);
        if (lastDownTrend == NULL_OBJECT) return;

        if (newRecord.getPrice() < lastDownTrend.getPrice()) {
            if (recordsHolder.currentTrend() == DOWN) {
                markAsPicotPointIfNeeded(recordsHolder);
                newRecord.setStateAndRule(DOWN_TREND, _11b);
            } else if (anyReaction(lastDownTrend, newRecord)) {
                markAsPicotPointIfNeeded(recordsHolder);
                newRecord.setStateAndRule(DOWN_TREND, _11b);
            }
        }
    }

    private void markAsPicotPointIfNeeded(RecordsHolder recordsHolder) {
        if (recordsHolder.currentTrend() == DOWN && recordsHolder.getPivotPoints().last().getState() != NATURAL_RALLY)
            recordsHolder.lastWithState().markAsPivotPoint();
    }
}
