package com.spark.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.spark.service.hbase.pojo.Point;
import com.spark.service.hbase.pojo.PointEventType;
import com.spark.service.hbase.pojo.WebRequest;
import com.spark.service.spark.service.WebRequestRddService;
import com.zuipin.action.listener.RemoveSessionCall;
import com.zuipin.pojo.LoginInOutHBase;
import com.zuipin.solr.DocumentFactory;
import com.zuipin.solr.PathConfig;
import com.zuipin.util.BeanUtils;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;

@Service
public class RequestCacheFactory {
	
	private final static Log											log					= LogFactory.getLog(RequestCacheFactory.class);
	// private final ConcurrentHashMap<String, List<WebRequest>> requests = new ConcurrentHashMap<String, List<WebRequest>>();
	private final ExecutorService										executorService		= Executors.newFixedThreadPool(200);
	private final ConcurrentHashMap<String, Map<String, WebRequest>>	requests			= new ConcurrentHashMap<String, Map<String, WebRequest>>();
	private final ConcurrentHashMap<String, WebRequest>					loginInOutHash		= new ConcurrentHashMap<String, WebRequest>();
	private final ConcurrentHashMap<String, Long>						lastRequestUserId	= new ConcurrentHashMap<String, Long>();
	private final ConcurrentHashMap<String, Date>						lastRequestTime		= new ConcurrentHashMap<String, Date>();
	private final ConcurrentHashMap<String, String>						uuidToSeIdMap		= new ConcurrentHashMap<String, String>();
	
	public void put(WebRequest request) {
		String key = String.format("%s%s", request.getUrl(), request.getPageid());
		if (!loginInOutHash.containsKey(request.getSessionId())) {
			if (StringUtils.isNotBlank(request.getTitle())) {
				loginInOutHash.putIfAbsent(request.getSessionId(), request);
			}
		}
		if (!requests.containsKey(request.getSessionId())) {
			if (StringUtils.isNotBlank(request.getTitle())) {
				requests.putIfAbsent(request.getSessionId(), new HashMap<String, WebRequest>());
			}
		}
		Map<String, WebRequest> map = requests.get(request.getSessionId());
		if (map.containsKey(key)) {
			WebRequest lastRequest = map.get(key);
			lastRequest.setCostTime(request.getAccessTime().getTime() - lastRequest.getAccessTime().getTime());
			Point newPoint = request.getPoints().isEmpty() ? null : request.getPoints().get(0);
			Point lastPoint = lastRequest.getLastPoint();
			if (lastPoint != null) {
				if (newPoint.getType() == PointEventType.Point) {
					if (!lastPoint.getX().equals(newPoint.getX()) || !lastPoint.getY().equals(newPoint.getY())) {
						lastRequest.setLastPoint(newPoint);
						lastRequest.getPoints().add(newPoint);
					} else {
						lastPoint.setLastTime(new Date());
					}
				}
			} else {
				lastRequest.setLastPoint(newPoint);
				lastRequest.getPoints().add(newPoint);
			}
			lastRequest.getProducts().addAll(request.getProducts());
		} else {
			if (StringUtils.isNotBlank(request.getTitle())) {
				request.setIsFirstPage(true);
				String referer = request.getReferrer().trim();
				if (referer.length() > 0) {
					request.setRefererDomain(getDomain(referer));
				}
				map.put(key, request);
			}
		}
		lastRequestUserId.put(request.getSessionId(), request.getUserId());
		lastRequestTime.put(request.getSessionId(), request.getAccessTime());
	}
	
	/**
	 * @param map
	 *            重新调整访问者id数据
	 * @param sessionId
	 */
	public Collection<WebRequest> changePropertyOfRequest(Map<String, WebRequest> map, String sessionId) {
		Long userId = lastRequestUserId.remove(sessionId);
		if (userId.intValue() != 0) {
			for (WebRequest request : map.values()) {
				request.setUserId(userId);
			}
			loginInOutHash.get(sessionId).setUserId(userId);
		}
		return map.values();
	}
	
	public void saveToRdd(List<WebRequest> requests) {
		WebRequestRddService webRequestRddService = SpringContextHolder.getBean("webRequestRddService");
		webRequestRddService.appendWebrequestRdd(requests);
	}
	
	public void saveToHBase(Collection<WebRequest> requests, String sessionId) throws IOException {
		
		WebRequest first = loginInOutHash.remove(sessionId);
		getUuidToSeIdMap().remove(first.getUuid());
		LoginInOutHBase in = new LoginInOutHBase();
		BeanUtils.copyProperties(in, first);
		in.makeKey();
		int size = requests.size();
		in.setPv(size);
		Date lastTime = lastRequestTime.remove(sessionId);
		in.setOutTime(lastTime);
		in.setCostTime(lastTime.getTime() - first.getAccessTime().getTime());
		saveIndex(requests, in);
		
	}
	
	/**
	 * @param requests2
	 * @param in
	 */
	private void saveIndex(Collection<WebRequest> requests2, LoginInOutHBase in) {
		SolrInputDocument parent = in.getSolrDoc();
		List<SolrInputDocument> children = new ArrayList<SolrInputDocument>();
		for (WebRequest request : requests2) {
			request.makeKey();
			children.add(request.getSolrDoc());
		}
		parent.addChildDocuments(children);
		DocumentFactory.instance.appendDoc(parent, PathConfig.getWebRequestPath());
	}
	
	/**
	 * @param requests2
	 */
	private void saveWebRequestAndLoadProducts(Collection<WebRequest> requests2) {
		// WebRequestRepository webRequestRepository = SpringContextHolder.getBean("webRequestRepository");
		// webRequestRepository.addAll(requests2);
		// List<LoadProduct> loadProducts = new ArrayList<LoadProduct>();
		// for (WebRequest webRequest : requests2) {
		// List<LoadProduct> products = webRequest.getProducts();
		// loadProducts.addAll(products);
		// }
		// LoadProductRepository loadProductRepository = SpringContextHolder.getBean("loadProductRepository");
		// loadProductRepository.addAll(loadProducts);
	}
	
	public ConcurrentHashMap<String, Map<String, WebRequest>> getRequests() {
		return requests;
	}
	
	/**
	 * @param sessionId
	 *            session过期的时候
	 */
	public void removeSession(String sessionId) {
		executorService.submit(new RemoveSessionCall(sessionId));
		
	}
	
	public static String getDomain(String url) {
		url = url.replaceAll("http://(www.)?", "");
		url = url.substring(0, url.indexOf("/"));
		return url;
	}
	
	public static void main(String[] args) {
		// List<Integer> list = new ArrayList<Integer>();
		// for (int i = 0; i < 10; i++) {
		// list.add(i);
		// }
		String url = "http://p.yiqifa.com/l?l=ClsDkPPc5Qy8UQ446wbQgN6y6wK7pn4VNtPSpNM2W9sWYPUdp7MSYy4NP7KSMNMCNnUQNE4J69ecpn4VR5PSpNM2W5etgEB1kwoQgQy2WwLVfpMlRJ7oWpFqfEtdWpMLR9yV3Q4eKOosR96y!NtLWnjs496sRc4yYcgNYmPS3OKoWNjLWnK9UnsuUZgL18H_UmUmfcAsCQXAY8eEY8HSWnt7Wnzq1njlCJDdCZgVYj--";
		System.out.println(getDomain(url));
		System.out.println(getDomain("http://www.zuipin.tk/fewf/fewfe"));
		System.out.println(getDomain("http://a.zuipin.tk/fewf/fewfe"));
		System.out.println(getDomain("http://zuipin.tk/fewf/fewfe"));
	}
	
	public ConcurrentHashMap<String, String> getUuidToSeIdMap() {
		return uuidToSeIdMap;
	}
}
