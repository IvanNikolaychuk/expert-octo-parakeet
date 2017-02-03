package com.jobs.analyzer;

import com.core.db.dao.CandlesDao;
import com.core.db.entity.Candle;

import java.util.List;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class Analyser {

    public void execute() {
        CandlesDao stockDao = new CandlesDao();
        List<Candle> candles = stockDao.getCandles("BMY");
    }

    public static void main(String[] args) {
        new Analyser().execute();
    }
}
