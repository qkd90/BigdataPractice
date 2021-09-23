package com.data.data.hmly.service.handdrawing.entity;


import com.data.data.hmly.service.entity.TbArea;

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
@Table(name = "hand_draw_scenic")
public class HandDrawScenic extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@Column(name = "scenic_id")
	private Long scenicId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_code")
	private TbArea city;
	@Column(name = "zoom_level")
	private Integer zoomLevel;
	@Column(name = "style")
	private String style;
	@Column(name = "width")
	private Integer width;
	@Column(name = "height")
	private Integer height;
	@Column(name = "lng")
	private Double lng;
	@Column(name = "lat")
	private Double lat;
	@Column(name = "operable")
	private Boolean operable;
	@Column(name = "modify_time")
	private Date modifyTime;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "name")
	private String name;
	@Column(name = "spell_name")
	private String spellName;
	@Column(name = "rank")
	private Integer rank;
	@Column(name = "scenic_name")
	private String scenicName;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hand_draw_map_id")
	private HandDrawMap handDrawMap;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getScenicId() {
		return scenicId;
	}

	public void setScenicId(Long scenicId) {
		this.scenicId = scenicId;
	}

	public TbArea getCity() {
		return city;
	}

	public void setCity(TbArea city) {
		this.city = city;
	}

	public Integer getZoomLevel() {
		return zoomLevel;
	}

	public void setZoomLevel(Integer zoomLevel) {
		this.zoomLevel = zoomLevel;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
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

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Boolean getOperable() {
		return operable;
	}

	public void setOperable(Boolean operable) {
		this.operable = operable;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpellName() {
		return spellName;
	}

	public void setSpellName(String spellName) {
		this.spellName = spellName;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getScenicName() {
		return scenicName;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}

	public HandDrawMap getHandDrawMap() {
		return handDrawMap;
	}

	public void setHandDrawMap(HandDrawMap handDrawMap) {
		this.handDrawMap = handDrawMap;
	}
}
