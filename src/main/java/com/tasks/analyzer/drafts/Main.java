package com.tasks.analyzer.drafts;

import com.core.db.dao.CompanyDao;
import com.core.db.entity.Candle;
import com.core.db.entity.company.BusinessType;
import com.core.db.entity.company.Company;
import com.tasks.utils.CandleUtils;
import com.tasks.utils.filters.CandlesFilter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CompanyDao companyDao = new CompanyDao();
        List<Company> companies = companyDao.getAll();
        List<Company> pharmaCompanies = new ArrayList<>();

        for (Company company : companies) {
            if (company.getBusinessType() == BusinessType.PHARMACEUTICS) {
                pharmaCompanies.add(company);
            }
        }

        evaluate(companies);
    }

    private static void evaluate(List<Company> pharmaCompanies) {
        for(Company company : pharmaCompanies) {
            List<Candle> candles = company.getCandles();
            Calendar from = Calendar.getInstance();
            from.set(2016, Calendar.NOVEMBER, 5);

            Calendar to = Calendar.getInstance();
            to.set(2016, Calendar.NOVEMBER, 11);

            List<Candle> filtered = CandlesFilter.filterByDate(candles, from, to);

            System.out.println(company.getName() + " " + CandleUtils.calculatePercentageProfit(filtered));
        }
    }
}
