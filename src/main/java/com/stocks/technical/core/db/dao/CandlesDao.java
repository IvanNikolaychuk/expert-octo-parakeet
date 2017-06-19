package com.stocks.technical.core.db.dao;

import com.stocks.technical.core.db.entity.Candle;
import com.stocks.technical.core.db.helper.HibernateUtils;
import com.stocks.technical.tasks.utils.filters.CandlesFilter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class CandlesDao extends AbstractDao<Candle> {

    public void removeWithNoPattern() {
        List<Candle> candleList = getAll();
        List<Candle> noPattern = CandlesFilter.filterByPattern(candleList, null);

        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            for (Candle candle : noPattern) {
                session.delete(candle);
            }
            transaction.commit();
        }
    }
}
