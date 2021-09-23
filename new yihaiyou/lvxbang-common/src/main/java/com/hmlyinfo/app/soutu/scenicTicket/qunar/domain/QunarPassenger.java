package com.hmlyinfo.app.soutu.scenicTicket.qunar.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class QunarPassenger extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 *
	 */
	private long orderQunarId;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 身份账号
	 */
	private String idCard;

	/**
	 * 拼音
	 */
	private String pinyin;

	/**
	 * 护照
	 */
	private String passport;

	/**
	 * 台湾通行证
	 */
	private String taiwanPermit;

	/**
	 * 港澳通行证
	 */
	private String hkAndMacauPermit;

	/**
	 * 自定义项1
	 */
	private String userDefinedI;

	/**
	 * 自定义项2
	 */
	private String userDefinedIi;


	public void setOrderQunarId(long orderQunarId) {
		this.orderQunarId = orderQunarId;
	}

	@JsonProperty
	public long getOrderQunarId() {
		return orderQunarId;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@JsonProperty
	public String getIdCard() {
		return idCard;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	@JsonProperty
	public String getPinyin() {
		return pinyin;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	@JsonProperty
	public String getPassport() {
		return passport;
	}

	public void setTaiwanPermit(String taiwanPermit) {
		this.taiwanPermit = taiwanPermit;
	}

	@JsonProperty
	public String getTaiwanPermit() {
		return taiwanPermit;
	}

	public void setHkAndMacauPermit(String hkAndMacauPermit) {
		this.hkAndMacauPermit = hkAndMacauPermit;
	}

	@JsonProperty
	public String getHkAndMacauPermit() {
		return hkAndMacauPermit;
	}

	public void setUserDefinedI(String userDefinedI) {
		this.userDefinedI = userDefinedI;
	}

	@JsonProperty
	public String getUserDefinedI() {
		return userDefinedI;
	}

	public void setUserDefinedIi(String userDefinedIi) {
		this.userDefinedIi = userDefinedIi;
	}

	@JsonProperty
	public String getUserDefinedIi() {
		return userDefinedIi;
	}
}
