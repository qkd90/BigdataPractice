package com.data.data.hmly.service.plan.entity;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "recommend_plan_photo")
@DynamicUpdate
@DynamicInsert
public class RecommendPlanPhoto extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recplan_id")
	private RecommendPlan recommendPlan;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recday_id")
	private RecommendPlanDay recommendPlanDay;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rectrip_id")
	private RecommendPlanTrip recommendPlanTrip;
    @Column(name = "photo_large")
    private String photoLarge;
    @Column(name = "photo_medium")
    private String photoMedium;
    @Column(name = "photo_small")
    private String photoSmall;
    @Column(name = "description")
    private String description;
    @Column(name = "modify_time")
    private Date modifyTime;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "width")
    private Integer width;
    @Column(name = "height")
    private Integer height;
    @Column(name = "sort")
    private Integer sort;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RecommendPlan getRecommendPlan() {
		return recommendPlan;
	}

	public void setRecommendPlan(RecommendPlan recommendPlan) {
		this.recommendPlan = recommendPlan;
	}

	public RecommendPlanDay getRecommendPlanDay() {
		return recommendPlanDay;
	}

	public void setRecommendPlanDay(RecommendPlanDay recommendPlanDay) {
		this.recommendPlanDay = recommendPlanDay;
	}

	public RecommendPlanTrip getRecommendPlanTrip() {
		return recommendPlanTrip;
	}

	public void setRecommendPlanTrip(RecommendPlanTrip recommendPlanTrip) {
		this.recommendPlanTrip = recommendPlanTrip;
	}

	public String getPhotoLarge() {
		return photoLarge;
	}

	public void setPhotoLarge(String photoLarge) {
		this.photoLarge = photoLarge;
	}

	public String getPhotoMedium() {
		return photoMedium;
	}

	public void setPhotoMedium(String photoMedium) {
		this.photoMedium = photoMedium;
	}

	public String getPhotoSmall() {
		return photoSmall;
	}

	public void setPhotoSmall(String photoSmall) {
		this.photoSmall = photoSmall;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
