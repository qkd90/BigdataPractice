package com.data.data.hmly.service.area.request;

import com.zuipin.util.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by guoshijie on 2015/12/18.
 */
public class DestinationSearchRequest {


	private String name;
	private String orderColumn;
	private SolrQuery.ORDER orderType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
		Set<String> set = new HashSet<String>();
		if (StringUtils.isNotBlank(name)) {
			set.add(String.format("name:%s", name));
		}
		if (set.isEmpty()) {
			return "*:*";
		}
		StringBuilder builder = new StringBuilder();
		for (String param : set) {
			if (builder.length() > 0) {
				builder.append(" AND ");
			}
			builder.append(param);
		}

		return builder.toString();
	}
}
