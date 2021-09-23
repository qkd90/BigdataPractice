package com.data.data.hmly.service.restaurant.entity;

import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.entity.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "restaurant")
public class Restaurant extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "score")
    private Double score;
    @Column(name = "ranking")
    private Integer ranking;
    @Column(name = "price")
    private Integer price;
    @Column(name = "cover")
    private String cover;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_code")
    private TbArea city;
    @Column(name = "is_show")
    private Boolean isShow;
    @Column(name = "status")
    private Integer status;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
    @Column(name = "feature")
    private String feature;
    @Column(name = "hot_num")
    private Integer hotNum;
    @Column(name = "source_id")
    private String sourceId;
    @OneToOne(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private RestaurantExtend extend;
    @OneToOne(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private RestaurantGeoInfo geoInfo;

    public Restaurant() {

    }

    public Restaurant(Long id, Double score) {
        super();
        this.id = id;
        this.score = score;
    }

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

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public TbArea getCity() {
        return city;
    }

    public void setCity(TbArea city) {
        this.city = city;
    }

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Integer getHotNum() {
        return hotNum;
    }

    public void setHotNum(Integer hotNum) {
        this.hotNum = hotNum;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public RestaurantExtend getExtend() {
        return extend;
    }

    public void setExtend(RestaurantExtend extend) {
        this.extend = extend;
    }

    public RestaurantGeoInfo getGeoInfo() {
        return geoInfo;
    }

    public void setGeoInfo(RestaurantGeoInfo geoInfo) {
        this.geoInfo = geoInfo;
    }
}
