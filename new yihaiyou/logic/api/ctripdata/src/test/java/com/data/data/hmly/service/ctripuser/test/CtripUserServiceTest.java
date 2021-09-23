package com.data.data.hmly.service.ctripuser.test;

import com.data.data.hmly.service.ctripuser.CtripUserService;
import junit.framework.TestCase;
import org.junit.Ignore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


@Ignore
public class CtripUserServiceTest extends TestCase {
	private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext*.xml") ;
	private CtripUserService ctripUserService = (CtripUserService) applicationContext.getBean("ctripUserService") ;
	
	@Override
	protected void tearDown() throws Exception {
		((ClassPathXmlApplicationContext)applicationContext).close();
	}

	/**
	 * 获取携程用户UserUniqueID
	 * @author caiys
	 * @date 2015年12月3日 下午1:43:29
	 */
/*	public void atestGetCtripUniqueUid() {
		String uidKey= "admin";
		String unqiueUid = ctripUserService.getCtripUniqueUid(uidKey);
		System.out.println(unqiueUid);
	}*/

	/**
	 * 获取本地携程用户UserUniqueID
	 * @author caiys
	 * @throws Exception 
	 * @date 2015年12月3日 下午1:43:29
	 */
	public void atestDoGetUniqueUid() throws Exception {
		String uidKey= "13817359271";
		String unqiueUid = ctripUserService.doGetUniqueUid(uidKey);
		System.out.println(unqiueUid);
	}
	
}
