package com.stocks.core.db.helper;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


/**
 * Created by ivnikolaychuk on 03.02.2017
 */
public class HibernateUtils {
    public static SessionFactory getSessionFactory() {
        return new Configuration()
                .configure("hibernate/hibernate-cfg.xml")
                .buildSessionFactory();
    }
}
