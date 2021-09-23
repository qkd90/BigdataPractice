package com.data.data.hmly.service.scenic.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "data_scenic_relation_details")
public class DataScenicRelationDetails extends com.framework.hibernate.util.Entity implements java.io.Serializable {
    private Long scenicId;
    private Integer name;
    private Integer cityCode;
    private Integer star;
    private Integer price;
    private Integer score;
    private Integer address;
    private Integer telephone;
    private Integer goingCount;
    private Integer cameCount;
    private Integer openTime;
    private Integer adviceTime;
    private Integer adviceHours;
    private Integer bestTime;
    private Integer ticket;
    private Integer commenter;
    private Integer shortComment;
    private Integer coverLarge;
    private Integer coverMedium;
    private Integer coverSmall;
    private Long father;
    private Integer fatherName;
    private Integer ifHasChild;
    private Integer longitude;
    private Integer latitude;
    private Integer url;
    private Integer isCity;
    private Integer isStation;
    private Integer stationType;
    private Integer gcjLng;
    private Integer gcjLat;
    private Integer lowestPrice;
    private Integer highestPrice;
    private Integer marketPrice;
    private Integer status;
    private Integer flagHasTaxi;
    private Integer flagHasBus;
    private Integer flagThreeLevelRegion;
    private Integer dataSource;
    private Integer dataSourceId;
    private Integer dataSourceUrl;
    private Integer dataHtml;
    private Integer xml;
    private Integer level;
    private Integer ranking;
    private Integer bestHour;
    private Integer introduction;
    private Integer website;
    private Integer advice;
    private Integer theme;
    private Integer how;
    private Integer scenicType;
    private Integer notice;
    private Integer guide;
    private Integer parentsThreeLevelRegion;
    private Integer hpl;
    private Integer recommendReason;
    private Integer etlStatus;

    @Id
    @Column(name = "scenic_id")
    public Long getScenicId() {
        return scenicId;
    }

    public void setScenicId(Long scenicId) {
        this.scenicId = scenicId;
    }

    @Basic
    @Column(name = "name")
    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    @Basic
    @Column(name = "city_code")
    public Integer getCityCode() {
        return cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    @Basic
    @Column(name = "star")
    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    @Basic
    @Column(name = "price")
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Basic
    @Column(name = "score")
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Basic
    @Column(name = "address")
    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    @Basic
    @Column(name = "telephone")
    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "going_count")
    public Integer getGoingCount() {
        return goingCount;
    }

    public void setGoingCount(Integer goingCount) {
        this.goingCount = goingCount;
    }

    @Basic
    @Column(name = "came_count")
    public Integer getCameCount() {
        return cameCount;
    }

    public void setCameCount(Integer cameCount) {
        this.cameCount = cameCount;
    }

    @Basic
    @Column(name = "open_time")
    public Integer getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Integer openTime) {
        this.openTime = openTime;
    }

    @Basic
    @Column(name = "advice_time")
    public Integer getAdviceTime() {
        return adviceTime;
    }

    public void setAdviceTime(Integer adviceTime) {
        this.adviceTime = adviceTime;
    }

    @Basic
    @Column(name = "advice_hours")
    public Integer getAdviceHours() {
        return adviceHours;
    }

    public void setAdviceHours(Integer adviceHours) {
        this.adviceHours = adviceHours;
    }

    @Basic
    @Column(name = "best_time")
    public Integer getBestTime() {
        return bestTime;
    }

    public void setBestTime(Integer bestTime) {
        this.bestTime = bestTime;
    }

    @Basic
    @Column(name = "ticket")
    public Integer getTicket() {
        return ticket;
    }

    public void setTicket(Integer ticket) {
        this.ticket = ticket;
    }

    @Basic
    @Column(name = "commenter")
    public Integer getCommenter() {
        return commenter;
    }

    public void setCommenter(Integer commenter) {
        this.commenter = commenter;
    }

    @Basic
    @Column(name = "short_comment")
    public Integer getShortComment() {
        return shortComment;
    }

    public void setShortComment(Integer shortComment) {
        this.shortComment = shortComment;
    }

    @Basic
    @Column(name = "cover_large")
    public Integer getCoverLarge() {
        return coverLarge;
    }

    public void setCoverLarge(Integer coverLarge) {
        this.coverLarge = coverLarge;
    }

    @Basic
    @Column(name = "cover_medium")
    public Integer getCoverMedium() {
        return coverMedium;
    }

    public void setCoverMedium(Integer coverMedium) {
        this.coverMedium = coverMedium;
    }

    @Basic
    @Column(name = "cover_small")
    public Integer getCoverSmall() {
        return coverSmall;
    }

    public void setCoverSmall(Integer coverSmall) {
        this.coverSmall = coverSmall;
    }

    @Basic
    @Column(name = "father")
    public Long getFather() {
        return father;
    }

    public void setFather(Long father) {
        this.father = father;
    }

    @Basic
    @Column(name = "father_name")
    public Integer getFatherName() {
        return fatherName;
    }

    public void setFatherName(Integer fatherName) {
        this.fatherName = fatherName;
    }

    @Basic
    @Column(name = "if_has_child")
    public Integer getIfHasChild() {
        return ifHasChild;
    }

    public void setIfHasChild(Integer ifHasChild) {
        this.ifHasChild = ifHasChild;
    }

    @Basic
    @Column(name = "longitude")
    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "latitude")
    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "url")
    public Integer getUrl() {
        return url;
    }

    public void setUrl(Integer url) {
        this.url = url;
    }

    @Basic
    @Column(name = "is_city")
    public Integer getIsCity() {
        return isCity;
    }

    public void setIsCity(Integer isCity) {
        this.isCity = isCity;
    }

    @Basic
    @Column(name = "is_station")
    public Integer getIsStation() {
        return isStation;
    }

    public void setIsStation(Integer isStation) {
        this.isStation = isStation;
    }

    @Basic
    @Column(name = "station_type")
    public Integer getStationType() {
        return stationType;
    }

    public void setStationType(Integer stationType) {
        this.stationType = stationType;
    }

    @Basic
    @Column(name = "gcj_lng")
    public Integer getGcjLng() {
        return gcjLng;
    }

    public void setGcjLng(Integer gcjLng) {
        this.gcjLng = gcjLng;
    }

    @Basic
    @Column(name = "gcj_lat")
    public Integer getGcjLat() {
        return gcjLat;
    }

    public void setGcjLat(Integer gcjLat) {
        this.gcjLat = gcjLat;
    }

    @Basic
    @Column(name = "lowest_price")
    public Integer getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(Integer lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    @Basic
    @Column(name = "highest_price")
    public Integer getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(Integer highestPrice) {
        this.highestPrice = highestPrice;
    }

    @Basic
    @Column(name = "market_price")
    public Integer getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Integer marketPrice) {
        this.marketPrice = marketPrice;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "flag_has_taxi")
    public Integer getFlagHasTaxi() {
        return flagHasTaxi;
    }

    public void setFlagHasTaxi(Integer flagHasTaxi) {
        this.flagHasTaxi = flagHasTaxi;
    }

    @Basic
    @Column(name = "flag_has_bus")
    public Integer getFlagHasBus() {
        return flagHasBus;
    }

    public void setFlagHasBus(Integer flagHasBus) {
        this.flagHasBus = flagHasBus;
    }

    @Basic
    @Column(name = "flag_three_level_region")
    public Integer getFlagThreeLevelRegion() {
        return flagThreeLevelRegion;
    }

    public void setFlagThreeLevelRegion(Integer flagThreeLevelRegion) {
        this.flagThreeLevelRegion = flagThreeLevelRegion;
    }

    @Basic
    @Column(name = "data_source")
    public Integer getDataSource() {
        return dataSource;
    }

    public void setDataSource(Integer dataSource) {
        this.dataSource = dataSource;
    }

    @Basic
    @Column(name = "data_source_id")
    public Integer getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Integer dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    @Basic
    @Column(name = "data_source_url")
    public Integer getDataSourceUrl() {
        return dataSourceUrl;
    }

    public void setDataSourceUrl(Integer dataSourceUrl) {
        this.dataSourceUrl = dataSourceUrl;
    }

    @Basic
    @Column(name = "data_html")
    public Integer getDataHtml() {
        return dataHtml;
    }

    public void setDataHtml(Integer dataHtml) {
        this.dataHtml = dataHtml;
    }

    @Basic
    @Column(name = "xml")
    public Integer getXml() {
        return xml;
    }

    public void setXml(Integer xml) {
        this.xml = xml;
    }

    @Basic
    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Basic
    @Column(name = "ranking")
    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    @Basic
    @Column(name = "best_hour")
    public Integer getBestHour() {
        return bestHour;
    }

    public void setBestHour(Integer bestHour) {
        this.bestHour = bestHour;
    }

    @Basic
    @Column(name = "introduction")
    public Integer getIntroduction() {
        return introduction;
    }

    public void setIntroduction(Integer introduction) {
        this.introduction = introduction;
    }

    @Basic
    @Column(name = "website")
    public Integer getWebsite() {
        return website;
    }

    public void setWebsite(Integer website) {
        this.website = website;
    }

    @Basic
    @Column(name = "advice")
    public Integer getAdvice() {
        return advice;
    }

    public void setAdvice(Integer advice) {
        this.advice = advice;
    }

    @Basic
    @Column(name = "theme")
    public Integer getTheme() {
        return theme;
    }

    public void setTheme(Integer theme) {
        this.theme = theme;
    }

    @Basic
    @Column(name = "how")
    public Integer getHow() {
        return how;
    }

    public void setHow(Integer how) {
        this.how = how;
    }

    @Basic
    @Column(name = "scenic_type")
    public Integer getScenicType() {
        return scenicType;
    }

    public void setScenicType(Integer scenicType) {
        this.scenicType = scenicType;
    }

    @Basic
    @Column(name = "notice")
    public Integer getNotice() {
        return notice;
    }

    public void setNotice(Integer notice) {
        this.notice = notice;
    }

    @Basic
    @Column(name = "guide")
    public Integer getGuide() {
        return guide;
    }

    public void setGuide(Integer guide) {
        this.guide = guide;
    }

    @Basic
    @Column(name = "parents_three_level_region")
    public Integer getParentsThreeLevelRegion() {
        return parentsThreeLevelRegion;
    }

    public void setParentsThreeLevelRegion(Integer parentsThreeLevelRegion) {
        this.parentsThreeLevelRegion = parentsThreeLevelRegion;
    }

    @Basic
    @Column(name = "hpl")
    public Integer getHpl() {
        return hpl;
    }

    public void setHpl(Integer hpl) {
        this.hpl = hpl;
    }

    @Basic
    @Column(name = "recommend_reason")
    public Integer getRecommendReason() {
        return recommendReason;
    }

    public void setRecommendReason(Integer recommendReason) {
        this.recommendReason = recommendReason;
    }

    @Basic
    @Column(name = "etl_status")
    public Integer getEtlStatus() {
        return etlStatus;
    }

    public void setEtlStatus(Integer etlStatus) {
        this.etlStatus = etlStatus;
    }
}
