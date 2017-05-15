package com.stocks.core.db.dao;

import com.stocks.core.db.entity.company.Company;
import com.stocks.core.db.entity.statistic.VolumeStatisticData;
import com.stocks.core.db.helper.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class VolumeStatisticDataDao extends AbstractDao<VolumeStatisticData> {
    public VolumeStatisticData getByCompanyName(Company companyName) {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession()) {

            return (VolumeStatisticData) session.createCriteria(VolumeStatisticData.class)
                    .add(Restrictions.eq("company", companyName))
                    .uniqueResult();
        }
    }
}
