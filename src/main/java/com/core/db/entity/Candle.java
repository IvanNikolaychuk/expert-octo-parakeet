package com.core.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static com.core.db.entity.Candle.Trend.UP;

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

    public BigDecimal getGrowth() {
        return close.subtract(open);
    }

    public enum Trend {
        UP,
        DOWN
    }
}
