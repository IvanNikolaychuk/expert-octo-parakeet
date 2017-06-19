package com.stocks.technical.tasks.analyzer.algorithms;

import com.stocks.technical.core.db.entity.Candle;

import static com.stocks.technical.core.api.helpers.Constants.MIN_ACCEPTED_RISE_PERCENTAGE_FOR_STRONG_FALL_CANDLE;
import static com.stocks.technical.tasks.utils.CandleUtils.calculateGapPercentageProfit;
import static java.util.Arrays.asList;

public class StrongGapRiseCandleAlgorithm {
    public static boolean isStrongGapGrowCandle(Candle candleBefore, Candle target) {
        return calculateGapPercentageProfit(asList(candleBefore, target))
                .compareTo(MIN_ACCEPTED_RISE_PERCENTAGE_FOR_STRONG_FALL_CANDLE) > 0;
    }
}
