package com.data.data.hmly.action.mobile.interceptor;

import com.data.data.hmly.action.mobile.LoginMobileAction;
import com.data.data.hmly.action.user.UserConstans;
import com.data.data.hmly.action.wechat.WechatFilterMobileAction;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.mobile.MobileService;
import com.data.data.hmly.service.wechat.WechatAccountService;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.gson.oauth.Oauth;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zuipin.util.CookieUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;

public class LoginInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 511623997635017942L;
    @Resource
    private WechatAccountService wechatAccountService;
	@Resource
	private MobileService mobileService;
	public static final String COOKIENAME = "wxLogin_cookieId";

//	private final Log log = LogFactory.getLog(this.getClass());

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		if (LoginMobileAction.class == invocation.getAction().getClass() || WechatFilterMobileAction.class == invocation.getAction().getClass()) {
			return invocation.invoke();
		}

		User user = (User) invocation.getInvocationContext().getSession().get(UserConstans.CURRENT_LOGIN_USER);
		if (user == null) {		// 用户未登录，重定向到微信登陆服务器
			ActionContext context = ActionContext.getContext();   
			HttpServletRequest request = (HttpServletRequest) context.get(ServletActionContext.HTTP_REQUEST);   
			HttpServletResponse response = (HttpServletResponse) context.get(ServletActionContext.HTTP_RESPONSE);
			// 检查是否存在cookie
			Cookie cookie = CookieUtils.getCookie(request, COOKIENAME);
			if (cookie != null) {
				String value = cookie.getValue();
				try {
					value = URLDecoder.decode(value, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				String[] valArr = value.split("_");
				String accountId = valArr[0];
				String openId = valArr[1];
				// 判断openid是否已经存在，存在直接登录，不存在创建用户
				user = mobileService.doLogin(accountId, openId);
				WechatAccount account = wechatAccountService.get(Long.valueOf(accountId));
				context.getSession().put(UserConstans.CURRENT_LOGIN_USER, user);
				context.getSession().put(UserConstans.CURRENT_VIEW_ACCOUNTID, accountId);
				context.getSession().put(UserConstans.CURRENT_SUPPLIER_ID, account.getCompanyUnit().getId());
				return invocation.invoke();
			}

			// 设置用户登录前跳转链接
			invocation.getInvocationContext().getSession().put(UserConstans.FORWARD_URL, getForwardUrl(request));
			// 重定向到微信
			String accountId = (String) request.getParameter("accountId");
			WechatAccount wechatAccount = wechatAccountService.get(Long.valueOf(accountId));
			Oauth oauth = new Oauth(wechatAccount.getAppId(), wechatAccount.getAppSecret());
			StringBuilder redirectUri = new StringBuilder();
			String url = request.getRequestURL().toString();
			String uri = request.getRequestURI();
			if (StringUtils.isNotBlank(uri) && !"/".equals(uri)) {
				url = url.substring(0, url.indexOf(uri));
			}
			url = url + "/mobile/login/login.jhtml?accountId=" + accountId;
			redirectUri.append(request.getServerName());
			String location = oauth.getCode(url);
			response.setStatus(301); 
			response.setHeader("Location", location); 
			return null;
//			return "nologin";
		}
		// 的名称，在xml中配置的
		String namespace = invocation.getProxy().getNamespace(); // 获取到namespace，还能够获取到要执行的方法，class等
		if (StringUtils.isNotBlank(namespace)) {
			if ("/".equals(namespace.trim())) {
				// 说明是根路径，不需要再增加反斜杠了。
			} else {
				namespace += "/";
			}
		}
		return invocation.invoke();
	}

	//获取登陆前url
	private String getForwardUrl(HttpServletRequest request) {
		StringBuilder url = new StringBuilder();
		url.append(request.getRequestURI());

		String paramInfo = getParamStr(request);
		if (StringUtils.isNotBlank(paramInfo)) {
			url.append("?").append(getParamStr(request));
		}

		return url.toString();
	}
	
	private String getParamStr(HttpServletRequest request) {
		StringBuffer sbArgs = new StringBuffer();
		Enumeration<String> paramNames = request.getParameterNames();
		try {
			while (paramNames.hasMoreElements()) {
				String paramName = paramNames.nextElement();
				//				String paramValue = request.getParameter(paramName);
				//解决参数中文乱码问题
				String paramValue = URLEncoder.encode(request.getParameter(paramName), "UTF-8");

				sbArgs.append(paramName);
				sbArgs.append("=");
				sbArgs.append(paramValue);
				sbArgs.append("&");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (sbArgs.length() > 0) {
			sbArgs.deleteCharAt(sbArgs.length() - 1);
		}

		return sbArgs.toString();
	}
}
