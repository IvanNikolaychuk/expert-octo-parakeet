package com.stocks.livermor.strategy.book.utils;

import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.DAY_OF_MONTH;

public class DateGenerator {
    private static Calendar date;

    static {
        date = Calendar.getInstance();
        date.add(Calendar.YEAR, -7);
    }

    public static Date next() {
        date.add(DAY_OF_MONTH, 1);
        return date.getTime();
    }
}
