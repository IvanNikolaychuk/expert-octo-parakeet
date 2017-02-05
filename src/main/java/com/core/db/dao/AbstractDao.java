package com.core.db.dao;

import com.core.db.helper.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AbstractDao<T> {
    public List<T> getAll() {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            return session
                    .createCriteria(getObjectClass())
                    .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                    .list();
        }
    }

    public void clearAll() {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            for (T t : getAll()) {
                session.delete(t);
            }
            transaction.commit();
        }
    }

    protected Class<T> getObjectClass() {
        final Type actualArgumentType = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        if (actualArgumentType instanceof ParameterizedType) {
            return (Class<T>) ((ParameterizedType) actualArgumentType).getRawType();
        }
        return (Class<T>) actualArgumentType;
    }

    public void saveOrUpdate(T obj) {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(obj);
            transaction.commit();
        }
    }

    public void save(Set<T> objects) {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            for (T obj : objects) {
                session.save(obj);
            }
            transaction.commit();
        }
    }

    public void update(List<T> objects) {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            for (T obj : objects) {
                session.update(obj);
            }
            transaction.commit();
        }
    }

}
