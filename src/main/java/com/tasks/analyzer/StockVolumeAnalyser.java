package com.tasks.analyzer;

import com.core.db.dao.CompanyDao;
import com.core.db.dao.VolumeStatisticDataDao;
import com.core.db.entity.Candle;
import com.core.db.entity.company.Company;
import com.core.db.entity.statistic.VolumeStatisticData;
import com.tasks.utils.filters.CandlesFilter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class StockVolumeAnalyser {

    public void execute() {
        VolumeStatisticDataDao volumeStatisticDataDao = new VolumeStatisticDataDao();

        for (VolumeStatisticData volumeStatisticData : volumeStatisticDataDao.getAll()) {
            List<Candle> candles = volumeStatisticData.getCompany().getCandles();
            candles.sort(new CandlesFilter.HighVolumeFirstComparator());

            List<Calendar> highVolumeDates = new ArrayList<>(5);

            for (int i = 0; i < 5; i++) {
                highVolumeDates.add(candles.get(i).getDate());
            }

            volumeStatisticData.setHighestVolumeDates(highVolumeDates);
            volumeStatisticDataDao.saveOrUpdate(volumeStatisticData);
        }
    }

    public static void main(String[] args) {
        new StockVolumeAnalyser().execute();
    }
}
