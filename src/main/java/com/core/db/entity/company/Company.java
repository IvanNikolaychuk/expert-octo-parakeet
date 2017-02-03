package com.core.db.entity.company;

import com.core.db.entity.Candle;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
@Entity(name = "company")
@Data
public class Company {
    @Id
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "stock_currency")
    private StockCurrency stockCurrency;

    @Enumerated(EnumType.STRING)
    @Column(name = "business_type")
    private BusinessType businessType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Candle> candles;

    public Company() {
        candles = new ArrayList<Candle>();
    }

    public Company(String name, BusinessType businessType, StockCurrency stockCurrency) {
        this();
        this.name = name;
        this.businessType = businessType;
        this.stockCurrency = stockCurrency;
    }

    public void addCandle(Candle candle) {
        candles.add(candle);
    }

    public void addCandles(List<Candle> newCandles) {
        candles.addAll(newCandles);
    }
}
