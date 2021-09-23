package com.data.spider.service.pojo.tb;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sane on 15/9/21.
 */
@Entity
@javax.persistence.Table(name = "tb_scenic_info")
public class TbScenicInfo extends com.framework.hibernate.util.Entity {
    private long id;
    private Long userId;
    private String name;
    private String cityCode;
    private String regionCode;
    private Short star;
    private Integer price;
    private Integer score;
    private String address;
    private String telephone;
    private Integer orderNum;
    private Integer commentNum;
    private Integer goingCount;
    private Integer cameCount;
    private String openTime;
    private String adviceTime;
    private Integer adviceHours;
    private String bestTime;
    private String ticket;
    private String commenter;
    private String shortComment;
    private String coverLarge;
    private String coverMedium;
    private String coverSmall;
    private Long father;
    private String fatherName;
    private Boolean ifHasChild;
    private Double longitude;
    private Double latitude;
    private Timestamp modifyTime;
    private Timestamp createTime;
    private Integer isCity;
    private Integer isStation;
    private Integer stationType;
    private Double gcjLng;
    private Double gcjLat;
    private Float lowestPrice;
    private Float highestPrice;
    private Float marketPrice;
    private Integer status;
    private Integer flagHasTaxi;
    private Integer flagHasBus;
    private Integer flagThreeLevelRegion;
    private Integer firstTicketSource;
    private Integer firstTicketId;
    private String parentsThreeLevelRegion;
    private Integer createdBy;
    private Integer updatedBy;
    private String deleteFlag;
    private Integer isModified;
    private Integer refId;
    private String level;
//    private Integer ranking;
    private String bestHour;
    private String advice;
    private String theme;
    private String scenicType;
    private String notice;
    private String guide;
    private String hpl;
    private String how;
    private String dataSource;
    private Integer dataSourceId;
    private String baiduUrl;
    private Integer ctripId;

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
    @Column(name = "user_id", nullable = true, insertable = true, updatable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "region_code", nullable = true, insertable = true, updatable = true, length = 20)
    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    @Basic
    @Column(name = "star", nullable = true, insertable = true, updatable = true)
    public Short getStar() {
        return star;
    }

    public void setStar(Short star) {
        this.star = star;
    }

    @Basic
    @Column(name = "price", nullable = true, insertable = true, updatable = true)
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Basic
    @Column(name = "score", nullable = true, insertable = true, updatable = true)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Basic
    @Column(name = "address", nullable = true, insertable = true, updatable = true, length = 500)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "telephone", nullable = true, insertable = true, updatable = true, length = 500)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
    @Column(name = "comment_num", nullable = true, insertable = true, updatable = true)
    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    @Basic
    @Column(name = "going_count", nullable = true, insertable = true, updatable = true)
    public Integer getGoingCount() {
        return goingCount;
    }

    public void setGoingCount(Integer goingCount) {
        this.goingCount = goingCount;
    }

    @Basic
    @Column(name = "came_count", nullable = true, insertable = true, updatable = true)
    public Integer getCameCount() {
        return cameCount;
    }

    public void setCameCount(Integer cameCount) {
        this.cameCount = cameCount;
    }

    @Basic
    @Column(name = "open_time", nullable = true, insertable = true, updatable = true, length = 300)
    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    @Basic
    @Column(name = "advice_time", nullable = true, insertable = true, updatable = true, length = 300)
    public String getAdviceTime() {
        return adviceTime;
    }

    public void setAdviceTime(String adviceTime) {
        this.adviceTime = adviceTime;
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
    @Column(name = "best_time", nullable = true, insertable = true, updatable = true, length = 500)
    public String getBestTime() {
        return bestTime;
    }

    public void setBestTime(String bestTime) {
        this.bestTime = bestTime;
    }

    @Basic
    @Column(name = "ticket", nullable = true, insertable = true, updatable = true, length = 2048)
    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    @Basic
    @Column(name = "commenter", nullable = true, insertable = true, updatable = true, length = 100)
    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    @Basic
    @Column(name = "short_comment", nullable = true, insertable = true, updatable = true, length = 500)
    public String getShortComment() {
        return shortComment;
    }

    public void setShortComment(String shortComment) {
        this.shortComment = shortComment;
    }

    @Basic
    @Column(name = "cover_large", nullable = true, insertable = true, updatable = true, length = 200)
    public String getCoverLarge() {
        return coverLarge;
    }

    public void setCoverLarge(String coverLarge) {
        this.coverLarge = coverLarge;
    }

    @Basic
    @Column(name = "cover_medium", nullable = true, insertable = true, updatable = true, length = 200)
    public String getCoverMedium() {
        return coverMedium;
    }

    public void setCoverMedium(String coverMedium) {
        this.coverMedium = coverMedium;
    }

    @Basic
    @Column(name = "cover_small", nullable = true, insertable = true, updatable = true, length = 200)
    public String getCoverSmall() {
        return coverSmall;
    }

    public void setCoverSmall(String coverSmall) {
        this.coverSmall = coverSmall;
    }

    @Basic
    @Column(name = "father", nullable = true, insertable = true, updatable = true)
    public Long getFather() {
        return father;
    }

    public void setFather(Long father) {
        this.father = father;
    }

    @Basic
    @Column(name = "father_name", nullable = true, insertable = true, updatable = true, length = 100)
    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    @Basic
    @Column(name = "if_has_child", nullable = true, insertable = true, updatable = true)
    public Boolean getIfHasChild() {
        return ifHasChild;
    }

    public void setIfHasChild(Boolean ifHasChild) {
        this.ifHasChild = ifHasChild;
    }

    @Basic
    @Column(name = "longitude", nullable = true, insertable = true, updatable = true, precision = 0)
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "latitude", nullable = true, insertable = true, updatable = true, precision = 0)
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
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
    @Column(name = "is_city", nullable = true, insertable = true, updatable = true)
    public Integer getIsCity() {
        return isCity;
    }

    public void setIsCity(Integer isCity) {
        this.isCity = isCity;
    }

    @Basic
    @Column(name = "is_station", nullable = true, insertable = true, updatable = true)
    public Integer getIsStation() {
        return isStation;
    }

    public void setIsStation(Integer isStation) {
        this.isStation = isStation;
    }

    @Basic
    @Column(name = "station_type", nullable = true, insertable = true, updatable = true)
    public Integer getStationType() {
        return stationType;
    }

    public void setStationType(Integer stationType) {
        this.stationType = stationType;
    }

    @Basic
    @Column(name = "gcj_lng", nullable = true, insertable = true, updatable = true, precision = 0)
    public Double getGcjLng() {
        return gcjLng;
    }

    public void setGcjLng(Double gcjLng) {
        this.gcjLng = gcjLng;
    }

    @Basic
    @Column(name = "gcj_lat", nullable = true, insertable = true, updatable = true, precision = 0)
    public Double getGcjLat() {
        return gcjLat;
    }

    public void setGcjLat(Double gcjLat) {
        this.gcjLat = gcjLat;
    }

    @Basic
    @Column(name = "lowest_price", nullable = true, insertable = true, updatable = true, precision = 0)
    public Float getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(Float lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    @Basic
    @Column(name = "highest_price", nullable = true, insertable = true, updatable = true, precision = 0)
    public Float getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(Float highestPrice) {
        this.highestPrice = highestPrice;
    }

    @Basic
    @Column(name = "market_price", nullable = true, insertable = true, updatable = true, precision = 0)
    public Float getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Float marketPrice) {
        this.marketPrice = marketPrice;
    }

    @Basic
    @Column(name = "status", nullable = true, insertable = true, updatable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "flag_has_taxi", nullable = true, insertable = true, updatable = true)
    public Integer getFlagHasTaxi() {
        return flagHasTaxi;
    }

    public void setFlagHasTaxi(Integer flagHasTaxi) {
        this.flagHasTaxi = flagHasTaxi;
    }

    @Basic
    @Column(name = "flag_has_bus", nullable = true, insertable = true, updatable = true)
    public Integer getFlagHasBus() {
        return flagHasBus;
    }

    public void setFlagHasBus(Integer flagHasBus) {
        this.flagHasBus = flagHasBus;
    }

    @Basic
    @Column(name = "flag_three_level_region", nullable = true, insertable = true, updatable = true)
    public Integer getFlagThreeLevelRegion() {
        return flagThreeLevelRegion;
    }

    public void setFlagThreeLevelRegion(Integer flagThreeLevelRegion) {
        this.flagThreeLevelRegion = flagThreeLevelRegion;
    }

    @Basic
    @Column(name = "first_ticket_source", nullable = true, insertable = true, updatable = true)
    public Integer getFirstTicketSource() {
        return firstTicketSource;
    }

    public void setFirstTicketSource(Integer firstTicketSource) {
        this.firstTicketSource = firstTicketSource;
    }

    @Basic
    @Column(name = "first_ticket_id", nullable = true, insertable = true, updatable = true)
    public Integer getFirstTicketId() {
        return firstTicketId;
    }

    public void setFirstTicketId(Integer firstTicketId) {
        this.firstTicketId = firstTicketId;
    }

    @Basic
    @Column(name = "parents_three_level_region", nullable = true, insertable = true, updatable = true, length = 1)
    public String getParentsThreeLevelRegion() {
        return parentsThreeLevelRegion;
    }

    public void setParentsThreeLevelRegion(String parentsThreeLevelRegion) {
        this.parentsThreeLevelRegion = parentsThreeLevelRegion;
    }

    @Basic
    @Column(name = "created_by", nullable = true, insertable = true, updatable = true)
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "updated_by", nullable = true, insertable = true, updatable = true)
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Basic
    @Column(name = "delete_flag", nullable = true, insertable = true, updatable = true, length = 8)
    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Basic
    @Column(name = "is_modified", nullable = true, insertable = true, updatable = true)
    public Integer getIsModified() {
        return isModified;
    }

    public void setIsModified(Integer isModified) {
        this.isModified = isModified;
    }

    @Basic
    @Column(name = "ref_id", nullable = true, insertable = true, updatable = true)
    public Integer getRefId() {
        return refId;
    }

    public void setRefId(Integer refId) {
        this.refId = refId;
    }

    @Basic
    @Column(name = "level", nullable = true, insertable = true, updatable = true, length = 50)
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

//    @Basic
//    @Column(name = "ranking", nullable = true, insertable = true, updatable = true)
//    public Integer getRanking() {
//        return ranking;
//    }
//
//    public void setRanking(Integer ranking) {
//        this.ranking = ranking;
//    }

    @Basic
    @Column(name = "best_hour", nullable = true, insertable = true, updatable = true, length = 50)
    public String getBestHour() {
        return bestHour;
    }

    public void setBestHour(String bestHour) {
        this.bestHour = bestHour;
    }

    @Basic
    @Column(name = "advice", nullable = true, insertable = true, updatable = true, length = 500)
    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    @Basic
    @Column(name = "theme", nullable = true, insertable = true, updatable = true, length = 500)
    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Basic
    @Column(name = "scenic_type", nullable = true, insertable = true, updatable = true, length = 500)
    public String getScenicType() {
        return scenicType;
    }

    public void setScenicType(String scenicType) {
        this.scenicType = scenicType;
    }

    @Basic
    @Column(name = "notice", nullable = true, insertable = true, updatable = true, length = 3000)
    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    @Basic
    @Column(name = "guide", nullable = true, insertable = true, updatable = true, length = 3000)
    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    @Basic
    @Column(name = "hpl", nullable = true, insertable = true, updatable = true, length = 255)
    public String getHpl() {
        return hpl;
    }

    public void setHpl(String hpl) {
        this.hpl = hpl;
    }

    @Basic
    @Column(name = "how", nullable = true, insertable = true, updatable = true, length = 65535)
    public String getHow() {
        return how;
    }

    public void setHow(String how) {
        this.how = how;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbScenicInfo that = (TbScenicInfo) o;

        if (id != that.id) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (cityCode != null ? !cityCode.equals(that.cityCode) : that.cityCode != null) return false;
        if (regionCode != null ? !regionCode.equals(that.regionCode) : that.regionCode != null) return false;
        if (star != null ? !star.equals(that.star) : that.star != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (score != null ? !score.equals(that.score) : that.score != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        if (orderNum != null ? !orderNum.equals(that.orderNum) : that.orderNum != null) return false;
        if (commentNum != null ? !commentNum.equals(that.commentNum) : that.commentNum != null) return false;
        if (goingCount != null ? !goingCount.equals(that.goingCount) : that.goingCount != null) return false;
        if (cameCount != null ? !cameCount.equals(that.cameCount) : that.cameCount != null) return false;
        if (openTime != null ? !openTime.equals(that.openTime) : that.openTime != null) return false;
        if (adviceTime != null ? !adviceTime.equals(that.adviceTime) : that.adviceTime != null) return false;
        if (adviceHours != null ? !adviceHours.equals(that.adviceHours) : that.adviceHours != null) return false;
        if (bestTime != null ? !bestTime.equals(that.bestTime) : that.bestTime != null) return false;
        if (ticket != null ? !ticket.equals(that.ticket) : that.ticket != null) return false;
        if (commenter != null ? !commenter.equals(that.commenter) : that.commenter != null) return false;
        if (shortComment != null ? !shortComment.equals(that.shortComment) : that.shortComment != null) return false;
        if (coverLarge != null ? !coverLarge.equals(that.coverLarge) : that.coverLarge != null) return false;
        if (coverMedium != null ? !coverMedium.equals(that.coverMedium) : that.coverMedium != null) return false;
        if (coverSmall != null ? !coverSmall.equals(that.coverSmall) : that.coverSmall != null) return false;
        if (father != null ? !father.equals(that.father) : that.father != null) return false;
        if (fatherName != null ? !fatherName.equals(that.fatherName) : that.fatherName != null) return false;
        if (ifHasChild != null ? !ifHasChild.equals(that.ifHasChild) : that.ifHasChild != null) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (isCity != null ? !isCity.equals(that.isCity) : that.isCity != null) return false;
        if (isStation != null ? !isStation.equals(that.isStation) : that.isStation != null) return false;
        if (stationType != null ? !stationType.equals(that.stationType) : that.stationType != null) return false;
        if (gcjLng != null ? !gcjLng.equals(that.gcjLng) : that.gcjLng != null) return false;
        if (gcjLat != null ? !gcjLat.equals(that.gcjLat) : that.gcjLat != null) return false;
        if (lowestPrice != null ? !lowestPrice.equals(that.lowestPrice) : that.lowestPrice != null) return false;
        if (highestPrice != null ? !highestPrice.equals(that.highestPrice) : that.highestPrice != null) return false;
        if (marketPrice != null ? !marketPrice.equals(that.marketPrice) : that.marketPrice != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (flagHasTaxi != null ? !flagHasTaxi.equals(that.flagHasTaxi) : that.flagHasTaxi != null) return false;
        if (flagHasBus != null ? !flagHasBus.equals(that.flagHasBus) : that.flagHasBus != null) return false;
        if (flagThreeLevelRegion != null ? !flagThreeLevelRegion.equals(that.flagThreeLevelRegion) : that.flagThreeLevelRegion != null)
            return false;
        if (firstTicketSource != null ? !firstTicketSource.equals(that.firstTicketSource) : that.firstTicketSource != null)
            return false;
        if (firstTicketId != null ? !firstTicketId.equals(that.firstTicketId) : that.firstTicketId != null)
            return false;
        if (parentsThreeLevelRegion != null ? !parentsThreeLevelRegion.equals(that.parentsThreeLevelRegion) : that.parentsThreeLevelRegion != null)
            return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (updatedBy != null ? !updatedBy.equals(that.updatedBy) : that.updatedBy != null) return false;
        if (deleteFlag != null ? !deleteFlag.equals(that.deleteFlag) : that.deleteFlag != null) return false;
        if (isModified != null ? !isModified.equals(that.isModified) : that.isModified != null) return false;
        if (refId != null ? !refId.equals(that.refId) : that.refId != null) return false;
        if (level != null ? !level.equals(that.level) : that.level != null) return false;
//        if (ranking != null ? !ranking.equals(that.ranking) : that.ranking != null) return false;
        if (bestHour != null ? !bestHour.equals(that.bestHour) : that.bestHour != null) return false;
        if (advice != null ? !advice.equals(that.advice) : that.advice != null) return false;
        if (theme != null ? !theme.equals(that.theme) : that.theme != null) return false;
        if (scenicType != null ? !scenicType.equals(that.scenicType) : that.scenicType != null) return false;
        if (notice != null ? !notice.equals(that.notice) : that.notice != null) return false;
        if (guide != null ? !guide.equals(that.guide) : that.guide != null) return false;
        if (hpl != null ? !hpl.equals(that.hpl) : that.hpl != null) return false;
        if (how != null ? !how.equals(that.how) : that.how != null) return false;
        if (dataSource != null ? !dataSource.equals(that.dataSource) : that.dataSource != null) return false;
        if (dataSourceId != null ? !dataSourceId.equals(that.dataSourceId) : that.dataSourceId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (cityCode != null ? cityCode.hashCode() : 0);
        result = 31 * result + (regionCode != null ? regionCode.hashCode() : 0);
        result = 31 * result + (star != null ? star.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (orderNum != null ? orderNum.hashCode() : 0);
        result = 31 * result + (commentNum != null ? commentNum.hashCode() : 0);
        result = 31 * result + (goingCount != null ? goingCount.hashCode() : 0);
        result = 31 * result + (cameCount != null ? cameCount.hashCode() : 0);
        result = 31 * result + (openTime != null ? openTime.hashCode() : 0);
        result = 31 * result + (adviceTime != null ? adviceTime.hashCode() : 0);
        result = 31 * result + (adviceHours != null ? adviceHours.hashCode() : 0);
        result = 31 * result + (bestTime != null ? bestTime.hashCode() : 0);
        result = 31 * result + (ticket != null ? ticket.hashCode() : 0);
        result = 31 * result + (commenter != null ? commenter.hashCode() : 0);
        result = 31 * result + (shortComment != null ? shortComment.hashCode() : 0);
        result = 31 * result + (coverLarge != null ? coverLarge.hashCode() : 0);
        result = 31 * result + (coverMedium != null ? coverMedium.hashCode() : 0);
        result = 31 * result + (coverSmall != null ? coverSmall.hashCode() : 0);
        result = 31 * result + (father != null ? father.hashCode() : 0);
        result = 31 * result + (fatherName != null ? fatherName.hashCode() : 0);
        result = 31 * result + (ifHasChild != null ? ifHasChild.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (isCity != null ? isCity.hashCode() : 0);
        result = 31 * result + (isStation != null ? isStation.hashCode() : 0);
        result = 31 * result + (stationType != null ? stationType.hashCode() : 0);
        result = 31 * result + (gcjLng != null ? gcjLng.hashCode() : 0);
        result = 31 * result + (gcjLat != null ? gcjLat.hashCode() : 0);
        result = 31 * result + (lowestPrice != null ? lowestPrice.hashCode() : 0);
        result = 31 * result + (highestPrice != null ? highestPrice.hashCode() : 0);
        result = 31 * result + (marketPrice != null ? marketPrice.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (flagHasTaxi != null ? flagHasTaxi.hashCode() : 0);
        result = 31 * result + (flagHasBus != null ? flagHasBus.hashCode() : 0);
        result = 31 * result + (flagThreeLevelRegion != null ? flagThreeLevelRegion.hashCode() : 0);
        result = 31 * result + (firstTicketSource != null ? firstTicketSource.hashCode() : 0);
        result = 31 * result + (firstTicketId != null ? firstTicketId.hashCode() : 0);
        result = 31 * result + (parentsThreeLevelRegion != null ? parentsThreeLevelRegion.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (updatedBy != null ? updatedBy.hashCode() : 0);
        result = 31 * result + (deleteFlag != null ? deleteFlag.hashCode() : 0);
        result = 31 * result + (isModified != null ? isModified.hashCode() : 0);
        result = 31 * result + (refId != null ? refId.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
//        result = 31 * result + (ranking != null ? ranking.hashCode() : 0);
        result = 31 * result + (bestHour != null ? bestHour.hashCode() : 0);
        result = 31 * result + (advice != null ? advice.hashCode() : 0);
        result = 31 * result + (theme != null ? theme.hashCode() : 0);
        result = 31 * result + (scenicType != null ? scenicType.hashCode() : 0);
        result = 31 * result + (notice != null ? notice.hashCode() : 0);
        result = 31 * result + (guide != null ? guide.hashCode() : 0);
        result = 31 * result + (hpl != null ? hpl.hashCode() : 0);
        result = 31 * result + (how != null ? how.hashCode() : 0);
        result = 31 * result + (dataSource != null ? dataSource.hashCode() : 0);
        result = 31 * result + (dataSourceId != null ? dataSourceId.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "baidu_url", nullable = true, insertable = true, updatable = true, length = 50)
    public String getBaiduUrl() {
        return baiduUrl;
    }

    public void setBaiduUrl(String baiduUrl) {
        this.baiduUrl = baiduUrl;
    }

    @Basic
    @Column(name = "ctrip_id", nullable = true, insertable = true, updatable = true)
    public Integer getCtripId() {
        return ctripId;
    }

    public void setCtripId(Integer ctripId) {
        this.ctripId = ctripId;
    }
}
