package com.stocks.strategy.strongbull;

import com.stocks.core.db.dao.CompanyDao;
import com.stocks.core.db.dao.ProfitDataDao;
import com.stocks.core.db.entity.Candle;
import com.stocks.core.db.entity.company.Company;
import com.stocks.core.db.entity.strategy.ProfitData;
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
public class BuyAfterBullSellOnSupportBreak {

    public void test() {
        List<ProfitData> profitDataList = new ArrayList<>();

        for (Company company : new CompanyDao().getAll()) {
            List<Candle> allCandles = company.getCandles();
            ProfitData profitData = calculateProfitData(allCandles, filterByPattern(allCandles, STRONG_BULL), company.getName());
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

    private ProfitData calculateProfitData(List<Candle> allCandles, List<Candle> strongBulls, String companyName) {
        ProfitData profitData = new ProfitData(companyName);

        for (Candle strongBull : strongBulls) {
            calculateProfitData(profitData, strongBull, new CandleByDateSequence(allCandles));
        }

        return profitData;
    }

    private void calculateProfitData(ProfitData profitData, Candle strongBull, CandleByDateSequence candleByDateSequence) {
        candleByDateSequence.setCurrent(strongBull);
        int daysCounter = 0;

        while (true) {
            daysCounter++;
            Candle nextCandle = candleByDateSequence.next();
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
