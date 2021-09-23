package com.data.data.hmly.service.restaurant.request;

import org.apache.solr.client.solrj.SolrQuery;

import java.util.HashSet;
import java.util.Set;

public class RestaurantSearchRequest {

    private String orderColumn;
    private SolrQuery.ORDER orderType;

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public SolrQuery.ORDER getOrderType() {
        return orderType == null ? SolrQuery.ORDER.desc : orderType;
    }

    public void setOrderType(SolrQuery.ORDER orderType) {
        this.orderType = orderType;
    }

    public String getSolrQueryStr() {
        StringBuilder builder = new StringBuilder();
        Set<String> set = new HashSet<String>();

	    if (set.isEmpty()) {
		    return "*:*";
	    }
	    for (String param : set) {
		    if (builder.length() > 0) {
			    builder.append(" AND ");
		    }
		    builder.append(param);
	    }
        return builder.toString();
    }
}