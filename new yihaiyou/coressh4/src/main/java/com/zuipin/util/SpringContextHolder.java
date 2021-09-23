package com.zuipin.util;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @版权：象屿商城 版权所有 (c) 2012
 * @author Kingsley
 * @version Revision 2.0.0
 * @email:wxlong@xiangyu.cn
 * @see:
 * @创建日期：2012-9-23
 * @功能说明：spring上下文持有工具类
 */
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {
	
	private static ApplicationContext	applicationContext	= null;
	
	private final static Log			log					= LogFactory.getLog(SpringContextHolder.class);
	
	public static ServletContext		context;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		SpringContextHolder.applicationContext = applicationContext;
	}
	
	/**
	 * 实现DisposableBean接口,在Context关闭时清理静态变 ?
	 */
	public void destroy() throws Exception {
		SpringContextHolder.clear();
	}
	
	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		assertContextInjected();
		return applicationContext;
	}
	
	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋 ?对象的
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		assertContextInjected();
		return (T) applicationContext.getBean(name);
	}
	
	/**
	 * 清除SpringContextHolder中的ApplicationContext为Null.
	 */
	public static void clear() {
		log.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
		applicationContext = null;
	}
	
	private static void assertContextInjected() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext未注!请在applicationContext.xml中定义SpringContextHolder");
		}
	}
	
	public static String getReadPath() {
		return context.getRealPath("");
	}
	
	public static void setCtx(ServletContext context) {
		// TODO Auto-generated method stub
		SpringContextHolder.context = context;
	}
	
}
