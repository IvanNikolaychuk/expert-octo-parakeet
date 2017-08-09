package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.utils.RecordsHolder;

public interface ExecutionStrategy {
    void execute(RecordsHolder recordsHolder, Record newRecord);
}
