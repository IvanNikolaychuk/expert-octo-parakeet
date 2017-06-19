package com.stocks.technical.tasks.analyzer.patterns.hammer;

import com.stocks.technical.tasks.analyzer.patterns.AfterPatternAnalyser;

import static com.stocks.technical.core.db.entity.Candle.Pattern.HAMMER;
import static com.stocks.technical.core.db.entity.Candle.Pattern.STRONG_HAMMER;

/**
 * @author Ivan Nikolaichuk
 */
public class AfterHammerDataAnalyser {
    public static void main(String[] args) {
        new AfterPatternAnalyser().analyse(STRONG_HAMMER);
        new AfterPatternAnalyser().analyse(HAMMER);
    }
}
