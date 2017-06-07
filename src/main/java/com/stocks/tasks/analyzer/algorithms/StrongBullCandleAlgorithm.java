package com.stocks.tasks.analyzer.algorithms;

import com.stocks.core.api.helpers.Constants;
import com.stocks.core.db.entity.Candle;
import com.stocks.tasks.utils.CandleUtils;

import java.math.BigDecimal;

import static com.stocks.core.db.entity.Candle.*;

public class StrongBullCandleAlgorithm {
    public static boolean isStrongBullCandle(Candle target) {
        return target.getTrend() == Trend.UP
                && percentageProfitIsBig(target)
                && shadowsAreLow(target);

    }

     static boolean shadowsAreLow(Candle candle) {

        boolean lowerShadowIsLow = calculateShadowPercentage(candle.getBody(), candle.getLowerShadow())
                .compareTo(Constants.MAX_ACCEPTED_LOWER_SHADOW_FROM_TOTAL_LENGTH_PERCENTAGE_FOR_STRONG_BULL_CANDLE) <= 0;

        boolean upperShadowIsLow = calculateShadowPercentage(candle.getBody(), candle.getUpperShadow())
                .compareTo(Constants.MAX_ACCEPTED_UPPER_SHADOW_FROM_TOTAL_LENGTH_PERCENTAGE_FOR_STRONG_BULL_CANDLE) <= 0;

        return lowerShadowIsLow && upperShadowIsLow;
    }

     static BigDecimal calculateShadowPercentage(BigDecimal bodyLength, BigDecimal shadowLength) {

        return shadowLength
                .multiply(BigDecimal.valueOf(100))
                .divide(bodyLength, BigDecimal.ROUND_UP);
    }


     static boolean percentageProfitIsBig(Candle candle) {
        return CandleUtils.calculatePercentageProfit(candle)
                .compareTo(Constants.MIN_ACCEPTED_GROW_PERCENTAGE_FOR_STRONG_BULL_CANDLE) >= 0;
    }
}
