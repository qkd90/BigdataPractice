package com.data.data.hmly.fenxiao.interceptor;

import com.danga.MemCached.MemCachedClient;
import com.data.data.hmly.action.sys.LoginAction;
import com.data.data.hmly.action.wechat.WechatFilterAction;
import com.data.data.hmly.service.entity.User;
import com.framework.struts.AjaxCheck;
import com.framework.struts.NotNeedLogin;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zuipin.util.PropertiesManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import java.lang.reflect.Method;

public class LoginInterceptor extends AbstractInterceptor {

    /**
     *
     */
    private static final long serialVersionUID = 6676114038033329599L;
    private final Log log = LogFactory.getLog(LoginInterceptor.class);

    @Resource
    private MemCachedClient memCachedClient;
    @Resource
    private PropertiesManager propertiesManager;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {

        if (LoginAction.class == invocation.getAction().getClass() || WechatFilterAction.class == invocation.getAction().getClass()) {
            return goon(invocation);
        }
        if (invocation.getAction().getClass().isAnnotationPresent(NotNeedLogin.class)) {
            return invocation.invoke();
        }
        String methodStr = invocation.getProxy().getMethod();
        Method method = invocation.getAction().getClass().getMethod(methodStr);
        if (method.isAnnotationPresent(NotNeedLogin.class)) {
            return goon(invocation);
        }

        User user = (User) invocation.getInvocationContext().getSession().get("loginuser");
        String id = ServletActionContext.getRequest().getSession().getId();
        if (user == null) {
            return "nologin";
        } else {
            Boolean singleLogin = propertiesManager.getBoolean("SINGLE_LOGIN", true);
            if (singleLogin) {
                String sessionId = (String) memCachedClient.get(user.getId().toString());
                if (!id.equals(sessionId)) {
                    ServletActionContext.getRequest().getSession().invalidate();
                    return "nologin";
                }
            }
        }
        return goon(invocation);

    }

    private String goon(ActionInvocation invocation) throws Exception {
        try {
            return invocation.invoke();
        } catch (Exception e) {
            // TODO: handle exception
            log.error(e.getMessage(), e);
            Method[] methods = invocation.getAction().getClass().getMethods();
            String methodName = invocation.getProxy().getMethod();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    if (method.isAnnotationPresent(AjaxCheck.class)) {
                        return "errorjson";
                    }
                }
            }
            return "error";
        }
    }

}
