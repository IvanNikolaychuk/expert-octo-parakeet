package com.stocks.technical.tasks.analyzer.trend;

import com.stocks.technical.core.db.entity.Candle;
import com.stocks.technical.tasks.analyzer.helpers.CandleByDateSequence;

import java.math.BigDecimal;
import java.util.List;

import static com.stocks.technical.core.api.helpers.Constants.*;
import static com.stocks.technical.tasks.analyzer.trend.TrendAnalyser.Movement.*;
import static com.stocks.technical.tasks.analyzer.trend.TrendAnalyser.Movement.UP;
import static com.stocks.technical.tasks.utils.CandleUtils.calculatePercentageProfit;

/**
 * @author Ivan Nikolaichuk
 */
public class TrendAnalyser {
    public enum Movement {
        UP, STRONG_UP, DOWN, STRONG_DOWN, NONE
    }

    public static Movement analyseTrendBefore(Candle target, List<Candle> candles) {
        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(candles);

        for (int counter = 0; counter < MAX_ACCEPTED_DAYS_FOR_STRONG_TREND; counter++) {
            candleByDateSequence.setCurrent(target);
            Candle previous = candleByDateSequence.back(counter);

            BigDecimal profit = calculatePercentageProfit(previous.getOpen(), target.getClose());
            if (profit.compareTo(MIN_ACCEPTED_PROFIT_FOR_STRONG_TREND.negate()) <= 0) {
                return STRONG_DOWN;
            } else if (profit.compareTo(MIN_ACCEPTED_PROFIT_FOR_STRONG_TREND) >= 0) {
                return STRONG_UP;
            }
        }

        for (int counter = 0; counter < MAX_ACCEPTED_DAYS_FOR_TREND; counter++) {
            candleByDateSequence.setCurrent(target);
            Candle previous = candleByDateSequence.back(counter);

            BigDecimal profit = calculatePercentageProfit(previous.getOpen(), target.getClose());
            if (profit.compareTo(MIN_ACCEPTED_PROFIT_FOR_TREND.negate()) <= 0) {
                return DOWN;
            } else if (profit.compareTo(MIN_ACCEPTED_PROFIT_FOR_TREND) >= 0) {
                return UP;
            }
        }

        return NONE;
    }
}
