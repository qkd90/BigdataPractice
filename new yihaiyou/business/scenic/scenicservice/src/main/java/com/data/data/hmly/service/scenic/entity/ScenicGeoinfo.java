package com.data.data.hmly.service.scenic.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Created by guoshijie on 2015/12/2.
 */
@Entity
@Table(name = "scenic_geoinfo")
public class ScenicGeoinfo extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	@Id
	@Column(name = "id")
	private Long id;
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@PrimaryKeyJoinColumn
	private ScenicInfo scenicInfo;
	@Column(name = "baidu_lng")
	private Double baiduLng;
	@Column(name = "baidu_lat")
	private Double baiduLat;
	@Column(name = "google_lng")
	private Double googleLng;
	@Column(name = "google_lat")
	private Double googleLat;

	public ScenicInfo getScenicInfo() {
		return scenicInfo;
	}

	public void setScenicInfo(ScenicInfo scenicInfo) {
		this.scenicInfo = scenicInfo;
	}

	public Double getBaiduLng() {
		return baiduLng;
	}

	public void setBaiduLng(Double baiduLng) {
		this.baiduLng = baiduLng;
	}

	public Double getBaiduLat() {
		return baiduLat;
	}

	public void setBaiduLat(Double baiduLat) {
		this.baiduLat = baiduLat;
	}

	public Double getGoogleLng() {
		return googleLng;
	}

	public void setGoogleLng(Double googleLng) {
		this.googleLng = googleLng;
	}

	public Double getGoogleLat() {
		return googleLat;
	}

	public void setGoogleLat(Double googleLat) {
		this.googleLat = googleLat;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
