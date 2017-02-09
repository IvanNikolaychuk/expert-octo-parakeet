package com.tasks.utils;

import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
    public static boolean isSameDay(Calendar first, Calendar second) {
        return first.get(Calendar.DATE) == second.get(Calendar.DATE) &&
                first.get(Calendar.YEAR) == second.get(Calendar.YEAR) &&
                first.get(Calendar.MONTH) == second.get(Calendar.MONTH);
    }

    public static Calendar subtractDaysFromToday(int numberOfDays) {
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        today.add(Calendar.DAY_OF_MONTH, numberOfDays * (-1));

        return today;
    }

    public static Calendar subtractDaysFrom(Calendar startDate, int numberOfDays) {
        Calendar result = Calendar.getInstance();
        result.setTime(startDate.getTime());
        result.add(Calendar.DATE, numberOfDays * (-1));

        return result;
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
