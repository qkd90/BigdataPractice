package com.data.spider.service.pojo;

import javax.persistence.*;

import com.framework.hibernate.util.Entity;

import java.util.List;

@Table(name = "data_map_region")
@javax.persistence.Entity
public class Area extends Entity {

    /**
     *
     */
    private static final long serialVersionUID = -1959134189454463485L;
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(name = "points")
    private String pointStr;
    private Integer priority;
    private String description;
    private String cityCode;
    @Transient
    private List<Point> points;

    @Transient
    private Boolean isFull;
    @Transient
    private int leftTime;
    @Transient
    private Boolean isTempArea = false;
    @Transient
    private List<Area> unitAreas;
    @Transient
    private int dayIndex;
    @Transient
    private Area leftArea;
    @Transient
    private Area rightArea;
    @Transient
    private Area toArea;

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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPointStr() {
        return pointStr;
    }

    public void setPointStr(String pointStr) {
        this.pointStr = pointStr;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
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

    public List<Area> getUnitAreas() {
        return unitAreas;
    }

    public void setUnitAreas(List<Area> unitAreas) {
        this.unitAreas = unitAreas;
    }

    public int getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(int dayIndex) {
        this.dayIndex = dayIndex;
    }

    public Area getLeftArea() {
        return leftArea;
    }

    public void setLeftArea(Area leftArea) {
        this.leftArea = leftArea;
    }

    public Area getRightArea() {
        return rightArea;
    }

    public void setRightArea(Area rightArea) {
        this.rightArea = rightArea;
    }

    public Area getToArea() {
        return toArea;
    }

    public void setToArea(Area toArea) {
        this.toArea = toArea;
    }

    @Override
    public Area clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return (Area) super.clone();
    }

}
