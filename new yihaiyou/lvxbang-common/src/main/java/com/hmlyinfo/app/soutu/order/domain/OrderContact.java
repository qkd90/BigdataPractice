package com.hmlyinfo.app.soutu.order.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class OrderContact extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 所属用户id
	 */
	private long userId;

	/**
	 * 电话
	 */
	private String mobile;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 邮寄信息
	 */
	private String postalInfo;

	/**
	 * 身份证
	 */
	private String idCard;

	/**
	 * 邮编
	 */
	private String postCode;


	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@JsonProperty
	public String getMobile() {
		return mobile;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty
	public String getEmail() {
		return email;
	}

	public void setPostalInfo(String postalInfo) {
		this.postalInfo = postalInfo;
	}

	@JsonProperty
	public String getPostalInfo() {
		return postalInfo;
	}

	@JsonProperty
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@JsonProperty
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
}
