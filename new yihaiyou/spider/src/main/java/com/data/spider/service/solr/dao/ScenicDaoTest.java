package com.data.spider.service.solr.dao;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.data.spider.service.pojo.Scenic;

public class ScenicDaoTest {

	private static ApplicationContext	ac;

	@Before
	public void setUp() throws Exception {
		ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	}

	@Test
	public void testFindByTitle() throws Exception {
		SolrScenicDao solrScenicDao = (SolrScenicDao) ac.getBean("solrScenicDao");
		List<Scenic> scenics = solrScenicDao.findByTitle("颐和园");
		for (Scenic scenic : scenics) {
			System.out.printf("%d %s %s", scenic.getId(), scenic.getName(), scenic.getAddress());
		}
	}

}
