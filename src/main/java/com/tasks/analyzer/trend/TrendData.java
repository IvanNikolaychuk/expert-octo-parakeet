package com.tasks.analyzer.trend;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TrendData {
    private Integer numberOfDays;
    private BigDecimal percentageProfit;

    public TrendData(BigDecimal percentageProfit, Integer numberOfDays) {
        this.percentageProfit = percentageProfit;
        this.numberOfDays = numberOfDays;
    }
}
