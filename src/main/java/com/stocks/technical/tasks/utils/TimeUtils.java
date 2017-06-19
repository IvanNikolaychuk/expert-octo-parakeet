package com.stocks.technical.tasks.utils;

import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    public static Calendar subtractDaysFromToday(int numberOfDays) {
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        today.add(Calendar.DAY_OF_MONTH, numberOfDays * (-1));

        return today;
    }

    public static Calendar yesterday() {
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        today.add(Calendar.DAY_OF_MONTH, (-1));

        return today;
    }

    public static Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar;
    }

    public static boolean between(Calendar target, Calendar low, Calendar high) {
        return target.compareTo(low) >= 0 && target.compareTo(high) <= 0;
    }
}
