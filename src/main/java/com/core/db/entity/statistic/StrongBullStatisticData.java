package com.core.db.entity.statistic;

import com.core.db.entity.Candle;
import com.core.db.entity.Candle.Trend;
import lombok.Data;

import java.math.BigDecimal;
import javax.persistence.*;

@Entity(name = "strong_bull_statistic_data")
@Data
public class StrongBullStatisticData {

    public StrongBullStatisticData(Candle candle) {
        this.candle = candle;
    }

    public StrongBullStatisticData() {}

    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    private Candle candle;

    @Column(name="after_days")
    private Integer afterDays;

    @Column(name="after_percentage_profit")
    private BigDecimal afterPercentageProfit;

    @Column(name="before_days")
    private Integer beforeDays;

    @Column(name="before_percentage_profit")
    private BigDecimal beforePercentageProfit;
}
