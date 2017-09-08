package com.stocks.livermor.entity;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

@Entity(name = "livermor_pair")
@Data
public class LivermorPair {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "first_ticker")
    private String firstTicker;

    @Column(name = "second_ticker")
    private String secondTicker;

    @Column(name = "key_price_ticker")
    private String keyPriceTicker;

    @Column(name = "business_type")
    @Enumerated(value = STRING)
    private BusinessType businessType;
}
