package com.core.db.entity.statistic;

import com.core.db.entity.company.Company;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity(name = "company_growth_statistic_data")
public class CompanyGrowthStatisticData {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "number_of_month_from_today")
    private int numberOfMonth;

    @Column(name = "percentage_profit")
    private BigDecimal percentageProfit;

    @Column(name = "company_name")
    private String companyName;

    public CompanyGrowthStatisticData(String companyName,int numberOfMonth, BigDecimal percentageProfit) {
        this.companyName = companyName;
        this.numberOfMonth = numberOfMonth;
        this.percentageProfit = percentageProfit;
    }

    public CompanyGrowthStatisticData() {}
}
