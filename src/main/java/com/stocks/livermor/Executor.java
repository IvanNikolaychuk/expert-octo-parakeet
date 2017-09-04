package com.stocks.livermor;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.strategy.StateProcessor;
import com.stocks.livermor.strategy.factory.StrategyPicker;
import com.stocks.livermor.utils.RecordsHolder;

import static com.stocks.livermor.entity.State.NONE;

public class Executor {
    private final StrategyPicker strategyPicker;

    public Executor(StrategyPicker strategyPicker) {
        this.strategyPicker = strategyPicker;
    }

    public void process(RecordsHolder recordsHolder, Record newRecord) {
        final StateProcessor stateProcessor = strategyPicker.pick(recordsHolder.lastWithState());
        stateProcessor.process(recordsHolder, newRecord);
        if (newRecord.getState() == null) newRecord.setState(NONE);

        recordsHolder.add(newRecord);
    }
}
