package com.data.data.hmly.service.base;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

public enum Solrconfig {

    //	SENIC("http://192.168.0.241:8983/solr/scenic/"),
//	SENICINFO("http://192.168.0.241:8983/solr/scenicInfo/");
    SENIC("http://110.80.136.201:8983/solr/scenic/"),
    SENICINFO("http://110.80.136.201:8983/solr/scenicInfo/");
    public String url;

    public SolrServer solrServer;

    private Solrconfig(String url) {
        // TODO Auto-generated constructor stub
        this.url = url;
        this.solrServer = new HttpSolrServer(url);
    }

}
