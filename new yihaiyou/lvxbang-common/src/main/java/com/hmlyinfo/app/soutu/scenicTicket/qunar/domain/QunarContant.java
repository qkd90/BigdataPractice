package com.hmlyinfo.app.soutu.scenicTicket.qunar.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class QunarContant extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 *
	 */
	private long orderQunarId;

	/**
	 *
	 */
	private String mobile;

	/**
	 *
	 */
	private String name;

	/**
	 *
	 */
	private String pinyin;

	/**
	 *
	 */
	private String email;


	/**
	 *
	 */
	private String postalCode;

	/**
	 *
	 */
	private String postalInfo;

	private List<QunarPassenger> ywrArr;


	public void setOrderQunarId(long orderQunarId) {
		this.orderQunarId = orderQunarId;
	}

	@JsonProperty
	public long getOrderQunarId() {
		return orderQunarId;
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

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	@JsonProperty
	public String getPinyin() {
		return pinyin;
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
	public List<QunarPassenger> getYwrArr() {
		return ywrArr;
	}

	public void setYwrArr(List<QunarPassenger> ywrArr) {
		this.ywrArr = ywrArr;
	}

	@JsonProperty
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
}
