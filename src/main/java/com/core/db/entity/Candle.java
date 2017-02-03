package com.core.db.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
@Data
@Entity(name = "candle")
public class Candle {

    @Id
    @GeneratedValue
    private int id;

    private Date date;

    private BigDecimal open;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal close;

    private BigDecimal volume;

    public Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
