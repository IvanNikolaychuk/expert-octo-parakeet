package com.stocks.livermor.utils;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.State;
import com.stocks.livermor.utils.RecordsHolder.ByDateComparator;

import java.util.*;

import static com.stocks.livermor.entity.State.DOWN_TREND;
import static com.stocks.livermor.entity.State.UPPER_TREND;
import static com.stocks.livermor.utils.RecordUtils.check6aaccRule;
import static com.stocks.livermor.utils.RecordsHolder.NULL_OBJECT;
import static java.util.stream.Collectors.toList;

public class PivotPointsHolder {
    private List<Record> records;

    public PivotPointsHolder(List<Record> records) {
        this.records = records;
        this.records.sort(new ByDateComparator());
    }

    public List<Record> getRecords() {
        return records;
    }

    public Record last(State state) {
        List<Record> pivotPoints = records.stream()
                .filter(record -> record.getState() == state && record.isPivotPoint())
                .sorted(new ByDateComparator().reversed())
                .collect(toList());

        return pivotPoints.isEmpty() ? NULL_OBJECT : pivotPoints.get(0);
    }

    public Record last() {
        List<Record> pivotPoints = records.stream()
                .filter(Record::isPivotPoint)
                .sorted(new ByDateComparator().reversed())
                .collect(toList());

        return pivotPoints.isEmpty() ? NULL_OBJECT : pivotPoints.get(0);
    }

    public List<Record> getSupportAndResistance() {
        List<Record> pivotPoints = records.stream()
                .filter(Record::isPivotPoint)
                .sorted(new ByDateComparator().reversed())
                .collect(toList());
        if (pivotPoints.size() < 1) return new ArrayList<>();

        final Record lastPivotPoint = pivotPoints.get(0);
        Record oneBeforeLast = null;
        for (Record nextPivotPoint : pivotPoints) {
            if (nextPivotPoint.getState() != lastPivotPoint.getState()) {
                oneBeforeLast = nextPivotPoint;
                break;
            }
        }

        if (oneBeforeLast == null) return new ArrayList<>();

        return Arrays.asList(lastPivotPoint, oneBeforeLast);
    }

    /**
     * TODO: здесь есть ошибка, когда новая запись становится сопротивлением или поддержкой, а мы берем старые 2 записи, не учитывая это.
     */
    public boolean isAfterSupportOrResistance(Record record) {
        List<Record> supportAndResistance = getSupportAndResistance();
        if (supportAndResistance.isEmpty()) return false;
        if (supportAndResistance.contains(record)) return true;

        final Date oldest = supportAndResistance.get(0).getDate();
        final Date currentRecordDate = record.getDate();

        return currentRecordDate.compareTo(oldest) >= 0;
    }


    public boolean check6aaRuleWhenReactionOccurred(Record last) {
        return check6RuleWhenReactionOccurred(last);
    }

    public boolean check6ccRuleWhenReactionOccurred(Record last) {
        return check6RuleWhenReactionOccurred(last);
    }

    private boolean check6RuleWhenReactionOccurred(Record last) {
        State recordState = last.getState();
        if (recordState != UPPER_TREND && recordState != DOWN_TREND) return false;

        Record lastTrendRecord = last(last.getState());
        if (lastTrendRecord == NULL_OBJECT) return false;

        if (!check6aaccRule(lastTrendRecord, last)) return false;

        Record seperateTrendRecords = last(recordState == UPPER_TREND ? DOWN_TREND : UPPER_TREND);
`        if (seperateTrendRecords == NULL_OBJECT) return false;

        final Date lastSeparateTrendDate = seperateTrendRecords.getDate();

        boolean wasSeparateTrendBetween = lastSeparateTrendDate.compareTo(lastTrendRecord.getDate()) > 0;
        return !wasSeparateTrendBetween;
    }

    public Record getOldestTrendPoint() {
        List<Record> recordsCopy = new ArrayList<>(records);
        Collections.reverse(recordsCopy);

        Record oldestTrendRecord = null;
        for (Record record : recordsCopy) {
            final State recordState = record.getState();
            if (recordState == UPPER_TREND || recordState == DOWN_TREND) {
                if (oldestTrendRecord == null) {
                    oldestTrendRecord = record;
                } else if (oldestTrendRecord.getState() != recordState) {
                    break;
                } else {
                    oldestTrendRecord = record;
                }
            }
        }

        return oldestTrendRecord;
    }
}
