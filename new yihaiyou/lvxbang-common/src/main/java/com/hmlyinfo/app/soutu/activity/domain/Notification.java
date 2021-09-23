package com.hmlyinfo.app.soutu.activity.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Fox on 2015-01-04,0004.
 */
public class Notification extends BaseEntity {

	public static int STATUS_IN_USE = 1;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 状态
	 */
	private int status;

	@JsonProperty
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonProperty
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
