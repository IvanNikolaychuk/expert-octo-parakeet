package com.stocks.livermor.algorithms.single;

import com.stocks.livermor.algorithms.Deal;
import com.stocks.livermor.algorithms.Deal.Type;
import com.stocks.livermor.entity.Record;
import com.stocks.livermor.utils.RecordsHolder;
import com.stocks.livermor.utils.Trend;

import java.util.ArrayList;
import java.util.List;

import static com.stocks.livermor.algorithms.Deal.Type.LONG;
import static com.stocks.livermor.algorithms.Deal.Type.SHORT;
import static com.stocks.livermor.entity.State.DOWN_TREND;
import static com.stocks.livermor.entity.State.UPPER_TREND;
import static com.stocks.livermor.utils.Trend.DOWN;
import static com.stocks.livermor.utils.Trend.UP;

/**
 */
public class SellOrBuyOnTrendChangeWithSignals extends AbstractStrategy {

    @Override
    public List<Deal> process(RecordsHolder recordsHolder) {
        List<Deal> deals = new ArrayList<>();

        Deal currentDeal = null;
        RecordsHolder recordsHolderCopy = new RecordsHolder();
        boolean first = true;
        for (Record record : recordsHolder.getRecords()) {
            if (first) {
                first = false;
                continue;
            }
            recordsHolderCopy.add(record);
            if (currentDeal == null) {
                if (record.getState() == UPPER_TREND) {
                    currentDeal = new Deal(record.getPrice(), LONG, record.getTicker());
                } else if (record.getState() == DOWN_TREND) {
                    currentDeal = new Deal(record.getPrice(), SHORT, record.getTicker());
                }
            } else {
                final Type dealType = currentDeal.getType();
                final Trend currentTrend = recordsHolderCopy.currentTrend();

                if (dealType == SHORT && currentTrend == UP || dealType == LONG && currentTrend == DOWN
                        || record.hasSignal()) {
                    currentDeal.close(record.getPrice());
                    deals.add(new Deal(currentDeal));
                    currentDeal = null;
                }
            }
        }

        return deals;
    }
}
