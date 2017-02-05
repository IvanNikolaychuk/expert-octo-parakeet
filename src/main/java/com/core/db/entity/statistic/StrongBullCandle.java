package com.core.db.entity.statistic;

import com.core.db.entity.Candle;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "strong_bull")
@Data
public class StrongBullCandle {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "investment_period_data")
    private InvestmentPeriodData investmentPeriodData;

    @ManyToOne
    @JoinColumn(name = "strong_bull_candle")
    private Candle strongBullCandle;

    public StrongBullCandle() {}

    public StrongBullCandle(InvestmentPeriodData investmentPeriodData, Candle strongBullCandle) {
        this.investmentPeriodData = investmentPeriodData;
        this.strongBullCandle = strongBullCandle;
    }
}
