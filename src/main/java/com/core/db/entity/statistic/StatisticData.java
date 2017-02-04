package com.core.db.entity.statistic;

import com.core.db.entity.company.Company;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
@Data
@Entity(name = "statistic_data")
public class StatisticData {

    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    private Company company;

    public StatisticData() {}

    public StatisticData(Company company) {
        this.company = company;
    }

    @AttributeOverrides({
            @AttributeOverride(name = "daysTraded", column = @Column(name = "days_traded_recent")),
            @AttributeOverride(name = "avgVolume", column = @Column(name = "recent_avg_volume"))
    })
    private VolumeData volumeRecentData;

    @AttributeOverrides({
            @AttributeOverride(name = "daysTraded", column = @Column(name = "days_traded_year")),
            @AttributeOverride(name = "avgVolume", column = @Column(name = "year_avg_volume"))
    })
    private VolumeData volumeYearData;

}
