package com.tasks.analyzer;

import com.core.db.dao.CompanyDao;
import com.core.db.dao.CompanyGrowthStatisticDao;
import com.core.db.entity.Candle;
import com.core.db.entity.company.Company;
import com.core.db.entity.statistic.CompanyGrowthStatisticData;
import com.tasks.utils.CandleUtils;
import com.tasks.utils.TimeUtils;
import com.tasks.utils.filters.CandlesFilter;
import com.tasks.utils.filters.CandlesFilter.RecentDateFirstComparator;

import java.math.BigDecimal;
import java.util.*;

import static com.tasks.utils.CandleUtils.calculatePercentageProfit;
import static com.tasks.utils.CandleUtils.getFirst;
import static com.tasks.utils.TimeUtils.subtractDaysFrom;
import static com.tasks.utils.TimeUtils.subtractDaysFromToday;
import static java.util.Arrays.asList;

public class CompanyGrowthAnalyser {
    public void execute() {
        CompanyDao companyDao = new CompanyDao();

        for(Company company : companyDao.getAll()) {
            List<Candle> candles = CandleUtils.sort(company.getCandles(), new RecentDateFirstComparator());
            analyse(company.getName(), candles);
        }
    }

    private void analyse(String companyName, List<Candle> candles) {
        CompanyGrowthStatisticDao companyGrowthStatisticDao = new CompanyGrowthStatisticDao();
        Set<CompanyGrowthStatisticData> companyGrowthStatisticDataSet = new HashSet<>();

        for (int month = 1; month < 7; month++) {
            Calendar approximateOldCandleDate = subtractDaysFrom(getFirst(candles).getDate(), 30 * month);
            Candle mostRecentCandle = getFirst(candles);
            Candle moreOldCandle = findCandle(candles, approximateOldCandleDate);

            BigDecimal percentageProfit = calculatePercentageProfit(asList(mostRecentCandle, moreOldCandle));
            companyGrowthStatisticDataSet.add(new CompanyGrowthStatisticData(companyName, month, percentageProfit));
        }

        companyGrowthStatisticDao.save(companyGrowthStatisticDataSet);
    }

    private Candle findCandle(List<Candle> candles, Calendar approximateCandleDate) {
        for(Candle candle : candles) {
            if (candle.getDate().compareTo(approximateCandleDate) <= 0) {
                return candle;
            }
        }

        return null;
    }

    public static void main(String[] args) {
        new CompanyGrowthStatisticDao().clearAll();
        new CompanyGrowthAnalyser().execute();
    }

}
