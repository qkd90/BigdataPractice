package com.data.data.solr;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.springframework.data.solr.server.support.MulticoreSolrServerFactory;

public class MulticoreFactory extends MulticoreSolrServerFactory {

	public MulticoreFactory(SolrServer solrServer) {
		super(solrServer);
	}

	public MulticoreFactory(SolrServer solrServer, List<String> cores) {
		super(solrServer, cores);
	}

	@Override
	public SolrServer getSolrServer(String core) {
		// TODO Auto-generated method stub
		String coreStr = MulticoreSolrTemplate.THREAD_LOCAL.get();
		return super.getSolrServer(coreStr);
	}

}
