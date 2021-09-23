package com.data.data.hmly.service.scenic.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by guoshijie on 2015/12/2.
 */
@Entity
@Table(name = "scenic_gallery")
public class ScenicGallery  extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scenic_id")
	private ScenicInfo scenicInfo;
	@Column(name = "type")
	private String type;
	@Column(name = "img_url")
	private String imgUrl;
	@Column(name = "description")
	private String description;
	@Column(name = "ranking")
	private Integer ranking;
	@Column(name = "width")
	private Integer width;
	@Column(name = "height")
	private Integer height;
	@Column(name = "is_sync")
	private Boolean isSync;
	@Column(name = "remote_url")
	private String remoteUrl;
	@Column(name = "local_url")
	private String localUrl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ScenicInfo getScenicInfo() {
		return scenicInfo;
	}

	public void setScenicInfo(ScenicInfo scenicInfo) {
		this.scenicInfo = scenicInfo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
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

	public Boolean getIsSync() {
		return isSync;
	}

	public void setIsSync(Boolean isSync) {
		this.isSync = isSync;
	}

	public String getRemoteUrl() {
		return remoteUrl;
	}

	public void setRemoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}

	public String getLocalUrl() {
		return localUrl;
	}

	public void setLocalUrl(String localUrl) {
		this.localUrl = localUrl;
	}
}
