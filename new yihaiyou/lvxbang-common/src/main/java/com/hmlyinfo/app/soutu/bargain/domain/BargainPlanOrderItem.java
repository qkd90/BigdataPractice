package com.hmlyinfo.app.soutu.bargain.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class BargainPlanOrderItem extends BaseEntity {
	private static final long serialVersionUID = 1L;

	public static int TYPE_ADULT = 1;
	public static int TYPE_KID = 2;

	private long planOrderId;
	private int type;
	private String name;
	private int sex;
	private String mobile;
	private String idNo;

	public void setPlanOrderId(long planOrderId) {
		this.planOrderId = planOrderId;
	}

	@JsonProperty
	public long getPlanOrderId() {
		return planOrderId;
	}

	public void setType(int type) {
		this.type = type;
	}

	@JsonProperty
	public int getType() {
		return type;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	@JsonProperty
	public int getSex() {
		return sex;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@JsonProperty
	public String getMobile() {
		return mobile;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	@JsonProperty
	public String getIdNo() {
		return idNo;
	}
}
