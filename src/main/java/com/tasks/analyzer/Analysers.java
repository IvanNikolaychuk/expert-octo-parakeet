package com.tasks.analyzer;

import com.tasks.daily.RecentDataObtainTask;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class Analysers {

    public void execute() {
        new RecentDataObtainTask().execute();
        new AvgStockVolumeComputer().execute();
    }

    public static void main(String[] args) {
        new Analysers().execute();
    }
}
