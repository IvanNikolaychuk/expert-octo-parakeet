package com.tasks.utils.filters;

import com.core.api.helpers.Constants;
import com.core.db.entity.statistic.InvestmentPeriodData;

import java.util.HashSet;
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
}
