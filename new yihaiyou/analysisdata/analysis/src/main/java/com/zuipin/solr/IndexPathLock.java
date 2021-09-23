package com.zuipin.solr;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;

public enum IndexPathLock {
	User(PathConfig.getUserPath()), Sale(PathConfig.getSalePath()), WebRequest(PathConfig.getWebRequestPath());
	private String		path;
	private SolrServer	server;
	
	private IndexPathLock(String path) {
		// TODO Auto-generated constructor stub
		this.path = path;
		PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
		this.server = new HttpSolrServer(String.format(propertiesManager.getString("solr.master"), path));
	}
	
	public static IndexPathLock findIndexPathLock(String path) {
		for (IndexPathLock lock : values()) {
			if (lock.getPath().equals(path)) {
				return lock;
			}
		}
		return null;
	}
	
	public String getPath() {
		return path;
	}
	
	public SolrServer getServer() {
		return server;
	}
	
}
