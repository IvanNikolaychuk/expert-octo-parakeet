package com.stocks.technical.core.db.dao;

import com.stocks.livermor.entity.Record;
import com.stocks.technical.core.db.helper.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class RecordDao extends AbstractDao<Record> {
    public List<Record> getAll(String ticker) {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            return session
                    .createCriteria(getObjectClass())
                    .add(Restrictions.eq("ticker", ticker))
                    .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                    .list();
        }
    }
}
