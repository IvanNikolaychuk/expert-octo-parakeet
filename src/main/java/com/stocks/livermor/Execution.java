package com.stocks.livermor;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.utils.RecordsHolder;
import com.stocks.livermor.strategy.factory.StrategyPicker;
import org.springframework.beans.factory.annotation.Autowired;

public class Execution {
    @Autowired
    private final StrategyPicker strategyPicker;

    public Execution(StrategyPicker strategyPicker) {
        this.strategyPicker = strategyPicker;
    }

    public void process(RecordsHolder recordsHolder, Record current) {
        strategyPicker.pick(recordsHolder.last()).process(recordsHolder, current);
    }
}
