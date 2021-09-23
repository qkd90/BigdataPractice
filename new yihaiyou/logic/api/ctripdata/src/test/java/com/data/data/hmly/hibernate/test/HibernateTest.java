package com.data.data.hmly.hibernate.test;

import com.data.data.hmly.service.ctripcommon.entity.CtripApiLog;
import junit.framework.TestCase;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.Ignore;

@Ignore
public class HibernateTest extends TestCase{

	public void testCreateTable() {
		Session session = new AnnotationConfiguration().configure().buildSessionFactory().openSession();
//		session.beginTransaction().begin();
//		session.save(employee);
		session.get(CtripApiLog.class, 1L);
//		session.beginTransaction().commit();
	}
}
