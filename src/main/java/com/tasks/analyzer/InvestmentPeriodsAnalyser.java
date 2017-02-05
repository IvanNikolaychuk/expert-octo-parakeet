package com.tasks.analyzer;

import com.core.db.dao.CompanyDao;
import com.core.db.dao.InvestmentPeriodDataDao;
import com.core.db.entity.Candle;
import com.core.db.entity.company.Company;
import com.core.db.entity.statistic.InvestmentPeriodData;
import com.tasks.utils.CandleUtils;
import com.tasks.utils.filters.CandlesFilter;
import com.tasks.utils.filters.InvestmentPeriodDataFilter;
import javafx.util.Pair;

import java.math.BigDecimal;
import java.util.*;

import static com.core.api.helpers.Constants.MAX_DAYS_INVESTING;
import static com.core.db.entity.Candle.Trend.UP;
import static com.google.common.collect.Iterables.getFirst;
import static com.google.common.collect.Iterables.getLast;
import static com.tasks.utils.CandleUtils.calculatePercentageProfit;
import static com.tasks.utils.CandleUtils.calculateTotalProfit;
import static com.tasks.utils.CandleUtils.getFirst;
import static com.tasks.utils.filters.InvestmentPeriodDataFilter.filterByPercentage;
import static com.tasks.utils.filters.InvestmentPeriodDataFilter.removeOverlaps;

public class InvestmentPeriodsAnalyser {

    void execute() {
        for (Company company : new CompanyDao().getAll()) {
            performAnalysis(company);
        }
    }

    private void performAnalysis(Company company) {
        Map<Pair<Calendar, Calendar>, List<Candle>> periodCandles = createPossibleCombinations(company.getCandles());
        Set<InvestmentPeriodData> investmentPeriodDataSet = computeMostSuccessfulPeriods(periodCandles, company.getName());
        Set<InvestmentPeriodData> filtered = filterByPercentage(investmentPeriodDataSet);
        removeOverlaps(filtered);

        new InvestmentPeriodDataDao().save(filtered);
    }

    Set<InvestmentPeriodData> computeMostSuccessfulPeriods(Map<Pair<Calendar, Calendar>, List<Candle>> periodCandles, String companyName) {
        Set<InvestmentPeriodData> investmentPeriodDataList = new HashSet<>();

        for (Map.Entry<Pair<Calendar, Calendar>, List<Candle>> periodCandlesEntry : periodCandles.entrySet()) {
            List<Candle> candlesInPeriod = periodCandlesEntry.getValue();
            Pair<Candle, Candle> candlePair = findLongestSuccessfulPeriodOpportunity(candlesInPeriod);
            if (candlePair != null) {
                Candle first = candlePair.getKey();
                Candle last = candlePair.getValue();

                investmentPeriodDataList.add(
                        new InvestmentPeriodData(companyName, first.getDate(), last.getDate(),
                                calculatePercentageProfit(Arrays.asList(first, last)), UP)
                );
            }
        }

        return investmentPeriodDataList;
    }

    Pair<Candle, Candle> findLongestSuccessfulPeriodOpportunity(List<Candle> candlesInPeriod) {
        candlesInPeriod.sort(new CandlesFilter.OldDateFirstComparator());
        Pair<Candle, Candle> mostSuccessful = null;
        BigDecimal biggestProfit = BigDecimal.ZERO;

        for (int currentCandleIndex = 0; currentCandleIndex < candlesInPeriod.size(); currentCandleIndex++) {
            for (int candleIndex = currentCandleIndex; candleIndex < candlesInPeriod.size(); candleIndex++) {
                List<Candle> subList = candlesInPeriod.subList(currentCandleIndex, candleIndex + 1);
                BigDecimal totalProfit = calculateTotalProfit(subList);

                if (biggestProfit.compareTo(totalProfit) == -1) {
                    mostSuccessful = new Pair<>(getFirst(subList, null), getLast(subList, null));
                    biggestProfit = totalProfit;
                }
            }
        }

        return mostSuccessful;
    }


    Map<Pair<Calendar, Calendar>, List<Candle>> createPossibleCombinations(List<Candle> candles) {
        candles.sort(new CandlesFilter.OldDateFirstComparator());
        if (candles.size() <= MAX_DAYS_INVESTING) {
            return Collections.singletonMap(computePeriod(candles), candles);
        }

        Map<Pair<Calendar, Calendar>, List<Candle>> periodCandles = new HashMap<>();
        for (int index = 0; index < candles.size() - MAX_DAYS_INVESTING + 1; index++) {
            List<Candle> subList = candles.subList(index, MAX_DAYS_INVESTING + index);
            periodCandles.put(computePeriod(subList), subList);
        }

        return periodCandles;
    }

    private Pair<Calendar, Calendar> computePeriod(List<Candle> candles) {
        candles.sort(new CandlesFilter.OldDateFirstComparator());
        final Candle oldest = getFirst(candles);
        final Candle youngest = getLast(candles);

        return new Pair<>(oldest.getDate(), youngest.getDate());
    }

    public static void main(String[] args) {
        new InvestmentPeriodsAnalyser().execute();
    }
}
