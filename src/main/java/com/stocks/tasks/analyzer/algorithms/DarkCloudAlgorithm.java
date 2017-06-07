package com.stocks.tasks.analyzer.algorithms;

import com.stocks.core.api.helpers.Constants;
import com.stocks.core.db.entity.Candle;
import com.stocks.tasks.utils.CandleUtils;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

public class DarkCloudAlgorithm {
    public static boolean isDarkCloud(Candle candleBefore, Candle target) {
        boolean candleBeforePasses = candleBefore.getTrend() == Candle.Trend.UP
                && percentageProfitIsBig(candleBefore)
                && shadowsAreLow(candleBefore);

        if (!candleBeforePasses) return false;

        final BigDecimal candleBeforeLength = candleBefore.getHigh().subtract(candleBefore.getLow());
        final BigDecimal middle = candleBefore.getOpen().add(candleBeforeLength.divide(valueOf(2)));

        final boolean nextCandleClosesLowerThanMiddleOfLastCandle = target.getClose().compareTo(middle) < 0;
        final boolean nextCandleOpenedHigherThanLastClose = target.getOpen().compareTo(candleBefore.getClose()) > 0;

        return nextCandleClosesLowerThanMiddleOfLastCandle && nextCandleOpenedHigherThanLastClose;
    }

    private static boolean shadowsAreLow(Candle candle) {
        final BigDecimal candleLength = candle.getHigh().subtract(candle.getLow());

        boolean lowerShadowIsLow = calculateShadowPercentage(candleLength, candle.getLowerShadow())
                .compareTo(Constants.MAX_ACCEPTED_SHADOW_FROM_TOTAL_LENGTH_PERCENTAGE_FOR_DARK_CLOUD_CANDLE) <= 0;

        boolean upperShadowIsLow = calculateShadowPercentage(candleLength, candle.getUpperShadow())
                .compareTo(Constants.MAX_ACCEPTED_SHADOW_FROM_TOTAL_LENGTH_PERCENTAGE_FOR_DARK_CLOUD_CANDLE) <= 0;

        return lowerShadowIsLow && upperShadowIsLow;
    }

    private static BigDecimal calculateShadowPercentage(BigDecimal bodyLength, BigDecimal shadowLength) {

        return shadowLength
                .multiply(valueOf(100))
                .divide(bodyLength, BigDecimal.ROUND_UP);
    }


    private static boolean percentageProfitIsBig(Candle candle) {
        return CandleUtils.calculatePercentageProfit(candle)
                .compareTo(Constants.MIN_ACCEPTED_GROW_PERCENTAGE_FOR_DARK_CLOUD) >= 0;
    }
}
