package com.stocks.technical.core.db.entity.statistic;

import com.stocks.technical.core.db.entity.company.Company;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
@Data
@Entity(name = "volume_statistic_data")
public class VolumeStatisticData {

    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    private Company company;

    @Column(name = "avg_month_volume")
    private Long avgMonthVolume;

    @Column(name = "avg_year_volume")
    private Long avgYearVolume;

    @Column(name = "avg_five_days_volume")
    private Long avgFiveDaysVolume;

    @Column(name = "last_day_volume")
    private Long lastDayVolume;

    public VolumeStatisticData() {}

    public VolumeStatisticData(Company company) {
        this.company = company;
    }
}
