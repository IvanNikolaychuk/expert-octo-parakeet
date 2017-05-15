package com.stocks.tasks.analyzer.patterns;

import com.stocks.core.db.dao.CandlesDao;
import com.stocks.core.db.dao.CompanyDao;
import com.stocks.core.db.entity.Candle;
import com.stocks.core.db.entity.company.Company;
import com.stocks.tasks.analyzer.helpers.CandleByDateSequence;
import com.stocks.tasks.utils.CandleUtils;
import com.stocks.tasks.utils.filters.CandlesFilter;

import java.util.List;

import static com.stocks.core.db.entity.Candle.Pattern.*;

public class CandlesPatternAnalyser {

    public void execute() {
        CandlesDao candlesDao = new CandlesDao();

        for(Company company : new CompanyDao().getAll()) {
            List<Candle> candles = CandleUtils.sort(company.getCandles(), new CandlesFilter.OldDateFirstComparator());
            findAndSetPatterns(candles);
            candlesDao.update(candles);
        }
    }

    public void findAndSetPatterns(List<Candle> candles) {
        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(candles);
        while (candleByDateSequence.hasNext()) {
            Candle candle = candleByDateSequence.next();
            if (candleByDateSequence.isStrongBullCandle(candle)) {
                candle.setPattern(STRONG_BULL);
            } else if (candleByDateSequence.isStrongGapFallCandle(candle)) {
                candle.setPattern(STRONG_GAP_FALL);
            } else if (candleByDateSequence.isStrongGapRiseCandle(candle)) {
                candle.setPattern(STRONG_GAP_RISE);
            } else {
                candle.setPattern(NONE);
            }
        }
    }

    public static void main(String[] args) {
        new CandlesPatternAnalyser().execute();
    }
}
