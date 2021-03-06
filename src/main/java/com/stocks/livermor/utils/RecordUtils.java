package com.stocks.livermor.utils;

import com.stocks.livermor.entity.Record;

import static com.stocks.livermor.Constants.BASIC_CHANGE_PERCENTAGE;
import static com.stocks.livermor.utils.RecordUtils.MovementType.*;
import static java.math.BigDecimal.valueOf;

public class RecordUtils {
    public enum MovementType {
        RALLY, REACTION, STRONG_RALLY, STRONG_REACTION, NONE
    }

    public static boolean priceIsGrater(Record prev, Record current) {
        return current.getPrice() >= prev.getPrice();
    }

    public static boolean priceIsLower(Record prev, Record current) {
        return current.getPrice() <= prev.getPrice();
    }

    public static boolean reaction(Record prev, Record current) {
        return getMovementType(prev, current) == REACTION;
    }

    public static boolean anyReaction(Record prev, Record current) {
        return reaction(prev, current) || strongReaction(prev, current);
    }

    public static boolean anyRally(Record prev, Record current) {
        return rally(prev, current) || strongRally(prev, current);
    }

    public static boolean strongReaction(Record prev, Record current) {
        return getMovementType(prev, current) == STRONG_REACTION;
    }

    public static boolean rally(Record prev, Record current) {
        return getMovementType(prev, current) == RALLY;
    }

    public static boolean strongRally(Record prev, Record current) {
        return getMovementType(prev, current) == STRONG_RALLY;
    }

    public static double percentageChange(Record firstRecord, Record secondRecord) {
        final double first = firstRecord.getPrice();
        final double second = secondRecord.getPrice();

        return 100 - second * 100 / first;
    }

    public static MovementType getMovementType(Record prev, Record current) {
        final double currPrice = current.getPrice();
        final double prevPrice = prev.getPrice();

        double netPercChange = valueOf(100 - (currPrice * 100 / prevPrice)).abs().doubleValue();
        if (netPercChange >= BASIC_CHANGE_PERCENTAGE * 2) {
            return currPrice < prevPrice ? STRONG_REACTION : STRONG_RALLY;
        }
        if (netPercChange >= BASIC_CHANGE_PERCENTAGE) {
            return currPrice < prevPrice ? REACTION : RALLY;
        }


        return NONE;
    }


    public static boolean check6aaccRule(Record prev, Record last) {
        double percChange = valueOf((last.getPrice() * 100 / prev.getPrice()) - 100).abs().doubleValue();
        return percChange <= BASIC_CHANGE_PERCENTAGE;
    }

}
