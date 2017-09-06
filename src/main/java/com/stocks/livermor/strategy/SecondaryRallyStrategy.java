package com.stocks.livermor.strategy;

import com.stocks.livermor.Constants;
import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.State;
import com.stocks.livermor.utils.RecordsHolder;
import org.springframework.util.Assert;

import static com.stocks.livermor.Constants.Rule.*;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordPriceMovementUtils.*;
import static com.stocks.livermor.utils.RecordUtils.strongReaction;
import static com.stocks.livermor.utils.RecordsHolder.NULL_OBJECT;

public class SecondaryRallyStrategy implements StateProcessor {

    @Override
    public void process(RecordsHolder recordsHolder, Record newRecord) {
        final Record last = recordsHolder.lastWithState();
        Assert.isTrue(last.getState() == SECONDARY_RALLY);

        if (downTrendPivotPointIsBroken(recordsHolder, newRecord))
            newRecord.setStateAndRule(DOWN_TREND, _11b);

        checkStrongReaction(recordsHolder, newRecord);

        if (rallyPivotPointIsBroken(recordsHolder, newRecord))
            newRecord.setStateAndRule(UPPER_TREND, _5a);

        checkPriceIsHigherThanLastInNaturalRally(recordsHolder, newRecord);

        if (newRecord.getPrice() > last.getPrice())
            newRecord.setStateAndRule(SECONDARY_RALLY, _12_secondary_rally);
    }

    private void checkStrongReaction(RecordsHolder recordsHolder, Record newRecord) {
        if (strongReaction(recordsHolder.lastWithState(), newRecord)) {
            if (priceIsGraterThanLastNaturalReaction(recordsHolder, newRecord))
                newRecord.setStateAndRule(SECONDARY_REACTION, _6h);
            else {
                final State newState = reactionPivotPointIsBroken(recordsHolder, newRecord) ? DOWN_TREND : NATURAL_REACTION;
                final Constants.Rule rule = reactionPivotPointIsBroken(recordsHolder, newRecord) ? _5b : _6b;
                newRecord.setStateAndRule(newState, rule);
            }
        }
    }

    private void checkPriceIsHigherThanLastInNaturalRally(RecordsHolder recordsHolder, Record newRecord) {
        Record lastRally = recordsHolder.last(NATURAL_RALLY);

        if (lastRally != NULL_OBJECT && newRecord.getPrice() > lastRally.getPrice()) {
            newRecord.setStateAndRule(NATURAL_RALLY, _6g3);
        }
    }
}
