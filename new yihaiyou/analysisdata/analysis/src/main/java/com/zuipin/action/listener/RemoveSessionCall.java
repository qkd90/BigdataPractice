package com.zuipin.action.listener;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Callable;

import com.spark.service.RequestCacheFactory;
import com.spark.service.hbase.pojo.WebRequest;
import com.zuipin.util.SpringContextHolder;

/**
 * @author Kingsley 用户过期的操作, 保存到hbase当中，保存到rdd当中.
 */
public class RemoveSessionCall implements Callable<String> {
	private String	sessionId;
	
	public RemoveSessionCall(String sessionId) {
		// TODO Auto-generated constructor stub
		this.sessionId = sessionId;
	}
	
	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub
		RequestCacheFactory requestCacheFactory = SpringContextHolder.getBean("requestCacheFactory");
		Map<String, WebRequest> requests = requestCacheFactory.getRequests().remove(sessionId);
		if (requests != null && !requests.isEmpty()) {
			Collection<WebRequest> requestDetail = requestCacheFactory.changePropertyOfRequest(requests, sessionId);
			requestCacheFactory.saveToHBase(requestDetail, sessionId);
			// RequestCacheFactory.instance.saveToRdd(requests);
		}
		return sessionId;
	}
	
}
