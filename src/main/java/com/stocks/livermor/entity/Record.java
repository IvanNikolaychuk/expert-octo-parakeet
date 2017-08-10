package com.stocks.livermor.entity;

import lombok.Data;

import java.util.Date;


@Data
public class Record {
    private Date date;
    private double price;
    private State state;
    private boolean pivotPoint;
    private Enum rule;

    public Record(Date date, double price) {
        this.date = date;
        this.price = price;
    }

    public void markAsPivotPoint() {
        pivotPoint = true;
    }

    public void setStateAndRule(State state, Enum rule) {
        setState(state);
        setRule(rule);
    }
}
