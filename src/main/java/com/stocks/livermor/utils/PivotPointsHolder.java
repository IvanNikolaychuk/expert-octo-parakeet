package com.stocks.livermor.utils;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.State;
import com.stocks.livermor.utils.RecordsHolder.ByDateComparator;

import java.util.Date;
import java.util.List;

import static com.stocks.livermor.entity.State.DOWN_TREND;
import static com.stocks.livermor.entity.State.UPPER_TREND;
import static com.stocks.livermor.utils.RecordUtils.check6aaccRule;
import static java.util.stream.Collectors.toList;

public class PivotPointsHolder {
    private List<Record> records;

    public PivotPointsHolder(List<Record> records) {
        this.records = records;
        this.records.sort(new ByDateComparator());
    }

    public List<Record> lastPivotPointsRecords(State state) {
        return records.stream()
                .filter(record -> record.getState() == state && record.isPivotPoint())
                .sorted(new ByDateComparator())
                .collect(toList());
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

        List<Record> prevTrendRecords = lastPivotPointsRecords(last.getState());
        if (prevTrendRecords.isEmpty()) return false;
        final Record lastTrendRecord = prevTrendRecords.get(0);

        if (!check6aaccRule(lastTrendRecord, last)) return false;

        List<Record> seperateTrendRecords = lastPivotPointsRecords(recordState == UPPER_TREND ? DOWN_TREND : UPPER_TREND);
        if (seperateTrendRecords.isEmpty()) return true;

        final Date lastSeparateTrendDate = seperateTrendRecords.get(0).getDate();

        boolean wasUperTrendBetween = lastSeparateTrendDate.compareTo(lastTrendRecord.getDate()) > 0;
        return !wasUperTrendBetween;
    }
}
