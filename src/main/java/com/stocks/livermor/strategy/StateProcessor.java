package com.stocks.livermor.strategy;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.utils.RecordsHolder;

public interface StateProcessor {
    /**
     * Определяет какое состояние примет {@param newRecord}
     */
    void process(RecordsHolder recordsHolder, Record newRecord);
}
