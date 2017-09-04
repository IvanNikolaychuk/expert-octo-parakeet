package com.stocks.livermor.utils;

import com.stocks.livermor.entity.Record;

import static com.stocks.livermor.entity.State.NATURAL_RALLY;
import static com.stocks.livermor.utils.RecordUtils.anyRally;
import static com.stocks.livermor.utils.RecordsHolder.NULL_OBJECT;

public class RecordPriceMovementUtils {
    public static boolean rallyPivotPointIsBroken(RecordsHolder recordsHolder, Record newRecord) {
        Record lastRallyPivotPoint = recordsHolder.getPivotPoints().last(NATURAL_RALLY);

        return lastRallyPivotPoint != NULL_OBJECT
                && recordsHolder.getPivotPoints().getSupportAndResistance().contains(lastRallyPivotPoint)
                && anyRally(lastRallyPivotPoint, newRecord);
    }
}
