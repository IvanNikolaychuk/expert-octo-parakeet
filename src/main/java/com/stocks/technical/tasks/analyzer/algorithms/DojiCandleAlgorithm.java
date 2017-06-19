package com.stocks.technical.tasks.analyzer.algorithms;

import com.stocks.technical.core.db.entity.Candle;

import java.math.BigDecimal;

public class DojiCandleAlgorithm {
    public static boolean isDojiCandle(Candle target) {
        BigDecimal percentageProfit = target.getPercentageProfit();
        if (percentageProfit.compareTo(BigDecimal.ZERO) < 0) {
            percentageProfit = percentageProfit.negate();
        }

        return percentageProfit.compareTo(BigDecimal.valueOf(0.1)) < 0;
    }
}
