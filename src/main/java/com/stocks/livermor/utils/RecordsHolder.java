package com.stocks.livermor.utils;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.State;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@Getter
public class RecordsHolder {
    public static final Record NULL_OBJECT = new Record(null, 0d);
    private List<Record> records;

    public RecordsHolder(List<Record> records) {
        this.records = records;
        this.records.sort(new ByDateComparator());
    }

    public RecordsHolder(Record ...records) {
        this.records = asList(records);
        this.records.sort(new ByDateComparator());
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

        for(Record record : recordsCopy) {
            if (record.getState() == state) {
                return record;
            }
        }

        return NULL_OBJECT;
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
