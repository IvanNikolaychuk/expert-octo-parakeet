package com.tasks.analyzer;

import com.core.db.dao.CompanyDao;
import com.core.db.dao.GapCandleDataDao;
import com.core.db.entity.Candle;
import com.core.db.entity.company.Company;
import com.core.db.entity.statistic.GapCandleData;
import com.tasks.analyzer.helpers.CandleByDateSequence;
import com.tasks.analyzer.trend.Movement;
import com.tasks.analyzer.trend.TrendAnalyser;
import com.tasks.analyzer.trend.TrendData;
import com.tasks.utils.filters.CandlesFilter;

import java.util.ArrayList;
import java.util.List;

import static com.tasks.utils.filters.CandlesFilter.filterByPattern;

public class GapsAnalyser {
    private TrendAnalyser trendAnalyser;

    public GapsAnalyser(TrendAnalyser trendAnalyser) {
        this.trendAnalyser = trendAnalyser;
    }

    public void execute() {
        for (Company company : new CompanyDao().getAll()) {
            List<Candle> candles = company.getCandles();
            List<Candle> filtered = filterByPattern(candles, Candle.Pattern.STRONG_GAP_RISE);
            filtered.addAll(filterByPattern(candles, Candle.Pattern.STRONG_GAP_FALL));

            analyse(filtered, new CandleByDateSequence(candles));
        }
    }

    public void analyse(List<Candle> gapCandles, CandleByDateSequence candleByDateSequence) {
        List<GapCandleData> gapCandleDataList = new ArrayList<>();
        for (Candle candle : gapCandles) {
            candleByDateSequence.setCurrent(candle);
            boolean hasBeforeAndNext = candleByDateSequence.hasPrev() && candleByDateSequence.hasNext();
            if (!hasBeforeAndNext) continue;

            Candle beforeCandle = candleByDateSequence.prev();
            TrendData beforeTrend = trendAnalyser.analyseTrend(beforeCandle, candleByDateSequence, Movement.BACK);

            candleByDateSequence.setCurrent(candle);
            candleByDateSequence.next();
            Candle afterCandle = candleByDateSequence.getCurrent();
            TrendData afterTrend = trendAnalyser.analyseTrend(afterCandle, candleByDateSequence, Movement.FORWARD);

            gapCandleDataList.add(new GapCandleData(candle,
                    beforeTrend.getNumberOfDays(), beforeTrend.getPercentageProfit(),
                    afterTrend.getNumberOfDays(), afterTrend.getPercentageProfit())
            );
        }
        save(gapCandleDataList);
    }

    public void save(List<GapCandleData> gapCandleDataList) {
        new GapCandleDataDao().save(gapCandleDataList);
    }

    public static void main(String[] args) {
        new GapCandleDataDao().clearAll();
        new GapsAnalyser(new TrendAnalyser()).execute();
    }
}
