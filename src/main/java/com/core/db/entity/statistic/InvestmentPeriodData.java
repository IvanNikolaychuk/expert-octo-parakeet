package com.core.db.entity.statistic;

import com.core.db.entity.Candle;
import com.sun.istack.internal.NotNull;
import com.tasks.utils.TimeUtils;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

@Data
@Entity(name = "investment_period_data")
public class InvestmentPeriodData {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "begin_date")
    private Calendar beginDate;

    @Column(name = "end_date")
    private Calendar endDate;

    @Column(name = "percentage_profit")
    private BigDecimal percentageProfit;

    @Column(name = "company_name")
    @NotNull
    private String companyName;

    @Enumerated(EnumType.STRING)
    private Candle.Trend trend;

    private long days;

    public InvestmentPeriodData() {
    }

    public InvestmentPeriodData(
            String companyName,
            Calendar beginDate,
            Calendar endDate,
            BigDecimal percentageProfit,
            Candle.Trend trend) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.percentageProfit = percentageProfit;
        this.trend = trend;
        this.companyName = companyName;

        days = TimeUnit.MILLISECONDS.toDays(Math.abs(endDate.getTimeInMillis() - beginDate.getTimeInMillis()));
    }


    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InvestmentPeriodData that = (InvestmentPeriodData) o;

        if (beginDate != null ? !TimeUtils.isSameDay(beginDate, that.beginDate) : that.beginDate != null) return false;
        if (endDate != null ? !TimeUtils.isSameDay(endDate, that.endDate) : that.endDate != null) return false;
        if (percentageProfit != null ? !percentageProfit.equals(that.percentageProfit) : that.percentageProfit != null)
            return false;
        if (companyName != null ? !companyName.equals(that.companyName) : that.companyName != null) return false;
        return trend == that.trend;
    }

    @Override
    public int hashCode() {
        int result = percentageProfit != null ? percentageProfit.hashCode() : 0;

        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (trend != null ? trend.hashCode() : 0);
        return result;
    }
}
