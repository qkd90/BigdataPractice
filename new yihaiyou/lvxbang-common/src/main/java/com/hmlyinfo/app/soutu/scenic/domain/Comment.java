package com.hmlyinfo.app.soutu.scenic.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Comment extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private long userId;

	/**
	 * 景点id
	 */
	private long scenicId;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 发表时间
	 */
	private Date publishTime;

	/**
	 * 有用数
	 */
	private int goodCount;

	/**
	 * 审核状态
	 */
	private int status;


	/**
	 * 是否包含图片
	 */
	private boolean ifHasImage;

	/**
	 * 评论图片
	 */
	private List<Map<String, Object>> imageList;

	/**
	 * 评分
	 */
	private int score;

	/**
	 * 是否还能点赞
	 */
	private Boolean ifCanGood;

	/**
	 * 用户昵称
	 */
	private String nickname;

	/**
	 * 用户头像
	 */
	private String userface;

	/**
	 * 发表IP
	 */
	private String publishIP;


	@JsonProperty
	public Boolean getIfCanGood() {
		return ifCanGood;
	}

	public void setIfCanGood(Boolean ifCanGood) {
		this.ifCanGood = ifCanGood;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setScenicId(long scenicId) {
		this.scenicId = scenicId;
	}

	@JsonProperty
	public long getScenicId() {
		return scenicId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonProperty
	public String getContent() {
		return content;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	@JsonProperty
	public Date getPublishTime() {
		return publishTime;
	}

	public void setGoodCount(int goodCount) {
		this.goodCount = goodCount;
	}

	@JsonProperty
	public int getGoodCount() {
		return goodCount;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setIfHasImage(boolean ifHasImage) {
		this.ifHasImage = ifHasImage;
	}

	public boolean getIfHasImage() {
		return ifHasImage;
	}

	@JsonProperty
	public List<Map<String, Object>> getImageList() {
		return imageList;
	}

	public void setImageList(List<Map<String, Object>> imageList) {
		this.imageList = imageList;
	}

	@JsonProperty
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@JsonProperty
	public String getUserface() {
		return userface;
	}

	public void setUserface(String userface) {
		this.userface = userface;
	}

	@JsonProperty
	public String getPublishIP() {
		return publishIP;
	}

	public void setPublishIP(String publishIP) {
		this.publishIP = publishIP;
	}

	@JsonProperty
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
