package com.data.data.hmly.action.mobile.interceptor;

import com.data.data.hmly.action.user.UserConstans;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.wechat.WechatResourceService;
import com.data.data.hmly.service.wechat.WechatVisitLogService;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.data.data.hmly.service.wechat.entity.WechatResource;
import com.data.data.hmly.service.wechat.entity.WechatVisitLog;
import com.data.data.hmly.service.wechat.entity.enums.ResType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zuipin.util.GlobalTheadPool;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

public class VisitLogInterceptor extends AbstractInterceptor {
    private static final long serialVersionUID = 511623997635017942L;
    private final Log log = LogFactory.getLog(this.getClass());
    @Resource
    private WechatVisitLogService wechatVisitLogService;
    @Resource
    private WechatResourceService wechatResourceService;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        try {
            ActionContext actionContext = invocation.getInvocationContext();
            HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
            HttpSession session = request.getSession();
            String uri = request.getRequestURI();
            String ip = request.getRemoteAddr();
            log.info(" remote ip " + ip + " request uri " + uri);

            // 是否在资源配置中
            WechatResource wr = new WechatResource();
            wr.setContent(uri);
            wr.setResType(ResType.view);
            wr.setValidFlag(true);
            List<WechatResource> wechatResources = wechatResourceService.findWechatResource(wr);

            // 访问日志信息
            final WechatVisitLog wechatVisitLog = new WechatVisitLog();
            wechatVisitLog.setPath(uri);
            if (!wechatResources.isEmpty()) {
                WechatResource wechatResource = wechatResources.get(0);
                wechatVisitLog.setWechatResource(wechatResource);
                String resObjectValue = request.getParameter(wechatResource.getResObjectParam());
                wechatVisitLog.setResObjectValue(resObjectValue);
            }
            wechatVisitLog.setIp(ip);
            String accountId = (String) session.getAttribute(UserConstans.CURRENT_VIEW_ACCOUNTID);
            if (StringUtils.isBlank(accountId)) {
                accountId = request.getParameter("accountId");
            }
            if (StringUtils.isNotBlank(accountId)) {
                WechatAccount wechatAccount = new WechatAccount();
                wechatAccount.setId(Long.valueOf(accountId));
                wechatVisitLog.setWechatAccount(wechatAccount);
            }
            wechatVisitLog.setVisitTime(new Date());
            // TODO openId和userId取法还需调整，先从session获取
            User user = (User) session.getAttribute(UserConstans.CURRENT_LOGIN_USER);
            if (user != null) {
                wechatVisitLog.setOpenId(user.getAccount());
                wechatVisitLog.setUserId(user.getId());
            }
            // 更新操作
            GlobalTheadPool.instance.submit(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    wechatVisitLogService.save(wechatVisitLog);
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invocation.invoke();
    }

}
