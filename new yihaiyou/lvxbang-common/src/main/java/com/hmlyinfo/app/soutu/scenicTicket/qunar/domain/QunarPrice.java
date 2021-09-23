package com.hmlyinfo.app.soutu.scenicTicket.qunar.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class QunarPrice extends BaseEntity {

	public static int PRICE_STATUS_VALID = 1;
	public static int PRICE_STATUS_MISS = 2;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 产品id
	 */
	private String productId;

	/**
	 * 价格id
	 */
	private String priceId;

	/**
	 * qunar价
	 */
	private double qunarPrice;

	/**
	 * 市场价
	 */
	private double marketPrice;

	/**
	 * 售价
	 */
	private double salePrice;

	/**
	 * 可用库存
	 */
	private int availableCount;

	/**
	 * 游玩日期，格式：yyyy-MM-dd
	 */
	private String useDate;

	/**
	 * 展示有效开始日期，格式：yyyy-MM-dd
	 */
	private String displayBeginDate;

	/**
	 * 展示有效结束日期，格式：yyyy-MM-dd
	 */
	private String displayEndDate;

	/**
	 * 最小购买张数
	 */
	private int minBuyCount;

	/**
	 * 最大购买张数
	 */
	private int maxBuyCount;

	/**
	 * 价格信息状态（1.有效 2.无效）
	 */
	private int status;


	public void setProductId(String productId) {
		this.productId = productId;
	}

	@JsonProperty
	public String getProductId() {
		return productId;
	}

	public void setPriceId(String priceId) {
		this.priceId = priceId;
	}

	@JsonProperty
	public String getPriceId() {
		return priceId;
	}

	public void setQunarPrice(double qunarPrice) {
		this.qunarPrice = qunarPrice;
	}

	@JsonProperty
	public double getQunarPrice() {
		return qunarPrice;
	}

	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}

	@JsonProperty
	public double getMarketPrice() {
		return marketPrice;
	}

	public void setAvailableCount(int availableCount) {
		this.availableCount = availableCount;
	}

	@JsonProperty
	public int getAvailableCount() {
		return availableCount;
	}

	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}

	@JsonProperty
	public String getUseDate() {
		return useDate;
	}

	public void setDisplayBeginDate(String displayBeginDate) {
		this.displayBeginDate = displayBeginDate;
	}

	@JsonProperty
	public String getDisplayBeginDate() {
		return displayBeginDate;
	}

	public void setDisplayEndDate(String displayEndDate) {
		this.displayEndDate = displayEndDate;
	}

	@JsonProperty
	public String getDisplayEndDate() {
		return displayEndDate;
	}

	public void setMinBuyCount(int minBuyCount) {
		this.minBuyCount = minBuyCount;
	}

	@JsonProperty
	public int getMinBuyCount() {
		return minBuyCount;
	}

	public void setMaxBuyCount(int maxBuyCount) {
		this.maxBuyCount = maxBuyCount;
	}

	@JsonProperty
	public int getMaxBuyCount() {
		return maxBuyCount;
	}

	@JsonProperty
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@JsonProperty
	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

}
