package com.stocks.technical.tasks.analyzer.movingavarage;

import java.math.BigDecimal;

public class ExpMovingAverage {
    private BigDecimal value;

    public BigDecimal compute(ExpMovingAverage previous, BigDecimal lastPrice) {
        BigDecimal coeff = getCoeff();

        if (previous == null) {
            value = lastPrice.multiply(coeff);
        } else {
            value = coeff.multiply(lastPrice)
                    .add((BigDecimal.ONE.subtract(coeff).multiply(previous.value)));
        }

        return value;
    }

    public BigDecimal getCoeff() {
        // length = 20
        return BigDecimal.valueOf(0.0952);
    }
}
