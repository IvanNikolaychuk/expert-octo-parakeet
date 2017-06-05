package com.stocks.tasks.strategy;

import com.stocks.core.db.dao.CompanyDao;
import com.stocks.core.db.dao.ProfitDataDao;
import com.stocks.core.db.dao.strongbull.ExpAverageCandleDao;
import com.stocks.core.db.entity.Candle;
import com.stocks.core.db.entity.ProfitData;
import com.stocks.core.db.entity.company.Company;
import com.stocks.core.db.entity.statistic.stongbull.ExpAverageCandle;
import com.stocks.tasks.analyzer.helpers.CandleByDateSequence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.stocks.core.db.entity.Candle.Pattern.STRONG_BULL;
import static com.stocks.tasks.utils.CandleUtils.calculatePercentageProfit;
import static com.stocks.tasks.utils.filters.CandlesFilter.filterByPattern;

/**
 * @author Ivan Nikolaichuk
 */
public class BuyOnStrongBullSellOnExpAverageBreak {
    private final CompanyDao companyDao = new CompanyDao();
    private final ExpAverageCandleDao expAverageCandleDao = new ExpAverageCandleDao();
    private final ProfitDataDao profitDataDao = new ProfitDataDao();

    public void analyze() {
        for (Company company : companyDao.getAll()) {
            profitDataDao.save(analyze(company));
        }
    }

    public List<ProfitData> analyze(Company company) {
        List<ProfitData> profitDataList = new ArrayList<>();

        List<Candle> strongBulls = filterWithExpAvg(filterByPattern(company.getCandles(), STRONG_BULL));

        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(company.getCandles());
        for (Candle strongBull : strongBulls) {
            ProfitData profitData = new ProfitData(strongBull);
            candleByDateSequence.setCurrent(strongBull);

            while (candleByDateSequence.hasNext()) {
                Candle nextCandle = candleByDateSequence.next();
                BigDecimal movingAvg = expAverageCandleDao.findByCandleId(nextCandle.getId()).value;

                if (nextCandle.getClose().compareTo(movingAvg) < 0) {
                    profitData.percProfit = calculatePercentageProfit(strongBull.getClose(), nextCandle.getClose());
                    profitDataList.add(profitData);
                    break;
                }
            }
        }

        return profitDataList;
    }

    private List<Candle> filterWithExpAvg(List<Candle> strongBulls) {
        List<Candle> filtered = new ArrayList<>();

        for (Candle candle : strongBulls) {
            ExpAverageCandle expAverageCandle = expAverageCandleDao.findByCandleId(candle.getId());
            if (expAverageCandle != null) {
                if (candle.getClose().compareTo(expAverageCandle.getValue()) > 0) {
                    filtered.add(candle);
                }
            }
        }

        return filtered;
    }

    public static void main(String[] args) {
        new BuyOnStrongBullSellOnExpAverageBreak().analyze();
    }
}
