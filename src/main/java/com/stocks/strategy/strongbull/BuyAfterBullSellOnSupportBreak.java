package com.stocks.strategy.strongbull;

import com.stocks.core.db.dao.AfterStrongBullStatisticDao;
import com.stocks.core.db.dao.CompanyDao;
import com.stocks.core.db.dao.ProfitDataDao;
import com.stocks.core.db.entity.Candle;
import com.stocks.core.db.entity.company.Company;
import com.stocks.core.db.entity.statistic.AfterStrongBullStatistic;
import com.stocks.core.db.entity.strategy.ProfitData;
import com.stocks.tasks.analyzer.helpers.CandleByDateSequence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.stocks.core.db.entity.Candle.Pattern.STRONG_BULL;
import static com.stocks.tasks.utils.CandleUtils.calculatePercentageProfit;
import static com.stocks.tasks.utils.filters.CandlesFilter.filterByDate;
import static com.stocks.tasks.utils.filters.CandlesFilter.filterByPattern;

/**
 * @author Ivan Nikolaichuk
 */
public class BuyAfterBullSellOnSupportBreak {

    public void test() {
        List<ProfitData> profitDataList = new ArrayList<>();
        List<Company> companies = new CompanyDao().getAll();
        List<AfterStrongBullStatistic> afterStrongBullStatistics = new AfterStrongBullStatisticDao().getAll();
        for (Company company : companies) {
            List<Candle> allCandles = company.getCandles();

            Calendar from = Calendar.getInstance();
            from.set(2017, Calendar.APRIL, 1);

            Calendar to = Calendar.getInstance();
            to.set(2017, Calendar.MAY, 30);

            List<Candle> strongBulls = filterByDate(filterByPattern(allCandles, STRONG_BULL), from, to);

            List<Candle> strongBullsToCheck = new ArrayList<>();
            for (Candle candle : strongBulls) {
                for (AfterStrongBullStatistic afterStrongBullStatistic : afterStrongBullStatistics) {
                    if (afterStrongBullStatistic.getCandle().getId() == candle.getId()) {
                        strongBullsToCheck.add(candle);
                        break;
                    }
                }
            }

            ProfitData profitData = calculateProfit(allCandles, strongBullsToCheck, company.getName());
            if (profitData.totalPeriodsCounter != 0) {
                profitDataList.add(profitData);
            }
        }

        profitDataList.add(calculateCommonProft(profitDataList));
        new ProfitDataDao().save(profitDataList);
    }

    private ProfitData calculateCommonProft(List<ProfitData> profitDataList) {
        ProfitData common = new ProfitData("COMMON");
        for (ProfitData profitData : profitDataList) {
            common.supportBreakCounter = common.supportBreakCounter + profitData.supportBreakCounter;
            common.totalPeriodsCounter = common.totalPeriodsCounter + profitData.totalPeriodsCounter;
            common.totalProfitPerc = common.totalProfitPerc.add(profitData.totalProfitPerc);
        }

        return common;
    }

    private ProfitData calculateProfit(List<Candle> allCandles, List<Candle> strongBulls, String companyName) {
        ProfitData profitData = new ProfitData(companyName);

        for (Candle strongBull : strongBulls) {
            calculateProfit(profitData, strongBull, new CandleByDateSequence(allCandles));
        }

        return profitData;
    }

    private void calculateProfit(ProfitData profitData, Candle strongBull, CandleByDateSequence candleByDateSequence) {
        int daysCounter = 0;

        candleByDateSequence.setCurrent(strongBull);
        while (true) {
            daysCounter++;
            candleByDateSequence.next();


            Candle nextCandle;
            try {
                nextCandle = candleByDateSequence.getCurrent();
            } catch (IndexOutOfBoundsException e) {
                return;
            }

            BigDecimal percProfit = calculatePercentageProfit(strongBull.getClose(), nextCandle.getClose());

            if (strongBull.getLow().compareTo(nextCandle.getClose()) > 0) {
                profitData.totalProfitPerc = profitData.totalProfitPerc.add(percProfit);
                profitData.totalPeriodsCounter++;
                profitData.supportBreakCounter++;
                return;
            }
            if (daysCounter == 30) {
                profitData.totalPeriodsCounter++;
                profitData.totalProfitPerc = profitData.totalProfitPerc.add(percProfit);
                return;
            }
        }
    }

    public static void main(String[] args) {
        new BuyAfterBullSellOnSupportBreak().test();
    }

}
