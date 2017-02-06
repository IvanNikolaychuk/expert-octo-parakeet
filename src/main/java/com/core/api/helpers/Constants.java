package com.core.api.helpers;

import java.math.BigDecimal;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class Constants {
    public static final int CURRENT_YEAR = 2017;
    public static final int RECENT_CANDLES_NUMBER = 8;
    public static final int MAX_DAYS_INVESTING = 30;

    public static final BigDecimal MIN_ACCEPTED_PROFIT_PERCENTAGE = BigDecimal.TEN;

    public static final BigDecimal MIN_ACCEPTED_GROW_PERCENTAGE_FOR_STRONG_BULL_CANDLE = BigDecimal.valueOf(4);
    public static final BigDecimal MIN_ACCEPTED_BODY_RATIO_FOR_STRONG_BULL_CANDLE = BigDecimal.valueOf(2.5);
    public static final BigDecimal MAX_ACCEPTED_LOWER_SHADOW_FROM_TOTAL_LENGTH_PERCENTAGE_FOR_STRONG_BULL_CANDLE = BigDecimal.valueOf(20);
    public static final BigDecimal MAX_ACCEPTED_UPPER_SHADOW_FROM_TOTAL_LENGTH_PERCENTAGE_FOR_STRONG_BULL_CANDLE = BigDecimal.valueOf(15);
    public static final BigDecimal MIN_ACCEPTED_PERCENTAGE_CHANGE_FOR_STRONG_BULL_CANDLE = BigDecimal.valueOf(10);
}
