package com.tasks.analyzer.patterns;

import com.core.db.dao.CompanyDao;
import com.core.db.dao.StrongBullStatisticDataDao;
import com.core.db.entity.Candle;
import com.core.db.entity.company.Company;
import com.core.db.entity.statistic.StrongBullStatisticData;
import com.tasks.analyzer.drafts.helpers.CandleByDateSequence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.core.api.helpers.Constants.MIN_ACCEPTED_PERCENTAGE_CHANGE_FOR_STRONG_BULL_CANDLE;
import static com.tasks.analyzer.patterns.StrongBullStatisticDataAnalyser.Movement.BACK;
import static com.tasks.analyzer.patterns.StrongBullStatisticDataAnalyser.Movement.FORWARD;
import static com.tasks.utils.CandleUtils.calculatePercentageProfit;
import static com.tasks.utils.filters.CandlesFilter.filterStrongBull;
import static java.math.BigDecimal.ZERO;

public class StrongBullStatisticDataAnalyser {

    public void execute() {
        List<Company> companies = new CompanyDao().getAll();
        StrongBullStatisticDataDao strongBullStatisticDataDao = new StrongBullStatisticDataDao();
        for (Company company : companies) {
            List<Candle> allCandles = company.getCandles();
            List<Candle> strongBulls = filterStrongBull(allCandles);
            strongBullStatisticDataDao.save(analyse(allCandles, strongBulls));
        }
    }

    List<StrongBullStatisticData> analyse(List<Candle> allCandles, List<Candle> strongBulls) {
        List<StrongBullStatisticData> strongBullStatisticDataList = new ArrayList<>();

        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(allCandles);
        for (Candle candle : strongBulls) {
            StrongBullStatisticData strongBullStatisticData = new StrongBullStatisticData(candle);
            strongBullStatisticDataList.add(strongBullStatisticData);

            candleByDateSequence.setCurrent(candle);
            // start with next after strong bull
            if (candleByDateSequence.hasNext()) {
                candleByDateSequence.next();
                Candle nextAfterTarget = candleByDateSequence.getCurrent();
                analyseTrend(nextAfterTarget, candleByDateSequence, strongBullStatisticData, FORWARD);
            }

            candleByDateSequence.setCurrent(candle);
            if (candleByDateSequence.hasPrev()) {
                candleByDateSequence.prev();
                Candle prevBeforeTarget = candleByDateSequence.getCurrent();
                analyseTrend(prevBeforeTarget, candleByDateSequence, strongBullStatisticData, BACK);
            }
        }

        return strongBullStatisticDataList;
    }

    public enum Movement {
        BACK, FORWARD
    }

    /**
     * Number of days and percentages will be computed begining from firstCandle.
     */
    void analyseTrend(Candle firstCandle, CandleByDateSequence candleByDateSequence, StrongBullStatisticData strongBullStatisticData, Movement movement) {
        List<Candle> candles = new ArrayList<>();
        candles.add(firstCandle);

        int days = 0;
        while (hasMore(candleByDateSequence, movement)) {
            days++;
            Candle oneOfPrevOrNextCandles = moveToOther(candleByDateSequence, movement);
            candles.add(oneOfPrevOrNextCandles);

            BigDecimal percentageProfit = calculatePercentageProfit(candles);
            if (percentageProfit.compareTo(ZERO) > 0) {
                if (percentageProfit.compareTo(MIN_ACCEPTED_PERCENTAGE_CHANGE_FOR_STRONG_BULL_CANDLE) >= 0) {
                    fillStatisticData(percentageProfit, days, strongBullStatisticData, movement);
                    return;
                }
            } else {
                if (percentageProfit.compareTo(MIN_ACCEPTED_PERCENTAGE_CHANGE_FOR_STRONG_BULL_CANDLE.negate()) < 0) {
                    fillStatisticData(percentageProfit, days, strongBullStatisticData, movement);
                    return;
                }
            }
        }
    }

    private Candle moveToOther(CandleByDateSequence candleByDateSequence, Movement movement) {
        return movement == FORWARD ?
                candleByDateSequence.next() :
                candleByDateSequence.prev();
    }

    private void fillStatisticData(BigDecimal percentageProfit, int days, StrongBullStatisticData strongBullStatisticData, Movement movement) {
        if (movement == FORWARD) {
            strongBullStatisticData.setAfterDays(days);
            strongBullStatisticData.setAfterPercentageProfit(percentageProfit);
        } else {
            strongBullStatisticData.setBeforeDays(days);
            strongBullStatisticData.setBeforePercentageProfit(percentageProfit);
        }
    }

    private boolean hasMore(CandleByDateSequence candleByDateSequence, Movement movement) {
        return movement == FORWARD ?
                candleByDateSequence.hasNext() :
                candleByDateSequence.hasPrev();
    }

    public static void main(String[] args) {
        new StrongBullStatisticDataAnalyser().execute();
    }
}
