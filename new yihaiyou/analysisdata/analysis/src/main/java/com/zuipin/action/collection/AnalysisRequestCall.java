package com.zuipin.action.collection;

import java.util.concurrent.Callable;

import com.spark.service.RequestCacheFactory;
import com.spark.service.hbase.pojo.WebRequest;

public class AnalysisRequestCall implements Callable<WebRequest> {
	private WebRequest			webRequest;
	private RequestCacheFactory	requestCacheFactory;
	
	public AnalysisRequestCall(WebRequest webRequest, RequestCacheFactory requestCacheFactory) {
		// TODO Auto-generated constructor stub
		this.webRequest = webRequest;
		this.requestCacheFactory = requestCacheFactory;
	}
	
	@Override
	public WebRequest call() throws Exception {
		requestCacheFactory.put(webRequest);
		return webRequest;
	}
	
}
