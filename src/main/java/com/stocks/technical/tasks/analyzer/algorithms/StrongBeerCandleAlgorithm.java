package com.stocks.technical.tasks.analyzer.algorithms;

import com.stocks.technical.core.api.helpers.Constants;
import com.stocks.technical.core.db.entity.Candle;

import java.math.BigDecimal;

/**
 * @author Ivan Nikolaichuk
 */
public class StrongBeerCandleAlgorithm {
    public static boolean isStrongBearCandle(Candle candleBefore, Candle target) {
        if (candleBefore.getDate().compareTo(target.getDate()) > 0) {
            throw new IllegalStateException("candle before is younger than target");
        }

        return target.getTrend() == Candle.Trend.DOWN
                && percentageLossIsBig(target)
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

    static boolean bodyIsBig(Candle candleBefore, Candle candle) {
        return candleBefore.getBody().multiply(Constants.MIN_ACCEPTED_BODY_RATIO_FOR_STRONG_BULL_CANDLE)
                .compareTo(candle.getBody()) <= 0;
    }

    static boolean percentageLossIsBig(Candle candle) {
        BigDecimal minAcceptedPercentageLoss = Constants.MIN_ACCEPTED_GROW_PERCENTAGE_FOR_STRONG_BULL_CANDLE.negate();
        return candle.getPercentageProfit().compareTo(minAcceptedPercentageLoss) <= 0;
    }

}
