package com.tasks.analyzer.drafts.algorithms;

import com.core.api.helpers.Constants;
import com.core.db.entity.Candle;
import com.tasks.utils.CandleUtils;

public class StrongBullCandleAlgorithm {
    public static boolean isStrongBullCandle(Candle target) {
        if (target.getTrend() == Candle.Trend.DOWN) {
            return false;
        }

        return CandleUtils.calculatePercentageProfit(target)
                .compareTo(Constants.MIN_ACCEPTED_GROW_PERCENTAGE_FOR_STRONG_BULL_CANDLE) >= 0;
    }
}
