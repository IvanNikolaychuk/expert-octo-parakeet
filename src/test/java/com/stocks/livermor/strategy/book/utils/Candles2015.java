package com.stocks.livermor.strategy.book.utils;

import com.stocks.livermor.entity.Record;
import com.stocks.livermor.entity.State;
import com.stocks.technical.core.db.dao.RecordDao;

import java.util.Calendar;

public class Candles2015 {

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, Calendar.DECEMBER, 31);

        Record gazp = new Record(calendar.getTime(), 130.31);
        gazp.setState(State.NATURAL_REACTION);
        gazp.setTicker("GAZP.ME");

        Record rosn = new Record(calendar.getTime(), 195.8);
        rosn.setState(State.NATURAL_REACTION);
        rosn.setPivotPoint(true);
        rosn.setTicker("ROSN.ME");

        Record key = new Record(calendar.getTime(), 326.11);
        key.setState(State.NATURAL_REACTION);
        key.setTicker("GAZP.ME_ROSN.ME");

        RecordDao recordDao = new RecordDao();
        recordDao.save(gazp);
        recordDao.save(rosn);
        recordDao.save(key);

    }
}
