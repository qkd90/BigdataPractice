package com.zuipin.pojo;

import java.util.Date;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.solr.common.SolrInputDocument;

import com.spark.service.hbase.pojo.WebRequest;
import com.zuipin.solr.PathConfig;
import com.zuipin.util.DateUtils;

public class LoginInOutHBase extends WebRequest {
	
	/**
	 * 
	 */
	private static final long				serialVersionUID	= -3995622059272668709L;
	@Id
	private String							id;
	private Date							outTime				= DateUtils.getDate();
	private Integer							pv					= 1;
	private Boolean							isParent			= true;
	@Transient
	private static ReentrantReadWriteLock	lock				= new ReentrantReadWriteLock();
	@Transient
	public static ReadLock					readLock			= lock.readLock();
	@Transient
	public static WriteLock					writeLock			= lock.writeLock();
	
	public final static class SearchFields {
		public final static String	OUTTIME			= "OUTTIME";
		public final static String	COSTTIME		= "COSTTIME";
		public final static String	REFERRER		= "REFERRER";
		public final static String	USERID			= "USERID";
		public final static String	ACCESSTIME		= "ACCESSTIME";
		public final static String	PV				= "PV";
		public final static String	ID				= "ID";
		public final static String	ISPARENT		= "ISPARENT";
		public final static String	SESSIONID		= "SESSIONID";
		public final static String	REFERERDOMAIN	= "REFERERDOMAIN";
		public final static String	V				= "V";
		public final static String	BROWSER			= "BROWSER";
		public final static String	SYSTEM			= "SYSTEM";
		public final static String	TITLE			= "TITLE";
		public final static String	TYPE			= "TYPE";
	}
	
	public SolrInputDocument getSolrDoc() {
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField(SearchFields.ACCESSTIME, getAccessTime());
		doc.addField(SearchFields.COSTTIME, getCostTime());
		doc.addField(SearchFields.OUTTIME, getOutTime());
		doc.addField(SearchFields.REFERRER, getReferrer());
		doc.addField(SearchFields.USERID, getUserId());
		doc.addField(SearchFields.PV, getPv());
		doc.addField(SearchFields.ID, getId());
		doc.addField(SearchFields.ISPARENT, getIsParent());
		doc.addField(SearchFields.SESSIONID, getSessionId());
		doc.addField(SearchFields.REFERERDOMAIN, getRefererDomain());
		// doc.addField(SearchFields.V, getV());
		doc.addField(SearchFields.BROWSER, getBrowser());
		doc.addField(SearchFields.SYSTEM, getSystem());
		doc.addField(SearchFields.TITLE, getTitle());
		doc.addField(SearchFields.TYPE, getType());
		return doc;
	}
	
	@Override
	public String getIndexPath() {
		// TODO Auto-generated method stub
		return PathConfig.getLoginInOutPath();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public void makeKey() {
		id = String.format("%20d%s", getUserId(), DateUtils.formatDate(getAccessTime()));
	}
	
	public Date getOutTime() {
		return outTime;
	}
	
	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}
	
	public Integer getPv() {
		return pv;
	}
	
	public void setPv(Integer pv) {
		this.pv = pv;
	}
	
	@Override
	public Boolean getIsParent() {
		// TODO Auto-generated method stub
		return isParent;
	}
	
}
