package com.tasks.analyzer.patterns;

import com.core.db.dao.CandlesDao;
import com.core.db.dao.CompanyDao;
import com.core.db.entity.Candle;
import com.core.db.entity.company.Company;
import com.tasks.analyzer.drafts.helpers.CandleByDateSequence;
import com.tasks.utils.CandleUtils;
import com.tasks.utils.filters.CandlesFilter;

import java.util.List;

import static com.core.db.entity.Candle.Pattern.NONE;
import static com.core.db.entity.Candle.Pattern.STRONG_BULL;

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
            } else {
                candle.setPattern(NONE);
            }
        }
    }

    public static void main(String[] args) {
        new CandlesPatternAnalyser().execute();
    }
}
