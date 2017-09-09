package com.stocks.technical.core.db.service;

import com.stocks.technical.core.db.dao.CompanyDao;
import com.stocks.technical.core.db.entity.Candle;
import com.stocks.technical.core.db.entity.company.Company;

import java.util.Calendar;
import java.util.List;

import static com.stocks.livermor.utils.DateUtils.sameDate;

public class CompanyService {
    private final CompanyDao companyDao;

    public CompanyService() {
        this.companyDao = new CompanyDao();
    }

    public void addCandles(Company company, List<Candle> newCandles) {
        List<Candle> allCandles = company.getCandles();

        for (Candle potentiallyNew : newCandles) {
            boolean isNew = true;
            for (Candle companyCandle : allCandles) {
                if (sameDate(companyCandle.getDate(), potentiallyNew.getDate())
                        || sameDate(companyCandle.getDate(), Calendar.getInstance())) {
                    isNew = false;
                    break;
                }
            }
            if (isNew) {
                company.getCandles().add(potentiallyNew);
            }
        }

        companyDao.saveOrUpdate(company);
    }
}
