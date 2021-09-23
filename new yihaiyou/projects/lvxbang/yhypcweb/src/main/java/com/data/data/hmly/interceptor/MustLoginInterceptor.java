package com.data.data.hmly.interceptor;

import com.data.data.hmly.action.sys.LoginAction;
import com.data.data.hmly.action.yhypc.UserConstants;
import com.data.data.hmly.service.entity.Member;
import com.framework.struts.NeedLogin;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import java.lang.reflect.Method;

public class MustLoginInterceptor extends AbstractInterceptor {
    /**
     *
     */
    private static final long serialVersionUID = 511623997635017942L;


    @Override
    public String intercept(ActionInvocation invocation) throws Exception {

        Member user = (Member) invocation.getInvocationContext().getSession().get(UserConstants.CURRENT_LOGIN_USER);
//        Member user1 = (Member) ServletActionContext.getRequest().getSession().getAttribute(UserConstants.CURRENT_LOGIN_USER);
        if (invocation.getAction().getClass().isAnnotationPresent(NeedLogin.class) && user == null) {
            return "nologin";
        }
        String methodStr = invocation.getProxy().getMethod();
        Method method = invocation.getAction().getClass().getMethod(methodStr);
        if (method.isAnnotationPresent(NeedLogin.class) && user == null) {
            return "nologin";
        }

        if (LoginAction.class == invocation.getAction().getClass()) {
            return invocation.invoke();
        }

//		// 的名称，在xml中配置的
//		String namespace = invocation.getProxy().getNamespace(); // 获取到namespace，还能够获取到要执行的方法，class等
//		if ((namespace != null) && (namespace.trim().length() > 0)) {
//			if ("/".equals(namespace.trim())) {
//				// 说明是根路径，不需要再增加反斜杠了。
//			} else {
//				namespace += "/";
//			}
//		}
        return invocation.invoke();
    }
}
