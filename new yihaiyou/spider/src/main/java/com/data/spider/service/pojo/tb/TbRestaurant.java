package com.data.spider.service.pojo.tb;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by Sane on 15/9/17.
 */
@Entity
@javax.persistence.Table(name = "tb_restaurant")
public class TbRestaurant extends com.framework.hibernate.util.Entity {
    private long id;

    @Id
    @GeneratedValue
    @javax.persistence.Column(name = "id", nullable = false, insertable = true, updatable = true)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private Long dianpingId;

    @Basic
    @javax.persistence.Column(name = "dianping_id", nullable = true, insertable = true, updatable = true)
    public Long getDianpingId() {
        return dianpingId;
    }

    public void setDianpingId(Long dianpingId) {
        this.dianpingId = dianpingId;
    }

    private String resName;

    @Basic
    @javax.persistence.Column(name = "res_name", nullable = true, insertable = true, updatable = true, length = 32)
    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    private Integer price;

    @Basic
    @javax.persistence.Column(name = "price", nullable = true, insertable = true, updatable = true)
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    private String resPrice;

    @Basic
    @javax.persistence.Column(name = "res_price", nullable = true, insertable = true, updatable = true, length = 20)
    public String getResPrice() {
        return resPrice;
    }

    public void setResPrice(String resPrice) {
        this.resPrice = resPrice;
    }

    private String resAddress;

    @Basic
    @javax.persistence.Column(name = "res_address", nullable = true, insertable = true, updatable = true, length = 500)
    public String getResAddress() {
        return resAddress;
    }

    public void setResAddress(String resAddress) {
        this.resAddress = resAddress;
    }

    private String resPhone;

    @Basic
    @javax.persistence.Column(name = "res_phone", nullable = true, insertable = true, updatable = true, length = 100)
    public String getResPhone() {
        return resPhone;
    }

    public void setResPhone(String resPhone) {
        this.resPhone = resPhone;
    }

    private String resFeature;

    @Basic
    @javax.persistence.Column(name = "res_feature", nullable = true, insertable = true, updatable = true, length = 100)
    public String getResFeature() {
        return resFeature;
    }

    public void setResFeature(String resFeature) {
        this.resFeature = resFeature;
    }

    private String resPicture;

    @Basic
    @javax.persistence.Column(name = "res_picture", nullable = true, insertable = true, updatable = true, length = 200)
    public String getResPicture() {
        return resPicture;
    }

    public void setResPicture(String resPicture) {
        this.resPicture = resPicture;
    }

    private Double resLongitude;

    @Basic
    @javax.persistence.Column(name = "res_longitude", nullable = true, insertable = true, updatable = true, precision = 0)
    public Double getResLongitude() {
        return resLongitude;
    }

    public void setResLongitude(Double resLongitude) {
        this.resLongitude = resLongitude;
    }

    private Double resLatitude;

    @Basic
    @javax.persistence.Column(name = "res_latitude", nullable = true, insertable = true, updatable = true, precision = 0)
    public Double getResLatitude() {
        return resLatitude;
    }

    public void setResLatitude(Double resLatitude) {
        this.resLatitude = resLatitude;
    }

    private Double gcjLng;

    @Basic
    @javax.persistence.Column(name = "gcj_lng", nullable = true, insertable = true, updatable = true, precision = 0)
    public Double getGcjLng() {
        return gcjLng;
    }

    public void setGcjLng(Double gcjLng) {
        this.gcjLng = gcjLng;
    }

    private Double gcjLat;

    @Basic
    @javax.persistence.Column(name = "gcj_lat", nullable = true, insertable = true, updatable = true, precision = 0)
    public Double getGcjLat() {
        return gcjLat;
    }

    public void setGcjLat(Double gcjLat) {
        this.gcjLat = gcjLat;
    }

    private String resComment;

    @Basic
    @javax.persistence.Column(name = "res_comment", nullable = true, insertable = true, updatable = true, length = 500)
    public String getResComment() {
        return resComment;
    }

    public void setResComment(String resComment) {
        this.resComment = resComment;
    }

    private Integer cityCode;

    @Basic
    @javax.persistence.Column(name = "city_code", nullable = true, insertable = true, updatable = true)
    public Integer getCityCode() {
        return cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    private Integer score;

    @Basic
    @javax.persistence.Column(name = "score", nullable = true, insertable = true, updatable = true)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    private String shopHours;

    @Basic
    @javax.persistence.Column(name = "shop_hours", nullable = true, insertable = true, updatable = true, length = 100)
    public String getShopHours() {
        return shopHours;
    }

    public void setShopHours(String shopHours) {
        this.shopHours = shopHours;
    }

    private Short hotNum;

    @Basic
    @javax.persistence.Column(name = "hot_num", nullable = true, insertable = true, updatable = true)
    public Short getHotNum() {
        return hotNum;
    }

    public void setHotNum(Short hotNum) {
        this.hotNum = hotNum;
    }

    private String dianpingComment;

    @Basic
    @javax.persistence.Column(name = "dianping_comment", nullable = true, insertable = true, updatable = true, length = 65535)
    public String getDianpingComment() {
        return dianpingComment;
    }

    public void setDianpingComment(String dianpingComment) {
        this.dianpingComment = dianpingComment;
    }

    private Timestamp modifyTime;

    @Basic
    @javax.persistence.Column(name = "modify_time", nullable = true, insertable = true, updatable = true)
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    private Timestamp createTime;

    @Basic
    @javax.persistence.Column(name = "create_time", nullable = true, insertable = true, updatable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    private String sourceId;

    @Basic
    @javax.persistence.Column(name = "source_id", nullable = true, insertable = true, updatable = true, length = 32)
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbRestaurant that = (TbRestaurant) o;

        if (id != that.id) return false;
        if (dianpingId != null ? !dianpingId.equals(that.dianpingId) : that.dianpingId != null) return false;
        if (resName != null ? !resName.equals(that.resName) : that.resName != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (resPrice != null ? !resPrice.equals(that.resPrice) : that.resPrice != null) return false;
        if (resAddress != null ? !resAddress.equals(that.resAddress) : that.resAddress != null) return false;
        if (resPhone != null ? !resPhone.equals(that.resPhone) : that.resPhone != null) return false;
        if (resFeature != null ? !resFeature.equals(that.resFeature) : that.resFeature != null) return false;
        if (resPicture != null ? !resPicture.equals(that.resPicture) : that.resPicture != null) return false;
        if (resLongitude != null ? !resLongitude.equals(that.resLongitude) : that.resLongitude != null) return false;
        if (resLatitude != null ? !resLatitude.equals(that.resLatitude) : that.resLatitude != null) return false;
        if (gcjLng != null ? !gcjLng.equals(that.gcjLng) : that.gcjLng != null) return false;
        if (gcjLat != null ? !gcjLat.equals(that.gcjLat) : that.gcjLat != null) return false;
        if (resComment != null ? !resComment.equals(that.resComment) : that.resComment != null) return false;
        if (cityCode != null ? !cityCode.equals(that.cityCode) : that.cityCode != null) return false;
        if (score != null ? !score.equals(that.score) : that.score != null) return false;
        if (shopHours != null ? !shopHours.equals(that.shopHours) : that.shopHours != null) return false;
        if (hotNum != null ? !hotNum.equals(that.hotNum) : that.hotNum != null) return false;
        if (dianpingComment != null ? !dianpingComment.equals(that.dianpingComment) : that.dianpingComment != null)
            return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (sourceId != null ? !sourceId.equals(that.sourceId) : that.sourceId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (dianpingId != null ? dianpingId.hashCode() : 0);
        result = 31 * result + (resName != null ? resName.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (resPrice != null ? resPrice.hashCode() : 0);
        result = 31 * result + (resAddress != null ? resAddress.hashCode() : 0);
        result = 31 * result + (resPhone != null ? resPhone.hashCode() : 0);
        result = 31 * result + (resFeature != null ? resFeature.hashCode() : 0);
        result = 31 * result + (resPicture != null ? resPicture.hashCode() : 0);
        result = 31 * result + (resLongitude != null ? resLongitude.hashCode() : 0);
        result = 31 * result + (resLatitude != null ? resLatitude.hashCode() : 0);
        result = 31 * result + (gcjLng != null ? gcjLng.hashCode() : 0);
        result = 31 * result + (gcjLat != null ? gcjLat.hashCode() : 0);
        result = 31 * result + (resComment != null ? resComment.hashCode() : 0);
        result = 31 * result + (cityCode != null ? cityCode.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (shopHours != null ? shopHours.hashCode() : 0);
        result = 31 * result + (hotNum != null ? hotNum.hashCode() : 0);
        result = 31 * result + (dianpingComment != null ? dianpingComment.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (sourceId != null ? sourceId.hashCode() : 0);
        return result;
    }
}
