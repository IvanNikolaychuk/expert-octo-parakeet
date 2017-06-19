package com.stocks.technical.tasks.analyzer.patterns;

import com.stocks.technical.core.db.dao.CandlesDao;
import com.stocks.technical.core.db.dao.CompanyDao;
import com.stocks.technical.core.db.entity.Candle;
import com.stocks.technical.core.db.entity.company.Company;
import com.stocks.technical.tasks.analyzer.helpers.CandleByDateSequence;

import java.util.List;

public class CandlesPatternAnalyser {
    private final CandlesDao candlesDao = new CandlesDao();
    private final CompanyDao companyDao = new CompanyDao();

    public void execute() {

        for (Company company : companyDao.getAll()) {
            findAndSetPatterns(company.getCandles());
            candlesDao.update(company.getCandles());
        }
    }

    private void findAndSetPatterns(List<Candle> candles) {
        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(candles);

        while (candleByDateSequence.hasNext()) {
            Candle candle = candleByDateSequence.next();
            candle.setPattern(candleByDateSequence.findPattern());
        }
    }

    public static void main(String[] args) {
        new CandlesPatternAnalyser().execute();
    }
}
