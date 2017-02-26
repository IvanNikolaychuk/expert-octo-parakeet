package com.tasks.utils.filters;

import com.core.db.entity.statistic.InvestmentPeriodData;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static com.core.api.yahoo.helpers.Constants.*;
import static com.tasks.utils.TimeUtils.subtractDaysFromToday;
import static com.tasks.utils.TimeUtils.today;
import static java.math.BigDecimal.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InvestmentPeriodDataFilterTest {

    @Test
    public void investmentPeriodDataWithLowPercentageIsFiltered() {
        InvestmentPeriodData goodPercentage = createInvestmentPeriodData(MIN_ACCEPTED_PROFIT_PERCENTAGE.add(ONE));
        InvestmentPeriodData badPercentage = createInvestmentPeriodData(MIN_ACCEPTED_PROFIT_PERCENTAGE.subtract(ONE));

        Set<InvestmentPeriodData> investmentPeriodDataSet = new HashSet<>();
        investmentPeriodDataSet.add(goodPercentage);
        investmentPeriodDataSet.add(badPercentage);

        Set<InvestmentPeriodData> filtered = InvestmentPeriodDataFilter.filterByPercentage(investmentPeriodDataSet);

        assertEquals(1, filtered.size());
        assertTrue(filtered.contains(goodPercentage));
    }

    @Test
    public void allOverlapsAreFilteredCorrectly_BeginDateIsSame() {
        Calendar twoDaysAgo = subtractDaysFromToday(2);

        InvestmentPeriodData investmentPeriodDataOnePercentageProfit = createInvestmentPeriodData(BigDecimal.ONE);
        investmentPeriodDataOnePercentageProfit.setBeginDate(twoDaysAgo);
        investmentPeriodDataOnePercentageProfit.setEndDate(subtractDaysFromToday(1));

        InvestmentPeriodData investmentPeriodDataTenPercentageProfit = createInvestmentPeriodData(BigDecimal.TEN);
        investmentPeriodDataTenPercentageProfit.setBeginDate(twoDaysAgo);
        investmentPeriodDataTenPercentageProfit.setEndDate(today());

        Set<InvestmentPeriodData> investmentPeriodDataSet = new HashSet<>();
        investmentPeriodDataSet.add(investmentPeriodDataOnePercentageProfit);
        investmentPeriodDataSet.add(investmentPeriodDataTenPercentageProfit);

        assertEquals(investmentPeriodDataSet.size(), 2);

        InvestmentPeriodDataFilter.removeOverlaps(investmentPeriodDataSet);

        assertEquals(investmentPeriodDataSet.size(), 1);
        assertTrue(investmentPeriodDataSet.contains(investmentPeriodDataTenPercentageProfit));
    }

    @Test
    public void allOverlapsAreFilteredCorrectly_EndDateIsSame() {
        Calendar today = today();

        InvestmentPeriodData investmentPeriodDataOnePercentageProfit = createInvestmentPeriodData(BigDecimal.ONE);
        investmentPeriodDataOnePercentageProfit.setBeginDate(subtractDaysFromToday(1));
        investmentPeriodDataOnePercentageProfit.setEndDate(today);

        InvestmentPeriodData investmentPeriodDataTenPercentageProfit = createInvestmentPeriodData(BigDecimal.TEN);
        investmentPeriodDataTenPercentageProfit.setBeginDate(subtractDaysFromToday(21));
        investmentPeriodDataTenPercentageProfit.setEndDate(today);

        Set<InvestmentPeriodData> investmentPeriodDataSet = new HashSet<>();
        investmentPeriodDataSet.add(investmentPeriodDataOnePercentageProfit);
        investmentPeriodDataSet.add(investmentPeriodDataTenPercentageProfit);

        assertEquals(investmentPeriodDataSet.size(), 2);

        InvestmentPeriodDataFilter.removeOverlaps(investmentPeriodDataSet);

        assertEquals(investmentPeriodDataSet.size(), 1);
        assertTrue(investmentPeriodDataSet.contains(investmentPeriodDataTenPercentageProfit));
    }

    private InvestmentPeriodData createInvestmentPeriodData(BigDecimal profitInPercentages) {
        InvestmentPeriodData investmentPeriodData = new InvestmentPeriodData();
        investmentPeriodData.setPercentageProfit(profitInPercentages);

        return investmentPeriodData;
    }
}
