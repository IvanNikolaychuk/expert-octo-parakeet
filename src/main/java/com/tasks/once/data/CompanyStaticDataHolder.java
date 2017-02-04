package com.tasks.once.data;

import com.core.db.entity.company.BusinessType;
import com.core.db.entity.company.Company;
import com.core.db.entity.company.StockCurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.core.db.entity.company.BusinessType.ENERGETIC;
import static com.core.db.entity.company.BusinessType.PHARMACEUTICS;
import static com.core.db.entity.company.StockCurrency.DOLLAR;
import static com.core.db.entity.company.StockCurrency.RUBLE;

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
//        companies.addAll(getEnergeticRusCompanies());
//        companies.addAll(getEnergeticUsaCompanies());

        return companies;
    }

    private static List<Company> getPharmaUsaCompanies() {
        List<Company> companies = new ArrayList<Company>();

        companies.add(new Company("BMY", PHARMACEUTICS, DOLLAR));
//        companies.add(new Company("AGN", PHARMACEUTICS, DOLLAR));
//        companies.add(new Company("AMGN", PHARMACEUTICS, DOLLAR));
//        companies.add(new Company("ABBV", PHARMACEUTICS, DOLLAR));
//        companies.add(new Company("GILD", PHARMACEUTICS, DOLLAR));
//        companies.add(new Company("CELG", PHARMACEUTICS, DOLLAR));
//        companies.add(new Company("PFE", PHARMACEUTICS, DOLLAR));
//        companies.add(new Company("BIIB", PHARMACEUTICS, DOLLAR));

        return companies;
    }

    private static List<Company> getEnergeticRusCompanies() {
        List<Company> companies = new ArrayList<>();

        companies.add(new Company("FEES", ENERGETIC, RUBLE));
        companies.add(new Company("RSTI", ENERGETIC, RUBLE));
        companies.add(new Company("OGKB", ENERGETIC, RUBLE));
        companies.add(new Company("HYDR", ENERGETIC, RUBLE));
        companies.add(new Company("MSNG", ENERGETIC, RUBLE));
        companies.add(new Company("IRAO", ENERGETIC, RUBLE));

        return companies;
    }

    private static List<Company> getEnergeticUsaCompanies() {
        List<Company> companies = new ArrayList<>();

        companies.add(new Company("NRG", ENERGETIC, DOLLAR));
        companies.add(new Company("CHK", ENERGETIC, DOLLAR));
        companies.add(new Company("FSLR", ENERGETIC, DOLLAR));
        companies.add(new Company("CVX", ENERGETIC, DOLLAR));
        companies.add(new Company("VLO", ENERGETIC, DOLLAR));
        companies.add(new Company("EXC", ENERGETIC, DOLLAR));

        return companies;
    }



}
