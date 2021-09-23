package com.data.data.hmly.action.other.util;

import com.zuipin.util.CookieUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

public class VisitHistoryCookieUtils {
//	private final Log log = LogFactory.getLog(this.getClass());
	public final static String COOKIENAME = "VisitHistory_cookieId";

	
	/**
	 * 获取（并更新）客户端的cookieId
	 * @author caiys
	 * @date 2015年12月22日 下午5:20:10
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getCookieId(HttpServletRequest request, HttpServletResponse response) {
		String cookieId = null;
		Cookie cookie = CookieUtils.getCookie(request, COOKIENAME);
		if (cookie == null) {	// 如果cookie不存在或者已经过期，新建cookie
			cookieId = UUID.randomUUID().toString();
			// 自设过期时间为90天，超过后重写cookie，cookie过期时间设置为180天，只要90天内有访问cookie就不会过期
			String expiryTime = String.valueOf(System.currentTimeMillis() + 90 * 24 * 60 * 60 * 1000L);
			String value = cookieId + "_" + expiryTime;
			try {
				value = URLEncoder.encode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			cookie = CookieUtils.addCookie(request, response, COOKIENAME, value, 180 * 24 * 60 * 60);
		} else {
			String value = cookie.getValue();
			try {
				value = URLDecoder.decode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String[] valArr = value.split("_");
			String expiryTime = valArr[1];
			long nowTime = System.currentTimeMillis();
			if (Long.parseLong(expiryTime) <= nowTime) {	// 自设过期时间已过期，重写cookie
				cookieId = UUID.randomUUID().toString();
				// 自设过期时间为90天，超过后重写cookie，cookie过期时间设置为180天，只要90天内有访问cookie就不会过期
				expiryTime = String.valueOf(nowTime + 90 * 24 * 60 * 60 * 1000L);
				value = cookieId + "_" + expiryTime;
				cookie = CookieUtils.addCookie(request, response, COOKIENAME, value, 180 * 24 * 60 * 60);
			} else {
				cookieId = valArr[0];
			}
		}
		return cookieId;
	}

}
