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
@javax.persistence.Table(name = "tb_delicacy")
public class TbDelicacy extends com.framework.hibernate.util.Entity {
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

    private Long cityId;

    @Basic
    @javax.persistence.Column(name = "city_id", nullable = true, insertable = true, updatable = true)
    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    private String foodName;

    @Basic
    @javax.persistence.Column(name = "food_name", nullable = true, insertable = true, updatable = true, length = 20)
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    private Double price;

    @Basic
    @javax.persistence.Column(name = "price", nullable = true, insertable = true, updatable = true, precision = 0)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    private String cuisine;

    @Basic
    @javax.persistence.Column(name = "cuisine", nullable = true, insertable = true, updatable = true, length = 10)
    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    private String taste;

    @Basic
    @javax.persistence.Column(name = "taste", nullable = true, insertable = true, updatable = true, length = 32)
    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    private String bussinessCircle;

    @Basic
    @javax.persistence.Column(name = "bussiness_circle", nullable = true, insertable = true, updatable = true, length = 32)
    public String getBussinessCircle() {
        return bussinessCircle;
    }

    public void setBussinessCircle(String bussinessCircle) {
        this.bussinessCircle = bussinessCircle;
    }

    private String sign;

    @Basic
    @javax.persistence.Column(name = "sign", nullable = true, insertable = true, updatable = true, length = 32)
    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    private String efficacy;

    @Basic
    @javax.persistence.Column(name = "efficacy", nullable = true, insertable = true, updatable = true, length = 32)
    public String getEfficacy() {
        return efficacy;
    }

    public void setEfficacy(String efficacy) {
        this.efficacy = efficacy;
    }

    private Long userId;

    @Basic
    @javax.persistence.Column(name = "user_id", nullable = true, insertable = true, updatable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private String foodPicture;

    @Basic
    @javax.persistence.Column(name = "food_picture", nullable = true, insertable = true, updatable = true, length = 200)
    public String getFoodPicture() {
        return foodPicture;
    }

    public void setFoodPicture(String foodPicture) {
        this.foodPicture = foodPicture;
    }

    private String introduction;

    @Basic
    @javax.persistence.Column(name = "introduction", nullable = true, insertable = true, updatable = true, length = 4096)
    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    private Short mustEat;

    @Basic
    @javax.persistence.Column(name = "must_eat", nullable = true, insertable = true, updatable = true)
    public Short getMustEat() {
        return mustEat;
    }

    public void setMustEat(Short mustEat) {
        this.mustEat = mustEat;
    }

    private Short recommendCount;

    @Basic
    @javax.persistence.Column(name = "recommend_count", nullable = true, insertable = true, updatable = true)
    public Short getRecommendCount() {
        return recommendCount;
    }

    public void setRecommendCount(Short recommendCount) {
        this.recommendCount = recommendCount;
    }

    private Short localNum;

    @Basic
    @javax.persistence.Column(name = "local_num", nullable = true, insertable = true, updatable = true)
    public Short getLocalNum() {
        return localNum;
    }

    public void setLocalNum(Short localNum) {
        this.localNum = localNum;
    }

    private Short touristNum;

    @Basic
    @javax.persistence.Column(name = "tourist_num", nullable = true, insertable = true, updatable = true)
    public Short getTouristNum() {
        return touristNum;
    }

    public void setTouristNum(Short touristNum) {
        this.touristNum = touristNum;
    }

    private Short goodNum;

    @Basic
    @javax.persistence.Column(name = "good_num", nullable = true, insertable = true, updatable = true)
    public Short getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Short goodNum) {
        this.goodNum = goodNum;
    }

    private Short shareNum;

    @Basic
    @javax.persistence.Column(name = "share_num", nullable = true, insertable = true, updatable = true)
    public Short getShareNum() {
        return shareNum;
    }

    public void setShareNum(Short shareNum) {
        this.shareNum = shareNum;
    }

    private String delicacyComment;

    @Basic
    @javax.persistence.Column(name = "delicacy_comment", nullable = true, insertable = true, updatable = true, length = 500)
    public String getDelicacyComment() {
        return delicacyComment;
    }

    public void setDelicacyComment(String delicacyComment) {
        this.delicacyComment = delicacyComment;
    }

    private String recommendReson;

    @Basic
    @javax.persistence.Column(name = "recommend_reson", nullable = true, insertable = true, updatable = true, length = 256)
    public String getRecommendReson() {
        return recommendReson;
    }

    public void setRecommendReson(String recommendReson) {
        this.recommendReson = recommendReson;
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

        TbDelicacy that = (TbDelicacy) o;

        if (id != that.id) return false;
        if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) return false;
        if (foodName != null ? !foodName.equals(that.foodName) : that.foodName != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (cuisine != null ? !cuisine.equals(that.cuisine) : that.cuisine != null) return false;
        if (taste != null ? !taste.equals(that.taste) : that.taste != null) return false;
        if (bussinessCircle != null ? !bussinessCircle.equals(that.bussinessCircle) : that.bussinessCircle != null)
            return false;
        if (sign != null ? !sign.equals(that.sign) : that.sign != null) return false;
        if (efficacy != null ? !efficacy.equals(that.efficacy) : that.efficacy != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (foodPicture != null ? !foodPicture.equals(that.foodPicture) : that.foodPicture != null) return false;
        if (introduction != null ? !introduction.equals(that.introduction) : that.introduction != null) return false;
        if (mustEat != null ? !mustEat.equals(that.mustEat) : that.mustEat != null) return false;
        if (recommendCount != null ? !recommendCount.equals(that.recommendCount) : that.recommendCount != null)
            return false;
        if (localNum != null ? !localNum.equals(that.localNum) : that.localNum != null) return false;
        if (touristNum != null ? !touristNum.equals(that.touristNum) : that.touristNum != null) return false;
        if (goodNum != null ? !goodNum.equals(that.goodNum) : that.goodNum != null) return false;
        if (shareNum != null ? !shareNum.equals(that.shareNum) : that.shareNum != null) return false;
        if (delicacyComment != null ? !delicacyComment.equals(that.delicacyComment) : that.delicacyComment != null)
            return false;
        if (recommendReson != null ? !recommendReson.equals(that.recommendReson) : that.recommendReson != null)
            return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (sourceId != null ? !sourceId.equals(that.sourceId) : that.sourceId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (foodName != null ? foodName.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (cuisine != null ? cuisine.hashCode() : 0);
        result = 31 * result + (taste != null ? taste.hashCode() : 0);
        result = 31 * result + (bussinessCircle != null ? bussinessCircle.hashCode() : 0);
        result = 31 * result + (sign != null ? sign.hashCode() : 0);
        result = 31 * result + (efficacy != null ? efficacy.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (foodPicture != null ? foodPicture.hashCode() : 0);
        result = 31 * result + (introduction != null ? introduction.hashCode() : 0);
        result = 31 * result + (mustEat != null ? mustEat.hashCode() : 0);
        result = 31 * result + (recommendCount != null ? recommendCount.hashCode() : 0);
        result = 31 * result + (localNum != null ? localNum.hashCode() : 0);
        result = 31 * result + (touristNum != null ? touristNum.hashCode() : 0);
        result = 31 * result + (goodNum != null ? goodNum.hashCode() : 0);
        result = 31 * result + (shareNum != null ? shareNum.hashCode() : 0);
        result = 31 * result + (delicacyComment != null ? delicacyComment.hashCode() : 0);
        result = 31 * result + (recommendReson != null ? recommendReson.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (sourceId != null ? sourceId.hashCode() : 0);
        return result;
    }
}
