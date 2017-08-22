package com.stocks.livermor.utils;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.State;
import com.stocks.livermor.utils.RecordsHolder.ByDateComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    public Record lastPivotPointRecord(State state) {
        List<Record> pivotPoints = records.stream()
                .filter(record -> record.getState() == state && record.isPivotPoint())
                .sorted(new ByDateComparator().reversed())
                .collect(toList());

        return pivotPoints.isEmpty() ? NULL_OBJECT : pivotPoints.get(0);
    }

    public List<Record> getSupportAndResistance() {
        List<Record> pivotPoints = records.stream()
                .filter(Record::isPivotPoint)
                .sorted(new ByDateComparator())
                .collect(toList());
        if (pivotPoints.size() < 2) return new ArrayList<>();

        // last 2
        return Arrays.asList(pivotPoints.get(pivotPoints.size() - 1), pivotPoints.get(pivotPoints.size() - 2));
    }

    public boolean isAfterSupportOrResistence(Record record) {
        List<Record> supportAndResistance = getSupportAndResistance();
        if (supportAndResistance.isEmpty()) return false;
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

        Record lastTrendRecord = lastPivotPointRecord(last.getState());
        if (lastTrendRecord == NULL_OBJECT) return false;

        if (!check6aaccRule(lastTrendRecord, last)) return false;

        Record seperateTrendRecords = lastPivotPointRecord(recordState == UPPER_TREND ? DOWN_TREND : UPPER_TREND);
        if (seperateTrendRecords == NULL_OBJECT) return true;

        final Date lastSeparateTrendDate = seperateTrendRecords.getDate();

        boolean wasUperTrendBetween = lastSeparateTrendDate.compareTo(lastTrendRecord.getDate()) > 0;
        return !wasUperTrendBetween;
    }
}
