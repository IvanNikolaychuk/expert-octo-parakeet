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

    @Column(name = "index_type")
    @Enumerated(EnumType.STRING)
    private Index.Type indexType;

    @Column(name = "perc_change")
    private Double percChange;

    public IndexChangeAnalyzeRecord() {
    }

    public IndexChangeAnalyzeRecord(Date date, Index.Type indexType, Double percChange) {
        this.date = date;
        this.indexType = indexType;
        this.percChange = BigDecimal.valueOf(percChange).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
