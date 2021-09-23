package com.data.data.hmly.interceptor;

import com.data.data.hmly.action.sys.LoginAction;
import com.data.data.hmly.service.entity.User;
import com.framework.struts.NotNeedLogin;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;

public class LoginInterceptor extends AbstractInterceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 511623997635017942L;

	private final Log log = LogFactory.getLog(this.getClass());

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		if (invocation.getAction().getClass().isAnnotationPresent(NotNeedLogin.class)) {
			return invocation.invoke();
		}
		String methodStr = invocation.getProxy().getMethod();
		Method method = invocation.getAction().getClass().getMethod(methodStr);
		if (method.isAnnotationPresent(NotNeedLogin.class)) {
			return invocation.invoke();
		}

		if (LoginAction.class == invocation.getAction().getClass()) {
			return invocation.invoke();
		}

		User user = (User) invocation.getInvocationContext().getSession().get("loginuser");
		if (user == null) {
			return "nologin";
		}
		// 的名称，在xml中配置的
		String namespace = invocation.getProxy().getNamespace(); // 获取到namespace，还能够获取到要执行的方法，class等
		if ((namespace != null) && (namespace.trim().length() > 0)) {
			if ("/".equals(namespace.trim())) {
				// 说明是根路径，不需要再增加反斜杠了。
			} else {
				namespace += "/";
			}
		}
		return invocation.invoke();
	}
}
