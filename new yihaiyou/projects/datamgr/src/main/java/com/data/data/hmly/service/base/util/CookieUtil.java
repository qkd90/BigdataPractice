package com.data.data.hmly.service.base.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.data.data.hmly.service.base.UserInfo;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class CookieUtil {  

	// 保存cookie时的cookieName  
	private final static String cookieDomainName = "soutu-bgmgr";  
	// 设置cookie有效期
	private final static int cookieMaxAge = 30*24*60*60;  

	/**
	 * 保存Cookie到客户端
	 * Cookie内容为用户名、有效期、MD5加密后的密码
	 * @param user
	 * @param response
	 */
	public static void writeCookie(UserInfo userInfo, HttpServletResponse response) {
		try {
			// cookie的有效期  
			long validTime = System.currentTimeMillis() + (((long)cookieMaxAge) * 1000);  
			String cookieValue = userInfo.getLoginName() + ":" + validTime + ":" + userInfo.getPassword();  
			// 再一次对Cookie的值进行BASE64编码  
			String cookieValueBase64 = new String(Base64.encode(cookieValue.getBytes()));  

			// 开始保存Cookie  
			Cookie cookie = new Cookie(cookieDomainName, cookieValueBase64);  
			cookie.setMaxAge(cookieMaxAge);  
			cookie.setPath("/");  

			// 向客户端写入  
			response.addCookie(cookie);  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}  


	/**
	 * 读取Cookie
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 * @throws UnsupportedEncodingException
	 */
	public static UserInfo readCookie(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 根据cookieName取cookieValue  
			Cookie cookies[] = request.getCookies();  
			String cookieValue = null;  
			if(cookies != null){  
				for(int i= 0; i < cookies.length; i++) {
					if (cookieDomainName.equals(cookies[i].getName())) {  
						cookieValue = cookies[i].getValue();  
						break;  
					}  
				}
			}  

			// 如果cookieValue为空,返回,  
			if (cookieValue == null){  
				return null;  
			}  

			// 先得到的CookieValue进行Base64解码  
			String cookieValueAfterDecode = new String(Base64.decode(cookieValue), "utf-8");

			// 对解码后的值进行分拆,得到一个数组,如果数组长度不为3,就是非法登陆  
			String cookieValues[] = cookieValueAfterDecode.split(":");
			if (cookieValues.length != 3) {
				clearCookie(response);
				return null;
			}

			// 判断是否在有效期内,过期就删除Cookie  
			long validTimeInCookie = Long.parseLong(cookieValues[1]);
			if (validTimeInCookie < System.currentTimeMillis()) {
				clearCookie(response);
				return null;  
			}  

			// 取出cookie中的信息  
			UserInfo userInfo = new UserInfo();
			userInfo.setLoginName(cookieValues[0]);
			userInfo.setPassword(cookieValues[2]);
			return userInfo;  
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}  

	/**
	 * 清除Cookie
	 * @param response
	 */
	public static void clearCookie( HttpServletResponse response){  
		Cookie cookie = new Cookie(cookieDomainName, null);  
		cookie.setMaxAge(0);  
		cookie.setPath("/");  
		response.addCookie(cookie);  
	}  


} 
