package com.zuipin.solr;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

public enum IndexPathLock {
	User(PathConfig.getUserPath()), Sale(PathConfig.getSalePath()), WebRequest(PathConfig.getWebRequestPath());
	private String				path;
	private SolrServer			server;
	private final static String	baseURL	= "http://master.khadoop.com:8983/solr/%s/";
	
	private IndexPathLock(String path) {
		// TODO Auto-generated constructor stub
		this.path = path;
		this.server = new HttpSolrServer(String.format(baseURL, path));
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
