package com.tasks.helpers;

import com.core.db.entity.Candle;

import java.math.BigDecimal;

import static com.tasks.utils.TimeUtils.today;
import static com.tasks.utils.TimeUtils.yesterday;

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
}
