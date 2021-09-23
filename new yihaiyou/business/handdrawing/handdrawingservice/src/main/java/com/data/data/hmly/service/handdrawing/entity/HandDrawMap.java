package com.data.data.hmly.service.handdrawing.entity;


import com.data.data.hmly.service.entity.TbArea;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "hand_draw_map")
public class HandDrawMap extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_code")
	private TbArea city;
    @Column(name = "destination_id")
    private Long destinationId;
    @Column(name = "level")
    private Integer level;
    @Column(name = "north")
    private Double north;
    @Column(name = "south")
    private Double south;
    @Column(name = "west")
    private Double west;
    @Column(name = "east")
    private Double east;
    @Column(name = "modify_time")
    private Date modifyTime;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "hd_north")
    private Double hdNorth;
    @Column(name = "hd_south")
    private Double hdSouth;
    @Column(name = "hd_west")
    private Double hdWest;
    @Column(name = "hd_east")
    private Double hdEast;
	@OneToMany(mappedBy = "handDrawMap", fetch = FetchType.LAZY)
	private List<HandDrawScenic> scenicList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TbArea getCity() {
		return city;
	}

	public void setCity(TbArea city) {
		this.city = city;
	}

	public Long getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(Long destinationId) {
		this.destinationId = destinationId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Double getNorth() {
		return north;
	}

	public void setNorth(Double north) {
		this.north = north;
	}

	public Double getSouth() {
		return south;
	}

	public void setSouth(Double south) {
		this.south = south;
	}

	public Double getWest() {
		return west;
	}

	public void setWest(Double west) {
		this.west = west;
	}

	public Double getEast() {
		return east;
	}

	public void setEast(Double east) {
		this.east = east;
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

	public Double getHdNorth() {
		return hdNorth;
	}

	public void setHdNorth(Double hdNorth) {
		this.hdNorth = hdNorth;
	}

	public Double getHdSouth() {
		return hdSouth;
	}

	public void setHdSouth(Double hdSouth) {
		this.hdSouth = hdSouth;
	}

	public Double getHdWest() {
		return hdWest;
	}

	public void setHdWest(Double hdWest) {
		this.hdWest = hdWest;
	}

	public Double getHdEast() {
		return hdEast;
	}

	public void setHdEast(Double hdEast) {
		this.hdEast = hdEast;
	}

	public List<HandDrawScenic> getScenicList() {
		return scenicList;
	}

	public void setScenicList(List<HandDrawScenic> scenicList) {
		this.scenicList = scenicList;
	}
}
