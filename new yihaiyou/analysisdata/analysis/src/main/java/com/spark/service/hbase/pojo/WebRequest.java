package com.spark.service.hbase.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import javax.persistence.Id;
import javax.persistence.Transient;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.beans.Field;
import org.apache.solr.common.SolrInputDocument;

import com.zuipin.solr.PathConfig;
import com.zuipin.util.DateUtils;

public class WebRequest implements Serializable {
	@Id
	private String							id;
	@Field
	protected String						sessionId		= "";
	protected String						pageid			= "";
	protected String						uuid			= "";
	private Integer							type			= 0;
	private Integer							bodyWidth		= 0;
	private Integer							bodyHeight		= 0;
	private Boolean							isFirstPage		= false;
	private String							refererDomain	= "";
	/**
	 * 默认为0
	 */
	private Long							userId			= 0L;
	private Integer							r				= 0;
	private Integer							f				= 0;
	private Integer							m				= 0;
	private String							userName		= "";
	private String							sh				= "", sw = "", cd = "";
	private String							domain			= "", lang = "", url = "", title = "", referrer = "", msg = "";
	private String							browser			= "", system = "";
	private Date							accessTime		= new Date();
	private Long							costTime		= 0l;
	private String							ip				= "";
	/**
	 * 所有的鼠标和点击事件放入points中
	 */
	private List<Point>						points			= new ArrayList<Point>();
	private Point							lastPoint;
	private List<Click>						clicks			= new ArrayList<Click>();
	private List<LoadProduct>				products		= new ArrayList<LoadProduct>();
	private String							x				= "", y = "";
	private Boolean							cookieEnabled	= true;
	private Boolean							isParent		= false;
	private String							v				= "";
	/**
	 * mouseover:0 click:1
	 */
	private String							pointType		= "";
	@Transient
	private static ReentrantReadWriteLock	lock			= new ReentrantReadWriteLock();
	@Transient
	public static ReadLock					readLock		= lock.readLock();
	@Transient
	public static WriteLock					writeLock		= lock.writeLock();
	
	// @Override
	// public WriteLock getWriteLock() {
	// // TODO Auto-generated method stub
	// return writeLock;
	// }
	
	public final static class SearchFields {
		public final static String	ID				= "ID";
		public final static String	OUTTIME			= "OUTTIME";
		public final static String	COSTTIME		= "COSTTIME";
		public final static String	USERID			= "USERID";
		public final static String	ACCESSTIME		= "ACCESSTIME";
		public final static String	URL				= "URL";
		public final static String	REFERRER		= "REFERRER";
		public final static String	ISPARENT		= "ISPARENT";
		public final static String	SESSIONID		= "SESSIONID";
		public final static String	V				= "V";
		public final static String	ISFIRSTPAGE		= "ISFIRSTPAGE";
		public final static String	REFERERDOMAIN	= "REFERERDOMAIN";
		public final static String	TYPE			= "TYPE";
		public final static String	POINTS			= "POINTS";
	}
	
	@Transient
	public SolrInputDocument getSolrDoc() {
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField(SearchFields.ID, getId());
		doc.addField(SearchFields.ACCESSTIME, getAccessTime());
		doc.addField(SearchFields.COSTTIME, getCostTime());
		doc.addField(SearchFields.USERID, getUserId());
		doc.addField(SearchFields.REFERRER, getReferrer());
		doc.addField(SearchFields.URL, getUrl());
		doc.addField(SearchFields.ISPARENT, getIsParent());
		doc.addField(SearchFields.SESSIONID, getSessionId());
		// doc.addField(SearchFields.V, getV());
		doc.addField(SearchFields.REFERERDOMAIN, getRefererDomain());
		doc.addField(SearchFields.ISFIRSTPAGE, getIsFirstPage());
		doc.addField(SearchFields.TYPE, getType());
		JSONArray array = new JSONArray();
		for (Point point : getPoints()) {
			JSONObject o = new JSONObject();
			o.put("s", point.getAccessTime().getTime());
			if (point.getLastTime() != null) {
				o.put("e", point.getLastTime().getTime());
			}
			o.put("t", point.getType().ordinal());
			o.put("x", point.getX());
			o.put("y", point.getY());
			array.add(o);
		}
		doc.addField(SearchFields.POINTS, array.toString());
		return doc;
	}
	
	public String getIndexPath() {
		return PathConfig.getWebRequestPath();
	}
	
	//
	public void makeKey() {
		id = String.format("%s%20d%s", DateUtils.formatDate(accessTime), userId, sessionId);
	}
	
	public String getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getSh() {
		return sh;
	}
	
	public void setSh(String sh) {
		this.sh = sh;
	}
	
	public String getSw() {
		return sw;
	}
	
	public void setSw(String sw) {
		this.sw = sw;
	}
	
	public String getCd() {
		return cd;
	}
	
	public void setCd(String cd) {
		this.cd = cd;
	}
	
	public String getDomain() {
		return domain;
	}
	
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public String getLang() {
		return lang;
	}
	
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getReferrer() {
		return referrer;
	}
	
	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}
	
	public String getBrowser() {
		return browser;
	}
	
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	
	public String getSystem() {
		return system;
	}
	
	public void setSystem(String system) {
		this.system = system;
	}
	
	public String getX() {
		return x;
	}
	
	public void setX(String x) {
		this.x = x;
	}
	
	public String getY() {
		return y;
	}
	
	public void setY(String y) {
		this.y = y;
	}
	
	public String getPointType() {
		return pointType;
	}
	
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}
	
	public void setMsg(String msg) {
		// TODO Auto-generated method stub
		this.msg = msg;
	}
	
	public String getMsg() {
		// TODO Auto-generated method stub
		return this.msg;
	}
	
	// public List<Point> getPoints() {
	// return points;
	// }
	//
	// public void setPoints(List<Point> points) {
	// this.points = points;
	// }
	//
	// public List<Click> getClicks() {
	// return clicks;
	// }
	//
	// public void setClicks(List<Click> clicks) {
	// this.clicks = clicks;
	// }
	//
	// public List<LoadProduct> getProducts() {
	// return products;
	// }
	//
	// public void setProducts(List<LoadProduct> products) {
	// this.products = products;
	// }
	
	public Boolean getCookieEnabled() {
		return cookieEnabled;
	}
	
	public void setCookieEnabled(Boolean cookieEnabled) {
		this.cookieEnabled = cookieEnabled;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public Long getCostTime() {
		return costTime;
	}
	
	public void setCostTime(Long costTime) {
		this.costTime = costTime;
	}
	
	public Integer getR() {
		return r;
	}
	
	public void setR(Integer r) {
		this.r = r;
	}
	
	public Integer getF() {
		return f;
	}
	
	public void setF(Integer f) {
		this.f = f;
	}
	
	public Integer getM() {
		return m;
	}
	
	public void setM(Integer m) {
		this.m = m;
	}
	
	public Boolean getIsParent() {
		return isParent;
	}
	
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	
	public String getV() {
		return v;
	}
	
	public void setV(String v) {
		this.v = v;
	}
	
	public String getPageid() {
		return pageid;
	}
	
	public void setPageid(String pageid) {
		this.pageid = pageid;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Boolean getIsFirstPage() {
		return isFirstPage;
	}
	
	public void setIsFirstPage(Boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}
	
	public String getRefererDomain() {
		return refererDomain;
	}
	
	public void setRefererDomain(String refererDomain) {
		this.refererDomain = refererDomain;
	}
	
	public Date getAccessTime() {
		return accessTime;
	}
	
	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}
	
	public List<Point> getPoints() {
		return points;
	}
	
	public void setPoints(List<Point> points) {
		this.points = points;
	}
	
	public List<LoadProduct> getProducts() {
		return products;
	}
	
	public void setProducts(List<LoadProduct> products) {
		this.products = products;
	}
	
	public List<Click> getClicks() {
		return clicks;
	}
	
	public void setClicks(List<Click> clicks) {
		this.clicks = clicks;
	}
	
	public Point getLastPoint() {
		return lastPoint;
	}
	
	public void setLastPoint(Point lastPoint) {
		this.lastPoint = lastPoint;
	}
	
	public Integer getBodyWidth() {
		return bodyWidth;
	}
	
	public void setBodyWidth(Integer bodyWidth) {
		this.bodyWidth = bodyWidth;
	}
	
	public Integer getBodyHeight() {
		return bodyHeight;
	}
	
	public void setBodyHeight(Integer bodyHeight) {
		this.bodyHeight = bodyHeight;
	}
	
}
