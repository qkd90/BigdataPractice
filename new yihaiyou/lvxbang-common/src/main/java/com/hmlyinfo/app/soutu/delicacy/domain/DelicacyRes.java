package com.hmlyinfo.app.soutu.delicacy.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class DelicacyRes extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 美食编号
	 */
	private long delicacyId;

	/**
	 * 餐厅编号
	 */
	private long resId;


	public void setDelicacyId(long delicacyId) {
		this.delicacyId = delicacyId;
	}

	@JsonProperty
	public long getDelicacyId() {
		return delicacyId;
	}

	public void setResId(long resId) {
		this.resId = resId;
	}

	@JsonProperty
	public long getResId() {
		return resId;
	}
}
