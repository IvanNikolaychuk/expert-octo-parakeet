package com.core.db.dao;

import com.core.db.entity.statistic.CommonStatisticData;
import com.core.db.entity.statistic.InvestmentPeriodData;
import com.core.db.helper.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Set;

public class InvestmentPeriodDataDao {

    public void save(Set<InvestmentPeriodData> investmentPeriodDataList) {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            for (InvestmentPeriodData investmentPeriodData : investmentPeriodDataList) {
                session.save(investmentPeriodData);
            }

            transaction.commit();
        }
    }

    public void clearAll() {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            List<InvestmentPeriodData> investmentPeriodDataList = session
                    .createCriteria(InvestmentPeriodData.class)
                    .list();

            for (InvestmentPeriodData investmentPeriodData : investmentPeriodDataList) {
                session.delete(investmentPeriodData);
            }

            transaction.commit();
        }
    }
}
