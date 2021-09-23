package com.data.data.hmly.service.scenic.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by guoshijie on 2015/12/2.
 */
@Entity
@Table(name = "scenic_area")
public class ScenicArea extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	@Id
	@Column(name = "id")
    @GeneratedValue
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scenic_id")
	private ScenicInfo scenicInfo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "area_id")
	private Region region;
	@Column(name = "scenic_name")
	private String scenicName;
	@Column(name = "area_name")
	private String areaName;
	@Column(name = "ranking")
	private Integer ranking;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
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

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
}
