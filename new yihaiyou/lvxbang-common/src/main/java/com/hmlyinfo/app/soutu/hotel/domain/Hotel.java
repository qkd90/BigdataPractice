package com.hmlyinfo.app.soutu.hotel.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class Hotel extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 评论人
	 */
	private Long userId;

	/**
	 * 酒店精简点评
	 */
	private String hotelComment;

	/**
	 * 点评数
	 */
	private int commentNum;

	/**
	 * 分享数
	 */
	private int shareNum;

	/**
	 * 收藏数
	 */
	private int collectionNum;

	/**
	 * 推荐人数
	 */
	private int recommendNum;

	/**
	 * 经度
	 */
	private Double longitude;

	/**
	 * 纬度
	 */
	private Double latitude;

	/**
	 * 评分
	 */
	private int score;

	@JsonProperty
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@JsonProperty
	public int getRecommendNum() {
		return recommendNum;
	}

	public void setRecommendNum(int recommendNum) {
		this.recommendNum = recommendNum;
	}

	@JsonProperty
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setHotelComment(String hotelComment) {
		this.hotelComment = hotelComment;
	}

	@JsonProperty
	public String getHotelComment() {
		return hotelComment;
	}

	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}

	@JsonProperty
	public int getCommentNum() {
		return commentNum;
	}

	public void setShareNum(int shareNum) {
		this.shareNum = shareNum;
	}

	@JsonProperty
	public int getShareNum() {
		return shareNum;
	}

	public void setCollectionNum(int collectionNum) {
		this.collectionNum = collectionNum;
	}

	@JsonProperty
	public int getCollectionNum() {
		return collectionNum;
	}

	@JsonProperty
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@JsonProperty
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
}
