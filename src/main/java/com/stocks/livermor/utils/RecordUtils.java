package com.stocks.livermor.utils;

import com.stocks.livermor.constants.Constants;
import com.stocks.livermor.entity.Record;

import static com.stocks.livermor.constants.Constants.BASIC_CHANGE_PERCENTAGE;
import static com.stocks.livermor.constants.Constants.BASIC_CHANGE_POINT;
import static com.stocks.livermor.utils.RecordUtils.ChangeMeasure.PERCENTAGE;

public class RecordUtils {
    public static ChangeMeasure CHANGE_MEASURE = PERCENTAGE;

    public enum ChangeMeasure {
        POINTS, PERCENTAGE
    }

    public static boolean priceIsGrater(Record prev, Record current) {
        return current.getPrice() < prev.getPrice();
    }

    public static boolean strongReaction(Record prev, Record current) {
        double percChange = 100 - (current.getPrice() * 100 / prev.getPrice());
        double pointsChange = prev.getPrice() - current.getPrice();

        return CHANGE_MEASURE == PERCENTAGE ?
                percChange >= BASIC_CHANGE_PERCENTAGE * 2 :
                pointsChange >= BASIC_CHANGE_POINT * 2;
    }


    public static boolean check6aaRule(Record prev, Record last) {
        double percChange = (last.getPrice() * 100 / prev.getPrice()) - 100;
        double pointsChange = last.getPrice() - prev.getPrice();

        return CHANGE_MEASURE == PERCENTAGE ?
                percChange < BASIC_CHANGE_PERCENTAGE :
                pointsChange <= BASIC_CHANGE_POINT;
    }

}
