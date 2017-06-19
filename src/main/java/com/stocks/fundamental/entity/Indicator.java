package com.stocks.fundamental.entity;

import lombok.Data;

import javax.annotation.security.DenyAll;
import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

import static javax.persistence.EnumType.STRING;

/**
 * @author Ivan Nikolaichuk
 */
@Data
@Entity(name = "indicator")
public class Indicator {
    @Id
    @GeneratedValue
    private int id;

    @Enumerated(STRING)
    private Type type;
    private Date date;
    private double value;

    public Indicator() {}

    public Indicator(Type type, Date date, double value) {
        this.type = type;
        this.date = date;
        this.value = value;
    }

    public enum Type {
        PMI,
    }
}
