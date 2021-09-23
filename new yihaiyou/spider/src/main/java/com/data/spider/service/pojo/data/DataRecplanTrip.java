package com.data.spider.service.pojo.data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by Sane on 15/10/27.
 */
@Entity
@javax.persistence.Table(name = "data_recplan_trip")
public class DataRecplanTrip extends com.framework.hibernate.util.Entity {
    private long id;
    private Long recplanId;
    private Long userId;
    private Long recdayId;
    private Long scenicId;
    private Integer travelType;
    private Integer tripType;
    private Time startTime;
    private Integer orderNum;
    private String tripDesc;
    private Timestamp modifyTime;
    private Timestamp createTime;
    private Double taxiCost;
    private String taxiDetail;
    private String taxiTime;
    private Integer taxiHour;
    private String taxiMilleage;
    private String walkDetail;
    private String walkTime;
    private Integer walkHour;
    private String walkMilleage;
    private Double busCost;
    private String busDetail;
    private String busTime;
    private Integer busHour;
    private String busMilleage;
    private String exa;
    private Integer lineDis;
    private Double oldPrice;
    private Double userPrice;
    private Integer adviceHours;
    private String cityCode;
    private Long delicacyId;
    private String scenicName;
    private String coverImg;
    private Integer imgWidth;
    private Integer imgHeight;
    private Integer sort;
    private String dataSource;
    private Integer dataSourceId;
    private String dataSourceType;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "recplan_id", nullable = true, insertable = true, updatable = true)
    public Long getRecplanId() {
        return recplanId;
    }

    public void setRecplanId(Long recplanId) {
        this.recplanId = recplanId;
    }

    @Basic
    @Column(name = "user_id", nullable = true, insertable = true, updatable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "recday_id", nullable = true, insertable = true, updatable = true)
    public Long getRecdayId() {
        return recdayId;
    }

    public void setRecdayId(Long recdayId) {
        this.recdayId = recdayId;
    }

    @Basic
    @Column(name = "scenic_id", nullable = true, insertable = true, updatable = true)
    public Long getScenicId() {
        return scenicId;
    }

    public void setScenicId(Long scenicId) {
        this.scenicId = scenicId;
    }

    @Basic
    @Column(name = "travel_type", nullable = true, insertable = true, updatable = true)
    public Integer getTravelType() {
        return travelType;
    }

    public void setTravelType(Integer travelType) {
        this.travelType = travelType;
    }

    @Basic
    @Column(name = "trip_type", nullable = true, insertable = true, updatable = true)
    public Integer getTripType() {
        return tripType;
    }

    public void setTripType(Integer tripType) {
        this.tripType = tripType;
    }

    @Basic
    @Column(name = "start_time", nullable = true, insertable = true, updatable = true)
    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "order_num", nullable = true, insertable = true, updatable = true)
    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    @Basic
    @Column(name = "trip_desc", nullable = true, insertable = true, updatable = true, length = 256)
    public String getTripDesc() {
        return tripDesc;
    }

    public void setTripDesc(String tripDesc) {
        this.tripDesc = tripDesc;
    }

    @Basic
    @Column(name = "modify_time", nullable = true, insertable = true, updatable = true)
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Basic
    @Column(name = "create_time", nullable = true, insertable = true, updatable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "taxi_cost", nullable = true, insertable = true, updatable = true, precision = 2)
    public Double getTaxiCost() {
        return taxiCost;
    }

    public void setTaxiCost(Double taxiCost) {
        this.taxiCost = taxiCost;
    }

    @Basic
    @Column(name = "taxi_detail", nullable = true, insertable = true, updatable = true, length = 4096)
    public String getTaxiDetail() {
        return taxiDetail;
    }

    public void setTaxiDetail(String taxiDetail) {
        this.taxiDetail = taxiDetail;
    }

    @Basic
    @Column(name = "taxi_time", nullable = true, insertable = true, updatable = true, length = 255)
    public String getTaxiTime() {
        return taxiTime;
    }

    public void setTaxiTime(String taxiTime) {
        this.taxiTime = taxiTime;
    }

    @Basic
    @Column(name = "taxi_hour", nullable = true, insertable = true, updatable = true)
    public Integer getTaxiHour() {
        return taxiHour;
    }

    public void setTaxiHour(Integer taxiHour) {
        this.taxiHour = taxiHour;
    }

    @Basic
    @Column(name = "taxi_milleage", nullable = true, insertable = true, updatable = true, length = 255)
    public String getTaxiMilleage() {
        return taxiMilleage;
    }

    public void setTaxiMilleage(String taxiMilleage) {
        this.taxiMilleage = taxiMilleage;
    }

    @Basic
    @Column(name = "walk_detail", nullable = true, insertable = true, updatable = true, length = 4096)
    public String getWalkDetail() {
        return walkDetail;
    }

    public void setWalkDetail(String walkDetail) {
        this.walkDetail = walkDetail;
    }

    @Basic
    @Column(name = "walk_time", nullable = true, insertable = true, updatable = true, length = 255)
    public String getWalkTime() {
        return walkTime;
    }

    public void setWalkTime(String walkTime) {
        this.walkTime = walkTime;
    }

    @Basic
    @Column(name = "walk_hour", nullable = true, insertable = true, updatable = true)
    public Integer getWalkHour() {
        return walkHour;
    }

    public void setWalkHour(Integer walkHour) {
        this.walkHour = walkHour;
    }

    @Basic
    @Column(name = "walk_milleage", nullable = true, insertable = true, updatable = true, length = 255)
    public String getWalkMilleage() {
        return walkMilleage;
    }

    public void setWalkMilleage(String walkMilleage) {
        this.walkMilleage = walkMilleage;
    }

    @Basic
    @Column(name = "bus_cost", nullable = true, insertable = true, updatable = true, precision = 2)
    public Double getBusCost() {
        return busCost;
    }

    public void setBusCost(Double busCost) {
        this.busCost = busCost;
    }

    @Basic
    @Column(name = "bus_detail", nullable = true, insertable = true, updatable = true, length = 4096)
    public String getBusDetail() {
        return busDetail;
    }

    public void setBusDetail(String busDetail) {
        this.busDetail = busDetail;
    }

    @Basic
    @Column(name = "bus_time", nullable = true, insertable = true, updatable = true, length = 255)
    public String getBusTime() {
        return busTime;
    }

    public void setBusTime(String busTime) {
        this.busTime = busTime;
    }

    @Basic
    @Column(name = "bus_hour", nullable = true, insertable = true, updatable = true)
    public Integer getBusHour() {
        return busHour;
    }

    public void setBusHour(Integer busHour) {
        this.busHour = busHour;
    }

    @Basic
    @Column(name = "bus_milleage", nullable = true, insertable = true, updatable = true, length = 255)
    public String getBusMilleage() {
        return busMilleage;
    }

    public void setBusMilleage(String busMilleage) {
        this.busMilleage = busMilleage;
    }

    @Basic
    @Column(name = "exa", nullable = true, insertable = true, updatable = true, length = 65535)
    public String getExa() {
        return exa;
    }

    public void setExa(String exa) {
        this.exa = exa;
    }

    @Basic
    @Column(name = "line_dis", nullable = true, insertable = true, updatable = true)
    public Integer getLineDis() {
        return lineDis;
    }

    public void setLineDis(Integer lineDis) {
        this.lineDis = lineDis;
    }

    @Basic
    @Column(name = "old_price", nullable = true, insertable = true, updatable = true, precision = 2)
    public Double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    @Basic
    @Column(name = "user_price", nullable = true, insertable = true, updatable = true, precision = 2)
    public Double getUserPrice() {
        return userPrice;
    }

    public void setUserPrice(Double userPrice) {
        this.userPrice = userPrice;
    }

    @Basic
    @Column(name = "advice_hours", nullable = true, insertable = true, updatable = true)
    public Integer getAdviceHours() {
        return adviceHours;
    }

    public void setAdviceHours(Integer adviceHours) {
        this.adviceHours = adviceHours;
    }

    @Basic
    @Column(name = "city_code", nullable = true, insertable = true, updatable = true, length = 20)
    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    @Basic
    @Column(name = "delicacy_id", nullable = true, insertable = true, updatable = true)
    public Long getDelicacyId() {
        return delicacyId;
    }

    public void setDelicacyId(Long delicacyId) {
        this.delicacyId = delicacyId;
    }

    @Basic
    @Column(name = "scenic_name", nullable = true, insertable = true, updatable = true, length = 255)
    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    @Basic
    @Column(name = "cover_img", nullable = true, insertable = true, updatable = true, length = 255)
    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    @Basic
    @Column(name = "img_width", nullable = true, insertable = true, updatable = true)
    public Integer getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(Integer imgWidth) {
        this.imgWidth = imgWidth;
    }

    @Basic
    @Column(name = "img_height", nullable = true, insertable = true, updatable = true)
    public Integer getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(Integer imgHeight) {
        this.imgHeight = imgHeight;
    }

    @Basic
    @Column(name = "sort", nullable = true, insertable = true, updatable = true)
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Basic
    @Column(name = "data_source", nullable = true, insertable = true, updatable = true, length = 45)
    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    @Basic
    @Column(name = "data_source_id", nullable = true, insertable = true, updatable = true)
    public Integer getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Integer dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    @Basic
    @Column(name = "data_source_type", nullable = true, insertable = true, updatable = true, length = 45)
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

        DataRecplanTrip that = (DataRecplanTrip) o;

        if (id != that.id) return false;
        if (recplanId != null ? !recplanId.equals(that.recplanId) : that.recplanId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (recdayId != null ? !recdayId.equals(that.recdayId) : that.recdayId != null) return false;
        if (scenicId != null ? !scenicId.equals(that.scenicId) : that.scenicId != null) return false;
        if (travelType != null ? !travelType.equals(that.travelType) : that.travelType != null) return false;
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
        if (dataSourceType != null ? !dataSourceType.equals(that.dataSourceType) : that.dataSourceType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (recplanId != null ? recplanId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (recdayId != null ? recdayId.hashCode() : 0);
        result = 31 * result + (scenicId != null ? scenicId.hashCode() : 0);
        result = 31 * result + (travelType != null ? travelType.hashCode() : 0);
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
