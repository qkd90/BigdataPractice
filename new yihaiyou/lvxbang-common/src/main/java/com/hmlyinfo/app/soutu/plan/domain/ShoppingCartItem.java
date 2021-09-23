package com.hmlyinfo.app.soutu.plan.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

public class ShoppingCartItem extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private long userId;

	/**
	 * 购物车编号
	 */
	private long planId;

	/**
	 * 门票编号
	 */
	private long ticketId;

	/**
	 * 门票类型
	 */
	private int ticketType;

	/**
	 * 游客编号
	 */
	private long passengerId;

	/**
	 * 游玩日期
	 */
	private Date playDate;

	/**
	 * 行程的第几天
	 */
	private int days;

	/**
	 *
	 */
	private String additional1;

	/**
	 *
	 */
	private String additional2;

	private Long playDateLong;

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setPlanId(long planId) {
		this.planId = planId;
	}

	@JsonProperty
	public long getPlanId() {
		return planId;
	}

	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}

	@JsonProperty
	public long getTicketId() {
		return ticketId;
	}

	public void setTicketType(int ticketType) {
		this.ticketType = ticketType;
	}

	@JsonProperty
	public int getTicketType() {
		return ticketType;
	}

	public void setPassengerId(long passengerId) {
		this.passengerId = passengerId;
	}

	@JsonProperty
	public long getPassengerId() {
		return passengerId;
	}

	public void setPlayDate(Date playDate) {
		this.playDate = playDate;
	}

	public Long getPlayDateLong() {
		return playDateLong;
	}

	public void setPlayDateLong(Long playDateLong) {
		this.playDateLong = playDateLong;
		this.playDate = new Date(playDateLong);
	}

	@JsonProperty
	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	@JsonProperty
	public Date getPlayDate() {
		return playDate;
	}

	public void setAdditional1(String additional1) {
		this.additional1 = additional1;
	}

	@JsonProperty
	public String getAdditional1() {
		return additional1;
	}

	public void setAdditional2(String additional2) {
		this.additional2 = additional2;
	}

	@JsonProperty
	public String getAdditional2() {
		return additional2;
	}
}
