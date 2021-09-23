package com.hmlyinfo.app.soutu.hotel.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class HotelComment extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * 用户编号
	 */
	private long userId;

	/**
	 * 酒店编号
	 */
	private long hotelId;

	/**
	 * 酒店索引编号
	 */
	private long indexId;


	/**
	 * 评论内容
	 */
	private String content;

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

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@JsonProperty
	public String getNickName() {
		return nickName;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}

	@JsonProperty
	public long getHotelId() {
		return hotelId;
	}

	@JsonProperty
	public long getIndexId() {
		return indexId;
	}

	public void setIndexId(long indexId) {
		this.indexId = indexId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonProperty
	public String getContent() {
		return content;
	}


}
