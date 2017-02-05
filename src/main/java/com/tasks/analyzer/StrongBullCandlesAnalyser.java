package com.tasks.analyzer;

import com.core.api.dto.StockData;
import com.core.db.dao.CompanyDao;
import com.core.db.entity.Candle;
import com.core.db.entity.company.Company;
import com.tasks.analyzer.drafts.helpers.CandleSequence;

import java.util.List;

public class StrongBullCandlesAnalyser {
    public void analyse() {
        List<Company> companies = new CompanyDao().getAll();

        Company company = companies.get(0);
        analyse(company.getCandles());
    }

    public void analyse(List<Candle> candles) {
        CandleSequence candleSequence = new CandleSequence(candles);
        while (candleSequence.hasNext()) {
            Candle candle = candleSequence.next();
            if (candleSequence.isStrongBullCandle(candle)) {

            }
        }
    }
}
