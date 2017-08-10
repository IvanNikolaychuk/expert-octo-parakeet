package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.utils.PivotPointsHolder;
import com.stocks.livermor.utils.RecordUtils.MovementType;
import com.stocks.livermor.utils.RecordsHolder;
import org.springframework.util.Assert;

import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordUtils.MovementType.REACTION;
import static com.stocks.livermor.utils.RecordUtils.MovementType.STRONG_REACTION;
import static com.stocks.livermor.utils.RecordUtils.getMovementType;
import static com.stocks.livermor.utils.RecordsHolder.NULL_OBJECT;

public class SecondaryReactionStrategy implements ExecutionStrategy {
    @Override
    public void execute(RecordsHolder recordsHolder, Record newRecord) {
        final Record last = recordsHolder.last();
        Assert.isTrue(last.getState() == SECONDARY_REACTION);

        checkPriceIsLowerThanLastInNaturalReaction(recordsHolder, newRecord);
        checkPriceIsLowerThanLastPivotPointInNaturalReaction(recordsHolder, newRecord);
    }

    private void checkPriceIsLowerThanLastPivotPointInNaturalReaction(RecordsHolder recordsHolder, Record newRecord) {
        PivotPointsHolder pivotPointsHolder = recordsHolder.getPivotPoints();
        Record lastPivotPoint = pivotPointsHolder.lastPivotPointRecord(NATURAL_REACTION);
        if (lastPivotPoint != NULL_OBJECT) {
            MovementType movementType = getMovementType(lastPivotPoint, newRecord);
            if (movementType == REACTION || movementType == STRONG_REACTION) {
                newRecord.setState(DOWN_TREND);
            }
        }
    }

    private void checkPriceIsLowerThanLastInNaturalReaction(RecordsHolder recordsHolder, Record newRecord) {
        Record lastReaction = recordsHolder.last(NATURAL_REACTION);
        if (lastReaction != NULL_OBJECT) {
            if (newRecord.getPrice() < lastReaction.getPrice()) {
                newRecord.setState(NATURAL_REACTION);
            }
        }
    }
}
