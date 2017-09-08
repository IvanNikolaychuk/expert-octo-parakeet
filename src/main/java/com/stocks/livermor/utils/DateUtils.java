package com.stocks.livermor.utils;

import com.stocks.livermor.entity.Record;

import java.util.Calendar;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class DateUtils {

    public static boolean sameDate(Record firstRecord, Record secondRecord) {
        final Calendar firstDate = Calendar.getInstance();
        firstDate.setTime(firstRecord.getDate());

        final Calendar secondDate = Calendar.getInstance();
        secondDate.setTime(secondRecord.getDate());

        return sameDate(firstDate, secondDate);
    }

    public static boolean sameDate(Calendar firstDate, Calendar secondDate) {
        return firstDate.get(YEAR) == secondDate.get(YEAR) &&
                firstDate.get(MONTH) == secondDate.get(MONTH) &&
                firstDate.get(DAY_OF_MONTH) == secondDate.get(DAY_OF_MONTH);
    }
}
