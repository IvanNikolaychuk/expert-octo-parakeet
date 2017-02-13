package com.tasks.analyzer.drafts.algorithms;

import com.core.db.entity.Candle;

import static com.core.api.helpers.Constants.MIN_ACCEPTED_RISE_PERCENTAGE_FOR_STRONG_FALL_CANDLE;
import static com.tasks.utils.CandleUtils.calculateGapPercentageProfit;
import static java.util.Arrays.asList;

public class StrongGapRiseCandleAlgorithm {
    public static boolean isStrongGapRiseCandle(Candle candleBefore, Candle target) {
        return calculateGapPercentageProfit(asList(candleBefore, target))
                .compareTo(MIN_ACCEPTED_RISE_PERCENTAGE_FOR_STRONG_FALL_CANDLE) > 0;
    }
}
