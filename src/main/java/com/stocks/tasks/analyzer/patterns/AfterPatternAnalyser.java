package com.stocks.tasks.analyzer.patterns;

import com.stocks.core.db.dao.CompanyDao;
import com.stocks.core.db.dao.strongbull.AfterPatternStatisticDao;
import com.stocks.core.db.entity.Candle;
import com.stocks.core.db.entity.company.Company;
import com.stocks.core.db.entity.statistic.stongbull.AfterPatternStatistic;
import com.stocks.tasks.analyzer.helpers.CandleByDateSequence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.stocks.tasks.utils.CandleCommonOperations.candleBrokeSupport;
import static com.stocks.tasks.utils.CandleUtils.calculatePercentageProfit;
import static com.stocks.tasks.utils.filters.CandlesFilter.filterByPattern;

/**
 * @author Ivan Nikolaichuk
 */
public class AfterPatternAnalyser {
    private final CompanyDao companyDao = new CompanyDao();
    private final AfterPatternStatisticDao strongBullStatisticDataDao = new AfterPatternStatisticDao();

    public void analyse(Candle.Pattern pattern) {
        for (Company company : companyDao.getAll()) {
            strongBullStatisticDataDao.save(analyse(company, pattern));
        }
    }


    private List<AfterPatternStatistic> analyse(Company company, Candle.Pattern pattern) {
        List<Candle> allCandles = company.getCandles();

        List<AfterPatternStatistic> afterPatternStatistics = new ArrayList<>();
        for (Candle strongBull : filterByPattern(allCandles, pattern)) {
            AfterPatternStatistic afterPatternStatistic = analyse(strongBull, new CandleByDateSequence(allCandles));
            afterPatternStatistic.setCompanyName(company.getName());
            afterPatternStatistics.add(afterPatternStatistic);
        }

        return afterPatternStatistics;
    }


    private AfterPatternStatistic analyse(Candle candleWithPattern, CandleByDateSequence candleByDateSequence) {
        AfterPatternStatistic afterPatternStatistic = new AfterPatternStatistic(candleWithPattern);

        candleByDateSequence.setCurrent(candleWithPattern);
        int candleCounter = 0;
        while (candleByDateSequence.hasNext() && candleCounter < 30) {
            candleCounter++;

            Candle nextCandle = candleByDateSequence.next();
            afterPatternStatistic.setProfit(calculatePercentageProfit(candleWithPattern.getClose(), nextCandle.getClose()), candleCounter);

            final boolean priceIsLowerThanSupport = candleWithPattern.getClose().compareTo(nextCandle.getClose()) > 0;
            if (afterPatternStatistic.priceAlwaysWasHigherThanFirstClose && priceIsLowerThanSupport) {
                afterPatternStatistic.priceAlwaysWasHigherThanFirstClose = false;
            }

            final BigDecimal halfSupportLevel = candleWithPattern.getLow().add(candleWithPattern.getBody().divide(BigDecimal.valueOf(2)));
            final boolean halfSupportIsBroken = halfSupportLevel.compareTo(nextCandle.getClose()) > 0;
            if (!afterPatternStatistic.halfSupportIsBroken && halfSupportIsBroken) {
                afterPatternStatistic.halfSupportIsBroken = true;
            }

            if (!afterPatternStatistic.supportIsBroken && candleBrokeSupport(candleWithPattern.getLow(), nextCandle)) {
                afterPatternStatistic.supportIsBroken = true;
            }

        }

        return afterPatternStatistic;
    }

}
