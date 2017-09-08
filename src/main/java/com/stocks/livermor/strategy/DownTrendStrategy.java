package com.stocks.livermor.strategy;

import com.stocks.livermor.Constants.Rule;
import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.State;
import com.stocks.livermor.utils.RecordsHolder;
import org.springframework.util.Assert;

import static com.stocks.livermor.Constants.Rule.*;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordPriceMovementUtils.rallyPivotPointIsBroken;
import static com.stocks.livermor.utils.RecordUtils.priceIsLower;
import static com.stocks.livermor.utils.RecordUtils.strongRally;
import static com.stocks.livermor.utils.RecordsSignageSearcher.searchForSignals;

public class DownTrendStrategy implements StateProcessor {
    @Override
    public void process(RecordsHolder recordsHolder, Record newRecord) {
        processState(recordsHolder, newRecord);
        searchForSignals(recordsHolder, newRecord);
    }

    private void processState(RecordsHolder recordsHolder, Record newRecord) {
        final Record last = recordsHolder.lastWithState();
        Assert.isTrue(last.getState() == DOWN_TREND);

        if (priceIsLower(last, newRecord))
            newRecord.setStateAndRule(DOWN_TREND, _12_down);

        if (strongRally(last, newRecord)) {
            if (rallyPivotPointIsBroken(recordsHolder, newRecord)) {
                newRecord.setStateAndRule(UPPER_TREND, _5a);
            } else {
                State state = weakDownTrend(recordsHolder) ? SECONDARY_RALLY : NATURAL_RALLY;
                Rule rule = weakDownTrend(recordsHolder) ? _6cc : _6c;
                newRecord.setStateAndRule(state, rule);
            }

            last.markAsPivotPoint();
        }
    }

    /**
     * Это значит, что тренд пробивает свой предыдущий минимум, но меньше чем на 1%.
     */
    private boolean weakDownTrend(RecordsHolder recordsHolder) {
        final Record lastDownTrend = recordsHolder.lastWithState();
        return recordsHolder.getPivotPoints().check6ccRuleWhenReactionOccurred(lastDownTrend);
    }
}
