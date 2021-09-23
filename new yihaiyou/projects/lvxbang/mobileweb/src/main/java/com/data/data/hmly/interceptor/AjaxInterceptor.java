package com.data.data.hmly.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AjaxInterceptor extends AbstractInterceptor {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 511623997635017942L;

	private final Log			log					= LogFactory.getLog(this.getClass());

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		try {
			return invocation.invoke();
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
			return null;
		}
	}
}
