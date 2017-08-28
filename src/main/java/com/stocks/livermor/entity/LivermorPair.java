package com.stocks.livermor.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
}
