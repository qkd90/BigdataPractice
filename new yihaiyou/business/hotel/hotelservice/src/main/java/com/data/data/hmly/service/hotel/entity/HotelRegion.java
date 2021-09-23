package com.data.data.hmly.service.hotel.entity;

import com.data.data.hmly.service.entity.TbArea;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * Created by guoshijie on 2015/12/2.
 */
@Entity
@Table(name = "hotel_region")
public class HotelRegion extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "points")
    private String pointStr;
    @Column(name = "priority")
    private int priority;
    @Column(name = "description")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cityCode")
    private TbArea city;
    @Column(name = "cityCode", insertable = false, updatable = false)
    private String cityCode;
    @Column(name = "region_cat")
    private Integer regionCat;
    @Column(name = "region_type")
    private Integer regionType;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "create_time")
    private Date createTime;
    @Transient
    private List<HotelPoint> points;

    @Transient
    private Boolean isFull;
    @Transient
    private int leftTime;
    @Transient
    private Boolean isTempArea = false;
    @Transient
    private List<HotelRegion> unitAreas;
    @Transient
    private int dayIndex;
    @Transient
    private HotelRegion leftArea;
    @Transient
    private HotelRegion rightArea;
    @Transient
    private HotelRegion toArea;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPointStr() {
        return pointStr;
    }

    public void setPointStr(String pointStr) {
        this.pointStr = pointStr;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TbArea getCity() {
        return city;
    }

    public void setCity(TbArea city) {
        this.city = city;
    }

    public List<HotelPoint> getPoints() {
        return points;
    }

    public void setPoints(List<HotelPoint> points) {
        this.points = points;
    }

    public Boolean getIsFull() {
        return isFull;
    }

    public void setIsFull(Boolean isFull) {
        this.isFull = isFull;
    }

    public int getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(int leftTime) {
        this.leftTime = leftTime;
    }

    public Boolean getIsTempArea() {
        return isTempArea;
    }

    public void setIsTempArea(Boolean isTempArea) {
        this.isTempArea = isTempArea;
    }

    public List<HotelRegion> getUnitAreas() {
        return unitAreas;
    }

    public void setUnitAreas(List<HotelRegion> unitAreas) {
        this.unitAreas = unitAreas;
    }

    public int getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(int dayIndex) {
        this.dayIndex = dayIndex;
    }

    public HotelRegion getLeftArea() {
        return leftArea;
    }

    public void setLeftArea(HotelRegion leftArea) {
        this.leftArea = leftArea;
    }

    public HotelRegion getRightArea() {
        return rightArea;
    }

    public void setRightArea(HotelRegion rightArea) {
        this.rightArea = rightArea;
    }

    public HotelRegion getToArea() {
        return toArea;
    }

    public void setToArea(HotelRegion toArea) {
        this.toArea = toArea;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getRegionCat() {
        return regionCat;
    }

    public void setRegionCat(Integer regionCat) {
        this.regionCat = regionCat;
    }

    public Integer getRegionType() {
        return regionType;
    }

    public void setRegionType(Integer regionType) {
        this.regionType = regionType;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
