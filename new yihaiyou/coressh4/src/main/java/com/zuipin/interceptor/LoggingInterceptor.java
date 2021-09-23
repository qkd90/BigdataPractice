package com.zuipin.interceptor;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zuipin.action.JxmallAction;
import com.zuipin.entity.Member;
import com.zuipin.util.Constants;
import com.zuipin.util.CookieUtils;
import com.zuipin.util.StringUtils;

@SuppressWarnings("serial")
public class LoggingInterceptor extends AbstractInterceptor {

	public static final Log		logger	= LogFactory.getLog(LoggingInterceptor.class);
	private final static String	ip		= "ip";
	private final static String	sid		= "sid";
	private final static String	uid		= "uid";
	private final static String	rurl	= "rurl";
	private final static String	referer	= "referer";
	private final static String	uname	= "uname";

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		return invokeWithLogging(invocation);
	}

	private String invokeWithLogging(ActionInvocation invocation) throws Exception {
		Date startTime = new Date();
		Member user = getCurrentUser(invocation);
		HttpServletRequest request = ServletActionContext.getRequest();
		String userId = "0";
		String userName = "unknown";
		try {
			if (user != null) {
				userId = user.getId().toString();
				userName = user.getName();
			} else {
				Cookie userIdCookie = CookieUtils.getCookie(request, Constants.MEMBER_INFO_NO);
				Cookie userNameCookie = CookieUtils.getCookie(request, Constants.LOGIN_NAME);
				if (userIdCookie != null) {
					userId = userIdCookie.getValue();
				}
				if (userNameCookie != null) {
					userName = userNameCookie.getValue();
				}
			}
			addLogContext(request, userId, userName);
			return invocation.invoke();
		} finally {
			try {
				// cleanLogContext();
				Date endTime = new Date();
				long usage = endTime.getTime() - startTime.getTime();
				logger.info(String.format("%dms Cost", usage));
			} catch (Exception e) {
				// ignore this exception
			}
		}
	}

	// private void cleanLogContext() {
	// // TODO Auto-generated method stub
	// MDC.remove(ip);
	// MDC.remove(sid);
	// MDC.remove(uid);
	// MDC.remove(rurl);
	// MDC.remove(referer);
	// }

	private void addLogContext(HttpServletRequest request, String userId, String userName) {
		MDC.put(ip, StringUtils.getIpAddr(request));
		MDC.put(sid, request.getSession(true).getId());
		MDC.put(uid, userId);
		MDC.put(uname, userName);
		String requestURI = request.getRequestURI();
		String queryString = request.getQueryString() == null ? "" : request.getQueryString();
		String requestURIWithQueryString = queryString != null && queryString.length() == 0 ? requestURI : requestURI + "?" + queryString;
		MDC.put(rurl, requestURIWithQueryString);
		String refererStr = "";
		Enumeration<?> headers = request.getHeaderNames();
		while (headers.hasMoreElements()) {
			String header = (String) headers.nextElement();
			if (header.toLowerCase().equals("referer")) {
				refererStr = request.getHeader(header);
				break;
			}
		}
		MDC.put(referer, refererStr);
	}

	private Member getCurrentUser(ActionInvocation invocation) {
		try {
			JxmallAction action = (JxmallAction) invocation.getAction();
			return action.getSessionMember();
		} catch (Exception e) {
			return null;
		}
	}

}
