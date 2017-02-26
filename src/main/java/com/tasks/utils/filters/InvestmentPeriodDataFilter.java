package com.tasks.utils.filters;

import com.core.api.yahoo.helpers.Constants;
import com.core.db.entity.statistic.InvestmentPeriodData;
import com.tasks.utils.TimeUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InvestmentPeriodDataFilter {
    public static Set<InvestmentPeriodData> filterByPercentage(Set<InvestmentPeriodData> investmentPeriodDataSet) {
        Set<InvestmentPeriodData> filtered = new HashSet<>();

        for (InvestmentPeriodData investmentPeriodData : investmentPeriodDataSet) {
            if (investmentPeriodData.getPercentageProfit().compareTo(Constants.MIN_ACCEPTED_PROFIT_PERCENTAGE) >= 0) {
                filtered.add(investmentPeriodData);
            }
        }

        return filtered;
    }

    public static void removeOverlaps(Set<InvestmentPeriodData> investmentPeriodDataSet) {

        List<InvestmentPeriodData> investmentPeriodDataList = new ArrayList<>();
        investmentPeriodDataList.addAll(investmentPeriodDataSet);

        Set<InvestmentPeriodData> toBeRemoved = new HashSet<>();
        for (int currentPeriodIndex = 0; currentPeriodIndex < investmentPeriodDataList.size(); currentPeriodIndex++) {
            InvestmentPeriodData current = investmentPeriodDataList.get(currentPeriodIndex);
            for (int periodIndex = currentPeriodIndex + 1; periodIndex < investmentPeriodDataList.size(); periodIndex++) {
                InvestmentPeriodData other = investmentPeriodDataList.get(periodIndex);
                if (TimeUtils.isSameDay(current.getBeginDate(), other.getBeginDate()) ||
                        TimeUtils.isSameDay(current.getEndDate(), other.getEndDate())) {
                    if (current.getPercentageProfit().compareTo(other.getPercentageProfit()) >= 0) {
                        toBeRemoved.add(other);
                    } else {
                        toBeRemoved.add(current);
                    }
                }
            }
        }

        investmentPeriodDataSet.removeAll(toBeRemoved);
    }
}
