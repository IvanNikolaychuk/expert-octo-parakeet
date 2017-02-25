package com.tasks.analyzer;

import com.core.db.dao.CandlesDao;
import com.core.db.dao.CompanyDao;
import com.core.db.dao.CompanyGrowthStatisticDao;
import com.core.db.entity.Candle;
import com.core.db.entity.company.Company;
import com.core.db.entity.statistic.CompanyGrowthStatisticData;
import com.tasks.utils.CandleUtils;
import com.tasks.utils.filters.CandlesFilter.OldDateFirstComparator;
import javafx.util.Pair;

import java.math.BigDecimal;
import java.util.*;

import static com.tasks.utils.CandleUtils.*;
import static java.util.Calendar.*;

public class CompanyGrowthAnalyser {
    public void execute() {
        CompanyDao companyDao = new CompanyDao();

        for(Company company : companyDao.getAll()) {
            List<Candle> candles = CandleUtils.sort(company.getCandles(), new OldDateFirstComparator());
            analyse(company.getName(), candles);
        }
    }

    private void analyse(String companyName, List<Candle> candles) {
        CompanyGrowthStatisticDao companyGrowthStatisticDao = new CompanyGrowthStatisticDao();
        Set<CompanyGrowthStatisticData> companyGrowthStatisticDataSet = new HashSet<>();

        Candle firstCandle = getFirst(candles);
        int currentMonth = firstCandle.getDate().get(MONTH);
        int currentYear = firstCandle.getDate().get(YEAR);

        Candle lastCandle = getLast(candles);
        int lastMonth = lastCandle.getDate().get(MONTH);
        int lastYear = lastCandle.getDate().get(YEAR);

        while (currentMonth != lastMonth + 1 || currentYear < lastYear) {
            Calendar from = Calendar.getInstance();
            from.set(currentYear, currentMonth, 1);

            Calendar to = Calendar.getInstance();
            to.set(currentYear, currentMonth, 25);

            List<Candle> filtered = getCandlesForMonthAndYear(candles, currentMonth, currentYear);
            CompanyGrowthStatisticData companyGrowthStatisticData = new CompanyGrowthStatisticData(companyName, from, to, calculatePercentageProfit(filtered));
            companyGrowthStatisticData.setOpen(getFirst(filtered).getOpen());
            companyGrowthStatisticData.setClose(getLast(filtered).getClose());
            companyGrowthStatisticDataSet.add(companyGrowthStatisticData);

            if (currentMonth == DECEMBER) {
                currentMonth = JANUARY;
                currentYear++;
            } else {
                currentMonth++;
            }
        }

        companyGrowthStatisticDao.save(companyGrowthStatisticDataSet);
    }


    public static void main(String[] args) {
        new CompanyGrowthStatisticDao().clearAll();
        new CompanyGrowthAnalyser().execute();
    }

}
