package com.tasks.analyzer;

import com.core.db.dao.CompanyDao;
import com.core.db.dao.StatisticDataDao;
import com.core.db.entity.Candle;
import com.core.db.entity.company.Company;
import com.core.db.entity.statistic.StatisticData;
import com.core.db.entity.statistic.VolumeData;
import com.tasks.analyzer.filters.CandlesFilter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.core.api.helpers.Constants.CURRENT_YEAR;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class AvgStockVolumeComputer {

    public void execute() {
        StatisticDataDao statisticDataDao = new StatisticDataDao();
        List<Company> companies = new CompanyDao().getAll();

        for (Company company : companies) {
            StatisticData statisticData = statisticDataDao.getByCompanyName(company);

            List<Candle> candlesThisYear = CandlesFilter.filter(company.getCandles(), CURRENT_YEAR);
            statisticData.setVolumeYearData(new VolumeData(candlesThisYear.size(), calculateAvgVolume(candlesThisYear)));

            List<Candle> mostRecentCandles = CandlesFilter.filterMostRecent(candlesThisYear);
            statisticData.setVolumeRecentData(new VolumeData(mostRecentCandles.size(), calculateAvgVolume(mostRecentCandles)));

            statisticDataDao.saveOrUpdate(statisticData);
        }
    }

    public BigDecimal calculateAvgVolume(List<Candle> candles) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Candle candle : candles) {
            totalAmount = totalAmount.add(candle.getVolume());
        }

        return totalAmount.divide(BigDecimal.valueOf(candles.size()), RoundingMode.HALF_EVEN);
    }
}
