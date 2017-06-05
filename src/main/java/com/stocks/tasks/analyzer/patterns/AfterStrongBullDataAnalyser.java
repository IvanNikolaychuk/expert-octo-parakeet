package com.stocks.tasks.analyzer.patterns;

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
import static com.stocks.tasks.utils.CandleCommonOperations.candleBrokeSupport;
import static com.stocks.tasks.utils.CandleUtils.calculatePercentageProfit;
import static com.stocks.tasks.utils.filters.CandlesFilter.filterByPattern;


public class AfterStrongBullDataAnalyser {

    public void execute() {
        List<Company> companies = new CompanyDao().getAll();
        AfterStrongBullStatisticDao strongBullStatisticDataDao = new AfterStrongBullStatisticDao();
        for (Company company : companies) {
            strongBullStatisticDataDao.save(analyse(company));
        }
    }

    private List<AfterStrongBullStatistic> analyse(Company company) {
        List<Candle> allCandles = company.getCandles();
        List<Candle> strongBulls = filterByPattern(allCandles, STRONG_BULL);

        List<AfterStrongBullStatistic> afterStrongBullStatistics = new ArrayList<>();
        for (Candle strongBull : strongBulls) {
            AfterStrongBullStatistic afterStrongBullStatistic = analyse(strongBull, new CandleByDateSequence(allCandles));
            afterStrongBullStatistic.setCompanyName(company.getName());
            afterStrongBullStatistics.add(afterStrongBullStatistic);
        }

        return afterStrongBullStatistics;
    }

    private AfterStrongBullStatistic analyse(Candle strongBull, CandleByDateSequence candleByDateSequence) {
        AfterStrongBullStatistic afterStrongBullStatistic = new AfterStrongBullStatistic(strongBull);

        candleByDateSequence.setCurrent(strongBull);
        int candleCounter = 0;
        while (candleByDateSequence.hasNext() && candleCounter < 30) {
            candleCounter++;

            Candle nextCandle = candleByDateSequence.next();
            afterStrongBullStatistic.setProfit(calculatePercentageProfit(strongBull.getClose(), nextCandle.getClose()), candleCounter);

            final boolean priceIsLowerThanSupport = strongBull.getClose().compareTo(nextCandle.getClose()) > 0;
            if (afterStrongBullStatistic.priceAlwaysWasHigherThanFirstClose && priceIsLowerThanSupport) {
                afterStrongBullStatistic.priceAlwaysWasHigherThanFirstClose = false;
            }

            final BigDecimal halfSupportLevel = strongBull.getLow().add(strongBull.getBody().divide(BigDecimal.valueOf(2)));
            final boolean halfSupportIsBroken = halfSupportLevel.compareTo(nextCandle.getClose()) > 0;
            if (!afterStrongBullStatistic.halfSupportIsBroken && halfSupportIsBroken) {
                afterStrongBullStatistic.halfSupportIsBroken = true;
            }

            if (!afterStrongBullStatistic.supportIsBroken && candleBrokeSupport(strongBull.getLow(), nextCandle)) {
                afterStrongBullStatistic.supportIsBroken = true;
            }


        }

        return afterStrongBullStatistic;
    }

    public static void main(String[] args) {
        new AfterStrongBullDataAnalyser().execute();
    }
}
