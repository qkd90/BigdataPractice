package com.hmlyinfo.app.soutu.scenicTicket.qunar.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class QunarSight extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 景点id
	 */
	private long Id;

	/**
	 * 本地景点id
	 */
	private long scenicId;
	/**
	 * 景点名称
	 */
	private String name;

	/**
	 * 景点名称拼音
	 */
	private String namePinyin;

	/**
	 * 景区地址
	 */
	private String address;

	/**
	 * 景区所在城市
	 */
	private String city;

	/**
	 * 景区所在省份
	 */
	private String province;

	/**
	 * 景区所在国家
	 */
	private String country;

	/**
	 * 景区层级关系
	 */
	private String areaNamePath;


	public void setId(long Id) {
		this.Id = Id;
	}

	@JsonProperty
	public Long getId() {
		return Id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public void setNamePinyin(String namePinyin) {
		this.namePinyin = namePinyin;
	}

	@JsonProperty
	public String getNamePinyin() {
		return namePinyin;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@JsonProperty
	public String getAddress() {
		return address;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@JsonProperty
	public String getCity() {
		return city;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@JsonProperty
	public String getProvince() {
		return province;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@JsonProperty
	public String getCountry() {
		return country;
	}

	public void setAreaNamePath(String areaNamePath) {
		this.areaNamePath = areaNamePath;
	}

	@JsonProperty
	public String getAreaNamePath() {
		return areaNamePath;
	}

	@JsonProperty
	public long getScenicId() {
		return scenicId;
	}

	public void setScenicId(long scenicId) {
		this.scenicId = scenicId;
	}
}
