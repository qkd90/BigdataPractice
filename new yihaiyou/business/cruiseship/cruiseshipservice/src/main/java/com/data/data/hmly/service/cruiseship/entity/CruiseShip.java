package com.data.data.hmly.service.cruiseship.entity;
// Generated 2016-9-13 14:06:21 by Hibernate Tools 3.2.0.CR1


import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.goods.entity.Category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "cruise_ship")
@PrimaryKeyJoinColumn(name = "productId")
public class CruiseShip extends Product {
    @Column(name = "productNo")
    private String productNo;
    @Column(name = "startCityId")
    private Long startCityId;
    @Column(name = "startCity")
    private String startCity;
    @Column(name = "arriveCityId")
    private Long arriveCityId;
    @Column(name = "arriveCity")
    private String arriveCity;
    @ManyToOne
    @JoinColumn(name = "brand")
    private Category brand;
    @ManyToOne
    @JoinColumn(name = "route")
    private Category route;
    @Column(name = "services")
    private String services;
    @Column(name = "satisfaction")
    private Integer satisfaction;
    @Column(name = "commentNum")
    private Integer commentNum;
    @Column(name = "collectionNum")
    private Integer collectionNum;
    @Column(name = "attend")
    private Integer attend;
    @Column(name = "attendNoPassport")
    private Integer attendNoPassport;
    @Column(name = "attendNoVisa")
    private Integer attendNoVisa;
    @Column(name = "recommend")
    private String recommend;
    @Column(name = "playDay")
    private Integer playDay;
    @Column(name = "coverImage")
    private String coverImage;
    @Column(name = "remark")
    private String remark;

    @OneToOne(mappedBy = "cruiseShip", fetch = FetchType.LAZY)
    private CruiseShipExtend cruiseShipExtend;
    @OneToMany(mappedBy = "cruiseShip", fetch = FetchType.LAZY)
    private Set<CruiseShipDate> cruiseShipDates;
    @OneToMany(mappedBy = "cruiseShip", fetch = FetchType.LAZY)
    private Set<CruiseShipDeck> cruiseShipDecks;
    @OneToMany(mappedBy = "cruiseShip", fetch = FetchType.LAZY)
    private Set<CruiseShipPlan> cruiseShipPlans;
    @OneToMany(mappedBy = "cruiseShip", fetch = FetchType.LAZY)
    private Set<CruiseShipRoom> cruiseShipRooms;
    @OneToMany(mappedBy = "cruiseShip", fetch = FetchType.LAZY)
    private Set<CruiseShipVisa> cruiseShipVisas;

    /**
     * 页面字段
     */
    @Transient
    private String keyword;

    @Transient
    private String[] startDateRanges;   //出航日期范围
    @Transient
    private String routeName;   //航线名称

    @Transient
    private Integer avgScore;   //平均评分

    @Transient
    private Date startDate;   //出航日期

    @Transient
    private Date endDate;   //返回日期

    @Transient
    private Long brandId;   //品牌id

    @Transient
    private Long routeId;   //航线id

    @Transient
    private Long dateId;   //出发日期id



    public CruiseShip() {
    }

    public CruiseShip(Map<String, Object> mapObj) {

    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public Long getStartCityId() {
        return startCityId;
    }

    public void setStartCityId(Long startCityId) {
        this.startCityId = startCityId;
    }

    public Long getArriveCityId() {
        return arriveCityId;
    }

    public void setArriveCityId(Long arriveCityId) {
        this.arriveCityId = arriveCityId;
    }

    public Category getBrand() {
        return brand;
    }

    public void setBrand(Category brand) {
        this.brand = brand;
    }

    public Category getRoute() {
        return route;
    }

    public void setRoute(Category route) {
        this.route = route;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public Integer getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(Integer satisfaction) {
        this.satisfaction = satisfaction;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(Integer collectionNum) {
        this.collectionNum = collectionNum;
    }

    public Integer getAttend() {
        return attend;
    }

    public void setAttend(Integer attend) {
        this.attend = attend;
    }

    public Integer getAttendNoPassport() {
        return attendNoPassport;
    }

    public void setAttendNoPassport(Integer attendNoPassport) {
        this.attendNoPassport = attendNoPassport;
    }

    public Integer getAttendNoVisa() {
        return attendNoVisa;
    }

    public void setAttendNoVisa(Integer attendNoVisa) {
        this.attendNoVisa = attendNoVisa;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public Integer getPlayDay() {
        return playDay;
    }

    public void setPlayDay(Integer playDay) {
        this.playDay = playDay;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public CruiseShipExtend getCruiseShipExtend() {
        return cruiseShipExtend;
    }

    public void setCruiseShipExtend(CruiseShipExtend cruiseShipExtend) {
        this.cruiseShipExtend = cruiseShipExtend;
    }

    public Set<CruiseShipDate> getCruiseShipDates() {
        return cruiseShipDates;
    }

    public void setCruiseShipDates(Set<CruiseShipDate> cruiseShipDates) {
        this.cruiseShipDates = cruiseShipDates;
    }

    public Set<CruiseShipDeck> getCruiseShipDecks() {
        return cruiseShipDecks;
    }

    public void setCruiseShipDecks(Set<CruiseShipDeck> cruiseShipDecks) {
        this.cruiseShipDecks = cruiseShipDecks;
    }

    public Set<CruiseShipPlan> getCruiseShipPlans() {
        return cruiseShipPlans;
    }

    public void setCruiseShipPlans(Set<CruiseShipPlan> cruiseShipPlans) {
        this.cruiseShipPlans = cruiseShipPlans;
    }

    public Set<CruiseShipRoom> getCruiseShipRooms() {
        return cruiseShipRooms;
    }

    public void setCruiseShipRooms(Set<CruiseShipRoom> cruiseShipRooms) {
        this.cruiseShipRooms = cruiseShipRooms;
    }

    public Set<CruiseShipVisa> getCruiseShipVisas() {
        return cruiseShipVisas;
    }

    public void setCruiseShipVisas(Set<CruiseShipVisa> cruiseShipVisas) {
        this.cruiseShipVisas = cruiseShipVisas;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getArriveCity() {
        return arriveCity;
    }

    public void setArriveCity(String arriveCity) {
        this.arriveCity = arriveCity;
    }

    public String[] getStartDateRanges() {
        return startDateRanges;
    }

    public void setStartDateRanges(String[] startDateRanges) {
        this.startDateRanges = startDateRanges;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public Integer getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(Integer avgScore) {
        this.avgScore = avgScore;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Long getDateId() {
        return dateId;
    }

    public void setDateId(Long dateId) {
        this.dateId = dateId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}


