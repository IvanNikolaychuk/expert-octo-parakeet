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
    private final CandlesDao candlesDao = new CandlesDao();
    private final CompanyDao companyDao = new CompanyDao();

    public void execute() {

        for (Company company : companyDao.getAll()) {
            findAndSetPatterns(company.getCandles());
            candlesDao.update(company.getCandles());
        }
    }

    private void findAndSetPatterns(List<Candle> candles) {
        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(candles);

        while (candleByDateSequence.hasNext()) {
            Candle candle = candleByDateSequence.next();
            candle.setPattern(candleByDateSequence.findPattern());
        }
    }

    public static void main(String[] args) {
        new CandlesPatternAnalyser().execute();
    }
}
