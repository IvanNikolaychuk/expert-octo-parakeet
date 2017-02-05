package com.core.db.dao;

import com.core.db.entity.company.Company;
import com.core.db.entity.statistic.CommonStatisticData;
import com.core.db.helper.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class StatisticDataDao extends AbstractDao<CommonStatisticData> {
    public CommonStatisticData getByCompanyName(Company companyName) {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession()) {

            return (CommonStatisticData) session.createCriteria(CommonStatisticData.class)
                    .add(Restrictions.eq("company", companyName))
                    .uniqueResult();
        }
    }
}
