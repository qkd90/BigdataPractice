package com.data.data.hmly.struts;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import org.apache.struts2.dispatcher.ServletDispatcherResult;

public class MobileServletDispatcherResult extends ServletDispatcherResult {
    private static final long serialVersionUID = -7704632649130134504L;
    
    @Override
    public void execute(ActionInvocation invocation) throws Exception {
        ActionProxy actionProxy = invocation.getProxy();
	    if (actionProxy.getNamespace().startsWith("mobile") || actionProxy.getActionName().startsWith("mobile")) {
		    super.doExecute(String.format("/WEB-INF/jsp/%s/%s.jsp", actionProxy.getNamespace(), actionProxy.getActionName()), invocation);
	    } else {
		    super.doExecute(String.format("/WEB-INF/jsp/mobile/%s/%s.jsp", actionProxy.getNamespace(), actionProxy.getActionName()), invocation);
	    }

    }
}
