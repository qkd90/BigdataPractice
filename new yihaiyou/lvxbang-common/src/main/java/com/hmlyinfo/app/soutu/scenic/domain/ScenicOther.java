package com.hmlyinfo.app.soutu.scenic.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class ScenicOther extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 景点id
	 */
	private long scenicInfoId;

	/**
	 * 简介
	 */
	private String introduction;

	/**
	 * 旅游建议
	 */
	private String advice;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 电话
	 */
	private String telephone;

	/**
	 * 网站地址
	 */
	private String website;

	/**
	 * 注意事项
	 */
	private String warning;

	/**
	 * 最佳旅游时间
	 */
	private String bestTime;

	/**
	 * 紧急医疗
	 */
	private String hospital;

	/**
	 * 当地风俗
	 */
	private String custom;

	/**
	 * 重点推荐
	 */
	private String recommend;


	public void setScenicInfoId(long scenicInfoId) {
		this.scenicInfoId = scenicInfoId;
	}

	public long getScenicInfoId() {
		return scenicInfoId;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	@JsonProperty
	public String getIntroduction() {
		return introduction;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	@JsonProperty
	public String getAdvice() {
		return advice;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@JsonProperty
	public String getAddress() {
		return address;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@JsonProperty
	public String getTelephone() {
		return telephone;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@JsonProperty
	public String getWebsite() {
		return website;
	}

	public void setWarning(String warning) {
		this.warning = warning;
	}

	@JsonProperty
	public String getWarning() {
		return warning;
	}

	public void setBestTime(String bestTime) {
		this.bestTime = bestTime;
	}

	@JsonProperty
	public String getBestTime() {
		return bestTime;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	@JsonProperty
	public String getHospital() {
		return hospital;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	@JsonProperty
	public String getCustom() {
		return custom;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	@JsonProperty
	public String getRecommend() {
		return recommend;
	}
}
