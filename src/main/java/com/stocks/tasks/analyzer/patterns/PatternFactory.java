package com.stocks.tasks.analyzer.patterns;

import com.stocks.core.db.entity.Candle;
import com.stocks.core.db.entity.Candle.Pattern;

import static com.stocks.core.db.entity.Candle.Pattern.*;
import static com.stocks.core.db.entity.Candle.Pattern.STRONG_BULL;
import static com.stocks.core.db.entity.Candle.Pattern.STRONG_GAP_FALL;
import static com.stocks.core.db.entity.Candle.Pattern.STRONG_GAP_RISE;
import static com.stocks.tasks.analyzer.algorithms.DojiCandleAlgorithm.isDojiCandle;
import static com.stocks.tasks.analyzer.algorithms.StrongBeerCandleAlgorithm.isStrongBearCandle;
import static com.stocks.tasks.analyzer.algorithms.StrongBullCandleAlgorithm.isStrongBullCandle;
import static com.stocks.tasks.analyzer.algorithms.StrongGapFallCandleAlgorithm.isStrongGapFallCandle;
import static com.stocks.tasks.analyzer.algorithms.StrongGapRiseCandleAlgorithm.isStrongGapGrowCandle;

/**
 * @author Ivan Nikolaichuk
 */
public class PatternFactory {

    public static Pattern findPattern(Candle candleBefore, Candle target) {
        if (isStrongGapGrowCandle(candleBefore, target)) return STRONG_GAP_RISE;
        if (isStrongGapFallCandle(candleBefore, target)) return STRONG_GAP_FALL;
        if (isStrongBullCandle(candleBefore, target)) return STRONG_BULL;
        if (isStrongBearCandle(candleBefore, target)) return STRONG_BEAR;
        if (isDojiCandle(target)) return DOJI;

        return NONE;
    }
}
