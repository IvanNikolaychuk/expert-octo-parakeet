package com.stocks.livermor.utils;

import com.stocks.livermor.entity.PivotPointType;
import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.State;
import com.stocks.livermor.utils.RecordsHolder.ByDateComparator;

import java.util.Date;
import java.util.List;

import static com.stocks.livermor.entity.PivotPointType.DOWN_TREND;
import static com.stocks.livermor.entity.PivotPointType.UPPER_TREND;
import static com.stocks.livermor.utils.RecordUtils.check6aaRule;
import static java.util.stream.Collectors.toList;

public class PivotPointsHolder {
    private List<Record> records;

    public PivotPointsHolder(List<Record> records) {
        this.records = records;
        this.records.sort(new ByDateComparator());
    }

    public List<Record> lastRecords(PivotPointType pivotPointType) {
        return records.stream()
                .filter(record -> record.getPivotPointType() == pivotPointType)
                .sorted(new ByDateComparator())
                .collect(toList());
    }

    public boolean check6aaRuleWhenReactionOccurred(Record last) {
        if (last.getState() != State.UPPER_TREND) return false;

        List<Record> upperTrendRecords = lastRecords(UPPER_TREND);
        if (upperTrendRecords.isEmpty()) return false;

        final Record lastUpperTrend = upperTrendRecords.get(0);
        if (!check6aaRule(lastUpperTrend, last)) return false;

        List<Record> downTrendRecords = lastRecords(DOWN_TREND);
        if (downTrendRecords.isEmpty()) return true;

        final Date lastDownTrendDate = downTrendRecords.get(0).getDate();

        boolean wasDownTrendBetween = lastDownTrendDate.compareTo(lastUpperTrend.getDate()) > 0;
        return !wasDownTrendBetween;
    }
}
