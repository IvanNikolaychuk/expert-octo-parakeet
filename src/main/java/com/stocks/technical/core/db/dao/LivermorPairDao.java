package com.stocks.technical.core.db.dao;

import com.stocks.livermor.entity.LivermorPair;
import com.stocks.technical.core.db.helper.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class LivermorPairDao extends AbstractDao<LivermorPair> {
    public LivermorPair getByKeyTicker(String keyPriceTicker) {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession()) {

            return (LivermorPair) session.createCriteria(LivermorPair.class)
                    .add(Restrictions.eq("keyPriceTicker", keyPriceTicker))
                    .uniqueResult();
        }
    }

}
