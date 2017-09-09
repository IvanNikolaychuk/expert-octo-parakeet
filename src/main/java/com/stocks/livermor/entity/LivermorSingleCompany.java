package com.stocks.livermor.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity(name = "livermor_single_pair")
@Data
public class LivermorSingleCompany {
    @Id
    @GeneratedValue
    private int id;

    private String ticker;

    public LivermorSingleCompany() {}

    public LivermorSingleCompany(String ticker) {
        this.ticker = ticker;
    }
}
