package com.tasks.analyzer;

import com.core.db.dao.CompanyGrowthStatisticDao;
import com.core.db.dao.InvestmentPeriodDataDao;
import com.tasks.analyzer.patterns.CandlesPatternAnalyser;
import com.tasks.daily.RecentDataObtainTask;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class Analysers {

    public void execute() {
        cleanPreviousData();
        obtainRecentData();
        analyseAvgVolume();
        analyseInvestmentPeriods();
        analyseCandlePatterns();
        analyseCompanyGrowth();
    }

    private void analyseCompanyGrowth() {
        new CompanyGrowthAnalyser().execute();
    }

    private void analyseCandlePatterns() {
        new CandlesPatternAnalyser().execute();
    }


    private void analyseInvestmentPeriods() {
        new InvestmentPeriodsAnalyser().execute();
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
