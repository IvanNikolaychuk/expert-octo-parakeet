package com.tasks.analyzer.drafts;

import com.core.db.dao.CompanyDao;
import com.core.db.entity.Candle;
import com.core.db.entity.company.Company;
import com.core.db.entity.statistic.InvestmentPeriodData;
import com.core.db.entity.statistic.StrongBullCandle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.core.db.entity.company.BusinessType.IT;
import static com.core.db.entity.company.StockCurrency.DOLLAR;
import static com.tasks.helpers.CandleHelper.createTodaysCandle;
import static com.tasks.helpers.CandleHelper.createYesterdaysCandle;
import static java.math.BigDecimal.*;
import static java.math.BigDecimal.valueOf;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class CandlePatternsAnalyserTest {
    private static final String COMPANY_NAME = "company name";

    private CandlePatternsAnalyser candlePatternsAnalyser;

    @Before
    public void setUp() throws Exception {
        candlePatternsAnalyser = new CandlePatternsAnalyser();
    }

    @Test
    public void strongBullFoundWhenItExists() {
        Company company = new Company(COMPANY_NAME, IT, DOLLAR);

        Candle yesterdays = createYesterdaysCandle(ONE, ONE);
        Candle todays = createTodaysCandle(ONE, valueOf(2));

        company.setCandles(Arrays.asList(yesterdays, todays));

        mockDao(company);
        InvestmentPeriodData investmentPeriodData = new InvestmentPeriodData(COMPANY_NAME, yesterdays.getDate(), todays.getDate(), valueOf(100), Candle.Trend.UP);

        StrongBullCandle strongBullCandle = candlePatternsAnalyser.getFirstStrongBull(investmentPeriodData);
        Assert.assertNotNull(strongBullCandle);
        Assert.assertEquals(strongBullCandle.getStrongBullCandle(), todays);
    }

  @Test
    public void strongBullNotFoundWhenItDoesnotExists() {
        Company company = new Company(COMPANY_NAME, IT, DOLLAR);

        Candle yesterdays = createYesterdaysCandle(ONE, ONE);
        Candle todays = createTodaysCandle(BigDecimal.valueOf(100), BigDecimal.valueOf(100));

        company.setCandles(Arrays.asList(yesterdays, todays));

        mockDao(company);
        InvestmentPeriodData investmentPeriodData = new InvestmentPeriodData(COMPANY_NAME, yesterdays.getDate(), todays.getDate(), valueOf(100), Candle.Trend.UP);

        StrongBullCandle strongBullCandle = candlePatternsAnalyser.getFirstStrongBull(investmentPeriodData);
        Assert.assertNull(strongBullCandle);
    }

    private void mockDao(Company company) {
        candlePatternsAnalyser = spy(candlePatternsAnalyser);
        final CompanyDao companyDao = mock(CompanyDao.class);
        doReturn(company).when(companyDao).getByName(COMPANY_NAME);

        doReturn(companyDao).when(candlePatternsAnalyser).getCompanyDao();
    }
}
