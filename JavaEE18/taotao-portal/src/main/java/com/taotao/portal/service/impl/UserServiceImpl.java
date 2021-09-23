package com.taotao.portal.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;

/**
 * 用户管理 Service
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Value("${SSO_BASE_URL}")
	private String SSO_BASE_URL;
	@Value("${SSO_USER_TOKEN_SERVICE}")
	private String SSO_USER_TOKEN_SERVICE;
	

	@Override
	public TbUser getUserByToken(HttpServletRequest request, HttpServletResponse response) {
		try { 
			// 从 cookie 中取 token
			String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
			// 判断 token 是否有值
			if (StringUtils.isBlank(token)) {
				return null;
			}
			//调用 sso 的服务查询用户信息
			String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN_SERVICE + token);
			//把 json 转换成 java 对象
			TaotaoResult result = TaotaoResult.format(json);
			if (result.getStatus() != 200) {
				return null;
			}
			//取用户对象
			result = TaotaoResult.formatToPojo(json, TbUser.class);
			TbUser user = (TbUser) result.getData();
			
			return user;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
