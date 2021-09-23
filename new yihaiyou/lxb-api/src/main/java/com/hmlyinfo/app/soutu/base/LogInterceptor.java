package com.hmlyinfo.app.soutu.base;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class LogInterceptor extends HandlerInterceptorAdapter
{
	private static Logger logger = LogManager.getLogger(HandlerInterceptorAdapter.class);
	private static final String API_START_KEY = "_api_start_key";

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception 
			{
		// 设置应用开始处理的事件
		request.setAttribute(API_START_KEY, new Date().getTime());
		
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		StringBuffer logSb = new StringBuffer();
		Long time = new Date().getTime() - (Long)request.getAttribute(API_START_KEY);
		logSb.append(request.getRemoteAddr()).append("\t").append(request.getRequestURI()).append("\t").append(time);
		
		logger.info(logSb.toString());
		
		super.postHandle(request, response, handler, modelAndView);
	}

	
}
