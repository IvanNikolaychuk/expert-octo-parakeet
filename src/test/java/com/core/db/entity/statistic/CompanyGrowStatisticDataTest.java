package com.core.db.entity.statistic;

import junit.framework.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;

import static com.core.db.entity.statistic.CompanyGrowthStatisticData.BEGIN_DAY_DEF;
import static com.core.db.entity.statistic.CompanyGrowthStatisticData.END_DAY_DEF;
import static java.util.Calendar.*;
import static junit.framework.Assert.*;

public class CompanyGrowStatisticDataTest {

    @Test
    public void dateOfMonthIsUnified() {
        Calendar from = getInstance();
        from.set(2016, JANUARY, 5);

        Calendar to = getInstance();
        to.set(2016, JANUARY, 29);
        CompanyGrowthStatisticData companyGrowthStatisticData = new CompanyGrowthStatisticData("", from, to, BigDecimal.ONE);

        Calendar resultFrom = companyGrowthStatisticData.getFrom();
        assertEquals(resultFrom.get(YEAR), from.get(YEAR));
        assertEquals(resultFrom.get(MONTH), from.get(MONTH));
        assertEquals(resultFrom.get(DAY_OF_MONTH), BEGIN_DAY_DEF);

        Calendar resultTo = companyGrowthStatisticData.getTo();
        assertEquals(resultTo.get(YEAR), to.get(YEAR));
        assertEquals(resultTo.get(MONTH), to.get(MONTH));
        assertEquals(resultTo.get(DAY_OF_MONTH), END_DAY_DEF);

    }
}
