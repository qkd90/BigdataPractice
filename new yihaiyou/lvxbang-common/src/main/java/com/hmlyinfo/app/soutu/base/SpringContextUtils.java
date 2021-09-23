package com.hmlyinfo.app.soutu.base;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <p>Title: SpringContextUtil.java</p>
 * <p/>
 * <p>Description: 获取spring context 工具类</p>
 * <p/>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p/>
 * <p>Company: </p>
 * <p/>
 * <p>Create Date:2012-8-22</p>
 *
 * @author zheng.yongfeng
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
		throws BeansException {
		context = applicationContext;
	}

	/**
	 * 根据名称获取容器中指定名称的bean
	 *
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

	/**
	 * 获取spring 容器上下文
	 *
	 * @return
	 */
	public ApplicationContext getApplicationContext() {
		return context;
	}
}

