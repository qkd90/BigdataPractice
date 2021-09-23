package com.zuipin.action.listener;

import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.spark.service.RequestCacheFactory;
import com.zuipin.util.SpringContextHolder;

public class SessionListener implements HttpSessionListener {
	private final static Log		log		= LogFactory.getLog(SessionListener.class);
	public final static AtomicLong	counter	= new AtomicLong(0);
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		// TODO Auto-generated method stub
		log.debug(String.format("create session %s", event.getSession().getId()));
		counter.incrementAndGet();
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// TODO Auto-generated method stub
		counter.decrementAndGet();
		String id = event.getSession().getId();
		log.debug(String.format("destroy session %s", id));
		RequestCacheFactory requestCacheFactory = SpringContextHolder.getBean("requestCacheFactory");
		requestCacheFactory.removeSession(id);
	}
	
}
