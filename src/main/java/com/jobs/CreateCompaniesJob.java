package com.jobs;

import com.core.api.dto.StockData;
import com.core.db.entity.company.Company;
import com.core.db.helper.HibernateUtils;
import com.core.service.StockService;
import com.jobs.converter.StockDataToCandleConverter;
import com.jobs.data.CompanyStaticDataHolder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

import static com.core.db.entity.company.BusinessType.PHARMACEUTICS;
import static com.core.db.entity.company.StockCurrency.DOLLAR;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class CreateCompaniesJob {

    public void execute() {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            execute(session);
        }
    }

    public void execute(Session session) {
        List<Company> companies = CompanyStaticDataHolder.getCompanies(PHARMACEUTICS, DOLLAR);

        Transaction transaction = session.beginTransaction();

        for (Company company : companies) {
            List<StockData> stockDataList = queryStockDataForYear(company);
            company.addCandles(StockDataToCandleConverter.convert(stockDataList));
            session.save(company);
        }

        transaction.commit();
    }

    public List<StockData> queryStockDataForYear(Company company) {
        StockService stockService = new StockService();
        List<StockData> listOfStocks = stockService.queryStocks(company, 2016);
        listOfStocks.addAll(stockService.queryStocks(company, 2017));

        return listOfStocks;
    }


    public static void main(String[] args) {
        new CreateCompaniesJob().execute();
    }

}
