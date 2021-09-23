package com.data.data.hmly.service.common.request;

import org.apache.solr.client.solrj.SolrQuery;

/**
 * Created by guoshijie on 2015/12/21.
 */
public abstract class SolrSearchRequest {

	public abstract String getSolrQueryStr();

	public abstract String getOrderColumn();

	public abstract SolrQuery.ORDER getOrderType();

	public abstract Class getResultClass();
}
