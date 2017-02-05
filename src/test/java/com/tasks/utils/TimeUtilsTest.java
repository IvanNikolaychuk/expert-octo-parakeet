package com.tasks.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class TimeUtilsTest {
    @Test
    public void betweenInclusive() {
        Date now = new Date();

        Calendar from = createCalendar(now);
        Calendar to = createCalendar(now);
        Calendar target = createCalendar(now);

        Assert.assertTrue(TimeUtils.between(target, from, to));
    }

    private Calendar createCalendar(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        return calendar;
    }
}
