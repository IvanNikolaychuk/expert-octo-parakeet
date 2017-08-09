package com.stocks.livermor.utils;

import com.stocks.livermor.entity.PivotPointType;
import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.State;

import java.util.Calendar;
import java.util.Date;

public class RecordHelper {
    public static Record getYestredays() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        return new Record(calendar.getTime(), 1);
    }

    public static Record getYestredays(PivotPointType pivotPointType) {
        Record record = getYestredays();
        record.setPivotPointType(pivotPointType);
        return record;
    }

    public static Record getYestredays(PivotPointType pivotPointType, double price) {
        Record record = getYestredays(pivotPointType);
        record.setPrice(price);
        return record;
    }

    public static Record get2DaysAgo(PivotPointType pivotPointType, double price) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -2);

        Record record = new Record(calendar.getTime(), price);
        record.setPivotPointType(pivotPointType);
        record.setPrice(price);
        return record;
    }


    public static Record getTodays(PivotPointType pivotPointType) {
        Record record = new Record(new Date(), 1);
        record.setPivotPointType(pivotPointType);
        return record;
    }

    public static Record getTodays(State state) {
        return getTodays(state, 1);
    }

    public static Record getTodays(State state, double price) {
        Record record = new Record(new Date(), price);
        record.setState(state);
        return record;
    }
}
