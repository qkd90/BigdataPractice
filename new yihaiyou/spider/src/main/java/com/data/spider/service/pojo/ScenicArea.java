package com.data.spider.service.pojo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "scenic_area")
public class ScenicArea extends com.framework.hibernate.util.Entity {
    private long id;
    private Long areaId;
    private Long scenicId;
    private String scenicName;
    private String areaName;
    private Integer ranking;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "area_id", nullable = true)
    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    @Basic
    @Column(name = "scenic_id", nullable = true)
    public Long getScenicId() {
        return scenicId;
    }

    public void setScenicId(Long scenicId) {
        this.scenicId = scenicId;
    }

    @Basic
    @Column(name = "scenic_name", nullable = true, length = 500)
    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    @Basic
    @Column(name = "area_name", nullable = true, length = 500)
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Basic
    @Column(name = "ranking", nullable = true)
    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScenicArea that = (ScenicArea) o;

        if (id != that.id) return false;
        if (areaId != null ? !areaId.equals(that.areaId) : that.areaId != null) return false;
        if (scenicId != null ? !scenicId.equals(that.scenicId) : that.scenicId != null) return false;
        if (scenicName != null ? !scenicName.equals(that.scenicName) : that.scenicName != null) return false;
        if (areaName != null ? !areaName.equals(that.areaName) : that.areaName != null) return false;
        if (ranking != null ? !ranking.equals(that.ranking) : that.ranking != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (areaId != null ? areaId.hashCode() : 0);
        result = 31 * result + (scenicId != null ? scenicId.hashCode() : 0);
        result = 31 * result + (scenicName != null ? scenicName.hashCode() : 0);
        result = 31 * result + (areaName != null ? areaName.hashCode() : 0);
        result = 31 * result + (ranking != null ? ranking.hashCode() : 0);
        return result;
    }
}
