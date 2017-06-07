package com.stocks.tasks.analyzer;

import com.stocks.tasks.analyzer.patterns.strongbull.AfterStrongBullDataAnalyser;
import com.stocks.tasks.analyzer.patterns.CandlesPatternAnalyser;
import com.stocks.tasks.analyzer.volume.StockVolumeAnalyser;
import com.stocks.tasks.daily.RecentDataObtainTask;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class Analysers {

    public void execute() {
        obtainRecentData();
//        analyseAvgVolume();
        analyseCandlePatterns();
//        new AfterStrongBullDataAnalyser().execute();
    }

    private void analyseCandlePatterns() {
        new CandlesPatternAnalyser().execute();
    }

    private void analyseAvgVolume() {
        new StockVolumeAnalyser().execute();
    }

    private void obtainRecentData() {
        new RecentDataObtainTask().execute();
    }


    public static void main(String[] args) {
        new Analysers().execute();
    }
}
