package com.stocks.livermor.strategy.factory;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.strategy.DownTrendStrategy;
import com.stocks.livermor.strategy.ExecutionStrategy;
import com.stocks.livermor.strategy.UpperTrendStrategy;
import org.springframework.stereotype.Service;

@Service
public class StrategyPicker {
    public ExecutionStrategy pick(Record previousRecord) {
        switch (previousRecord.getState()) {
            case UPPER_TREND:
                return new UpperTrendStrategy();
            case DOWN_TREND:
                return new DownTrendStrategy();
            default:
                return null;
        }
    }
}
