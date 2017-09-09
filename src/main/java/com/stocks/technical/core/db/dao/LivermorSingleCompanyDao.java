package com.stocks.technical.core.db.dao;

import com.stocks.livermor.entity.LivermorSingleCompany;
import com.stocks.technical.core.db.helper.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class LivermorSingleCompanyDao extends AbstractDao<LivermorSingleCompany> {
    public LivermorSingleCompany getByKeyTicker(String ticker) {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession()) {

            return (LivermorSingleCompany) session.createCriteria(LivermorSingleCompany.class)
                    .add(Restrictions.eq("ticker", ticker))
                    .uniqueResult();
        }
    }

}
