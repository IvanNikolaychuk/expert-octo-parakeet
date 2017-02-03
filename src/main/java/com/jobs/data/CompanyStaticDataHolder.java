package com.jobs.data;

import com.core.db.entity.company.BusinessType;
import com.core.db.entity.company.Company;
import com.core.db.entity.company.StockCurrency;

import java.util.ArrayList;
import java.util.List;

import static com.core.db.entity.company.BusinessType.PHARMACEUTICS;
import static com.core.db.entity.company.StockCurrency.DOLLAR;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class CompanyStaticDataHolder {

    public static List<Company> getCompanies(BusinessType businessType, StockCurrency stockCurrency) {
        List<Company> allCompanies = getAllCompanies();

        List<Company> filtered = new ArrayList<Company>();

        for (Company company : allCompanies) {
            if (company.getBusinessType() == businessType && company.getStockCurrency() == stockCurrency) {
                filtered.add(company);
            }
        }

        return filtered;
    }

    public static List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<Company>();

        companies.addAll(getPharmaUsaCompanies());

        return companies;
    }

    private static List<Company> getPharmaUsaCompanies() {
        List<Company> companies = new ArrayList<Company>();

        companies.add(new Company("BMY", PHARMACEUTICS, DOLLAR));
        companies.add(new Company("AGN", PHARMACEUTICS, DOLLAR));
        companies.add(new Company("AMGN", PHARMACEUTICS, DOLLAR));
        companies.add(new Company("ABBV", PHARMACEUTICS, DOLLAR));
        companies.add(new Company("GILD", PHARMACEUTICS, DOLLAR));
        companies.add(new Company("CELG", PHARMACEUTICS, DOLLAR));
        companies.add(new Company("PFE", PHARMACEUTICS, DOLLAR));
        companies.add(new Company("BIIB", PHARMACEUTICS, DOLLAR));

        return companies;
    }
}
