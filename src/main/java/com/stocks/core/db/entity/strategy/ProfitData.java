package com.stocks.core.db.entity.strategy;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author Ivan Nikolaichuk
 */
@Data
@Entity(name = "strong_bull_profit_data")
public class ProfitData {
    @Id
    @Column(name = "company_name")
    public String companyName;

    @Column(name = "total_profit_perc")
    public BigDecimal totalProfitPerc;

    @Column(name = "support_break_counter")
    public int supportBreakCounter;

    @Column(name = "total_periods_counter")
    public int totalPeriodsCounter;

    public ProfitData(String companyName) {
        this.companyName = companyName;
        totalProfitPerc = BigDecimal.ZERO;
    }

    public ProfitData() {
    }
}
