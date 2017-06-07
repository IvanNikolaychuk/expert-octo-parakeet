package com.stocks.tasks.analyzer.patterns.hammer;

import com.stocks.tasks.analyzer.patterns.AfterPatternAnalyser;

import static com.stocks.core.db.entity.Candle.Pattern.HAMMER;
import static com.stocks.core.db.entity.Candle.Pattern.STRONG_BULL;
import static com.stocks.core.db.entity.Candle.Pattern.STRONG_HAMMER;

/**
 * @author Ivan Nikolaichuk
 */
public class AfterHammerDataAnalyser {
    public static void main(String[] args) {
        new AfterPatternAnalyser().analyse(STRONG_HAMMER);
        new AfterPatternAnalyser().analyse(HAMMER);
    }
}
