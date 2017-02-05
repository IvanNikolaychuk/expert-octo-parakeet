package com.tasks.analyzer.drafts;

import com.core.db.dao.CompanyDao;
import com.core.db.dao.InvestmentPeriodDataDao;
import com.core.db.dao.StrongBullCandleDao;
import com.core.db.entity.Candle;
import com.core.db.entity.company.Company;
import com.core.db.entity.statistic.InvestmentPeriodData;
import com.core.db.entity.statistic.StrongBullCandle;
import com.tasks.analyzer.drafts.helpers.CandleSequence;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.tasks.utils.filters.CandlesFilter.filterByDate;


public class CandlePatternsAnalyser {
    /**
     * Check how many positive trends with percentage more than MIN_ACCEPTED_PROFIT_PERCENTAGE
     * have strong bull.
     */
    public void execute() {
        List<InvestmentPeriodData> investmentPeriodDataList = new InvestmentPeriodDataDao().getAll();
        if (investmentPeriodDataList.isEmpty()) {
            System.out.println("No data to analyze.");
            return;
        }

        System.out.println(investmentPeriodDataList.size() + " to be analyzed");
        performAnalysis(investmentPeriodDataList);
    }

    private void performAnalysis(List<InvestmentPeriodData> investmentPeriodDataList) {
        int periodsWithStrongBullTrend = 0;

        Set<StrongBullCandle> strongBullCandles = new HashSet<>();
        for (InvestmentPeriodData investmentPeriodData : investmentPeriodDataList) {
            StrongBullCandle strongBullCandle = getFirstStrongBull(investmentPeriodData);
            if (strongBullCandle != null) {
                strongBullCandles.add(strongBullCandle);
                periodsWithStrongBullTrend++;
            }
        }

        new StrongBullCandleDao().save(strongBullCandles);
        System.out.println(periodsWithStrongBullTrend + " from " + investmentPeriodDataList.size() + " had strong bull candle");
    }

    StrongBullCandle getFirstStrongBull(InvestmentPeriodData investmentPeriodData) {
        Company company = getCompanyDao().getByName(investmentPeriodData.getCompanyName());
        List<Candle> candles = filterByDate(company.getCandles(), investmentPeriodData.getBeginDate(), investmentPeriodData.getEndDate());

        CandleSequence candleSequence = new CandleSequence(candles);

        while (candleSequence.hasNext()) {
            Candle candle = candleSequence.next();
            if (candleSequence.isStrongBullCandle(candle)) {
                return new StrongBullCandle(investmentPeriodData, candle);
            }
        }

        return null;
    }

    CompanyDao getCompanyDao() {
        return new CompanyDao();
    }

    public static void main(String[] args) {
        new StrongBullCandleDao().clearAll();
        new CandlePatternsAnalyser().execute();
    }

}
