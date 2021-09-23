package com.hmlyinfo.app.soutu.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.base.cache.CacheProvider;
import com.hmlyinfo.base.cache.XMemcachedImpl;
import com.hmlyinfo.base.util.Validate;

@Repository 
public class MemberInterceptor implements HandlerInterceptor {
	@Autowired
	private static CacheProvider saeMemcacheImpl = XMemcachedImpl.getInstance();
	@Autowired
	private MemberService memberService;
	
	@Override  
    public boolean preHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler) throws Exception {
		
		//取得需要的客户端ID和token和用户ID
		String clientId = request.getParameter("client_id");
		String accessToken = request.getParameter("access_token");
		String code = request.getParameter("user_id");
		String userId = request.getParameter("user_id");
		
		//判断是否空
		Validate.notNull(clientId, ErrorCode.ERROR_51003);
		Validate.notNull(accessToken, ErrorCode.ERROR_51003);
		Validate.notNull(code, ErrorCode.ERROR_51003);
		Validate.notNull(userId, ErrorCode.ERROR_51003);
		
		//判断该客户端和用户在token期限内是否有登陆
		Validate.isTrue(MemberService.getCurrentUserId() > 0, ErrorCode.ERROR_51002);
		
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
		
	}
}
