package com.stocks.technical.core.db.entity.statistic.stongbull;

import com.stocks.technical.core.db.entity.Candle;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Data
@Entity(name = "exp_average_candle")
public class ExpAverageCandle {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Candle candle;

    public BigDecimal value;

    public ExpAverageCandle() {}

    public ExpAverageCandle(Candle candle, BigDecimal value) {
        this.candle = candle;
        this.value = value;
    }
}
