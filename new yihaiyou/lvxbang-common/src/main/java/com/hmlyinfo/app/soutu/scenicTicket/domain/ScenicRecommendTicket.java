package com.hmlyinfo.app.soutu.scenicTicket.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Set;

public class ScenicRecommendTicket extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 景点编号
	 */
	private Long scenicId;

	private Set<Long> scenicIds;

	/**
	 * 单票门票编号
	 */
	private Long singleTicketId;

	/**
	 * 套票门票编号
	 */
	private Long seasoTicketId;

	/**
	 * 单票售价
	 */
	private double singleTicketPrice;

	/**
	 * 单票市场价
	 */
	private double sigleTicketMarketPrice;

	/**
	 * 套票售价
	 */
	private double seasonTicketPrice;

	/**
	 * 套票市场价
	 */
	private double seasonTicketMarketPrice;

	/**
	 * 单票名称
	 */
	private String singleTicketName;

	/**
	 * 套票名称
	 */
	private String seasonTicketName;

	/**
	 * 是否购买套票
	 */
	private boolean buySeasonFlag;

	/**
	 * 是否使用套票
	 */
	private boolean useSeasonFlag;

	@JsonProperty
	public Long getScenicId() {
		return scenicId;
	}

	public void setScenicId(Long scenicId) {
		this.scenicId = scenicId;
	}

	public Long getSingleTicketId() {
		return singleTicketId;
	}

	public void setSingleTicketId(Long singleTicketId) {
		this.singleTicketId = singleTicketId;
	}

	@JsonProperty
	public Long getSeasoTicketId() {
		return seasoTicketId;
	}

	public void setSeasoTicketId(Long seasoTicketId) {
		this.seasoTicketId = seasoTicketId;
	}

	public double getSingleTicketPrice() {
		return singleTicketPrice;
	}

	public void setSingleTicketPrice(double singleTicketPrice) {
		this.singleTicketPrice = singleTicketPrice;
	}

	@JsonProperty
	public double getSeasonTicketPrice() {
		return seasonTicketPrice;
	}

	public void setSeasonTicketPrice(double seasonTicketPrice) {
		this.seasonTicketPrice = seasonTicketPrice;
	}

	public String getSingleTicketName() {
		return singleTicketName;
	}

	public void setSingleTicketName(String singleTicketName) {
		this.singleTicketName = singleTicketName;
	}

	@JsonProperty
	public String getSeasonTicketName() {
		return seasonTicketName;
	}

	public void setSeasonTicketName(String seasonTicketName) {
		this.seasonTicketName = seasonTicketName;
	}

	public boolean isBuySeasonFlag() {
		return buySeasonFlag;
	}

	public void setBuySeasonFlag(boolean buySeasonFlag) {
		this.buySeasonFlag = buySeasonFlag;
	}

	public boolean isUseSeasonFlag() {
		return useSeasonFlag;
	}

	public void setUseSeasonFlag(boolean useSeasonFlag) {
		this.useSeasonFlag = useSeasonFlag;
	}

	public double getSigleTicketMarketPrice() {
		return sigleTicketMarketPrice;
	}

	public void setSigleTicketMarketPrice(double sigleTicketMarketPrice) {
		this.sigleTicketMarketPrice = sigleTicketMarketPrice;
	}

	@JsonProperty
	public double getSeasonTicketMarketPrice() {
		return seasonTicketMarketPrice;
	}

	public void setSeasonTicketMarketPrice(double seasonTicketMarketPrice) {
		this.seasonTicketMarketPrice = seasonTicketMarketPrice;
	}

	@JsonProperty
	public Set<Long> getScenicIds() {
		return scenicIds;
	}

	public void setScenicIds(Set<Long> scenicIds) {
		this.scenicIds = scenicIds;
	}
}
