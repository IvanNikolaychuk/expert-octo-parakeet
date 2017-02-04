package com.core.db.dao;

import com.core.db.entity.company.Company;
import com.core.db.entity.statistic.StatisticData;
import com.core.db.helper.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class StatisticDataDao {
    public StatisticData getByCompanyName(Company companyName) {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession()) {

            return (StatisticData) session.createCriteria(StatisticData.class)
                    .add(Restrictions.eq("company", companyName))
                    .uniqueResult();
        }
    }

    public void saveOrUpdate(StatisticData statisticData) {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(statisticData);
            transaction.commit();
        }
    }

    public void clearAll() {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            List<StatisticData> statisticDataList = session
                    .createCriteria(StatisticData.class)
                    .list();

            for (StatisticData statisticData : statisticDataList) {
                session.delete(statisticData);
            }
            transaction.commit();
        }
    }
}
