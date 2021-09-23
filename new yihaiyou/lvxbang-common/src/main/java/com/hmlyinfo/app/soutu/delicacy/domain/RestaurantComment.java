package com.hmlyinfo.app.soutu.delicacy.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class RestaurantComment extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 *
	 */
	private long userId;

	/**
	 *
	 */
	private String nickname;

	/**
	 *
	 */
	private String userFace;

	/**
	 *
	 */
	private long restaurantId;

	/**
	 *
	 */
	private int tasteScore;

	/**
	 *
	 */
	private int environmentScore;

	/**
	 *
	 */
	private int serviceScore;

	/**
	 *
	 */
	private int averageScore;

	/**
	 *
	 */
	private String content;


	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@JsonProperty
	public String getNickname() {
		return nickname;
	}

	public void setUserFace(String userFace) {
		this.userFace = userFace;
	}

	@JsonProperty
	public String getUserFace() {
		return userFace;
	}

	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
	}

	@JsonProperty
	public long getRestaurantId() {
		return restaurantId;
	}

	public void setTasteScore(int tasteScore) {
		this.tasteScore = tasteScore;
	}

	@JsonProperty
	public int getTasteScore() {
		return tasteScore;
	}

	public void setEnvironmentScore(int environmentScore) {
		this.environmentScore = environmentScore;
	}

	@JsonProperty
	public int getEnvironmentScore() {
		return environmentScore;
	}

	public void setServiceScore(int serviceScore) {
		this.serviceScore = serviceScore;
	}

	@JsonProperty
	public int getServiceScore() {
		return serviceScore;
	}

	public void setAverageScore(int averageScore) {
		this.averageScore = averageScore;
	}

	@JsonProperty
	public int getAverageScore() {
		return averageScore;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonProperty
	public String getContent() {
		return content;
	}
}
