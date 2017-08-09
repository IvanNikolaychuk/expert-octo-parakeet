package com.stocks.fundamental.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Map;

/**
 * @author Ivan Nikolaichuk
 */
@Data
@Entity(name = "stock_index_change_analyze")
public class IndexChangeAnalyzeRecord {
    @Id
    @GeneratedValue
    private int id;

    private Date date;

    @Column(name = "perc_change_cci")
    private Double percChangeCCI;

    @Column(name = "perc_change_pmi")
    private Double percChangePMI;

    @Column(name = "perc_change_s_and_p")
    private Double percChangeSNP;

    public IndexChangeAnalyzeRecord() {
    }

    public IndexChangeAnalyzeRecord(Date date) {
        this.date = date;
    }

    @Transient
    public void setPercChange(Index.Type indexType, Double percChange) {
        percChange = BigDecimal.valueOf(percChange).setScale(2, RoundingMode.HALF_UP).doubleValue();
        switch (indexType) {
            case CCI:
                setPercChangeCCI(percChange);
                break;
            case PMI:
                setPercChangePMI(percChange);
                break;
            case S_AND_P:
                setPercChangeSNP(percChange);
                break;
        }
    }
}
