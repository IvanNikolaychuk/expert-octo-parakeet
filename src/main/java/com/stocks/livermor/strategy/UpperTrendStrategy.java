package com.stocks.livermor.strategy;

import com.stocks.livermor.Constants;
import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.State;
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
                State state = weakDownTrend(recordsHolder) ? SECONDARY_REACTION : NATURAL_REACTION;
                Constants.Rule rule = weakDownTrend(recordsHolder) ? _6aa : _6a;
                newRecord.setStateAndRule(state, rule);
            }

            last.markAsPivotPoint();
        }
    }

    /**
     * Это значит, что тренд пробивает свой предыдущий максимум, но меньше чем на 1%.
     */
    private boolean weakDownTrend(RecordsHolder recordsHolder) {
        final Record lastUpTrend = recordsHolder.lastWithState();
        return recordsHolder.getPivotPoints().check6aaRuleWhenReactionOccurred(lastUpTrend);
    }
}
