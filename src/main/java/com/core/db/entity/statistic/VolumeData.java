package com.core.db.entity.statistic;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
@Embeddable
@Data
public class VolumeData {
    private int daysTraded;
    private BigDecimal avgVolume;

    public VolumeData(int daysTraded, BigDecimal avgVolume) {
        this.daysTraded = daysTraded;
        this.avgVolume = avgVolume;
    }
}
