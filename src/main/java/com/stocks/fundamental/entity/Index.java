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
@Entity(name = "stock_index")
public class Index {
    @Id
    @GeneratedValue
    private int id;

    @Enumerated(STRING)
    private Type type;

    private Date date;
    private double value;

    public Index() {}

    public Index(Type type, Date date, double value) {
        this.type = type;
        this.date = date;
        this.value = value;
    }

    public enum Type {
        PMI, CCI, S_AND_P
    }
}
