package com.stocks.technical.tasks.analyzer.algorithms;

import com.stocks.technical.core.db.entity.Candle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.stocks.technical.core.api.helpers.Constants.MIN_ACCEPTED_FALL_PERCENTAGE_FOR_STRONG_FALL_CANDLE;
import static com.stocks.technical.tasks.utils.CandleUtils.calculateGapPercentageProfit;
import static java.util.Arrays.asList;

public class StrongGapFallCandleAlgorithm {
    public static List<String> data = new ArrayList<>();

    public static boolean isStrongGapFallCandle(Candle candleBefore, Candle target) {
        BigDecimal gapPercentageProfit = calculateGapPercentageProfit(asList(candleBefore, target));

        boolean bigGap = gapPercentageProfit
                .compareTo(MIN_ACCEPTED_FALL_PERCENTAGE_FOR_STRONG_FALL_CANDLE) < 0 &&
                target.getTrend() == Candle.Trend.DOWN;

        if (bigGap) {
            data.add(target.getId() + " fall " + gapPercentageProfit);
        }

        return bigGap;
    }

    public static List<String> getData() {
        return data;
    }
}
