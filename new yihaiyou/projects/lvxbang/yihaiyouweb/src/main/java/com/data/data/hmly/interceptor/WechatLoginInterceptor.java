package com.data.data.hmly.interceptor;

import com.data.data.hmly.action.yihaiyou.UserConstants;
import com.data.data.hmly.enums.ThirdPartyUserType;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.lvxbang.ThirdPartyLonginService;
import com.data.data.hmly.service.user.ThirdPartyUserService;
import com.data.data.hmly.service.user.entity.ThirdPartyUser;
import com.data.data.hmly.service.user.vo.OpenLoginInfo;
import com.data.data.hmly.service.wechat.WechatService;
import com.data.data.hmly.util.Encryption;
import com.framework.struts.NotNeedLogin;
import com.gson.bean.UserInfo;
import com.gson.oauth.Oauth;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zuipin.util.CookieUtils;
import com.zuipin.util.PropertiesManager;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;

//import com.data.data.hmly.service.SysUserService;

public class WechatLoginInterceptor extends AbstractInterceptor {
    private static final long serialVersionUID = 511623997635017942L;
    public static final String FORWARD_URL = "_FORWARD_URL";
    public static final String USER_INFO_KEY = "_YHY_USER_INFO";
    private final Log log = LogFactory.getLog(this.getClass());
    @Resource
    private MemberService memberService;
    @Resource
    private ThirdPartyLonginService thirdPartyLonginService;
    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private WechatService wechatService;
    @Resource
    private ThirdPartyUserService thirdPartyUserService;

    @Override
    public String intercept(ActionInvocation invocation)
            throws Exception {
        // 是否必须登录
        if (invocation.getAction().getClass().isAnnotationPresent(NotNeedLogin.class)) {
            return invocation.invoke();
        }
        String methodStr = invocation.getProxy().getMethod();
        Method method = invocation.getAction().getClass().getMethod(methodStr);
        if (method.isAnnotationPresent(NotNeedLogin.class)) {
            return invocation.invoke();
        }

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Map session = invocation.getInvocationContext().getSession();
        if (request != null && request.getHeader("user-agent") != null && request.getHeader("user-agent").toLowerCase().contains("micromessenger")) {
            Member loginuser = (Member) session.get(UserConstants.CURRENT_LOGIN_USER);
            if (loginuser == null || loginuser.getId() == null) {
                return noLoginUser(invocation, request, response, session);
            } else {    // 更新cookie
                Cookie cookie = CookieUtils.getCookie(request, USER_INFO_KEY);
                if (cookie == null) {
                    String cookieValue = loginuser.getId() + "," + loginuser.getCurrLoginOpenId(); // 格式：用户标识 + "," + openId
                    cookieValue = Encryption.desEncrypt(cookieValue, USER_INFO_KEY);
                    CookieUtils.addCookie(request, response, USER_INFO_KEY, cookieValue, Integer.MAX_VALUE);
                }
            }
        }
        return invocation.invoke();
    }

    private String noLoginUser(ActionInvocation invocation, HttpServletRequest request, HttpServletResponse response, Map session) throws Exception {
        Cookie userInfoCookie = CookieUtils.getCookie(request, USER_INFO_KEY);  // 需要记录对应的openId
//        Cookie unionidC = null;
        if (userInfoCookie != null) {
            String cookieValue = Encryption.desDecrypt(userInfoCookie.getValue(), USER_INFO_KEY);
            log.warn(">> find cookie key=value: " + userInfoCookie.getName() + "=" + cookieValue); // 调试时使用，可改为info
            String[] cookieValueArray = cookieValue.split(","); // 格式：用户标识 + "," + openId
            Member member = memberService.get(Long.valueOf(cookieValueArray[0]));
            if (member != null) {
                member.setCurrLoginOpenId(cookieValueArray[1]);
                member.loginInfo();
                memberService.update(member);
                session.put(UserConstants.CURRENT_LOGIN_USER, member);
                return invocation.invoke();
            }
        }
        String code = request.getParameter("code");
        String appId = propertiesManager.getString("WEBCHAT_APP_ID");
        String appSecret = propertiesManager.getString("WEBCHAT_App_Secret");
        String localAddr = propertiesManager.getString("WEBCHAT_LOCAL_ADDR");
        // 重定向微信授权登录
        Oauth oauth = new Oauth(appId, appSecret);
        if (code == null) {
            log.info("重定向微信授权登录");
            String forwardUrl = getForwardUrl(request);
//                    session.put(FORWARD_URL, forwardUrl);
            String location = oauth.getCode(localAddr + forwardUrl);
            response.setStatus(301);
            response.setHeader("Location", location);
            return null;
        }
        // 微信用户同意授权后回调处理：进一步获取用户详细信息
        JSONObject json = JSONObject.fromObject(oauth.getToken(code));
        log.info(json);
        String openid = json.getString("openid");
        Long accountId = propertiesManager.getLong("WEBCHAT_ACCOUNT_ID");
        String token = wechatService.doGetTokenBy(accountId);
        UserInfo userInfo = null;
        OpenLoginInfo openLoginInfo = null;
        try {
            userInfo = com.gson.oauth.User.getUserInfo(token, openid);
            openLoginInfo = new OpenLoginInfo(openid, ThirdPartyUserType.weixin, userInfo.getNickname(), userInfo.getHeadimgurl(), userInfo.getUnionid());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
//                String accessToken = json.getString("access_token");
//                JSONObject userInfo = JSONObject.fromObject(oauth.getUserInfo(accessToken, openid));
//                String nickname = userInfo.getString("nickname");
        Member member = null;
        if (StringUtils.isBlank(userInfo.getUnionid())) {   // unionId没有时暂时处理方法，开通微信开放平台帐号后要先处理旧数据的unionId，否则会出现多个账号
            ThirdPartyUser thirdPartyUser = thirdPartyUserService.getByOpenIdAndType(openid, ThirdPartyUserType.weixin, accountId);
            if (thirdPartyUser != null) {
                member = memberService.get(thirdPartyUser.getUserId());
            }
        } else {
            member = memberService.findByUnionId(userInfo.getUnionid());
        }
        if (member != null) { // 存在，更新
            if (openLoginInfo != null) {
                member = thirdPartyLonginService.update(member, openLoginInfo, accountId);
            }
        } else {    // 不存在，新增
            member = thirdPartyLonginService.create(openLoginInfo, accountId);
        }
        member.setCurrLoginOpenId(openid);
        session.put(UserConstants.CURRENT_LOGIN_USER, member);
        if (userInfoCookie == null) {
            log.warn(">> not find cookie key: " + member.getId() + "," + openid); // 调试时使用，可改为info
        }
        // 写Cookie
        String cookieValue = member.getId() + "," + openid; // 格式：用户标识 + "," + openId
        cookieValue = Encryption.desEncrypt(cookieValue, USER_INFO_KEY);
        CookieUtils.addCookie(request, response, USER_INFO_KEY, cookieValue, Integer.MAX_VALUE);

        return invocation.invoke();
    }

    private String getForwardUrl(HttpServletRequest request) {
        StringBuilder url = new StringBuilder();
        url.append(request.getRequestURI());
        String paramInfo = getParamStr(request);
        if (StringUtils.isNotBlank(paramInfo)) {
            url.append("?").append(getParamStr(request));
        }
        StringBuffer srcUrl = request.getRequestURL();
        if (srcUrl != null && srcUrl.toString().indexOf("#") > -1) {
            String subUrl = srcUrl.substring(srcUrl.indexOf("#"));
            url.append(srcUrl);
        }
        return url.toString();
    }

    private String getParamStr(HttpServletRequest request) {
        StringBuffer sbArgs = new StringBuffer();
        Enumeration paramNames = request.getParameterNames();
        try {
            while (paramNames.hasMoreElements()) {
                String paramName = (String) paramNames.nextElement();
                String paramValue = URLEncoder.encode(request.getParameter(paramName), "UTF-8");
                sbArgs.append(paramName);
                sbArgs.append("=");
                sbArgs.append(paramValue);
                sbArgs.append("&");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sbArgs.length() > 0)
            sbArgs.deleteCharAt(sbArgs.length() - 1);
        return sbArgs.toString();
    }

}
