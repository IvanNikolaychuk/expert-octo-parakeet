package com.stocks.core.db.entity;

import com.stocks.core.db.entity.Candle;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author Ivan Nikolaichuk
 */
@Entity(name = "profit_data")
@Data
public class ProfitData {
    @Id
    public long strongBullId;

    @Column(name = "perc_profit")
    public BigDecimal percProfit;

    public ProfitData() {}

    public ProfitData(Candle strongBull) {
        this.strongBullId = strongBull.getId();
    }
}
