package com.jobs.analyzer;

import com.core.db.dao.CompanyDao;
import com.core.db.entity.Candle;
import com.core.db.entity.company.Company;
import com.core.db.entity.statistic.StatisticData;
import com.core.db.entity.statistic.VolumeData;
import com.jobs.analyzer.filters.CandlesFilter;
import com.sun.org.glassfish.external.statistics.Statistic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

import static com.core.api.helpers.Constants.CURRENT_YEAR;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class AvgStockVolumeComputer {

    public void execute() {
        for (Company company : new CompanyDao().getAll()) {
            StatisticData statisticData = new StatisticData(company);

            List<Candle> candlesThisYear = CandlesFilter.filter(company.getCandles(), CURRENT_YEAR);
            statisticData.setVolumeOldData(new VolumeData(candlesThisYear.size(), calculateAvgVolume(candlesThisYear)));

            List<Candle> mostRecentCandles = CandlesFilter.filterMostRecent(candlesThisYear);
            statisticData.setVolumeRecentData(new VolumeData(mostRecentCandles.size(), calculateAvgVolume(mostRecentCandles)));
        }
    }

    private BigDecimal calculateAvgVolume(List<Candle> candles) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Candle candle : candles) {
            totalAmount = totalAmount.add(candle.getVolume());
        }

        return totalAmount.divide(BigDecimal.valueOf(candles.size()), RoundingMode.HALF_EVEN);
    }
}
