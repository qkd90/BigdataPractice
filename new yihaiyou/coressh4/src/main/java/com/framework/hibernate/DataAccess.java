package com.framework.hibernate;

import com.framework.hibernate.util.Entity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

public class DataAccess<T extends Entity> extends BaseDataAccess<T> {
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		setHibernateTemplate(new HibernateTemplate(sessionFactory));
	}

	public void flush() {
		getHibernateTemplate().flush();
	}
}