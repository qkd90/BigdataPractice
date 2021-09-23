package com.hmlyinfo.app.soutu.activity.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class ActivityUser extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 *
	 */
	private long activityId;

	/**
	 *
	 */
	private String name;

	/**
	 *
	 */
	private String sex;

	/**
	 *
	 */
	private String phone;

	/**
	 *
	 */
	private String openId;


	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	@JsonProperty
	public long getActivityId() {
		return activityId;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@JsonProperty
	public String getSex() {
		return sex;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@JsonProperty
	public String getPhone() {
		return phone;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@JsonProperty
	public String getOpenId() {
		return openId;
	}
}
