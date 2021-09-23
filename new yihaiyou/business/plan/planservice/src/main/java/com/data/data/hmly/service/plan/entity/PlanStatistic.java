package com.data.data.hmly.service.plan.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by guoshijie on 2015/12/4.
 */
@Entity
@Table(name = "plan_statistic")
public class PlanStatistic extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_id")
	private Plan plan;
	@Column(name = "share_num")
	private Integer shareNum;
	@Column(name = "comment_num")
	private Integer commentNum;
	@Column(name = "quote_num")
	private Integer quoteNum;
	@Column(name = "collect_num")
	private Integer collectNum;
	@Column(name = "view_num")
	private Integer viewNum;
	@Column(name = "plan_cost")
	private Float planCost;
	@Column(name = "include_seasonticket_cost")
	private Float includeSeasonticketCost;
	@Column(name = "plan_hotel_cost")
	private Float planHotelCost = 0f;
	@Column(name = "plan_travel_cost")
	private Float planTravelCost = 0f;
	@Column(name = "plan_ticket_cost")
	private Float planTicketCost = 0f;
	@Column(name = "plan_seasonticket_cost")
	private Float planSeasonticketCost;
	@Column(name = "plan_food_cost")
	private Float planFoodCost;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public Integer getShareNum() {
		return shareNum;
	}

	public void setShareNum(Integer shareNum) {
		this.shareNum = shareNum;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public Integer getQuoteNum() {
		return quoteNum;
	}

	public void setQuoteNum(Integer quoteNum) {
		this.quoteNum = quoteNum;
	}

	public Integer getCollectNum() {
		return collectNum;
	}

	public void setCollectNum(Integer collectNum) {
		this.collectNum = collectNum;
	}

	public Integer getViewNum() {
		return viewNum;
	}

	public void setViewNum(Integer viewNum) {
		this.viewNum = viewNum;
	}

	public Float getPlanCost() {
		return planCost;
	}

	public void setPlanCost(Float planCost) {
		this.planCost = planCost;
	}

	public Float getIncludeSeasonticketCost() {
		return includeSeasonticketCost;
	}

	public void setIncludeSeasonticketCost(Float includeSeasonticketCost) {
		this.includeSeasonticketCost = includeSeasonticketCost;
	}

	public Float getPlanHotelCost() {
		return planHotelCost;
	}

	public void setPlanHotelCost(Float planHotelCost) {
		this.planHotelCost = planHotelCost;
	}

	public Float getPlanTravelCost() {
		return planTravelCost;
	}

	public void setPlanTravelCost(Float planTravelCost) {
		this.planTravelCost = planTravelCost;
	}

	public Float getPlanTicketCost() {
		return planTicketCost;
	}

	public void setPlanTicketCost(Float planTicketCost) {
		this.planTicketCost = planTicketCost;
	}

	public Float getPlanSeasonticketCost() {
		return planSeasonticketCost;
	}

	public void setPlanSeasonticketCost(Float planSeasonticketCost) {
		this.planSeasonticketCost = planSeasonticketCost;
	}

	public Float getPlanFoodCost() {
		return planFoodCost;
	}

	public void setPlanFoodCost(Float planFoodCost) {
		this.planFoodCost = planFoodCost;
	}
}
