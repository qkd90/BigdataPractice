package com.hmlyinfo.app.soutu.scenicTicket.ctrip.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class TicketCtrip extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 携程门票产品ID
	 */
	private int ctripId;

	/**
	 * 资源ID
	 */
	private int ctripResourceId;

	/**
	 * 门票名称
	 */
	private String name;

	/**
	 * 市场价
	 */
	private int marketPrice;

	/**
	 * 携程价格
	 */
	private int price;

	/**
	 * 是否返现
	 */
	private String isReturnCash;

	/**
	 * 返现金额
	 */
	private int returnCashAmount;

	/**
	 * 门票所属景点在携程的景点编号
	 */
	private int ctripScenicId;

	/**
	 * 本地景点id
	 */
	private long scenicId;

	/**
	 * 门票所属景点名称
	 */
	private String scenicName;

	/**
	 * 门票资源类型: 0，无。1，特惠。2.单票。4.套票
	 */
	private int ticketType;

	/**
	 * 适用人群(成人:1 儿童:2 学生:4 老人:8 通用:16)
	 */
	private int peopleGroup;

	/**
	 * 景点所属国家编号
	 */
	private int countryId;

	/**
	 * 景点所属省编号
	 */
	private int provinceId;

	/**
	 * 景点所属景区编号
	 */
	private int districtId;

	@JsonProperty
	public int getCtripId() {
		return ctripId;
	}

	public void setCtripId(int ctripId) {
		this.ctripId = ctripId;
	}

	@JsonProperty
	public int getCtripResourceId() {
		return ctripResourceId;
	}

	public void setCtripResourceId(int ctripResourceId) {
		this.ctripResourceId = ctripResourceId;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public int getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(int marketPrice) {
		this.marketPrice = marketPrice;
	}

	@JsonProperty
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@JsonProperty
	public String getIsReturnCash() {
		return isReturnCash;
	}

	public void setIsReturnCash(String isReturnCash) {
		this.isReturnCash = isReturnCash;
	}

	@JsonProperty
	public int getReturnCashAmount() {
		return returnCashAmount;
	}

	public void setReturnCashAmount(int returnCashAmount) {
		this.returnCashAmount = returnCashAmount;
	}

	@JsonProperty
	public int getCtripScenicId() {
		return ctripScenicId;
	}

	public void setCtripScenicId(int ctripScenicId) {
		this.ctripScenicId = ctripScenicId;
	}

	@JsonProperty
	public long getScenicId() {
		return scenicId;
	}

	public void setScenicId(long scenicId) {
		this.scenicId = scenicId;
	}

	@JsonProperty
	public String getScenicName() {
		return scenicName;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}

	@JsonProperty
	public int getTicketType() {
		return ticketType;
	}

	public void setTicketType(int ticketType) {
		this.ticketType = ticketType;
	}

	@JsonProperty
	public int getPeopleGroup() {
		return peopleGroup;
	}

	public void setPeopleGroup(int peopleGroup) {
		this.peopleGroup = peopleGroup;
	}

	@JsonProperty
	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	@JsonProperty
	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	@JsonProperty
	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

}
