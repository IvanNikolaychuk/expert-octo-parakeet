package com.stocks.livermor.utils;

import com.stocks.livermor.entity.Record;

import java.util.Calendar;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class RecordsFilter {
    public static List<Record> byYear(int minYear, List<Record> allRecords) {
        return allRecords.stream().filter(record -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(record.getDate());
            return calendar.get(Calendar.YEAR) >= minYear;
        }).collect(toList());
    }
}
