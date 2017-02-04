package com.tasks.analyzer;

import com.core.db.dao.InvestmentPeriodDataDao;
import com.core.db.dao.StatisticDataDao;
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
    }

    private void analyseInvestmentPeriods() {
        new InvestmentPeriodsAnalyser().execute();
    }

    private void analyseAvgVolume() {
        new AvgStockVolumeComputer().execute();
    }

    private void obtainRecentData() {
        new RecentDataObtainTask().execute();
    }


    private void cleanPreviousData() {
            new StatisticDataDao().clearAll();
            new InvestmentPeriodDataDao().clearAll();
    }

    public static void main(String[] args) {
        new Analysers().execute();
    }
}
