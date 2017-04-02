package com.tasks.analyzer;

import com.core.db.dao.CompanyGrowthStatisticDao;
import com.core.db.dao.InvestmentPeriodDataDao;
import com.tasks.analyzer.patterns.CandlesPatternAnalyser;
import com.tasks.analyzer.volume.StockVolumeAnalyser;
import com.tasks.daily.RecentDataObtainTask;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class Analysers {

    public void execute() {
        cleanPreviousData();
        obtainRecentData();
        analyseAvgVolume();
        analyseCandlePatterns();
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


    private void cleanPreviousData() {
        new InvestmentPeriodDataDao().clearAll();
        new CompanyGrowthStatisticDao().clearAll();
    }

    public static void main(String[] args) {
        new Analysers().execute();
    }
}
