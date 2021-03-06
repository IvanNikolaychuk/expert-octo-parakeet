package com.stocks.technical.core.db.entity.statistic.stongbull;

import com.stocks.technical.core.db.entity.Candle;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity(name = "after_pattern_statistic")
public class AfterPatternStatistic {
    @ManyToOne
    private Candle candle;

    @Column(name = "pattern")
    @Enumerated(EnumType.STRING)
    private Candle.Pattern pattern;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "after_1_day")
    private BigDecimal profitAfterOneDay;

    @Column(name = "after_3_day")
    private BigDecimal profitAfterThreeDays;


    @Column(name = "after_5_day")
    private BigDecimal profitAfterFiveDays;

    @Column(name = "after_10_day")
    private BigDecimal profitAfterTenDays;

    @Column(name = "after_20_day")
    private BigDecimal profitAfterTwentyDays;

    @Column(name = "after_30_day")
    private BigDecimal profitAfterThirtyDays;

    @Column(name = "half_support_is_broken")
    public boolean halfSupportIsBroken;

    @Column(name = "support_is_broken")
    public boolean supportIsBroken;

    @Column(name = "price_always_closed_higher_than_first_close")
    public boolean priceAlwaysWasHigherThanFirstClose;

    public AfterPatternStatistic(Candle candle) {
        priceAlwaysWasHigherThanFirstClose = true;
        this.candle = candle;
        this.pattern = candle.getPattern();
    }

    public AfterPatternStatistic() {
    }

    public void setProfit(BigDecimal profit, int daysPassed) {
        if (daysPassed == 1) profitAfterOneDay = profit;
        if (daysPassed == 3) profitAfterThreeDays = profit;
        if (daysPassed == 5) profitAfterFiveDays = profit;
        if (daysPassed == 10) profitAfterTenDays = profit;
        if (daysPassed == 20) profitAfterTwentyDays = profit;
        if (daysPassed == 30)  profitAfterThirtyDays = profit;
    }

    @Id
    @GeneratedValue
    private int id;

    public boolean isValid() {
        return profitAfterThirtyDays != null && profitAfterTwentyDays != null && profitAfterTenDays != null && profitAfterFiveDays != null
                && profitAfterThreeDays != null && profitAfterOneDay != null;
    }
}
