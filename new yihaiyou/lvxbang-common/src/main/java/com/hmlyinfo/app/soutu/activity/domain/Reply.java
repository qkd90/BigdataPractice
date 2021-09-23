package com.hmlyinfo.app.soutu.activity.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

public class Reply extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 *
	 */
	private long adviceId;

	/**
	 *
	 */
	private long userId;

	/**
	 *
	 */
	private String content;


	public void setAdviceId(long adviceId) {
		this.adviceId = adviceId;
	}

	@JsonProperty
	public long getAdviceId() {
		return adviceId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonProperty
	public String getContent() {
		return content;
	}

	@JsonProperty
	public Date createtime() {
		return super.getCreateTime();
	}
}
