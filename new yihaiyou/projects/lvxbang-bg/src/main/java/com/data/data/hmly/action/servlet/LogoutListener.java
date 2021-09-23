package com.data.data.hmly.action.servlet;

import com.danga.MemCached.MemCachedClient;
import com.data.data.hmly.service.entity.SysUser;
import com.zuipin.util.SpringContextHolder;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class LogoutListener implements HttpSessionListener {


    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        MemCachedClient memCachedClient = SpringContextHolder.getBean("memCachedClient");
        Object userObj = se.getSession().getAttribute("loginuser");
        if(userObj != null){
            SysUser user = (SysUser) userObj;
            memCachedClient.delete(user.getId().toString());
        }
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
