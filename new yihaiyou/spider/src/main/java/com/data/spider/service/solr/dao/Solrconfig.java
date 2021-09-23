package com.data.spider.service.solr.dao;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

public enum Solrconfig {

//	SENIC("http://localhost:8983/solr/senic/");
	SENIC("http://110.80.136.201:8983/solr/scenic/");

	public String		url;

	public SolrServer	solrServer;

	private Solrconfig(String url) {
		// TODO Auto-generated constructor stub
		this.url = url;
		this.solrServer = new HttpSolrServer(url);
	}

}
