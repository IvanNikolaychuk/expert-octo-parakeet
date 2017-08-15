package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.utils.PivotPointsHolder;
import com.stocks.livermor.utils.RecordUtils.MovementType;
import com.stocks.livermor.utils.RecordsHolder;
import org.springframework.util.Assert;

import static com.stocks.livermor.constants.Constants.Rule.*;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordUtils.MovementType.REACTION;
import static com.stocks.livermor.utils.RecordUtils.MovementType.STRONG_REACTION;
import static com.stocks.livermor.utils.RecordUtils.getMovementType;
import static com.stocks.livermor.utils.RecordUtils.strongRally;
import static com.stocks.livermor.utils.RecordsHolder.NULL_OBJECT;

public class SecondaryReactionStrategy implements StateProcessor {
    @Override
    public void process(RecordsHolder recordsHolder, Record newRecord) {
        final Record last = recordsHolder.lastWithState();
        Assert.isTrue(last.getState() == SECONDARY_REACTION);

        checkPriceIsLowerThanLastInNaturalReaction(recordsHolder, newRecord);
        checkPriceIsLowerThanLastPivotPointInNaturalReaction(recordsHolder, newRecord);
        checkStrongRally(recordsHolder, newRecord);

        setStateIfNotYet(newRecord, last);
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
        PivotPointsHolder pivotPointsHolder = recordsHolder.getPivotPoints();
        Record lastPivotPoint = pivotPointsHolder.lastPivotPointRecord(NATURAL_REACTION);
        if (lastPivotPoint != NULL_OBJECT) {
            MovementType movementType = getMovementType(lastPivotPoint, newRecord);
            if (movementType == REACTION || movementType == STRONG_REACTION) {
                newRecord.setStateAndRule(DOWN_TREND, _5b);
            }
        }
    }

    private void checkPriceIsLowerThanLastInNaturalReaction(RecordsHolder recordsHolder, Record newRecord) {
        Record lastReaction = recordsHolder.last(NATURAL_REACTION);
        if (lastReaction != NULL_OBJECT) {
            if (newRecord.getPrice() < lastReaction.getPrice()) {
                newRecord.setStateAndRule(NATURAL_REACTION, _6h);
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
