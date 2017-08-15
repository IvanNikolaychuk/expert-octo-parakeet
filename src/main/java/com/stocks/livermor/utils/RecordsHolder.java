package com.stocks.livermor.utils;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.State;
import com.stocks.livermor.entity.Trend;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.stocks.livermor.entity.State.DOWN_TREND;
import static com.stocks.livermor.entity.State.UPPER_TREND;
import static com.stocks.livermor.entity.Trend.DOWN;
import static com.stocks.livermor.entity.Trend.NONE;
import static com.stocks.livermor.entity.Trend.UP;
import static java.util.stream.Collectors.toList;

@Getter
public class RecordsHolder {
    public static final Record NULL_OBJECT = new Record(null, 0d);
    private List<Record> records;

    public RecordsHolder(List<Record> records) {
        this.records = records;
        sortByDate();
    }

    public RecordsHolder(Record... records) {
        this(newArrayList(records));
    }

    public void add(Record record) {
        records.add(record);
        sortByDate();
    }

    public Trend currentTrend() {
        Record lastUp = last(UPPER_TREND);
        Record lastDown = last(DOWN_TREND);

        if (lastDown == NULL_OBJECT && lastUp == NULL_OBJECT)
            return NONE;
        if (lastDown == NULL_OBJECT && lastUp != NULL_OBJECT)
            return UP;
        if (lastUp == NULL_OBJECT && lastDown != NULL_OBJECT)
            return DOWN;

        return lastDown.getDate().compareTo(lastUp.getDate()) > 0 ? DOWN : UP;
    }

    public Record last() {
        return records.get(records.size() - 1);
    }

    public PivotPointsHolder getPivotPoints() {
        List<Record> pivotPoints = records.stream()
                .filter(Record::isPivotPoint)
                .collect(toList());
        return new PivotPointsHolder(pivotPoints);
    }

    public Record last(State state) {
        List<Record> recordsCopy = new ArrayList<>(this.records);
        recordsCopy.sort(new ByDateComparator().reversed());

        for (Record record : recordsCopy) {
            if (record.getState() == state) {
                return record;
            }
        }

        return NULL_OBJECT;
    }

    private void sortByDate() {
        records.sort(new ByDateComparator());
    }

    /**
     * Last records go to end
     */
    public static class ByDateComparator implements Comparator<Record> {

        @Override
        public int compare(Record o1, Record o2) {
            return o1.getDate().compareTo(o2.getDate());
        }
    }
}
