package com.stocks.core.db.dao;

import com.stocks.core.db.entity.company.Company;
import com.stocks.core.db.helper.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class CompanyDao extends AbstractDao<Company> {


    public Company getByName(String name) {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession()) {

            return (Company) session.createCriteria(Company.class)
                    .add(Restrictions.eq("name", name))
                    .uniqueResult();
        }
    }

    public void remove(Company company) {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(company);
            transaction.commit();
        }
    }

}
