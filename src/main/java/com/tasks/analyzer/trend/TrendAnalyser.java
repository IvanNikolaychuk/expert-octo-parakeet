package com.tasks.analyzer.trend;

import com.core.db.entity.Candle;
import com.tasks.analyzer.helpers.CandleByDateSequence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.core.api.yahoo.helpers.Constants.MIN_ACCEPTED_PERCENTAGE_CHANGE_FOR_TREND;
import static com.tasks.analyzer.trend.Movement.FORWARD;
import static com.tasks.utils.CandleUtils.calculatePercentageProfit;
import static java.math.BigDecimal.ZERO;

public class TrendAnalyser {

    /**
     * Number of days and percentages will be computed begining from firstCandle.
     */
    public TrendData analyseTrend(Candle firstCandle, CandleByDateSequence candleByDateSequence, Movement movement) {
        List<Candle> candles = new ArrayList<>();
        candles.add(firstCandle);

        int days = 0;
        while (hasMore(candleByDateSequence, movement)) {
            days++;
            Candle oneOfPrevOrNextCandles = moveToOther(candleByDateSequence, movement);
            candles.add(oneOfPrevOrNextCandles);

            BigDecimal percentageProfit = calculatePercentageProfit(candles);
            if (percentageProfit.compareTo(ZERO) > 0) {
                if (percentageProfit.compareTo(MIN_ACCEPTED_PERCENTAGE_CHANGE_FOR_TREND) >= 0) {
                    return new TrendData(percentageProfit, days);
                }
            } else {
                if (percentageProfit.compareTo(MIN_ACCEPTED_PERCENTAGE_CHANGE_FOR_TREND.negate()) < 0) {
                    return new TrendData(percentageProfit, days);
                }
            }
        }

        return new TrendData(null, null);
    }

    private Candle moveToOther(CandleByDateSequence candleByDateSequence, Movement movement) {
        return movement == FORWARD ?
                candleByDateSequence.next() :
                candleByDateSequence.prev();
    }

    private boolean hasMore(CandleByDateSequence candleByDateSequence, Movement movement) {
        return movement == FORWARD ?
                candleByDateSequence.hasNext() :
                candleByDateSequence.hasPrev();
    }

}
