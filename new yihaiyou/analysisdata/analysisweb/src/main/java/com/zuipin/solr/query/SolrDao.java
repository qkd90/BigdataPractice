package com.zuipin.solr.query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.stereotype.Repository;

@Repository
public class SolrDao {
	private final static Log	log	= LogFactory.getLog(SolrDao.class);
	
	public QueryResponse find(SolrCriteria solrCriteria, SolrCriteriaFilter solrCriteriaFilter, SolrServer solrServer) {
		try {
			SolrQuery query = new SolrQuery();
			query.setQuery(solrCriteria.getQuery());
			if (solrCriteriaFilter != null) {
				query.setFilterQueries(solrCriteriaFilter.getQuery());
			}
			query.setFields(solrCriteria.getFl());
			query.setStart(solrCriteria.getPage().getFirstResult());
			query.setRows(solrCriteria.getPage().getPageSize());
			return solrServer.query(query, METHOD.POST);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
}
