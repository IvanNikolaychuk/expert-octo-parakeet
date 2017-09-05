package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.utils.RecordsHolder;
import org.springframework.util.Assert;

import static com.stocks.livermor.Constants.Rule.*;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordPriceMovementUtils.reactionPivotPointIsBroken;
import static com.stocks.livermor.utils.RecordUtils.priceIsGrater;
import static com.stocks.livermor.utils.RecordUtils.strongReaction;

public class UpperTrendStrategy implements StateProcessor {

    @Override
    public void process(RecordsHolder recordsHolder, Record newRecord) {
        final Record last = recordsHolder.lastWithState();
        Assert.isTrue(last.getState() == UPPER_TREND);

        if (priceIsGrater(last, newRecord))
            newRecord.setStateAndRule(UPPER_TREND, _12_upper);

        if (strongReaction(last, newRecord)) {
            if (reactionPivotPointIsBroken(recordsHolder, newRecord)) {
                newRecord.setStateAndRule(DOWN_TREND, _5b);
            } else {
                boolean rule6aa = recordsHolder.getPivotPoints().check6aaRuleWhenReactionOccurred(last);
                newRecord.setState(rule6aa ? SECONDARY_REACTION : NATURAL_REACTION);
                newRecord.setExplanation(rule6aa ? _6aa.getExplanation() : _6a.getExplanation());
            }

            last.markAsPivotPoint();
        }
    }
}
