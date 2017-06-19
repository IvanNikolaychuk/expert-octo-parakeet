package com.stocks.technical.tasks.analyzer.algorithms;

import com.stocks.technical.core.db.entity.Candle;
import com.stocks.technical.tasks.analyzer.trend.TrendAnalyser;

import java.math.BigDecimal;
import java.util.List;

import static com.stocks.technical.core.api.helpers.Constants.MAX_ACCEPTED_UPPER_SHADOW_FROM_TOTAL_LENGTH_PERCENTAGE_FOR_HAMMER;
import static com.stocks.technical.core.api.helpers.Constants.MIN_ACCEPTED_LOWER_SHADOW_FROM_TOTAL_LENGTH_PERCENTAGE_FOR_HAMMER;
import static com.stocks.technical.tasks.analyzer.trend.TrendAnalyser.Movement.DOWN;
import static com.stocks.technical.tasks.analyzer.trend.TrendAnalyser.Movement.STRONG_DOWN;
import static com.stocks.technical.tasks.utils.CandleUtils.calculatePercentageProfit;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.valueOf;

/**
 * @author Ivan Nikolaichuk
 */
public class HammerCandleAlgorithm {
    public enum HammerType {
        NONE, HAMMER, STRONG_HAMMER
    }

    public static HammerType getHammerType(Candle target, List<Candle> allCandles) {

        boolean mightBeHammer = shadowsAreLow(target) &&
                priceChangeWasSignificant(target);

        if (!mightBeHammer) {
            return HammerType.NONE;
        }

        TrendAnalyser.Movement movement = TrendAnalyser.analyseTrendBefore(target, allCandles);

        if (movement == STRONG_DOWN) return HammerType.STRONG_HAMMER;
        if (movement == DOWN) return HammerType.HAMMER;
        return HammerType.NONE;

    }

    private static boolean priceChangeWasSignificant(Candle target) {
        return calculatePercentageProfit(target.getLow(), target.getHigh()).compareTo(ONE) >= 0;
    }

    private static boolean shadowsAreLow(Candle candle) {
        BigDecimal candleLength = candle.getHigh().subtract(candle.getLow());

        boolean lowerShadowIsBig = calculatePercentage(candleLength, candle.getLowerShadow())
                .compareTo(MIN_ACCEPTED_LOWER_SHADOW_FROM_TOTAL_LENGTH_PERCENTAGE_FOR_HAMMER) > 0;

        boolean upperShadowIsLow = calculatePercentage(candleLength, candle.getUpperShadow())
                .compareTo(MAX_ACCEPTED_UPPER_SHADOW_FROM_TOTAL_LENGTH_PERCENTAGE_FOR_HAMMER) < 0;

        return lowerShadowIsBig && upperShadowIsLow;
    }

    private static BigDecimal calculatePercentage(BigDecimal hole, BigDecimal part) {
        return part
                .multiply(valueOf(100))
                .divide(hole, BigDecimal.ROUND_UP);
    }


}
