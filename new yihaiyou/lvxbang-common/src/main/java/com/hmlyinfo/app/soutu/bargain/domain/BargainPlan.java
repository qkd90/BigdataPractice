package com.hmlyinfo.app.soutu.bargain.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class BargainPlan extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String name;
	private double salePrice;
	private double kidPrice;
	private double suggestPrice;
	private String supply;
	private String safeNotice;
	private String orderNotice;
	private String feature;
	private String description;
	private String route;
	private int buyCount;
	private int dayCount;
	private String cities;
	private String coverLarge;
	private String coverSmall;
	private boolean warn;
	private String warnContent;
	private transient List<List<BargainPlanTrip>> dayList;
	private transient List<BargainPlanGallery> galleryList;

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	@JsonProperty
	public double getSalePrice() {
		return salePrice;
	}

	public void setSuggestPrice(double suggestPrice) {
		this.suggestPrice = suggestPrice;
	}

	@JsonProperty
	public double getSuggestPrice() {
		return suggestPrice;
	}

	public void setSupply(String supply) {
		this.supply = supply;
	}

	@JsonProperty
	public String getSupply() {
		return supply;
	}

	public void setSafeNotice(String safeNotice) {
		this.safeNotice = safeNotice;
	}

	@JsonProperty
	public String getSafeNotice() {
		return safeNotice;
	}

	public void setOrderNotice(String orderNotice) {
		this.orderNotice = orderNotice;
	}

	@JsonProperty
	public String getOrderNotice() {
		return orderNotice;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	@JsonProperty
	public String getFeature() {
		return feature;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty
	public String getDescription() {
		return description;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	@JsonProperty
	public String getRoute() {
		return route;
	}

	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}

	@JsonProperty
	public int getBuyCount() {
		return buyCount;
	}

	public void setDayCount(int dayCount) {
		this.dayCount = dayCount;
	}

	@JsonProperty
	public int getDayCount() {
		return dayCount;
	}

	@JsonProperty
	public List<List<BargainPlanTrip>> getDayList() {
		return dayList;
	}

	public void setDayList(List<List<BargainPlanTrip>> dayList) {
		this.dayList = dayList;
	}

	@JsonProperty
	public double getKidPrice() {
		return kidPrice;
	}

	public void setKidPrice(double kidPrice) {
		this.kidPrice = kidPrice;
	}

	@JsonProperty
	public String getCities() {
		return cities;
	}

	public void setCities(String cities) {
		this.cities = cities;
	}

	@JsonProperty
	public String getCoverLarge() {
		return coverLarge;
	}

	public void setCoverLarge(String coverLarge) {
		this.coverLarge = coverLarge;
	}

	@JsonProperty
	public String getCoverSmall() {
		return coverSmall;
	}

	public void setCoverSmall(String coverSmall) {
		this.coverSmall = coverSmall;
	}

	@JsonProperty
	public List<BargainPlanGallery> getGalleryList() {
		return galleryList;
	}

	public void setGalleryList(List<BargainPlanGallery> galleryList) {
		this.galleryList = galleryList;
	}

	@JsonProperty
	public boolean isWarn() {
		return warn;
	}

	public void setWarn(boolean warn) {
		this.warn = warn;
	}

	@JsonProperty
	public String getWarnContent() {
		return warnContent;
	}

	public void setWarnContent(String warnContent) {
		this.warnContent = warnContent;
	}
}
