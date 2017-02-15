package com.tasks.analyzer.patterns;

import com.core.db.dao.CompanyDao;
import com.core.db.dao.StrongBullStatisticDataDao;
import com.core.db.entity.Candle;
import com.core.db.entity.company.Company;
import com.core.db.entity.statistic.StrongBullStatisticData;
import com.tasks.analyzer.helpers.CandleByDateSequence;
import com.tasks.analyzer.trend.TrendAnalyser;
import com.tasks.analyzer.trend.TrendData;

import java.util.ArrayList;
import java.util.List;

import static com.core.db.entity.Candle.Pattern.STRONG_BULL;
import static com.tasks.analyzer.trend.Movement.BACK;
import static com.tasks.analyzer.trend.Movement.FORWARD;
import static com.tasks.utils.filters.CandlesFilter.filterByPattern;

public class StrongBullStatisticDataAnalyser {

    public void execute() {
        List<Company> companies = new CompanyDao().getAll();
        StrongBullStatisticDataDao strongBullStatisticDataDao = new StrongBullStatisticDataDao();
        for (Company company : companies) {
            List<Candle> allCandles = company.getCandles();
            List<Candle> strongBulls = filterByPattern(allCandles, STRONG_BULL);
            strongBullStatisticDataDao.save(analyse(allCandles, strongBulls));
        }
    }

    List<StrongBullStatisticData> analyse(List<Candle> allCandles, List<Candle> strongBulls) {
        List<StrongBullStatisticData> strongBullStatisticDataList = new ArrayList<>();

        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(allCandles);
        for (Candle candle : strongBulls) {
            StrongBullStatisticData strongBullStatisticData = new StrongBullStatisticData(candle);
            strongBullStatisticDataList.add(strongBullStatisticData);

            candleByDateSequence.setCurrent(candle);
            // start with next after strong bull
            if (candleByDateSequence.hasNext()) {
                candleByDateSequence.next();
                Candle nextAfterTarget = candleByDateSequence.getCurrent();
                TrendData trendData = new TrendAnalyser().analyseTrend(nextAfterTarget, candleByDateSequence, FORWARD);
                strongBullStatisticData.setAfterDays(trendData.getNumberOfDays());
                strongBullStatisticData.setAfterPercentageProfit(trendData.getPercentageProfit());
            }

            candleByDateSequence.setCurrent(candle);
            if (candleByDateSequence.hasPrev()) {
                candleByDateSequence.prev();
                Candle prevBeforeTarget = candleByDateSequence.getCurrent();
                TrendData trendData = new TrendAnalyser().analyseTrend(prevBeforeTarget, candleByDateSequence, BACK);
                strongBullStatisticData.setBeforeDays(trendData.getNumberOfDays());
                strongBullStatisticData.setAfterPercentageProfit(trendData.getPercentageProfit());
            }
        }

        return strongBullStatisticDataList;
    }

    public static void main(String[] args) {
        new StrongBullStatisticDataAnalyser().execute();
    }
}
