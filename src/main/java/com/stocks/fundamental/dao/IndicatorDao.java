package com.stocks.fundamental.dao;

import com.stocks.fundamental.entity.Index;
import com.stocks.technical.core.db.dao.AbstractDao;
import com.stocks.technical.core.db.helper.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * @author Ivan Nikolaichuk
 */
public class IndicatorDao extends AbstractDao<Index> {
}
