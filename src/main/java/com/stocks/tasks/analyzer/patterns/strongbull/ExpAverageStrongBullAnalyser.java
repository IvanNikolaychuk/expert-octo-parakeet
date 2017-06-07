package com.stocks.tasks.analyzer.patterns.strongbull;

import com.stocks.core.db.dao.CompanyDao;
import com.stocks.core.db.dao.strongbull.ExpAverageCandleDao;
import com.stocks.core.db.entity.Candle;
import com.stocks.core.db.entity.company.Company;
import com.stocks.core.db.entity.statistic.stongbull.ExpAverageCandle;
import com.stocks.tasks.analyzer.helpers.CandleByDateSequence;
import com.stocks.tasks.analyzer.movingavarage.ExpMovingAverage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ExpAverageStrongBullAnalyser {
    private final CompanyDao companyDao = new CompanyDao();
    private final ExpAverageCandleDao expAverageCandleDao = new ExpAverageCandleDao();

    public void analyze() {
        for (Company company : companyDao.getAll()) {
            expAverageCandleDao.save(analyze(company));
        }
    }

    public List<ExpAverageCandle> analyze(Company company) {
        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(company.getCandles());
        ExpMovingAverage expMovingAverage = makeFirst100(candleByDateSequence);

        List<ExpAverageCandle> expAverageCandles = new ArrayList<>();
        while (candleByDateSequence.hasNext()) {
            Candle nextCandle = candleByDateSequence.next();
            BigDecimal expAverage = expMovingAverage.compute(expMovingAverage, nextCandle.getClose());
            expAverageCandles.add(new ExpAverageCandle(nextCandle, expAverage));
        }

        return expAverageCandles;
    }

    /**
     * Мы можем начинать вести статистику только после того, как у нас есть length (=20) свечей.
     */
    private ExpMovingAverage makeFirst100(CandleByDateSequence candleByDateSequence) {
        ExpMovingAverage expMovingAverage = new ExpMovingAverage();
        // first run
        expMovingAverage.compute(null, candleByDateSequence.next().getClose());

        int counter = 0;
        while (candleByDateSequence.hasNext() && counter != 100) {
            counter++;
            expMovingAverage.compute(expMovingAverage, candleByDateSequence.next().getClose());
        }

        return expMovingAverage;
    }

    public static void main(String[] args) {
        new ExpAverageStrongBullAnalyser().analyze();
    }

}
