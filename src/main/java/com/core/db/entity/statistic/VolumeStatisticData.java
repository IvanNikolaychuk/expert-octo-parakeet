package com.core.db.entity.statistic;

import com.core.db.entity.company.Company;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

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

    @ElementCollection
    @CollectionTable(name="volume_statistic_data_highest_volume_dates")
    @Column(name = "highest_volume_dates")
    public List<Calendar> highestVolumeDates;

    public VolumeStatisticData() {}

    public VolumeStatisticData(Company company, List<Calendar> highestVolumeDates) {
        this.company = company;
        this.highestVolumeDates = highestVolumeDates;
    }


}
