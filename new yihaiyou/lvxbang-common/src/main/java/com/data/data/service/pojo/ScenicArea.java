package com.data.data.service.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class ScenicArea extends com.framework.hibernate.util.Entity {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -829617448217693822L;
	@Id
	@GeneratedValue
	private Long				id;
	@ManyToOne
	@JoinColumn(name = "areaid", nullable = false, updatable = false)
	private Area				area;
	private String				scenicName;
	private String				areaName;
	@OneToOne
	@JoinColumn(name = "scenicId", unique = true, nullable = false, updatable = false)
	private ScenicInfo			scenicInfo;
	private Integer				orderNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public ScenicInfo getScenicInfo() {
		return scenicInfo;
	}

	public void setScenicInfo(ScenicInfo scenicInfo) {
		this.scenicInfo = scenicInfo;
	}

	public String getScenicName() {
		return scenicName;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

}
