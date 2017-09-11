package com.stocks.livermor.algorithms.single;

import com.stocks.livermor.algorithms.Deal;
import com.stocks.livermor.entity.Record;
import com.stocks.livermor.utils.RecordsHolder;

import java.util.ArrayList;
import java.util.List;

import static com.stocks.livermor.algorithms.Deal.Type.LONG;

public class RandomBuyStrategy extends AbstractStrategy {
    public static final int NUMBER_OF_DAYS_HOLDING_POSITION = 10;

    @Override
    public List<Deal> process(RecordsHolder recordsHolder) {
        List<Deal> deals = new ArrayList<>();

        Deal currentDeal = null;
        int daysCounter = 0;
        for (Record record : recordsHolder.getRecords()) {
            if (currentDeal == null) {
                currentDeal = new Deal(record.getPrice(), LONG, record.getTicker());
                daysCounter = 1;
            } else {
                daysCounter++;
            }

            if (daysCounter == NUMBER_OF_DAYS_HOLDING_POSITION) {
                currentDeal.close(record.getPrice());
                deals.add(new Deal(currentDeal));
                currentDeal = null;
            }
        }

        return deals;
    }
}
