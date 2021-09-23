package com.data.spider.service.pojo.tb;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by Sane on 16/1/16.
 */
@Entity
@javax.persistence.Table(name = "recommend_plan_trip")
public class RecommendPlanTrip extends com.framework.hibernate.util.Entity {
    private long id;

    @Id
    @GeneratedValue
    @javax.persistence.Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private Long recplanId;

    @Basic
    @javax.persistence.Column(name = "recplan_id", nullable = true)
    public Long getRecplanId() {
        return recplanId;
    }

    public void setRecplanId(Long recplanId) {
        this.recplanId = recplanId;
    }

    private Long userId;

    @Basic
    @javax.persistence.Column(name = "user_id", nullable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private Long recdayId;

    @Basic
    @javax.persistence.Column(name = "recday_id", nullable = true)
    public Long getRecdayId() {
        return recdayId;
    }

    public void setRecdayId(Long recdayId) {
        this.recdayId = recdayId;
    }

    private Long scenicId;

    @Basic
    @javax.persistence.Column(name = "scenic_id", nullable = true)
    public Long getScenicId() {
        return scenicId;
    }

    public void setScenicId(Long scenicId) {
        this.scenicId = scenicId;
    }

    private Integer trafficType;

    @Basic
    @javax.persistence.Column(name = "traffic_type", nullable = true)
    public Integer getTrafficType() {
        return trafficType;
    }

    public void setTrafficType(Integer trafficType) {
        this.trafficType = trafficType;
    }

    private Integer tripType;

    @Basic
    @javax.persistence.Column(name = "trip_type", nullable = true)
    public Integer getTripType() {
        return tripType;
    }

    public void setTripType(Integer tripType) {
        this.tripType = tripType;
    }

    private Time startTime;

    @Basic
    @javax.persistence.Column(name = "start_time", nullable = true)
    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    private Integer orderNum;

    @Basic
    @javax.persistence.Column(name = "order_num", nullable = true)
    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    private String tripDesc;

    @Basic
    @javax.persistence.Column(name = "trip_desc", nullable = true, length = 256)
    public String getTripDesc() {
        return tripDesc;
    }

    public void setTripDesc(String tripDesc) {
        this.tripDesc = tripDesc;
    }

    private String modifyTime;

    @Basic
    @javax.persistence.Column(name = "modify_time", nullable = true)
    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    private Timestamp createTime;

    @Basic
    @javax.persistence.Column(name = "create_time", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    private Double taxiCost;

    @Basic
    @javax.persistence.Column(name = "taxi_cost", nullable = true, precision = 2)
    public Double getTaxiCost() {
        return taxiCost;
    }

    public void setTaxiCost(Double taxiCost) {
        this.taxiCost = taxiCost;
    }

    private String taxiDetail;

    @Basic
    @javax.persistence.Column(name = "taxi_detail", nullable = true, length = 4096)
    public String getTaxiDetail() {
        return taxiDetail;
    }

    public void setTaxiDetail(String taxiDetail) {
        this.taxiDetail = taxiDetail;
    }

    private String taxiTime;

    @Basic
    @javax.persistence.Column(name = "taxi_time", nullable = true, length = 255)
    public String getTaxiTime() {
        return taxiTime;
    }

    public void setTaxiTime(String taxiTime) {
        this.taxiTime = taxiTime;
    }

    private Integer taxiHour;

    @Basic
    @javax.persistence.Column(name = "taxi_hour", nullable = true)
    public Integer getTaxiHour() {
        return taxiHour;
    }

    public void setTaxiHour(Integer taxiHour) {
        this.taxiHour = taxiHour;
    }

    private String taxiMilleage;

    @Basic
    @javax.persistence.Column(name = "taxi_milleage", nullable = true, length = 255)
    public String getTaxiMilleage() {
        return taxiMilleage;
    }

    public void setTaxiMilleage(String taxiMilleage) {
        this.taxiMilleage = taxiMilleage;
    }

    private String walkDetail;

    @Basic
    @javax.persistence.Column(name = "walk_detail", nullable = true, length = 4096)
    public String getWalkDetail() {
        return walkDetail;
    }

    public void setWalkDetail(String walkDetail) {
        this.walkDetail = walkDetail;
    }

    private String walkTime;

    @Basic
    @javax.persistence.Column(name = "walk_time", nullable = true, length = 255)
    public String getWalkTime() {
        return walkTime;
    }

    public void setWalkTime(String walkTime) {
        this.walkTime = walkTime;
    }

    private Integer walkHour;

    @Basic
    @javax.persistence.Column(name = "walk_hour", nullable = true)
    public Integer getWalkHour() {
        return walkHour;
    }

    public void setWalkHour(Integer walkHour) {
        this.walkHour = walkHour;
    }

    private String walkMilleage;

    @Basic
    @javax.persistence.Column(name = "walk_milleage", nullable = true, length = 255)
    public String getWalkMilleage() {
        return walkMilleage;
    }

    public void setWalkMilleage(String walkMilleage) {
        this.walkMilleage = walkMilleage;
    }

    private Double busCost;

    @Basic
    @javax.persistence.Column(name = "bus_cost", nullable = true, precision = 2)
    public Double getBusCost() {
        return busCost;
    }

    public void setBusCost(Double busCost) {
        this.busCost = busCost;
    }

    private String busDetail;

    @Basic
    @javax.persistence.Column(name = "bus_detail", nullable = true, length = 4096)
    public String getBusDetail() {
        return busDetail;
    }

    public void setBusDetail(String busDetail) {
        this.busDetail = busDetail;
    }

    private String busTime;

    @Basic
    @javax.persistence.Column(name = "bus_time", nullable = true, length = 255)
    public String getBusTime() {
        return busTime;
    }

    public void setBusTime(String busTime) {
        this.busTime = busTime;
    }

    private Integer busHour;

    @Basic
    @javax.persistence.Column(name = "bus_hour", nullable = true)
    public Integer getBusHour() {
        return busHour;
    }

    public void setBusHour(Integer busHour) {
        this.busHour = busHour;
    }

    private String busMilleage;

    @Basic
    @javax.persistence.Column(name = "bus_milleage", nullable = true, length = 255)
    public String getBusMilleage() {
        return busMilleage;
    }

    public void setBusMilleage(String busMilleage) {
        this.busMilleage = busMilleage;
    }

    private String exa;

    @Basic
    @javax.persistence.Column(name = "exa", nullable = true, length = -1)
    public String getExa() {
        return exa;
    }

    public void setExa(String exa) {
        this.exa = exa;
    }

    private Integer lineDis;

    @Basic
    @javax.persistence.Column(name = "line_dis", nullable = true)
    public Integer getLineDis() {
        return lineDis;
    }

    public void setLineDis(Integer lineDis) {
        this.lineDis = lineDis;
    }

    private Double oldPrice;

    @Basic
    @javax.persistence.Column(name = "old_price", nullable = true, precision = 2)
    public Double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    private Double userPrice;

    @Basic
    @javax.persistence.Column(name = "user_price", nullable = true, precision = 2)
    public Double getUserPrice() {
        return userPrice;
    }

    public void setUserPrice(Double userPrice) {
        this.userPrice = userPrice;
    }

    private Integer adviceHours;

    @Basic
    @javax.persistence.Column(name = "advice_hours", nullable = true)
    public Integer getAdviceHours() {
        return adviceHours;
    }

    public void setAdviceHours(Integer adviceHours) {
        this.adviceHours = adviceHours;
    }

    private String cityCode;

    @Basic
    @javax.persistence.Column(name = "city_code", nullable = true, length = 20)
    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    private Long delicacyId;

    @Basic
    @javax.persistence.Column(name = "delicacy_id", nullable = true)
    public Long getDelicacyId() {
        return delicacyId;
    }

    public void setDelicacyId(Long delicacyId) {
        this.delicacyId = delicacyId;
    }

    private String scenicName;

    @Basic
    @javax.persistence.Column(name = "scenic_name", nullable = true, length = 255)
    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    private String coverImg;

    @Basic
    @javax.persistence.Column(name = "cover_img", nullable = true, length = 255)
    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    private Integer imgWidth;

    @Basic
    @javax.persistence.Column(name = "img_width", nullable = true)
    public Integer getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(Integer imgWidth) {
        this.imgWidth = imgWidth;
    }

    private Integer imgHeight;

    @Basic
    @javax.persistence.Column(name = "img_height", nullable = true)
    public Integer getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(Integer imgHeight) {
        this.imgHeight = imgHeight;
    }

    private Integer sort;

    @Basic
    @javax.persistence.Column(name = "sort", nullable = true)
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    private String dataSource;

    @Basic
    @javax.persistence.Column(name = "data_source", nullable = true, length = 45)
    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    private Integer dataSourceId;

    @Basic
    @javax.persistence.Column(name = "data_source_id", nullable = true)
    public Integer getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Integer dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    private String dataSourceType;

    @Basic
    @javax.persistence.Column(name = "data_source_type", nullable = true, length = 45)
    public String getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(String dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecommendPlanTrip that = (RecommendPlanTrip) o;

        if (id != that.id) return false;
        if (recplanId != null ? !recplanId.equals(that.recplanId) : that.recplanId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (recdayId != null ? !recdayId.equals(that.recdayId) : that.recdayId != null) return false;
        if (scenicId != null ? !scenicId.equals(that.scenicId) : that.scenicId != null) return false;
        if (trafficType != null ? !trafficType.equals(that.trafficType) : that.trafficType != null) return false;
        if (tripType != null ? !tripType.equals(that.tripType) : that.tripType != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (orderNum != null ? !orderNum.equals(that.orderNum) : that.orderNum != null) return false;
        if (tripDesc != null ? !tripDesc.equals(that.tripDesc) : that.tripDesc != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (taxiCost != null ? !taxiCost.equals(that.taxiCost) : that.taxiCost != null) return false;
        if (taxiDetail != null ? !taxiDetail.equals(that.taxiDetail) : that.taxiDetail != null) return false;
        if (taxiTime != null ? !taxiTime.equals(that.taxiTime) : that.taxiTime != null) return false;
        if (taxiHour != null ? !taxiHour.equals(that.taxiHour) : that.taxiHour != null) return false;
        if (taxiMilleage != null ? !taxiMilleage.equals(that.taxiMilleage) : that.taxiMilleage != null) return false;
        if (walkDetail != null ? !walkDetail.equals(that.walkDetail) : that.walkDetail != null) return false;
        if (walkTime != null ? !walkTime.equals(that.walkTime) : that.walkTime != null) return false;
        if (walkHour != null ? !walkHour.equals(that.walkHour) : that.walkHour != null) return false;
        if (walkMilleage != null ? !walkMilleage.equals(that.walkMilleage) : that.walkMilleage != null) return false;
        if (busCost != null ? !busCost.equals(that.busCost) : that.busCost != null) return false;
        if (busDetail != null ? !busDetail.equals(that.busDetail) : that.busDetail != null) return false;
        if (busTime != null ? !busTime.equals(that.busTime) : that.busTime != null) return false;
        if (busHour != null ? !busHour.equals(that.busHour) : that.busHour != null) return false;
        if (busMilleage != null ? !busMilleage.equals(that.busMilleage) : that.busMilleage != null) return false;
        if (exa != null ? !exa.equals(that.exa) : that.exa != null) return false;
        if (lineDis != null ? !lineDis.equals(that.lineDis) : that.lineDis != null) return false;
        if (oldPrice != null ? !oldPrice.equals(that.oldPrice) : that.oldPrice != null) return false;
        if (userPrice != null ? !userPrice.equals(that.userPrice) : that.userPrice != null) return false;
        if (adviceHours != null ? !adviceHours.equals(that.adviceHours) : that.adviceHours != null) return false;
        if (cityCode != null ? !cityCode.equals(that.cityCode) : that.cityCode != null) return false;
        if (delicacyId != null ? !delicacyId.equals(that.delicacyId) : that.delicacyId != null) return false;
        if (scenicName != null ? !scenicName.equals(that.scenicName) : that.scenicName != null) return false;
        if (coverImg != null ? !coverImg.equals(that.coverImg) : that.coverImg != null) return false;
        if (imgWidth != null ? !imgWidth.equals(that.imgWidth) : that.imgWidth != null) return false;
        if (imgHeight != null ? !imgHeight.equals(that.imgHeight) : that.imgHeight != null) return false;
        if (sort != null ? !sort.equals(that.sort) : that.sort != null) return false;
        if (dataSource != null ? !dataSource.equals(that.dataSource) : that.dataSource != null) return false;
        if (dataSourceId != null ? !dataSourceId.equals(that.dataSourceId) : that.dataSourceId != null) return false;
        if (dataSourceType != null ? !dataSourceType.equals(that.dataSourceType) : that.dataSourceType != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (recplanId != null ? recplanId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (recdayId != null ? recdayId.hashCode() : 0);
        result = 31 * result + (scenicId != null ? scenicId.hashCode() : 0);
        result = 31 * result + (trafficType != null ? trafficType.hashCode() : 0);
        result = 31 * result + (tripType != null ? tripType.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (orderNum != null ? orderNum.hashCode() : 0);
        result = 31 * result + (tripDesc != null ? tripDesc.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (taxiCost != null ? taxiCost.hashCode() : 0);
        result = 31 * result + (taxiDetail != null ? taxiDetail.hashCode() : 0);
        result = 31 * result + (taxiTime != null ? taxiTime.hashCode() : 0);
        result = 31 * result + (taxiHour != null ? taxiHour.hashCode() : 0);
        result = 31 * result + (taxiMilleage != null ? taxiMilleage.hashCode() : 0);
        result = 31 * result + (walkDetail != null ? walkDetail.hashCode() : 0);
        result = 31 * result + (walkTime != null ? walkTime.hashCode() : 0);
        result = 31 * result + (walkHour != null ? walkHour.hashCode() : 0);
        result = 31 * result + (walkMilleage != null ? walkMilleage.hashCode() : 0);
        result = 31 * result + (busCost != null ? busCost.hashCode() : 0);
        result = 31 * result + (busDetail != null ? busDetail.hashCode() : 0);
        result = 31 * result + (busTime != null ? busTime.hashCode() : 0);
        result = 31 * result + (busHour != null ? busHour.hashCode() : 0);
        result = 31 * result + (busMilleage != null ? busMilleage.hashCode() : 0);
        result = 31 * result + (exa != null ? exa.hashCode() : 0);
        result = 31 * result + (lineDis != null ? lineDis.hashCode() : 0);
        result = 31 * result + (oldPrice != null ? oldPrice.hashCode() : 0);
        result = 31 * result + (userPrice != null ? userPrice.hashCode() : 0);
        result = 31 * result + (adviceHours != null ? adviceHours.hashCode() : 0);
        result = 31 * result + (cityCode != null ? cityCode.hashCode() : 0);
        result = 31 * result + (delicacyId != null ? delicacyId.hashCode() : 0);
        result = 31 * result + (scenicName != null ? scenicName.hashCode() : 0);
        result = 31 * result + (coverImg != null ? coverImg.hashCode() : 0);
        result = 31 * result + (imgWidth != null ? imgWidth.hashCode() : 0);
        result = 31 * result + (imgHeight != null ? imgHeight.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (dataSource != null ? dataSource.hashCode() : 0);
        result = 31 * result + (dataSourceId != null ? dataSourceId.hashCode() : 0);
        result = 31 * result + (dataSourceType != null ? dataSourceType.hashCode() : 0);
        return result;
    }
}
