package com.stocks.livermor.utils;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.State;

import java.util.*;

import static com.google.common.collect.Lists.newArrayList;
import static com.stocks.livermor.entity.State.DOWN_TREND;
import static com.stocks.livermor.entity.State.UPPER_TREND;
import static com.stocks.livermor.utils.Trend.*;
import static java.util.stream.Collectors.toList;

public class RecordsHolder {
    public static final Record NULL_OBJECT = new Record(null, 0d);
    private List<Record> records;
    private List<Record> newRecords;

    public RecordsHolder(List<Record> records) {
        this.records = records;
        this.newRecords = new ArrayList<>();
        sortByDate();
    }

    public RecordsHolder(Record... records) {
        this(newArrayList(records));
    }

    public void add(Record record) {
        records.add(record);
        newRecords.add(record);
        sortByDate();
    }

    public Trend currentTrend() {
        Record lastUp = last(UPPER_TREND);
        Record lastDown = last(DOWN_TREND);

        if (lastDown == NULL_OBJECT && lastUp == NULL_OBJECT)
            return NONE;
        if (lastDown == NULL_OBJECT)
            return UP;
        if (lastUp == NULL_OBJECT)
            return DOWN;

        return lastDown.getDate().compareTo(lastUp.getDate()) > 0 ? DOWN : UP;
    }

    public Record lastWithState() {
        for (int i = 0; i < records.size(); i++) {
            Record record = records.get(records.size() - 1 - i);
            if (record.getState() != State.NONE)
                return record;
        }
        return NULL_OBJECT;
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

    public Set<State> getStates() {
        List<State> states = new ArrayList<>();

        int recordIndex = index(getPivotPoints().getOldestTrendPoint());
        for (int i = records.size() - 1; i >= recordIndex; i--) {
            states.add(0, records.get(i).getState());
        }

        return new HashSet<>(states);
    }

    private int index(Record searchedRecord) {
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).equals(searchedRecord)) {
                return i;
            }
        }

        throw new IllegalArgumentException("No such record: " + searchedRecord);
    }

    private void sortByDate() {
        records.sort(new ByDateComparator());
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public List<Record> getNewRecords() {
        return newRecords;
    }

    /**
     * Last records go to end
     */
    public static class ByDateComparator implements Comparator<Record> {

        @Override
        public int compare(Record o1, Record o2) {
            if (o1.getDate() == null || o2.getDate() == null) {
                return 0;
            }
            return o1.getDate().compareTo(o2.getDate());
        }
    }

}
