package com.core.db.entity.statistic;

import com.core.db.entity.Candle;
import lombok.Data;
import lombok.Generated;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity(name = "gap_candle_data")
public class GapCandleData {

    public GapCandleData() {}

    public GapCandleData(Candle candle, Integer daysBefore, BigDecimal percentageGrowthBefore, Integer daysAfter, BigDecimal percentageGrowthAfter) {
        this.candle = candle;
        this.daysBefore = daysBefore;
        this.percentageGrowthBefore = percentageGrowthBefore;
        this.daysAfter = daysAfter;
        this.percentageGrowthAfter = percentageGrowthAfter;
    }

    @GeneratedValue
    @Id
    private int id;

    @OneToOne
    private Candle candle;

    @Column(name = "days_before")
    private Integer daysBefore;

    @Column(name = "percentage_growth_before")
    private BigDecimal percentageGrowthBefore;

    @Column(name = "days_after")
    private Integer daysAfter;

    @Column(name = "percentage_growth_after")
    private BigDecimal percentageGrowthAfter;
}
