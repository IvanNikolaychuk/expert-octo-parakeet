package com.stocks.technical.tasks.helpers;

import com.stocks.technical.core.db.entity.Candle;

import java.math.BigDecimal;

import static com.stocks.technical.core.db.entity.Candle.Trend.UP;
import static com.stocks.technical.tasks.utils.TimeUtils.today;
import static com.stocks.technical.tasks.utils.TimeUtils.yesterday;

public class CandleHelper {
    public static Candle createTodaysCandle(BigDecimal open, BigDecimal close) {
        Candle candle = createTodaysCandle();
        candle.setOpen(open);
        candle.setClose(close);

        return candle;
    }

    public static Candle createTodaysCandle() {
        Candle candle = new Candle();
        candle.setDate(today());

        return candle;
    }


    public static Candle createYesterdaysCandle(BigDecimal open, BigDecimal close) {
        Candle candle = createYesterdaysCandle();
        candle.setOpen(open);
        candle.setClose(close);

        return candle;
    }

    public static Candle createYesterdaysCandle() {
        Candle candle = new Candle();
        candle.setDate(yesterday());

        return candle;
    }

    public static Candle createYesterdaysCandleWithUpperTrend(BigDecimal open, BigDecimal close) {
        Candle candle = createYesterdaysCandle(open, close);
        candle.setBody(close.subtract(open));

        return candle;
    }


    public static Candle createTodaysCandleWithUpperTrend(BigDecimal open, BigDecimal close) {
        Candle candle = createTodaysCandle(open, close);
        candle.setTrend(UP);
        candle.setBody(close.subtract(open));

        return candle;
    }
}
