package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.utils.RecordsHolder;
import org.springframework.util.Assert;

import static com.stocks.livermor.constants.Constants.Rule._6a;
import static com.stocks.livermor.constants.Constants.Rule._6aa;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordUtils.priceIsGrater;
import static com.stocks.livermor.utils.RecordUtils.strongReaction;

public class UpperTrendStrategy implements StateProcessor {

    @Override
    public void process(RecordsHolder recordsHolder, Record newRecord) {
        final Record last = recordsHolder.lastWithState();
        Assert.isTrue(last.getState() == UPPER_TREND);

        if (priceIsGrater(last, newRecord)) {
            newRecord.setState(UPPER_TREND);
            return;
        }

        if (strongReaction(last, newRecord)) {
            boolean rule6aa = recordsHolder.getPivotPoints().check6aaRuleWhenReactionOccurred(last);
            newRecord.setState(rule6aa ? SECONDARY_REACTION : NATURAL_REACTION);
            newRecord.setRule(rule6aa ? _6aa : _6a);

            last.markAsPivotPoint();
        }
    }
}
