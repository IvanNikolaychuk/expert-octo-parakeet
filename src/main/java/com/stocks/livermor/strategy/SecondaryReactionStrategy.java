package com.stocks.livermor.strategy;

import com.stocks.livermor.Constants.Rule;
import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.State;
import com.stocks.livermor.utils.RecordsHolder;
import org.springframework.util.Assert;

import static com.stocks.livermor.Constants.Rule.*;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordPriceMovementUtils.*;
import static com.stocks.livermor.utils.RecordUtils.strongRally;
import static com.stocks.livermor.utils.RecordsHolder.NULL_OBJECT;

public class SecondaryReactionStrategy implements StateProcessor {

    @Override
    public void process(RecordsHolder recordsHolder, Record newRecord) {
        final Record last = recordsHolder.lastWithState();
        Assert.isTrue(last.getState() == SECONDARY_REACTION);

        if (upperTrendPivotPointIsBroken(recordsHolder, newRecord))
            newRecord.setStateAndRule(UPPER_TREND, _11a);

        if (strongRally(recordsHolder.lastWithState(), newRecord)) {
            if (priceIsLowerThanLastNaturalRally(recordsHolder, newRecord))
                newRecord.setStateAndRule(SECONDARY_RALLY, _6g);
            else {
                final State newState = rallyPivotPointIsBroken(recordsHolder, newRecord) ? UPPER_TREND : NATURAL_RALLY;
                final Rule rule = rallyPivotPointIsBroken(recordsHolder, newRecord) ? _5a : _6d;
                newRecord.setStateAndRule(newState, rule);
            }
        }
        if (reactionPivotPointIsBroken(recordsHolder, newRecord))
            newRecord.setStateAndRule(DOWN_TREND, _5b);

        checkPriceIsLowerThanLastInNaturalReaction(recordsHolder, newRecord);

        if (newRecord.getPrice() < last.getPrice())
            newRecord.setStateAndRule(SECONDARY_REACTION, _12_secondary_reaction);
    }



    private void checkPriceIsLowerThanLastInNaturalReaction(RecordsHolder recordsHolder, Record newRecord) {
        Record lastReaction = recordsHolder.last(NATURAL_REACTION);

        if (lastReaction != NULL_OBJECT && newRecord.getPrice() < lastReaction.getPrice())
            newRecord.setStateAndRule(NATURAL_REACTION, _6h3);
    }
}
