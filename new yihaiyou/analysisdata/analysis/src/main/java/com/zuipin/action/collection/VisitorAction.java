package com.zuipin.action.collection;

import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.Result;
import com.spark.service.RequestCacheFactory;
import com.spark.service.hbase.pojo.Click;
import com.spark.service.hbase.pojo.LoadProduct;
import com.spark.service.hbase.pojo.Point;
import com.spark.service.hbase.pojo.WebRequest;
import com.spark.service.hbase.pojo.WebRequestType;
import com.spark.service.spark.SparkFactory;
import com.spark.service.spark.receiver.MyRequestReceiver;
import com.zuipin.action.JxmallAction;
import com.zuipin.util.StringUtils;

import eu.bitwalker.useragentutils.UserAgent;

public class VisitorAction extends JxmallAction {
	/**
	 * 
	 */
	private static final long		serialVersionUID	= -8220282303816163965L;
	private static ExecutorService	executorService		= Executors.newCachedThreadPool();
	@Resource
	private RequestCacheFactory		requestCacheFactory;
	private final static Log		log					= LogFactory.getLog(VisitorAction.class);
	public static MyRequestReceiver	receiver			= new MyRequestReceiver();
	
	public Result collect() throws FileNotFoundException {
		WebRequest webRequest = makeWebRequest(getRequest());
		SparkFactory.instance.queue.add(webRequest);
		executorService.submit(new AnalysisRequestCall(webRequest, requestCacheFactory));
		// InputStream ips = getServletContext().getResourceAsStream("/blank.png");
		// StreamResult streamResult = new StreamResult(ips);
		// streamResult.setContentType("image/png");
		// streamResult.setAllowCaching(false);
		// return streamResult;
		return dispatch("/blank.png");
	}
	
	private WebRequest makeWebRequest(HttpServletRequest request) {
		
		WebRequest webRequest = new WebRequest();
		webRequest.setIp(StringUtils.getIpAddr(request));
		String uuid = request.getParameter("__kuuid");
		String sessionId = requestCacheFactory.getUuidToSeIdMap().get(uuid);
		if (sessionId == null) {
			sessionId = request.getSession(true).getId();
			requestCacheFactory.getUuidToSeIdMap().putIfAbsent(uuid, sessionId);
		}
		webRequest.setSessionId(sessionId);
		webRequest.setUuid(uuid);
		String domain = request.getParameter("domain");
		if (StringUtils.isNotBlank(domain)) {
			webRequest.setDomain(domain);
		}
		String url = request.getParameter("url");
		if (StringUtils.isNotBlank(url)) {
			webRequest.setUrl(url);
		}
		String referrer = request.getParameter("referrer");
		if (StringUtils.isNotBlank(referrer)) {
			webRequest.setReferrer(referrer);
		}
		String title = request.getParameter("title");
		if (StringUtils.isNotBlank(title)) {
			webRequest.setTitle(title);
		}
		String sh = request.getParameter("sh");
		if (StringUtils.isNotBlank(sh)) {
			webRequest.setSh(sh);
		}
		String sw = request.getParameter("sw");
		if (StringUtils.isNotBlank(sw)) {
			webRequest.setSw(sw);
		}
		String v = request.getParameter("_v");
		if (StringUtils.isNotBlank(v)) {
			webRequest.setV(v);
		}
		String cd = request.getParameter("cd");
		if (StringUtils.isNotBlank(cd)) {
			webRequest.setCd(cd);
		}
		String pageid = request.getParameter("_pageid");
		if (StringUtils.isNotBlank(pageid)) {
			webRequest.setPageid(pageid);
		}
		String userId = request.getParameter("_userid");
		if (StringUtils.isNotBlank(userId)) {
			webRequest.setUserId(Long.parseLong(userId));
			// if (userId.equals("137560")) {
			// log.info(webRequest.getSessionId() + "  " + userId + "  " + webRequest.getIp());
			// }
		}
		String lang = request.getParameter("lang");
		if (StringUtils.isNotBlank(lang)) {
			webRequest.setLang(lang);
		}
		String msg = request.getParameter("msg");
		if (StringUtils.isNotBlank(msg)) {
			webRequest.setMsg(msg);
		}
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		webRequest.setBrowser(userAgent.getBrowser().getName());
		webRequest.setSystem(userAgent.getOperatingSystem().getName());
		String product = request.getParameter("product");
		String click = request.getParameter("click");
		String mouse = request.getParameter("mouse");
		String cookieEnabled = request.getParameter("cookieEnabled");
		if (StringUtils.isNotBlank(cookieEnabled)) {
			webRequest.setCookieEnabled(Boolean.parseBoolean(cookieEnabled));
		}
		String type = request.getParameter("_type");
		if (StringUtils.isNotBlank(type)) {
			webRequest.setType(WebRequestType.getType(type).ordinal());
		}
		// webRequest.makeKey();
		if (!StringUtils.isBlank(product)) {
			String x = request.getParameter("x");
			String y = request.getParameter("y");
			LoadProduct pro = new LoadProduct();
			pro.setProductId(product);
			webRequest.getProducts().add(pro);
			pro.setUserId(webRequest.getUserId());
			pro.setType(webRequest.getType());
			pro.setUrl(webRequest.getUrl());
			pro.setX(Integer.parseInt(x));
			pro.setY(Integer.parseInt(y));
			pro.setWebRequestId(webRequest.getId());
			// pro.makeKey();
		} else if (!StringUtils.isBlank(mouse)) {
			Point point = new Point();
			String[] xy = mouse.split(",");
			if (xy.length == 2) {
				point.setX(new Float(xy[0]).intValue());
				point.setY(new Float(xy[1]).intValue());
				webRequest.getPoints().add(point);
				// webRequest.setX(String.valueOf(point.getX()));
				// webRequest.setY(String.valueOf(point.getY()));
			}
		} else if (!StringUtils.isBlank(click)) {
			if (!click.contains("undefined")) {
				Click cli = new Click();
				String[] xy = click.split(",");
				if (xy.length == 2) {
					cli.setX(new Float(xy[0]).intValue());
					cli.setY(new Float(xy[1]).intValue());
					webRequest.getClicks().add(cli);
					// webRequest.setX(String.valueOf(cli.getX()));
					// webRequest.setY(String.valueOf(cli.getY()));
				}
			} else {
				// log.info(product + " __ " + webRequest.getBrowser() + " __ " + webRequest.getSystem() + " __ " + webRequest.getUserId() + " __ " + webRequest.getSessionId() +
				// " "
				// + webRequest.getIp() + "  " + webRequest.getUrl() + "  " + mouse);
			}
		}
		log.debug(uuid + " " + product + " __ " + webRequest.getBrowser() + " __ " + webRequest.getSystem() + " __ " + webRequest.getUserId() + " __ " + webRequest.getSessionId()
				+ " " + webRequest.getIp() + "  " + webRequest.getUrl() + "  " + mouse);
		return webRequest;
	}
	
}
