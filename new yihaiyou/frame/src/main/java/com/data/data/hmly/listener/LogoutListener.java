package com.data.data.hmly.listener;

import com.danga.MemCached.MemCachedClient;
import com.zuipin.util.SpringContextHolder;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class LogoutListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		MemCachedClient memCachedClient = SpringContextHolder.getBean("memCachedClient");
		memCachedClient.delete(se.getSession().getId());
	}
}
