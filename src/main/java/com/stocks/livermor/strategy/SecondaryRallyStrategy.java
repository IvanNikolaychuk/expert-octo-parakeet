package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.utils.PivotPointsHolder;
import com.stocks.livermor.utils.RecordUtils.MovementType;
import com.stocks.livermor.utils.RecordsHolder;
import org.springframework.util.Assert;

import static com.stocks.livermor.constants.Constants.Rule.*;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordUtils.MovementType.RALLY;
import static com.stocks.livermor.utils.RecordUtils.MovementType.STRONG_RALLY;
import static com.stocks.livermor.utils.RecordUtils.getMovementType;
import static com.stocks.livermor.utils.RecordsHolder.NULL_OBJECT;

public class SecondaryRallyStrategy implements StateProcessor {

    @Override
    public void process(RecordsHolder recordsHolder, Record newRecord) {
        final Record last = recordsHolder.lastWithState();
        Assert.isTrue(last.getState() == SECONDARY_RALLY);

        checkPriceIsHigherThanLastInNaturalRally(recordsHolder, newRecord);
        checkPriceIsHigherThanLastPivotPointInNaturalRally(recordsHolder, newRecord);

        setStateIfNotYet(newRecord, last);
    }

    private void checkPriceIsHigherThanLastPivotPointInNaturalRally(RecordsHolder recordsHolder, Record newRecord) {
        PivotPointsHolder pivotPointsHolder = recordsHolder.getPivotPoints();
        Record lastPivotPoint = pivotPointsHolder.lastPivotPointRecord(NATURAL_RALLY);
        if (lastPivotPoint != NULL_OBJECT) {
            MovementType movementType = getMovementType(lastPivotPoint, newRecord);
            if (movementType == RALLY || movementType == STRONG_RALLY) {
                newRecord.setStateAndRule(UPPER_TREND, _5a);
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
        else
            newRecord.setState(NONE);
    }
}
