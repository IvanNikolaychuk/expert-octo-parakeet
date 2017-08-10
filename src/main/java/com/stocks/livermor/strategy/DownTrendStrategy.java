package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.State;
import com.stocks.livermor.utils.PivotPointsHolder;
import com.stocks.livermor.utils.RecordsHolder;
import org.springframework.util.Assert;

import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordUtils.priceIsLower;
import static com.stocks.livermor.utils.RecordUtils.strongRally;

public class DownTrendStrategy implements ExecutionStrategy {

    @Override
    public void execute(RecordsHolder recordsHolder, Record newRecord) {
        final Record last = recordsHolder.last();
        Assert.isTrue(last.getState() == DOWN_TREND);

        if (priceIsLower(last, newRecord)) {
            newRecord.setState(DOWN_TREND);
            return;
        }

        if (strongRally(last, newRecord)) {
            last.markAsPivotPoint();

            PivotPointsHolder pivotPointsHolder = recordsHolder.getPivotPoints();
            State newRecordState = pivotPointsHolder.check6aaRuleWhenReactionOccurred(last) ? SECONDARY_RALLY : NATURAL_RALLY;
            newRecord.setState(newRecordState);
        }
    }
}
