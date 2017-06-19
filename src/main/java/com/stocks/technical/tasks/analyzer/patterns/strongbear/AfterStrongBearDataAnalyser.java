package com.stocks.technical.tasks.analyzer.patterns.strongbear;

import com.stocks.technical.tasks.analyzer.patterns.AfterPatternAnalyser;

import static com.stocks.technical.core.db.entity.Candle.Pattern.STRONG_BEAR;


public class AfterStrongBearDataAnalyser {

    public static void main(String[] args) {
        new AfterPatternAnalyser().analyse(STRONG_BEAR);
    }
}
