package com.core.db.dao;

import com.core.db.entity.Candle;
import com.core.db.entity.company.Company;
import com.core.db.helper.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class CandlesDao {

    public List<Candle> getCandles(String companyName) {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            Company company = session.get(Company.class, companyName);

            return company.getCandles();
        }
    }
}
