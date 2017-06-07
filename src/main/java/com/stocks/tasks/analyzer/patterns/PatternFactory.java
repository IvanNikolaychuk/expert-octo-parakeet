package com.stocks.tasks.analyzer.patterns;

import com.stocks.core.db.entity.Candle;
import com.stocks.core.db.entity.Candle.Pattern;
import com.stocks.tasks.analyzer.algorithms.HammerCandleAlgorithm;
import com.stocks.tasks.analyzer.algorithms.HammerCandleAlgorithm.HammerType;

import java.util.List;
import com.stocks.tasks.analyzer.algorithms.DarkCloudAlgorithm;

import static com.stocks.core.db.entity.Candle.Pattern.*;
import static com.stocks.core.db.entity.Candle.Pattern.STRONG_BULL;
import static com.stocks.core.db.entity.Candle.Pattern.STRONG_GAP_FALL;
import static com.stocks.core.db.entity.Candle.Pattern.STRONG_GAP_RISE;
import static com.stocks.tasks.analyzer.algorithms.DarkCloudAlgorithm.isDarkCloud;
import static com.stocks.tasks.analyzer.algorithms.DojiCandleAlgorithm.isDojiCandle;
import static com.stocks.tasks.analyzer.algorithms.HammerCandleAlgorithm.getHammerType;
import static com.stocks.tasks.analyzer.algorithms.StrongBeerCandleAlgorithm.isStrongBearCandle;
import static com.stocks.tasks.analyzer.algorithms.StrongBullCandleAlgorithm.isStrongBullCandle;
import static com.stocks.tasks.analyzer.algorithms.StrongGapFallCandleAlgorithm.isStrongGapFallCandle;
import static com.stocks.tasks.analyzer.algorithms.StrongGapRiseCandleAlgorithm.isStrongGapGrowCandle;

/**
 * @author Ivan Nikolaichuk
 */
public class PatternFactory {

    public static Pattern findPattern(Candle candleBefore, Candle target, List<Candle> allCandles) {
        if (isStrongGapGrowCandle(candleBefore, target)) return STRONG_GAP_RISE;
        if (isStrongGapFallCandle(candleBefore, target)) return STRONG_GAP_FALL;
        if (isStrongBullCandle(target)) return STRONG_BULL;

        if (isDarkCloud(candleBefore, target)) return DARK_CLOUD;

        if (isStrongBullCandle(candleBefore)) return STRONG_BULL;
        if (isStrongBearCandle(candleBefore, target)) return STRONG_BEAR;

        if (isDojiCandle(target)) return DOJI;

        HammerType hammerType = getHammerType(target, allCandles);
        if (hammerType == HammerType.HAMMER) return HAMMER;
        if (hammerType == HammerType.STRONG_HAMMER) return STRONG_HAMMER;

        return NONE;
    }
}
