package com.tasks.analyzer.patterns;

import com.stocks.core.db.dao.AfterStrongBullStatisticDao;
import com.stocks.core.db.dao.CompanyDao;
import com.stocks.core.db.entity.Candle;
import com.stocks.core.db.entity.company.Company;
import com.stocks.core.db.entity.statistic.AfterStrongBullStatistic;
import com.stocks.tasks.analyzer.helpers.CandleByDateSequence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.stocks.core.db.entity.Candle.Pattern.STRONG_BULL;
import static com.stocks.tasks.utils.CandleUtils.calculatePercentageProfit;
import static com.stocks.tasks.utils.filters.CandlesFilter.filterByPattern;


public class StrongBullStatisticDataAnalyser {

    public void execute() {
        List<Company> companies = new CompanyDao().getAll();
        AfterStrongBullStatisticDao strongBullStatisticDataDao = new AfterStrongBullStatisticDao();
        for (Company company : companies) {
            List<Candle> allCandles = company.getCandles();
            List<Candle> strongBulls = filterByPattern(allCandles, STRONG_BULL);

            strongBullStatisticDataDao.save(analyse(allCandles, strongBulls));
        }
    }

    private List<AfterStrongBullStatistic> analyse(List<Candle> allCandles, List<Candle> strongBulls) {
        List<AfterStrongBullStatistic> afterStrongBullStatistics = new ArrayList<>();

        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(allCandles);
        for (Candle firstCandle : strongBulls) {
            afterStrongBullStatistics.add(analyse(firstCandle, candleByDateSequence));
        }

        return afterStrongBullStatistics;
    }

    private AfterStrongBullStatistic analyse(Candle firstCandle, CandleByDateSequence candleByDateSequence) {
        AfterStrongBullStatistic afterStrongBullStatistic = new AfterStrongBullStatistic(firstCandle);

        candleByDateSequence.setCurrent(firstCandle);
        int candleCounter = 0;

        while (candleByDateSequence.hasNext() && candleCounter < 20) {
            candleCounter++;
            candleByDateSequence.next();

            try {
                Candle nextCandle = candleByDateSequence.getCurrent();
                afterStrongBullStatistic.setProfit(calculatePercentageProfit(firstCandle.getClose(), nextCandle.getClose()), candleCounter);

                final boolean priceIsLowerThanSupport = firstCandle.getClose().compareTo(nextCandle.getClose()) > 0;
                if (afterStrongBullStatistic.priceAlwaysWasHigherThanFirstClose && priceIsLowerThanSupport) {
                    afterStrongBullStatistic.priceAlwaysWasHigherThanFirstClose = false;
                }

                final BigDecimal halfSupportLevel = firstCandle.getOpen().add(firstCandle.getBody().divide(BigDecimal.valueOf(2)));
                final boolean halfSupportIsBroken = halfSupportLevel.compareTo(nextCandle.getClose()) > 0;
                if (!afterStrongBullStatistic.halfSupportIsBroken && halfSupportIsBroken) {
                    afterStrongBullStatistic.halfSupportIsBroken = true;
                }

                final boolean supportIsBroken = firstCandle.getOpen().compareTo(nextCandle.getClose()) > 0;
                if (!afterStrongBullStatistic.supportIsBroken && supportIsBroken) {
                    afterStrongBullStatistic.supportIsBroken = true;
                }

            } catch (IndexOutOfBoundsException e) {
                continue;
            }

        }

        return afterStrongBullStatistic;
    }

    public static void main(String[] args) {
        new StrongBullStatisticDataAnalyser().execute();
    }
}
