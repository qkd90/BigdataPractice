package com.hmlyinfo.app.soutu.signet.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class SignetRoad extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 *
	 */
	private long parentId;

	/**
	 *
	 */
	private String name;

	/**
	 *
	 */
	private String bookType;

	private transient List merchantIds;

	private transient List childIds;


	public void setparentId(long parentId) {
		this.parentId = parentId;
	}

	@JsonProperty
	public long getparentId() {
		return parentId;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	@JsonProperty
	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	@JsonProperty
	public List getMerchantIds() {
		return merchantIds;
	}

	public void setMerchantIds(List merchantIds) {
		this.merchantIds = merchantIds;
	}

	@JsonProperty
	public List getChildIds() {
		return childIds;
	}

	public void setChildIds(List childIds) {
		this.childIds = childIds;
	}
}
