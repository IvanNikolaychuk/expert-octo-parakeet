package com.stocks.livermor.entity;

import com.stocks.technical.core.db.entity.Candle;
import lombok.Data;

import java.util.Date;


@Data
public class Record {
    private Date date;
    private double price;
    private State state;
    private boolean pivotPoint;

    public Record(Date date, double price) {
        this.date = date;
        this.price = price;
    }

    public void markAsPivotPoint() {
        pivotPoint = true;
    }
}
