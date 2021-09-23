package com.hmlyinfo.app.soutu.scenicTicket.custom.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class TicketCustom extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private long scenicId;

	/**
	 * 购买门票的链接（网页）
	 */
	private String url;

	/**
	 * 购买门票的链接名称（网页）
	 */
	private String linkName;

	/**
	 * 购买门票的链接（手机）
	 */
	private String urlM;

	/**
	 * 购买门票的链接名称（手机）
	 */
	private String linkMName;

	/**
	 * 购买门票的电话
	 */
	private String phone;

	/**
	 * 是否首要门票
	 */
	private String primaryFlag;

	/**
	 * 门票简介
	 */
	private String intro;

	/**
	 *
	 */
	private String ticketName;

	/**
	 *
	 */
	private double salePrice;

	/**
	 *
	 */
	private double marketPrice;

	/**
	 *
	 */
	private String status;

	/**
	 *
	 */
	private String parentFlag;

	public void setScenicId(long scenicId) {
		this.scenicId = scenicId;
	}

	@JsonProperty
	public long getScenicId() {
		return scenicId;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@JsonProperty
	public String getUrl() {
		return url;
	}

	@JsonProperty
	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	@JsonProperty
	public String getUrlM() {
		return urlM;
	}

	public void setUrlM(String urlM) {
		this.urlM = urlM;
	}

	@JsonProperty
	public String getLinkMName() {
		return linkMName;
	}

	public void setLinkMName(String linkMName) {
		this.linkMName = linkMName;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@JsonProperty
	public String getPhone() {
		return phone;
	}

	public void setPrimaryFlag(String primaryFlag) {
		this.primaryFlag = primaryFlag;
	}

	@JsonProperty
	public String getPrimaryFlag() {
		return primaryFlag;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	@JsonProperty
	public String getIntro() {
		return intro;
	}

	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}

	@JsonProperty
	public String getTicketName() {
		return ticketName;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	@JsonProperty
	public double getSalePrice() {
		return salePrice;
	}

	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}

	@JsonProperty
	public double getMarketPrice() {
		return marketPrice;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty
	public String getStatus() {
		return status;
	}

	@JsonProperty
	public String getParentFlag() {
		return parentFlag;
	}

	public void setParentFlag(String parentFlag) {
		this.parentFlag = parentFlag;
	}
}
