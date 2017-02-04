package com.tasks.utils.filters;

import com.core.api.helpers.Constants;
import com.core.db.entity.statistic.InvestmentPeriodData;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static com.core.api.helpers.Constants.*;
import static java.math.BigDecimal.*;

public class InvestmentPeriodDataFilterTest {

    @Test
    public void investmentPeriodDataWithLowPercentageIsFiltered() {
        InvestmentPeriodData goodPercentage = createInvestmentPeriodData(MIN_ACCEPTED_PROFIT_PERCENTAGE.add(ONE));
        InvestmentPeriodData badPercentage = createInvestmentPeriodData(MIN_ACCEPTED_PROFIT_PERCENTAGE.subtract(ONE));

        Set<InvestmentPeriodData> investmentPeriodDataSet = new HashSet<>();
        investmentPeriodDataSet.add(goodPercentage);
        investmentPeriodDataSet.add(badPercentage);

        Set<InvestmentPeriodData> filtered = InvestmentPeriodDataFilter.filterByPercentage(investmentPeriodDataSet);

        Assert.assertEquals(1, filtered.size());
        Assert.assertTrue(filtered.contains(goodPercentage));

    }

    private InvestmentPeriodData createInvestmentPeriodData(BigDecimal profitInPercentages) {
        InvestmentPeriodData investmentPeriodData = new InvestmentPeriodData();
        investmentPeriodData.setPercentageProfit(profitInPercentages);

        return investmentPeriodData;
    }
}
