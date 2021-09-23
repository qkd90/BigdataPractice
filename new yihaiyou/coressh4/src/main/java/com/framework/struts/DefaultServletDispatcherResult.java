package com.framework.struts;

import org.apache.struts2.dispatcher.ServletDispatcherResult;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;

public class DefaultServletDispatcherResult extends ServletDispatcherResult {
    private static final long serialVersionUID = -7704632649130134504L;
    
    @Override
    public void execute(ActionInvocation invocation) throws Exception {
        ActionProxy actionProxy = invocation.getProxy();
        String location = String.format("/WEB-INF/jsp/%s/%s.jsp", actionProxy.getNamespace(), actionProxy.getActionName());
        super.doExecute(location, invocation);
    }
}
