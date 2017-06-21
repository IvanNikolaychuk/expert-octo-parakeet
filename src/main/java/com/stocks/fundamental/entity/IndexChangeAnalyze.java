package com.stocks.fundamental.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Map;

/**
 * @author Ivan Nikolaichuk
 */
@Data
@Entity(name = "stock_index_change_analyze")
public class IndexChangeAnalyze {
    @Id
    @GeneratedValue
    private int id;

    private Map<Date, Map<Index.Type, Double>> dateToIndexChangeMap;

    public IndexChangeAnalyze() {
    }

    public IndexChangeAnalyze(Map<Date, Map<Index.Type, Double>> dateToIndexChangeMap) {
        this.dateToIndexChangeMap = dateToIndexChangeMap;
    }
}
