package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.Trend;
import com.stocks.livermor.utils.RecordsHolder;

import static com.stocks.livermor.constants.Constants.Rule.*;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.entity.Trend.DOWN;
import static com.stocks.livermor.utils.RecordUtils.*;
import static com.stocks.livermor.utils.RecordsHolder.NULL_OBJECT;
import static org.springframework.util.Assert.isTrue;

public class NaturalRallyStrategy implements StateProcessor {

    @Override
    public void process(RecordsHolder recordsHolder, Record newRecord) {
        isTrue(recordsHolder.last().getState() == NATURAL_RALLY);

        checkPriceIsHigherThanLastInUpperTrend(recordsHolder, newRecord);
        checkPriceIsHigherThanLastLastPivotPointInNaturalRally(recordsHolder, newRecord);

        checkPriceIsLowerThanLastInDownTrend(recordsHolder, newRecord);
        checkStrongReaction(recordsHolder, newRecord);
    }

    private void checkStrongReaction(RecordsHolder recordsHolder, Record newRecord) {
        if (strongReaction(recordsHolder.last(), newRecord)) {
            Record lastReaction = recordsHolder.last(NATURAL_REACTION);
            if (lastReaction != NULL_OBJECT && newRecord.getPrice() >= lastReaction.getPrice())
                newRecord.setStateAndRule(SECONDARY_REACTION, _6h);
            else {
                markAsPicotPointIfNeeded(recordsHolder.currentTrend(), newRecord);
                newRecord.setStateAndRule(NATURAL_REACTION, _6b);
            }
        }
    }

    private void checkPriceIsHigherThanLastLastPivotPointInNaturalRally(RecordsHolder recordsHolder, Record newRecord) {
        Record lastRallyPivotPoint = recordsHolder.getPivotPoints().lastPivotPointRecord(NATURAL_RALLY);
        if (lastRallyPivotPoint == NULL_OBJECT) return;

        if (rally(lastRallyPivotPoint, newRecord) || strongRally(lastRallyPivotPoint, newRecord))
            newRecord.setStateAndRule(UPPER_TREND, _5a);
    }

    private void checkPriceIsHigherThanLastInUpperTrend(RecordsHolder recordsHolder, Record newRecord) {
        Record lastUpperTrend = recordsHolder.last(UPPER_TREND);
        if (lastUpperTrend == NULL_OBJECT) return;

        if (newRecord.getPrice() > lastUpperTrend.getPrice())
            newRecord.setStateAndRule(UPPER_TREND, _6d);
    }

    private void checkPriceIsLowerThanLastInDownTrend(RecordsHolder recordsHolder, Record newRecord) {
        Record lastDownTrend = recordsHolder.last(DOWN_TREND);
        if (lastDownTrend == NULL_OBJECT) return;

        if (newRecord.getPrice() < lastDownTrend.getPrice()) {
            markAsPicotPointIfNeeded(recordsHolder.currentTrend(), newRecord);
            newRecord.setStateAndRule(DOWN_TREND, _11b);
        }
    }

    private void markAsPicotPointIfNeeded(Trend trend, Record newRecord) {
        if (trend == DOWN)
            newRecord.markAsPivotPoint();
    }
}
