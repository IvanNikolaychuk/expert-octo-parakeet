package com.stocks.livermor.utils;

import com.stocks.livermor.entity.Record;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;

import static com.stocks.livermor.entity.PivotPointType.NONE;
import static java.util.stream.Collectors.toList;

@Getter
public class RecordsHolder {
    private List<Record> records;

    public RecordsHolder(List<Record> records) {
        this.records = records;
        this.records.sort(new ByDateComparator());
    }

    public Record last() {
        return records.get(records.size() - 1);
    }

    public PivotPointsHolder getPivotPoints() {
        List<Record> pivotPoints = records.stream()
                .filter(record -> record.getPivotPointType() != NONE)
                .collect(toList());
        return new PivotPointsHolder(pivotPoints);
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
