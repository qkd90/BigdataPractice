package com.hmlyinfo.app.soutu.account.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hmlyinfo.app.soutu.account.domain.AccessToken;
import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.account.mapper.AccessTokenMapper;
import com.hmlyinfo.app.soutu.account.mapper.UserMapper;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.base.cache.CacheProvider;
import com.hmlyinfo.base.cache.XMemcachedImpl;
import com.hmlyinfo.base.util.Validate;


@Service
public class MemberService {
	
	@Autowired
	private AccessTokenMapper<AccessToken> tokenMapper;
	@Autowired
	private UserMapper<User> mapper;
	
	private static CacheProvider saeMemcacheImpl = XMemcachedImpl.getInstance();

	public static long getCurrentUserId(){
		
		
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		Validate.notNull(requestAttributes, ErrorCode.ERROR_51001);

		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		

		return getUserId(request.getParameter("access_token"), request.getParameter("client_id"), request.getParameter("user_id"), request.getParameter("code"));
		

	}
	
	/**
	 * 根据token判断是否已登录，若已登录，返回用户信息
	 * <ul>
	 *  <li>必选：token{token}</li>
	 * </ul>
	 * 
	 * @return 
	 */
	public static long getUserId(String accessToken, String clientId, String userId, String code)
	{
		long cachedUserId = 0L;
		String cacheKey = OAuthServerService.getCacheKey(clientId, userId, code);
		AccessToken token = saeMemcacheImpl.get(cacheKey);
		if (token != null && token.getToken().equals(accessToken))
		{
			cachedUserId = token.getUserId();
		}
		return cachedUserId;
		
	}
	
}
