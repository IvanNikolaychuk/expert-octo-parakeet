package com.stocks.tasks.analyzer.volume;

import com.stocks.core.db.dao.VolumeStatisticDataDao;
import com.stocks.core.db.entity.Candle;
import com.stocks.core.db.entity.statistic.VolumeStatisticData;
import com.stocks.tasks.utils.filters.CandlesFilter;

import java.math.BigDecimal;
import java.util.List;

import static com.stocks.tasks.utils.CandleUtils.getFirst;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class StockVolumeAnalyser {
    private static final int AVG_TRADING_DAYS_PER_YEAR = 252;

    public void execute() {
        VolumeStatisticDataDao volumeStatisticDataDao = new VolumeStatisticDataDao();

        for (VolumeStatisticData volumeStatisticData : volumeStatisticDataDao.getAll()) {
            List<Candle> candles = volumeStatisticData.getCompany().getCandles();
            candles.sort(new CandlesFilter.RecentDateFirstComparator());

            fillVolumeData(volumeStatisticData, candles);

            volumeStatisticDataDao.saveOrUpdate(volumeStatisticData);
        }
    }

    private void fillVolumeData(VolumeStatisticData volumeStatisticData, List<Candle> candles) {
        if (candles.size() < AVG_TRADING_DAYS_PER_YEAR) {
            return;
        }

        volumeStatisticData.setLastDayVolume(getFirst(candles).getVolume().longValue());
        volumeStatisticData.setAvgFiveDaysVolume(calculateAvgVolume(candles.subList(0, 5)));
        volumeStatisticData.setAvgMonthVolume(calculateAvgVolume(candles.subList(0, AVG_TRADING_DAYS_PER_YEAR / 12)));
        volumeStatisticData.setAvgYearVolume(calculateAvgVolume(candles.subList(0, AVG_TRADING_DAYS_PER_YEAR)));
    }

    private long calculateAvgVolume(List<Candle> candles) {
        BigDecimal totalVolume = BigDecimal.ZERO;

        for(Candle candle : candles) {
            totalVolume = totalVolume.add(candle.getVolume());
        }

        return (long) (totalVolume.doubleValue() / candles.size());
    }

    public static void main(String[] args) {
        new StockVolumeAnalyser().execute();
    }
}
