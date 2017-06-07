package com.stocks.tasks.analyzer.patterns.strongbull;

import com.stocks.tasks.analyzer.patterns.AfterPatternAnalyser;

import static com.stocks.core.db.entity.Candle.Pattern.STRONG_BULL;


public class AfterStrongBullDataAnalyser {

    public static void main(String[] args) {
        new AfterPatternAnalyser().analyse(STRONG_BULL);
    }
}
