package com.tasks.analyzer.patterns;

import com.stocks.core.db.dao.CompanyDao;
import com.stocks.core.db.dao.StrongBullStatisticDataDao;
import com.stocks.core.db.entity.Candle;
import com.stocks.core.db.entity.company.Company;
import com.stocks.core.db.entity.statistic.StrongBullStatisticData;
import com.stocks.tasks.analyzer.helpers.CandleByDateSequence;
import com.stocks.tasks.analyzer.trend.TrendAnalyser;
import com.stocks.tasks.analyzer.trend.TrendData;

import java.util.ArrayList;
import java.util.List;

import static com.stocks.core.db.entity.Candle.Pattern.STRONG_BULL;
import static com.stocks.tasks.analyzer.trend.Movement.BACK;
import static com.stocks.tasks.analyzer.trend.Movement.FORWARD;
import static com.stocks.tasks.utils.filters.CandlesFilter.filterByPattern;


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
                strongBullStatisticData.setBeforePercentageProfit(trendData.getPercentageProfit());
            }
        }

        return strongBullStatisticDataList;
    }

    public static void main(String[] args) {
        new StrongBullStatisticDataAnalyser().execute();
    }
}
