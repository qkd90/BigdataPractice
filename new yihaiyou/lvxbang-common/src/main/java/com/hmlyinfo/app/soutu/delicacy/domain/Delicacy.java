package com.hmlyinfo.app.soutu.delicacy.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class Delicacy extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 城市编号
	 */
	private long cityId;

	/**
	 * 评论人
	 */
	private long userId;

	/**
	 * 美食名称
	 */
	private String foodName;

	/**
	 * 价格
	 */
	private long price;


	/**
	 * 商圈（暂不用）
	 */
	private String bussinessCircle;

	/**
	 * 标签
	 */
	private String sign;

	/**
	 * 功效
	 */
	private String efficacy;

	/**
	 * 介绍
	 */
	private String introduction;

	/**
	 * 必吃标志
	 */
	private int mustEat;

	/**
	 * 推荐人数
	 */
	private int recommendCount;

	/**
	 * 当地人标志
	 */
	private int localNum;

	/**
	 * 游客标志
	 */
	private int touristNum;

	/**
	 * 推荐理由
	 */
	private String recommendReson;

	/**
	 * 分享人数
	 */
	private int shareNum;

	/**
	 * 点赞人数
	 */
	private int goodNum;

	/**
	 * 美食图片
	 */
	private String foodPicture;

	/**
	 * 美食评论
	 */
	private String delicacyComment;

	/**
	 * 菜系
	 */
	public enum Cuisine {
		闽菜(1), 粤菜(2), 川菜(3), 苏菜(4),
		鲁菜(5), 浙菜(6), 徽菜(7), 湘菜(8);
		private Integer value;

		private Cuisine(Integer value) {
			this.value = value;
		}

		public static Cuisine nameOf(int value) {
			for (Cuisine cuisine : Cuisine.values()) {
				if (value == cuisine.value) {
					return cuisine;
				}
			}
			return null;
		}

	}

	private Cuisine cuisine;

	/**
	 * 口味
	 */
	public enum Taste {
		酸(4), 甜(2), 苦(3), 辣(1), 清淡(5);
		private Integer value;

		private Taste(Integer value) {
			this.value = value;
		}

		public static Taste nameOf(int value) {
			for (Taste taste : Taste.values()) {
				if (value == taste.value) {
					return taste;
				}
			}
			return null;
		}
	}

	private Taste taste;


	@JsonProperty
	public int getGoodNum() {
		return goodNum;
	}

	public void setGoodNum(int goodNum) {
		this.goodNum = goodNum;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public String getDelicacyComment() {
		return delicacyComment;
	}

	public void setDelicacyComment(String delicacyComment) {
		this.delicacyComment = delicacyComment;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	@JsonProperty
	public long getCityId() {
		return cityId;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	@JsonProperty
	public String getFoodName() {
		return foodName;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	@JsonProperty
	public long getPrice() {
		return price;
	}

	public void setBussinessCircle(String bussinessCircle) {
		this.bussinessCircle = bussinessCircle;
	}

	@JsonProperty
	public String getBussinessCircle() {
		return bussinessCircle;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@JsonProperty
	public String getSign() {
		return sign;
	}

	public void setEfficacy(String efficacy) {
		this.efficacy = efficacy;
	}

	@JsonProperty
	public String getEfficacy() {
		return efficacy;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	@JsonProperty
	public String getIntroduction() {
		return introduction;
	}

	public void setMustEat(int mustEat) {
		this.mustEat = mustEat;
	}

	@JsonProperty
	public int getMustEat() {
		return mustEat;
	}

	public void setRecommendCount(int recommendCount) {
		this.recommendCount = recommendCount;
	}

	@JsonProperty
	public int getRecommendCount() {
		return recommendCount;
	}

	@JsonProperty
	public int getLocalNum() {
		return localNum;
	}

	public void setLocalNum(int localNum) {
		this.localNum = localNum;
	}

	@JsonProperty
	public int getTouristNum() {
		return touristNum;
	}

	public void setTouristNum(int touristNum) {
		this.touristNum = touristNum;
	}

	public void setRecommendReson(String recommendReson) {
		this.recommendReson = recommendReson;
	}

	@JsonProperty
	public String getRecommendReson() {
		return recommendReson;
	}

	public void setShareNum(int shareNum) {
		this.shareNum = shareNum;
	}

	@JsonProperty
	public int getShareNum() {
		return shareNum;
	}

	public void setFoodPicture(String foodPicture) {
		this.foodPicture = foodPicture;
	}

	@JsonProperty
	public String getFoodPicture() {
		return foodPicture;
	}

	public Cuisine getCuisine() {
		return cuisine;
	}

	public void setCuisine(Cuisine cuisine) {
		this.cuisine = cuisine;
	}

	public Taste getTaste() {
		return taste;
	}

	public void setTaste(Taste taste) {
		this.taste = taste;
	}
}
