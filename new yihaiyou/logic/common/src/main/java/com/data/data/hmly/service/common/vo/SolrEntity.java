package com.data.data.hmly.service.common.vo;

import com.data.data.hmly.service.common.entity.enums.SolrType;

/**
 * Created by guoshijie on 2015/12/21.
 */
public abstract class SolrEntity<T> {

	private SolrType type;
	private String typeid;

	public SolrEntity() {

	}

	public SolrEntity(T entity) {

	}

	public String getName() {
		return null;
	}

	public SolrType getType() {
		return type;
	}

	public String getTypeid() {
		return typeid;
	}
}
