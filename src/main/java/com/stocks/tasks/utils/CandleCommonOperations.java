package com.stocks.tasks.utils;

import com.stocks.core.db.entity.Candle;

import java.math.BigDecimal;

/**
 * @author Ivan Nikolaichuk
 */
public class CandleCommonOperations {
    public boolean candleBrokeResistance(BigDecimal resistanceLevel, Candle candle) {
        return resistanceLevel.compareTo(candle.getClose()) > 0;
    }

}
