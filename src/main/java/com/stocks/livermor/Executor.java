package com.stocks.livermor;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.strategy.StateProcessor;
import com.stocks.livermor.strategy.factory.StrategyPicker;
import com.stocks.livermor.utils.RecordsHolder;

public class Executor {
    private final StrategyPicker strategyPicker;

    public Executor(StrategyPicker strategyPicker) {
        this.strategyPicker = strategyPicker;
    }

    public void process(RecordsHolder recordsHolder, Record current) {
        final StateProcessor stateProcessor = strategyPicker.pick(recordsHolder.lastWithState());
        stateProcessor.process(recordsHolder, current);
        recordsHolder.add(current);
    }

    public static void main(String[] args) {

    }
}
