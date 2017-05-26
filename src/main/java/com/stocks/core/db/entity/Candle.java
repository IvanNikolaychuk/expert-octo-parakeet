package com.stocks.core.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
@Data
@Entity(name = "candle")
public class Candle {

    @Id
    @GeneratedValue
    private int id;

    private Calendar date;

    private BigDecimal body;

    @Column(name = "upper_shadow")
    private BigDecimal upperShadow;

    @Column(name = "lower_shadow")
    private BigDecimal lowerShadow;

    @Enumerated(EnumType.STRING)
    private Trend trend;

    public BigDecimal volume;

    private BigDecimal open;

    private BigDecimal close;

    private BigDecimal low;

    private BigDecimal high;

    @Enumerated(EnumType.STRING)
    private Pattern pattern;

    @Column(name = "percentage_profit")
    private BigDecimal percentageProfit;

    public enum Trend {
        UP,
        DOWN
    }

    public enum Pattern {
        NONE,
        STRONG_BULL,
        STRONG_BEAR,
        STRONG_GAP_FALL,
        STRONG_GAP_RISE,
        DOJI
    }
}
