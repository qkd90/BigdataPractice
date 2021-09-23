package com.hmlyinfo.app.soutu.scenic.domain;

import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;
import java.util.List;

public class Note extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 行程编号
	 */
	private long planId;

	/**
	 * 作者id
	 */
	private long userId;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 花费
	 */
	private String cost;

	/**
	 * 游玩天数
	 */
	private int days;

	/**
	 * 里程数
	 */
	private int mileages;

	/**
	 * 出发时间
	 */
	private Date travelTime;

	/**
	 * 推荐景点
	 */
	private String scenics;

	/**
	 * 发表时间
	 */
	private Date publishTime;

	/**
	 * 上一次修改时间
	 */
	private Date lastEditTime;

	/**
	 * 点赞数
	 */
	private int good;

	/**
	 * 审核状态
	 */
	private int status;

	private List<NoteDay> dayList;
	private transient User user;
	private transient int commentCount;


	@JsonProperty
	public List<NoteDay> getDayList() {
		return dayList;
	}

	public void setDayList(List<NoteDay> dayList) {
		this.dayList = dayList;
	}

	public void setPlanId(long planId) {
		this.planId = planId;
	}

	@JsonProperty
	public long getPlanId() {
		return planId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty
	public String getTitle() {
		return title;
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

	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	@JsonProperty
	public Date getLastEditTime() {
		return lastEditTime;
	}

	public void setGood(int good) {
		this.good = good;
	}

	@JsonProperty
	public int getGood() {
		return good;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@JsonProperty
	public int getStatus() {
		return status;
	}

	@JsonProperty
	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	@JsonProperty
	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	@JsonProperty
	public int getMileages() {
		return mileages;
	}

	public void setMileages(int mileages) {
		this.mileages = mileages;
	}

	@JsonProperty
	public Date getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(Date travelTime) {
		this.travelTime = travelTime;
	}

	@JsonProperty
	public String getScenics() {
		return scenics;
	}

	public void setScenics(String scenics) {
		this.scenics = scenics;
	}

	@JsonProperty
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JsonProperty
	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
}
