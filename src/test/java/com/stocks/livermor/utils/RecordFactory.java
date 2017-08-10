package com.stocks.livermor.utils;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.State;

import java.util.Calendar;
import java.util.Date;

public class RecordFactory {
    public static Record getYestredays() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        return new Record(calendar.getTime(), 1);
    }

    public static Record getYestredays(boolean isPivotPoint) {
        Record record = getYestredays();
        record.setPivotPoint(isPivotPoint);
        return record;
    }

    public static Record getYestredays(State state) {
        Record record = getYestredays();
        record.setState(state);
        return record;
    }

    public static Record getYestredays(State state, boolean isPivotPoint, double price) {
        Record record = getYestredays(state, price);
        record.setPivotPoint(isPivotPoint);
        return record;
    }

    public static Record getYestredays(State state, double price) {
        Record record = getYestredays();
        record.setState(state);
        record.setPrice(price);
        return record;
    }

    public static Record get2DaysAgo(boolean isPivotPoint, double price) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -2);

        Record record = new Record(calendar.getTime(), price);
        record.setPivotPoint(isPivotPoint);
        record.setPrice(price);
        return record;

    }

    public static Record get2DaysAgo(State state, boolean isPivotPoint, double price) {
        Record record = get2DaysAgo(isPivotPoint, price);
        record.setState(state);
        return record;
    }


    public static Record getTodays(State state) {
        Record record = new Record(new Date(), 1);
        record.setState(state);
        return record;
    }

    public static Record getTodays(State state, boolean isPivotPoint) {
        Record record = getTodays(state, 1);
        record.setPivotPoint(isPivotPoint);
        return record;
    }

    public static Record getTodays(State state, double price) {
        Record record = new Record(new Date(), price);
        record.setState(state);
        return record;
    }
}
