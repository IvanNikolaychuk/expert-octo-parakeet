package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.utils.RecordsHolder;
import org.springframework.util.Assert;

import static com.stocks.livermor.constants.Constants.Rule.*;
import static com.stocks.livermor.entity.State.*;
import static com.stocks.livermor.utils.RecordUtils.priceIsLower;
import static com.stocks.livermor.utils.RecordUtils.strongRally;

public class DownTrendStrategy implements StateProcessor {

    @Override
    public void process(RecordsHolder recordsHolder, Record newRecord) {
        final Record last = recordsHolder.last();
        Assert.isTrue(last.getState() == DOWN_TREND);

        if (priceIsLower(last, newRecord)) {
            newRecord.setState(DOWN_TREND);
            return;
        }

        if (strongRally(last, newRecord)) {
            last.markAsPivotPoint();

            boolean rule6cc = recordsHolder.getPivotPoints().check6aaRuleWhenReactionOccurred(last);
            newRecord.setState(rule6cc ? SECONDARY_RALLY : NATURAL_RALLY);
            newRecord.setRule(rule6cc ? _6cc : _6c);
        }
    }
}
