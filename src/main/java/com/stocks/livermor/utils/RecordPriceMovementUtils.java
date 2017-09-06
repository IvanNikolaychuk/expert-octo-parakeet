package com.stocks.livermor.utils;

import com.stocks.livermor.entity.Record;

import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordUtils.anyRally;
import static com.stocks.livermor.utils.RecordUtils.anyReaction;
import static com.stocks.livermor.utils.RecordsHolder.NULL_OBJECT;
import static com.stocks.livermor.utils.Trend.DOWN;
import static com.stocks.livermor.utils.Trend.UP;

public class RecordPriceMovementUtils {
    public static boolean rallyPivotPointIsBroken(RecordsHolder recordsHolder, Record newRecord) {
        Record lastRallyPivotPoint = recordsHolder.getPivotPoints().last(NATURAL_RALLY);

        return lastRallyPivotPoint != NULL_OBJECT
                && recordsHolder.getPivotPoints().getSupportAndResistance().contains(lastRallyPivotPoint)
                && anyRally(lastRallyPivotPoint, newRecord);
    }

    public static boolean reactionPivotPointIsBroken(RecordsHolder recordsHolder, Record newRecord) {
        Record lastReactionPivotPoint = recordsHolder.getPivotPoints().last(NATURAL_REACTION);

        return lastReactionPivotPoint != NULL_OBJECT
                && recordsHolder.getPivotPoints().getSupportAndResistance().contains(lastReactionPivotPoint)
                && anyReaction(lastReactionPivotPoint, newRecord);
    }

    /**
     *  Пробита upper-точка или нет зависит от того, в каком тренде мы сейчас находимся. Если тренд восходящий, то любое
     *  пробитие считается пробитием. Если нисходящим - нужно пробитие больше 1% (чтобы не было ложных пробитий)
     */
    public static boolean upperTrendPivotPointIsBroken(RecordsHolder recordsHolder, Record newRecord) {
        Record lastUpperTrend = recordsHolder.last(UPPER_TREND);

        if (lastUpperTrend != NULL_OBJECT && newRecord.getPrice() > lastUpperTrend.getPrice()) {
            if (recordsHolder.currentTrend() == UP)
                return true;
            else if (anyRally(lastUpperTrend, newRecord))
                return true;
        }

        return false;
    }

    public static boolean downTrendPivotPointIsBroken(RecordsHolder recordsHolder, Record newRecord) {
        Record lastDownTrend = recordsHolder.last(DOWN_TREND);

        if (lastDownTrend != NULL_OBJECT && newRecord.getPrice() < lastDownTrend.getPrice()) {
            if (recordsHolder.currentTrend() == DOWN)
                return true;
            else if (anyReaction(lastDownTrend, newRecord))
                return true;
        }

        return false;
    }

    public static boolean priceIsGraterThanLastNaturalReaction(RecordsHolder recordsHolder, Record newRecord) {
        Record lastReaction = recordsHolder.last(NATURAL_REACTION);

        return lastReaction != NULL_OBJECT && newRecord.getPrice() >= lastReaction.getPrice()
                && recordsHolder.getPivotPoints().isAfterSupportOrResistance(lastReaction)
                && recordsHolder.getStates().contains(NATURAL_REACTION);
    }

    public static boolean priceIsLowerThanLastNaturalRally(RecordsHolder recordsHolder, Record newRecord) {
        Record lastRally = recordsHolder.last(NATURAL_RALLY);

        return lastRally != NULL_OBJECT && newRecord.getPrice() <= lastRally.getPrice()
                && recordsHolder.getPivotPoints().isAfterSupportOrResistance(lastRally)
                && recordsHolder.getStates().contains(NATURAL_RALLY);
    }
}
