package com.core.db.dao;

import com.core.db.entity.company.Company;
import com.core.db.entity.statistic.StatisticData;
import com.core.db.helper.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class StatisticDataDao {
    public StatisticData getByCompanyName(String companyName) {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession()) {

            return (StatisticData) session.createCriteria(StatisticData.class)
                    .add(Restrictions.eq("company.name", companyName))
                    .uniqueResult();
        }
    }
}
