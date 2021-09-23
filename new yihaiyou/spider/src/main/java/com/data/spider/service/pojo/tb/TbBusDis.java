package com.data.spider.service.pojo.tb;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by Sane on 15/9/10.
 */
@Entity
@javax.persistence.Table(name = "tb_dis_bus")
public class TbBusDis extends com.framework.hibernate.util.Entity {
    private long id;

    @Id
    @javax.persistence.Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private Integer cityCode;

    @Basic
    @javax.persistence.Column(name = "city_code")
    public Integer getCityCode() {
        return cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    private Long sId;

    @Basic
    @javax.persistence.Column(name = "s_id")
    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }

    private Double sLng;

    @Basic
    @javax.persistence.Column(name = "s_lng")
    public Double getsLng() {
        return sLng;
    }

    public void setsLng(Double sLng) {
        this.sLng = sLng;
    }

    private Double sLat;

    @Basic
    @javax.persistence.Column(name = "s_lat")
    public Double getsLat() {
        return sLat;
    }

    public void setsLat(Double sLat) {
        this.sLat = sLat;
    }

    private Long eId;

    @Basic
    @javax.persistence.Column(name = "e_id")
    public Long geteId() {
        return eId;
    }

    public void seteId(Long eId) {
        this.eId = eId;
    }

    private Double eLng;

    @Basic
    @javax.persistence.Column(name = "e_lng")
    public Double geteLng() {
        return eLng;
    }

    public void seteLng(Double eLng) {
        this.eLng = eLng;
    }

    private Double eLat;

    @Basic
    @javax.persistence.Column(name = "e_lat")
    public Double geteLat() {
        return eLat;
    }

    public void seteLat(Double eLat) {
        this.eLat = eLat;
    }

    private Integer taxiDis;

    @Basic
    @javax.persistence.Column(name = "taxi_dis")
    public Integer getTaxiDis() {
        return taxiDis;
    }

    public void setTaxiDis(Integer taxiDis) {
        this.taxiDis = taxiDis;
    }

    private Integer taxiCost;

    @Basic
    @javax.persistence.Column(name = "taxi_cost")
    public Integer getTaxiCost() {
        return taxiCost;
    }

    public void setTaxiCost(Integer taxiCost) {
        this.taxiCost = taxiCost;
    }

    private Integer taxiTime;

    @Basic
    @javax.persistence.Column(name = "taxi_time")
    public Integer getTaxiTime() {
        return taxiTime;
    }

    public void setTaxiTime(Integer taxiTime) {
        this.taxiTime = taxiTime;
    }

    private Integer walkDis;

    @Basic
    @javax.persistence.Column(name = "walk_dis")
    public Integer getWalkDis() {
        return walkDis;
    }

    public void setWalkDis(Integer walkDis) {
        this.walkDis = walkDis;
    }

    private Integer walkTime;

    @Basic
    @javax.persistence.Column(name = "walk_time")
    public Integer getWalkTime() {
        return walkTime;
    }

    public void setWalkTime(Integer walkTime) {
        this.walkTime = walkTime;
    }

    private Integer busDis;

    @Basic
    @javax.persistence.Column(name = "bus_dis")
    public Integer getBusDis() {
        return busDis;
    }

    public void setBusDis(Integer busDis) {
        this.busDis = busDis;
    }

    private Integer busCost;

    @Basic
    @javax.persistence.Column(name = "bus_cost")
    public Integer getBusCost() {
        return busCost;
    }

    public void setBusCost(Integer busCost) {
        this.busCost = busCost;
    }

    private Integer busTime;

    @Basic
    @javax.persistence.Column(name = "bus_time")
    public Integer getBusTime() {
        return busTime;
    }

    public void setBusTime(Integer busTime) {
        this.busTime = busTime;
    }

    private Timestamp modifyTime;

    @Basic
    @javax.persistence.Column(name = "modify_time")
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    private Timestamp createTime;

    @Basic
    @javax.persistence.Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    private Integer disType;

    @Basic
    @javax.persistence.Column(name = "dis_type")
    public Integer getDisType() {
        return disType;
    }

    public void setDisType(Integer disType) {
        this.disType = disType;
    }

    private int taxiEx;

    @Basic
    @javax.persistence.Column(name = "taxi_ex")
    public int getTaxiEx() {
        return taxiEx;
    }

    public void setTaxiEx(int taxiEx) {
        this.taxiEx = taxiEx;
    }

    private int walkEx;

    @Basic
    @javax.persistence.Column(name = "walk_ex")
    public int getWalkEx() {
        return walkEx;
    }

    public void setWalkEx(int walkEx) {
        this.walkEx = walkEx;
    }

    private Integer busEx;

    @Basic
    @javax.persistence.Column(name = "bus_ex")
    public Integer getBusEx() {
        return busEx;
    }

    public void setBusEx(Integer busEx) {
        this.busEx = busEx;
    }

    private Integer optStatus;

    @Basic
    @javax.persistence.Column(name = "opt_status")
    public Integer getOptStatus() {
        return optStatus;
    }

    public void setOptStatus(Integer optStatus) {
        this.optStatus = optStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbBusDis that = (TbBusDis) o;

        if (id != that.id) return false;
        if (taxiEx != that.taxiEx) return false;
        if (walkEx != that.walkEx) return false;
        if (cityCode != null ? !cityCode.equals(that.cityCode) : that.cityCode != null) return false;
        if (sId != null ? !sId.equals(that.sId) : that.sId != null) return false;
        if (sLng != null ? !sLng.equals(that.sLng) : that.sLng != null) return false;
        if (sLat != null ? !sLat.equals(that.sLat) : that.sLat != null) return false;
        if (eId != null ? !eId.equals(that.eId) : that.eId != null) return false;
        if (eLng != null ? !eLng.equals(that.eLng) : that.eLng != null) return false;
        if (eLat != null ? !eLat.equals(that.eLat) : that.eLat != null) return false;
        if (taxiDis != null ? !taxiDis.equals(that.taxiDis) : that.taxiDis != null) return false;
        if (taxiCost != null ? !taxiCost.equals(that.taxiCost) : that.taxiCost != null) return false;
        if (taxiTime != null ? !taxiTime.equals(that.taxiTime) : that.taxiTime != null) return false;
        if (walkDis != null ? !walkDis.equals(that.walkDis) : that.walkDis != null) return false;
        if (walkTime != null ? !walkTime.equals(that.walkTime) : that.walkTime != null) return false;
        if (busDis != null ? !busDis.equals(that.busDis) : that.busDis != null) return false;
        if (busCost != null ? !busCost.equals(that.busCost) : that.busCost != null) return false;
        if (busTime != null ? !busTime.equals(that.busTime) : that.busTime != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (disType != null ? !disType.equals(that.disType) : that.disType != null) return false;
        if (busEx != null ? !busEx.equals(that.busEx) : that.busEx != null) return false;
        if (optStatus != null ? !optStatus.equals(that.optStatus) : that.optStatus != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (cityCode != null ? cityCode.hashCode() : 0);
        result = 31 * result + (sId != null ? sId.hashCode() : 0);
        result = 31 * result + (sLng != null ? sLng.hashCode() : 0);
        result = 31 * result + (sLat != null ? sLat.hashCode() : 0);
        result = 31 * result + (eId != null ? eId.hashCode() : 0);
        result = 31 * result + (eLng != null ? eLng.hashCode() : 0);
        result = 31 * result + (eLat != null ? eLat.hashCode() : 0);
        result = 31 * result + (taxiDis != null ? taxiDis.hashCode() : 0);
        result = 31 * result + (taxiCost != null ? taxiCost.hashCode() : 0);
        result = 31 * result + (taxiTime != null ? taxiTime.hashCode() : 0);
        result = 31 * result + (walkDis != null ? walkDis.hashCode() : 0);
        result = 31 * result + (walkTime != null ? walkTime.hashCode() : 0);
        result = 31 * result + (busDis != null ? busDis.hashCode() : 0);
        result = 31 * result + (busCost != null ? busCost.hashCode() : 0);
        result = 31 * result + (busTime != null ? busTime.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (disType != null ? disType.hashCode() : 0);
        result = 31 * result + taxiEx;
        result = 31 * result + walkEx;
        result = 31 * result + (busEx != null ? busEx.hashCode() : 0);
        result = 31 * result + (optStatus != null ? optStatus.hashCode() : 0);
        return result;
    }
}
