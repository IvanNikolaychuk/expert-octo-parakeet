package com.core.db.dao;

import com.core.db.entity.company.Company;
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
public class CompanyDao extends AbstractDao<Company> {


    public Company getByName(String name) {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession()) {

            return (Company) session.createCriteria(Company.class)
                    .add(Restrictions.eq("name", name))
                    .uniqueResult();
        }
    }

}
