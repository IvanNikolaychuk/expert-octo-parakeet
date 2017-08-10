package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.utils.PivotPointsHolder;
import com.stocks.livermor.utils.RecordUtils.MovementType;
import com.stocks.livermor.utils.RecordsHolder;

import static com.stocks.livermor.entity.State.NATURAL_RALLY;
import static com.stocks.livermor.entity.State.UPPER_TREND;
import static com.stocks.livermor.utils.RecordUtils.MovementType.RALLY;
import static com.stocks.livermor.utils.RecordUtils.MovementType.STRONG_RALLY;
import static com.stocks.livermor.utils.RecordUtils.getMovementType;
import static com.stocks.livermor.utils.RecordsHolder.NULL_OBJECT;

public class SecondaryRallyStrategy implements ExecutionStrategy {

    @Override
    public void execute(RecordsHolder recordsHolder, Record newRecord) {
        checkPriceIsHigherThanLastInNaturalRally(recordsHolder, newRecord);
        checkPriceIsHigherThanLastPivotPointInNaturalRally(recordsHolder, newRecord);
    }

    private void checkPriceIsHigherThanLastPivotPointInNaturalRally(RecordsHolder recordsHolder, Record newRecord) {
        PivotPointsHolder pivotPointsHolder = recordsHolder.getPivotPoints();
        Record lastPivotPoint = pivotPointsHolder.lastPivotPointRecord(NATURAL_RALLY);
        if (lastPivotPoint != NULL_OBJECT) {
            MovementType movementType = getMovementType(lastPivotPoint, newRecord);
            if (movementType == RALLY || movementType == STRONG_RALLY) {
                newRecord.setState(UPPER_TREND);
            }
        }
    }

    private void checkPriceIsHigherThanLastInNaturalRally(RecordsHolder recordsHolder, Record newRecord) {
        Record lastRally = recordsHolder.last(NATURAL_RALLY);
        if (lastRally != NULL_OBJECT) {
            if (newRecord.getPrice() > lastRally.getPrice()) {
                newRecord.setState(NATURAL_RALLY);
            }
        }
    }
}
