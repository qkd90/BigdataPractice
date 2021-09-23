package com.framework.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.StringUtils;

@SuppressWarnings("serial")
public class RedirectResult implements Result {
    private String location;
    
    public RedirectResult(String location) {
        this.location = location;
    }
    
    public void execute(ActionInvocation invocation) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        if (isPathURL(location) && location.startsWith("/")) {
            String contextPath = request.getContextPath();
            if (StringUtils.hasText(contextPath)) {
                location = contextPath + location;
            }
        }
        location = response.encodeRedirectURL(location);
        response.sendRedirect(location);
    }
    
    boolean isPathURL(String url) {
        return (url.indexOf(':') == -1);
    }
}
