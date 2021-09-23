package com.zuipin.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @版权：象屿商城 版权所有 (c) 2012
 * @author:zhengry
 * @version Revision 2.0.0
 * @email:zryuan@xiangyu.cn
 * @see:
 * @创建日期：2013-9-23
 * @功能说明：Cookie辅助类
 */
public class CookieUtils {
	
//	private static String	cookieDomain;
	
	static {
//		PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
//		cookieDomain = propertiesManager.getCookieDomain();
	}
	
	/**
	 * @param request
	 * @param name
	 * @return
	 * @author:zhengry
	 * @email:zryuan@xiangyu.cn
	 * @创建日期:2012-8-3
	 * @功能说明：获得cookie
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie c : cookies) {
				if (c.getName().equals(name)) {
					return c;
				}
			}
		}
		return null;
	}
	
	/**
	 * @param request
	 * @param response
	 * @param name
	 * @param value
	 * @param expiry
	 * @return
	 * @author:zhengry
	 * @email:zryuan@xiangyu.cn
	 * @创建日期:2012-8-3
	 * @功能说明：创建cookie
	 */
	public static Cookie addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer expiry) {
		Cookie cookie = new Cookie(name, value);
		if (expiry != null) {
			cookie.setMaxAge(expiry);
		}
		cookie.setPath("/");
//		cookie.setDomain(cookieDomain);
		try {
			response.addCookie(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cookie;
	}
	
	/**
	 * @param request
	 * @param response
	 * @param name
	 * @author:zhengry
	 * @email:zryuan@xiangyu.cn
	 * @创建日期:2012-8-3
	 * @功能说明：删除cookie
	 */
	public static Cookie delCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		Cookie cookie = new Cookie(name, "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
//		cookie.setDomain(cookieDomain);
		response.addCookie(cookie);
		return cookie;
	}
	
	public static Cookie delCookie(HttpServletRequest request, HttpServletResponse response, String name, String domain) {
		Cookie cookie = new Cookie(name, "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		cookie.setDomain(domain);
		response.addCookie(cookie);
		return cookie;
	}
}
