package com.tasks.once.data;

import com.core.db.entity.company.BusinessType;
import com.core.db.entity.company.Company;
import com.core.db.entity.company.StockCurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.core.db.entity.company.BusinessType.*;
import static com.core.db.entity.company.StockCurrency.DOLLAR;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class CompanyStaticDataHolder {

    public static List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<Company>();

        companies.addAll(getPharmaUsaCompanies());
        companies.addAll(getEnergeticUsaCompanies());
        companies.addAll(getMechanicalEngineeringUsaCompanies());
        companies.addAll(getFoodUsaCompanies());
        companies.addAll(getItUsaCompanies());
        companies.addAll(getMountainCompanies());
        companies.addAll(getPetrochemicalCompanies());
        companies.addAll(getRetailCompanies());
        companies.addAll(getTelecommunicationCompanies());
        companies.addAll(getTransportCompanies());
        companies.addAll(getFinanceUsaCompanies());
        companies.addAll(getAllOtherCompanies());

        return companies;
    }

    private static List<Company> getFinanceUsaCompanies() {
        List<Company> companies = new ArrayList<>();

        companies.add(new Company("V", FINANCE, DOLLAR));
        companies.add(new Company("BAC", FINANCE, DOLLAR));
        companies.add(new Company("AXP", FINANCE, DOLLAR));
        companies.add(new Company("BLK", FINANCE, DOLLAR));
        companies.add(new Company("AIG", FINANCE, DOLLAR));
        companies.add(new Company("MET", FINANCE, DOLLAR));
        companies.add(new Company("PYPL", FINANCE, DOLLAR));
        companies.add(new Company("JPM", FINANCE, DOLLAR));
        companies.add(new Company("C", FINANCE, DOLLAR));

        return companies;
    }


    private static List<Company> getItUsaCompanies() {
        List<Company> companies = new ArrayList<>();

        companies.add(new Company("BIDU", IT, DOLLAR));
        companies.add(new Company("NFLX", IT, DOLLAR));
        companies.add(new Company("MSFT", IT, DOLLAR));
        companies.add(new Company("IBM", IT, DOLLAR));
        companies.add(new Company("AAPL", IT, DOLLAR));
        companies.add(new Company("CSCO", IT, DOLLAR));
        companies.add(new Company("GOOG", IT, DOLLAR));
        companies.add(new Company("FB", IT, DOLLAR));
        companies.add(new Company("YHOO", IT, DOLLAR));
        companies.add(new Company("TSLA", IT, DOLLAR));
        companies.add(new Company("INTC", IT, DOLLAR));
        companies.add(new Company("MU", IT, DOLLAR));
        companies.add(new Company("TWTR", IT, DOLLAR));
        companies.add(new Company("CRM", IT, DOLLAR));
        companies.add(new Company("NVDA", IT, DOLLAR));
        companies.add(new Company("ORCL", IT, DOLLAR));

        return companies;
    }

    private static List<Company> getAllOtherCompanies() {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("PM", OTHER, DOLLAR));
        companies.add(new Company("EFX", OTHER, DOLLAR));
        companies.add(new Company("EXPD", OTHER, DOLLAR));
        companies.add(new Company("DOV", OTHER, DOLLAR));
        companies.add(new Company("CHRW", OTHER, DOLLAR));
        companies.add(new Company("ACN", OTHER, DOLLAR));
        companies.add(new Company("DNB", OTHER, DOLLAR));
        companies.add(new Company("BSX", OTHER, DOLLAR));
        companies.add(new Company("DD", OTHER, DOLLAR));
        companies.add(new Company("CBS", OTHER, DOLLAR));
        companies.add(new Company("DHR", OTHER, DOLLAR));
        companies.add(new Company("JNJ", OTHER, DOLLAR));
        companies.add(new Company("DIS", OTHER, DOLLAR));
        companies.add(new Company("PG", OTHER, DOLLAR));

        companies.add(new Company("ATVI", OTHER, DOLLAR));
        companies.add(new Company("MA", OTHER, DOLLAR));
        companies.add(new Company("CPB", OTHER, DOLLAR));
        companies.add(new Company("HSY", OTHER, DOLLAR));
        companies.add(new Company("LUV", OTHER, DOLLAR));
        companies.add(new Company("COG", OTHER, DOLLAR));
        companies.add(new Company("UNH", OTHER, DOLLAR));
        companies.add(new Company("ABT", OTHER, DOLLAR));
        companies.add(new Company("HAL", OTHER, DOLLAR));
        companies.add(new Company("CL", OTHER, DOLLAR));
        companies.add(new Company("FDX", OTHER, DOLLAR));
        companies.add(new Company("BK", OTHER, DOLLAR));
        companies.add(new Company("GM", OTHER, DOLLAR));
        companies.add(new Company("SLB", OTHER, DOLLAR));
        companies.add(new Company("DPS", OTHER, DOLLAR));
        companies.add(new Company("COH", OTHER, DOLLAR));

        return companies;
    }


    private static List<Company> getRetailCompanies() {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("TIF", RETAIL, DOLLAR));
        companies.add(new Company("EBAY", RETAIL, DOLLAR));
        companies.add(new Company("WMT", RETAIL, DOLLAR));
        companies.add(new Company("DG", RETAIL, DOLLAR));
        companies.add(new Company("DLTR", RETAIL, DOLLAR));
        companies.add(new Company("AMZN", RETAIL, DOLLAR));
        companies.add(new Company("NKE", RETAIL, DOLLAR));
        companies.add(new Company("COST", RETAIL, DOLLAR));

        return companies;
    }

    private static List<Company> getTelecommunicationCompanies() {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("QCOM", TELECOMMUNICATION, DOLLAR));
        companies.add(new Company("VZ", TELECOMMUNICATION, DOLLAR));
        companies.add(new Company("T", TELECOMMUNICATION, DOLLAR));

        return companies;
    }

    private static List<Company> getTransportCompanies() {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("DAL", TRANSPORT, DOLLAR));

        return companies;
    }

    private static List<Company> getPetrochemicalCompanies() {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("COP", PETROCHEMICAL, DOLLAR));
        companies.add(new Company("APC", PETROCHEMICAL, DOLLAR));
        companies.add(new Company("CXO", PETROCHEMICAL, DOLLAR));
        companies.add(new Company("EOG", PETROCHEMICAL, DOLLAR));
        companies.add(new Company("APA", PETROCHEMICAL, DOLLAR));
        companies.add(new Company("DOW", PETROCHEMICAL, DOLLAR));
        companies.add(new Company("XOM", PETROCHEMICAL, DOLLAR));

        return companies;
    }

    private static List<Company> getMountainCompanies() {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("NEM", MOUNTAIN, DOLLAR));

        return companies;
    }


    private static List<Company> getFoodUsaCompanies() {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("SBUX", FOOD, DOLLAR));
        companies.add(new Company("MCD", FOOD, DOLLAR));
        companies.add(new Company("KO", FOOD, DOLLAR));
        companies.add(new Company("PEP", FOOD, DOLLAR));

        return companies;
    }

    private static List<Company> getMechanicalEngineeringUsaCompanies() {
        List<Company> companies = new ArrayList<Company>();

        companies.add(new Company("F", MECHANICAL_ENGINEERING, DOLLAR));
        companies.add(new Company("EMR", MECHANICAL_ENGINEERING, DOLLAR));
        companies.add(new Company("AME", MECHANICAL_ENGINEERING, DOLLAR));
        companies.add(new Company("COL", MECHANICAL_ENGINEERING, DOLLAR));
        companies.add(new Company("DE", MECHANICAL_ENGINEERING, DOLLAR));
        companies.add(new Company("ETN", MECHANICAL_ENGINEERING, DOLLAR));
        companies.add(new Company("CMI", MECHANICAL_ENGINEERING, DOLLAR));
        companies.add(new Company("BA", MECHANICAL_ENGINEERING, DOLLAR));
        companies.add(new Company("GE", MECHANICAL_ENGINEERING, DOLLAR));
        companies.add(new Company("CAT", MECHANICAL_ENGINEERING, DOLLAR));

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

    private static List<Company> getEnergeticUsaCompanies() {
        List<Company> companies = new ArrayList<>();

        companies.add(new Company("NRG", ENERGETIC, DOLLAR));
        companies.add(new Company("FSLR", ENERGETIC, DOLLAR));
        companies.add(new Company("CVX", ENERGETIC, DOLLAR));
        companies.add(new Company("VLO", ENERGETIC, DOLLAR));
        companies.add(new Company("EXC", ENERGETIC, DOLLAR));

        return companies;
    }


}
