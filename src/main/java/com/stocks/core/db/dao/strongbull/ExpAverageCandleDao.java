package com.stocks.core.db.dao.strongbull;

import com.stocks.core.db.dao.AbstractDao;
import com.stocks.core.db.entity.company.Company;
import com.stocks.core.db.entity.statistic.stongbull.ExpAverageCandle;
import com.stocks.core.db.helper.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class ExpAverageCandleDao extends AbstractDao<ExpAverageCandle> {

    public ExpAverageCandle findByCandleId(int candleId) {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            return (ExpAverageCandle) session.createCriteria(ExpAverageCandle.class)
                    .add(Restrictions.eq("candle.id", candleId))
                    .uniqueResult();
        }   }
}
