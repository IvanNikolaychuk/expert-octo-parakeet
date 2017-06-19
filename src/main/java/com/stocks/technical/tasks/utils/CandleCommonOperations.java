package com.stocks.technical.tasks.utils;

import com.stocks.technical.core.db.entity.Candle;

import java.math.BigDecimal;

/**
 * @author Ivan Nikolaichuk
 */
public class CandleCommonOperations {
    public static boolean candleBrokeSupport(BigDecimal supportLevel, Candle candle) {
        return supportLevel.compareTo(candle.getClose()) > 0;
    }
}
