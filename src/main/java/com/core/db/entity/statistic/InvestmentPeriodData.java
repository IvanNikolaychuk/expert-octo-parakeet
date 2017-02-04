package com.core.db.entity.statistic;

import com.core.db.entity.Candle;
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

    private Calendar begin;
    private Calendar end;

    @Column(name = "percentage_profit")
    private BigDecimal percentageProfit;
    private String companyName;

    @Enumerated(EnumType.STRING)
    private Candle.Trend trend;

    private long days;

    public InvestmentPeriodData() {
    }

    public InvestmentPeriodData(
            String companyName,
            Calendar begin,
            Calendar end,
            BigDecimal percentageProfit,
            Candle.Trend trend) {
        this.begin = begin;
        this.end = end;
        this.percentageProfit = percentageProfit;
        this.trend = trend;
        this.companyName = companyName;

        days = TimeUnit.MILLISECONDS.toDays(Math.abs(end.getTimeInMillis() - begin.getTimeInMillis()));
    }


    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InvestmentPeriodData that = (InvestmentPeriodData) o;

        if (begin != null ? !TimeUtils.isSameDay(begin, that.begin) : that.begin != null) return false;
        if (end != null ? !TimeUtils.isSameDay(end, that.end) : that.end != null) return false;
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
