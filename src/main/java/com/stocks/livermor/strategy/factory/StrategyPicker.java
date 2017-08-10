package com.stocks.livermor.strategy.factory;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.strategy.*;
import org.springframework.stereotype.Service;

@Service
public class StrategyPicker {
    public StateProcessor pick(Record previousRecord) {
        switch (previousRecord.getState()) {
            case UPPER_TREND:
                return new UpperTrendStrategy();
            case DOWN_TREND:
                return new DownTrendStrategy();
            case SECONDARY_RALLY:
                return new SecondaryRallyStrategy();
            case SECONDARY_REACTION:
                return new SecondaryReactionStrategy();
            case NATURAL_RALLY:
                return new NaturalRallyStrategy();
            case NATURAL_REACTION:
                return new NaturalReactionStrategy();
            default:
                throw new IllegalArgumentException("Record has unknown state: " + previousRecord.getState());
        }
    }
}
