package com.stocks.core.db.entity.statistic;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Calendar;

@Data
@Entity(name = "company_growth_statistic_data")
/**
 * Designed to analyse market in general. (e.g. by business type).
 * See some examples in analyser.sql
 */
public class CompanyGrowthStatisticData {
    public static final int BEGIN_DAY_DEF = 1;
    public static final int END_DAY_DEF = 25;

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "from_date")
    private Calendar from;

    @Column(name = "to_date")
    private Calendar to;

    @Column(name = "percentage_profit")
    private BigDecimal percentageProfit;

    @Column(name = "company_name")
    private String companyName;

    @Column(name="open")
    private BigDecimal open;

    @Column(name="close")
    private BigDecimal close;

    @Column(name="months_from_today")
    private int monthsFromToday;

    public CompanyGrowthStatisticData(String companyName, Calendar from, Calendar to, BigDecimal percentageProfit) {
        this.companyName = companyName;

        this.from = Calendar.getInstance();
        this.from.set(from.get(Calendar.YEAR), from.get(Calendar.MONTH), BEGIN_DAY_DEF, 0, 0, 0);

        this.to = Calendar.getInstance();
        this.to.set(to.get(Calendar.YEAR), to.get(Calendar.MONTH), END_DAY_DEF, 0, 0, 0);
        Calendar now = Calendar.getInstance();

        int diffYear = now.get(Calendar.YEAR) - from.get(Calendar.YEAR);
        monthsFromToday = diffYear * 12 + now.get(Calendar.MONTH) - from.get(Calendar.MONTH);

        this.percentageProfit = percentageProfit;
    }

    public CompanyGrowthStatisticData() {
    }
}
