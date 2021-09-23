package com.hmlyinfo.app.soutu.delicacy.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class Restaurant extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 该餐厅在大众点评上的shopId
	 */
	private Long dianpingId;

	/**
	 * 餐厅名
	 */
	private String resName;


	/**
	 * 价格
	 */
	private int price;

	/**
	 * 餐厅价格
	 */
	private String resPrice;

	/**
	 * 餐厅地址
	 */
	private String resAddress;

	/**
	 * 餐厅电话
	 */
	private String resPhone;

	/**
	 * 餐厅特色
	 */
	private String resFeature;

	/**
	 * 餐厅图片
	 */
	private String resPicture;

	/**
	 * 餐厅图片
	 */
	private String resLongitude;

	/**
	 * 餐厅图片
	 */
	private String resLatitude;

	/**
	 *
	 */
	private double gcjLng;

	/**
	 *
	 */
	private double gcjLat;

	/**
	 * 城市编号
	 */
	private int cityCode;
	/**
	 * 评分
	 */
	private String score;

	/**
	 * 热度
	 */
	private int hotNum;

	/**
	 * 餐厅评论
	 */
	private String resComment;

	/**
	 * 营业时间
	 */
	private String shopHours;

	/**
	 * 该餐厅在大众点评上的评论
	 */
	private String dianpingComment;

	private transient int distance;


	@JsonProperty
	public Long getDianpingId() {
		return dianpingId;
	}

	public void setDianpingId(Long dianpingId) {
		this.dianpingId = dianpingId;
	}

	@JsonProperty
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	@JsonProperty
	public String getResLongitude() {
		return resLongitude;
	}

	public void setResLongitude(String resLongitude) {
		this.resLongitude = resLongitude;
	}

	@JsonProperty
	public String getResLatitude() {
		return resLatitude;
	}

	public void setResLatitude(String resLatitude) {
		this.resLatitude = resLatitude;
	}

	@JsonProperty
	public String getResComment() {
		return resComment;
	}

	public void setResComment(String resComment) {
		this.resComment = resComment;
	}

	@JsonProperty
	public String getComment() {
		return resComment;
	}

	public void setComment(String comment) {
		this.resComment = comment;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	@JsonProperty
	public String getResName() {
		return resName;
	}

	@JsonProperty
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setResPrice(String resPrice) {
		this.resPrice = resPrice;
	}

	@JsonProperty
	public String getResPrice() {
		return resPrice;
	}

	public void setResAddress(String resAddress) {
		this.resAddress = resAddress;
	}

	@JsonProperty
	public String getResAddress() {
		return resAddress;
	}

	@JsonProperty
	public String getResPhone() {
		return resPhone;
	}

	public void setResPhone(String resPhone) {
		this.resPhone = resPhone;
	}

	public void setResFeature(String resFeature) {
		this.resFeature = resFeature;
	}

	@JsonProperty
	public String getResFeature() {
		return resFeature;
	}

	public void setResPicture(String resPicture) {
		this.resPicture = resPicture;
	}

	@JsonProperty
	public String getResPicture() {
		return resPicture;
	}

	@JsonProperty
	public int getCityCode() {
		return cityCode;
	}

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	public void setHotNum(int hotNum) {
		this.hotNum = hotNum;
	}

	@JsonProperty
	public int getHotNum() {
		return hotNum;
	}

	@JsonProperty
	public String getShopHours() {
		return shopHours;
	}

	public void setShopHours(String shopHours) {
		this.shopHours = shopHours;
	}

	@JsonProperty
	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	@JsonProperty
	public double getGcjLng() {
		return gcjLng;
	}

	public void setGcjLng(double gcjLng) {
		this.gcjLng = gcjLng;
	}

	@JsonProperty
	public double getGcjLat() {
		return gcjLat;
	}

	public void setGcjLat(double gcjLat) {
		this.gcjLat = gcjLat;
	}

	@JsonProperty
	public String getDianpingComment() {
		return dianpingComment;
	}

	public void setDianpingComment(String dianpingComment) {
		this.dianpingComment = dianpingComment;
	}

}
