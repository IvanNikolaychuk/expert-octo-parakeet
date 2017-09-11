package com.stocks.livermor.algorithms.single;

import com.stocks.livermor.algorithms.Deal;
import com.stocks.livermor.entity.Record;
import com.stocks.livermor.utils.RecordsHolder;
import com.stocks.livermor.utils.Trend;

import java.util.ArrayList;
import java.util.List;

import static com.stocks.livermor.algorithms.Deal.Type.LONG;
import static com.stocks.livermor.algorithms.Deal.Type.SHORT;
import static com.stocks.livermor.utils.Trend.DOWN;
import static com.stocks.livermor.utils.Trend.UP;

/**
 * Go short on down trend, go long on upper trend till trend is not changed
 * Very bad result.
 */
public class SellOrBuyOnTrendChange extends AbstractStrategy {
    @Override
    public List<Deal> process(RecordsHolder recordsHolder) {
        List<Deal> deals = new ArrayList<>();

        Deal currentDeal = null;
        RecordsHolder recordsHolderCopy = new RecordsHolder();
        for (Record record : recordsHolder.getRecords()) {
            recordsHolderCopy.add(record);
            if (currentDeal == null) {
                final Deal.Type dealType = recordsHolderCopy.currentTrend() == UP ? LONG : SHORT;
                currentDeal = new Deal(record.getPrice(), dealType, record.getTicker());
            } else {
                final Deal.Type dealType = currentDeal.getType();
                final Trend currentTrend = recordsHolderCopy.currentTrend();

                if (dealType == SHORT && currentTrend == UP || dealType == LONG && currentTrend == DOWN ) {
                    currentDeal.close(record.getPrice());
                    deals.add(new Deal(currentDeal));
                    currentDeal = null;
                }
            }
        }

        return deals;
    }
}
