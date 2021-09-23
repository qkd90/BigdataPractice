package com.hmlyinfo.app.soutu.activity.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class Activity extends BaseEntity {

	public static int STATUS_RUNNING = 1;
	public static int STATUS_CLOSED = 2;

	private static final long serialVersionUID = 1L;

	private String name;
	private String description;
	private int status;

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty
	public String getDescription() {
		return description;
	}

	@JsonProperty
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
