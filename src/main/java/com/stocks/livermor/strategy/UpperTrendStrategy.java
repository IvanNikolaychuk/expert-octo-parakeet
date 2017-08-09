package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.State;
import com.stocks.livermor.utils.PivotPointsHolder;
import com.stocks.livermor.utils.RecordsHolder;

import static com.stocks.livermor.entity.State.NATURAL_REACTION;
import static com.stocks.livermor.entity.State.SECONDARY_REACTION;
import static com.stocks.livermor.entity.State.UPPER_TREND;
import static com.stocks.livermor.utils.RecordUtils.*;

public class UpperTrendStrategy implements ExecutionStrategy {

    @Override
    public void execute(RecordsHolder recordsHolder, Record newRecord) {
        final Record last = recordsHolder.last();
        if (priceIsGrater(last, newRecord)) {
            newRecord.setState(UPPER_TREND);
            return;
        }

        if (strongReaction(last, newRecord)) {
            last.markAsPivotPoint();

            PivotPointsHolder pivotPointsHolder = recordsHolder.getPivotPoints();
            State newRecordState = pivotPointsHolder.check6aaRuleWhenReactionOccurred(last) ? SECONDARY_REACTION : NATURAL_REACTION;
            newRecord.setState(newRecordState);
        }
    }
}
