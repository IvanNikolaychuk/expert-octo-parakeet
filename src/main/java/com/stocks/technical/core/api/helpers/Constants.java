package com.stocks.technical.core.api.helpers;

import java.math.BigDecimal;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class Constants {
    public static final int CURRENT_YEAR = 2017;
    public static final int RECENT_CANDLES_NUMBER = 8;
    public static final int MAX_DAYS_INVESTING = 30;


    public static final BigDecimal MIN_ACCEPTED_GROW_PERCENTAGE_FOR_STRONG_BULL_CANDLE = BigDecimal.valueOf(3.5);
    public static final BigDecimal MIN_ACCEPTED_BODY_RATIO_FOR_STRONG_BULL_CANDLE = BigDecimal.valueOf(2);
    public static final BigDecimal MAX_ACCEPTED_LOWER_SHADOW_FROM_TOTAL_LENGTH_PERCENTAGE_FOR_STRONG_BULL_CANDLE = BigDecimal.valueOf(20);
    public static final BigDecimal MAX_ACCEPTED_UPPER_SHADOW_FROM_TOTAL_LENGTH_PERCENTAGE_FOR_STRONG_BULL_CANDLE = BigDecimal.valueOf(20);

    public static final BigDecimal MIN_ACCEPTED_LOWER_SHADOW_FROM_TOTAL_LENGTH_PERCENTAGE_FOR_HAMMER = BigDecimal.valueOf(75);
    public static final BigDecimal MAX_ACCEPTED_UPPER_SHADOW_FROM_TOTAL_LENGTH_PERCENTAGE_FOR_HAMMER = BigDecimal.valueOf(10);

    public static final BigDecimal MIN_ACCEPTED_PROFIT_FOR_TREND = BigDecimal.valueOf(8);
    public static final int MAX_ACCEPTED_DAYS_FOR_TREND = 20;
    public static final BigDecimal MIN_ACCEPTED_PROFIT_FOR_STRONG_TREND = BigDecimal.valueOf(30);
    public static final int MAX_ACCEPTED_DAYS_FOR_STRONG_TREND = 60;


    public static final BigDecimal MIN_ACCEPTED_GROW_PERCENTAGE_FOR_DARK_CLOUD = BigDecimal.valueOf(2);
    public static final BigDecimal MAX_ACCEPTED_SHADOW_FROM_TOTAL_LENGTH_PERCENTAGE_FOR_DARK_CLOUD_CANDLE = BigDecimal.valueOf(20);


    public static final BigDecimal MIN_ACCEPTED_FALL_PERCENTAGE_FOR_STRONG_FALL_CANDLE = BigDecimal.valueOf(5).negate();
    public static final BigDecimal MIN_ACCEPTED_RISE_PERCENTAGE_FOR_STRONG_FALL_CANDLE = BigDecimal.valueOf(5);

}
