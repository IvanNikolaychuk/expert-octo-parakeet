package com.stocks.livermor.entity;

import com.stocks.livermor.Constants;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Record {
    @Id
    @GeneratedValue
    public int id;

    private String ticker;
    private Date date;
    private double price;

    @Enumerated(EnumType.STRING)
    private State state;

    private boolean pivotPoint;

    private String explanation;

    public Record(Date date, double price) {
        this.date = date;
        this.price = price;
    }

   public Record() {}

    public void markAsPivotPoint() {
        pivotPoint = true;
    }

    public void setStateAndRule(State state, Constants.Rule rule) {
        this.state = state;
        this.explanation = rule.getExplanation();
    }

    public boolean hasState() {
        return state != null;
    }
}
