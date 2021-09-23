package com.zuipin.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class LogoutListener implements HttpSessionListener {
    
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // TODO Auto-generated method stub
        
    }
    // private Log log = LogFactory.getLog(IndexAction.class);
    //    
    // public void sessionCreated(HttpSessionEvent event) {
    // }
    //    
    // public void sessionDestroyed(HttpSessionEvent event) {
    // HttpSession session = event.getSession();
    //        
    // TMember member = (TMember) session.getAttribute(Constants.MEMBER);
    // String remarks = (String) session.getAttribute("remarks");
    // if (StringUtils.isBlank(remarks)) {
    // remarks = "异常退出";
    // }
    //        
    // LoginOutLogService loginOutLogService = (LoginOutLogService)
    // ContextUtil.getBean("loginOutLogService");
    // if (member != null) {
    // try {
    // loginOutLogService.loginOutLog(member, remarks);
    // } catch (Exception e) {
    // log.error(e.getMessage(), e);
    // }
    // }
    // }
}
