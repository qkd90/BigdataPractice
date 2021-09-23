package com.data.data.hmly.service.other.entity;

// Generated 2015-12-22 10:51:28 by Hibernate Tools 3.4.0.CR1

import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.cruiseship.entity.CruiseShip;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipDate;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.impression.entity.Impression;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.restaurant.entity.Delicacy;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.zuipin.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * OtherFavorite generated by hbm2java
 */
@Entity
@Table(name = "other_favorite")
public class OtherFavorite extends com.framework.hibernate.util.Entity {

    private static final long serialVersionUID = -3947307587816370690L;
    private Long id;
    private ProductType favoriteType;
    private String title;
    private String content;
    private String imgPath;
    private Long favoriteId;
    private Long userId;
    private Date createTime;
    private Boolean deleteFlag;
    private Double price;
    private Integer star;

    private String author;
    private Integer days;
    private String mainScenics;

    private String address;
    private Integer score;
    private Integer scorePeopleNum;
    private Double longitude;
    private Double latitude;
    //	private String relationRecplan;
    private String adviceTime;

    private String taste;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "favoriteType", nullable = false)
    @Enumerated(EnumType.STRING)
    public ProductType getFavoriteType() {
        return this.favoriteType;
    }

    public void setFavoriteType(ProductType favoriteType) {
        this.favoriteType = favoriteType;
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "imgPath")
    public String getImgPath() {
        return this.imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Column(name = "favoriteId", nullable = false)
    public Long getFavoriteId() {
        return this.favoriteId;
    }

    public void setFavoriteId(Long favoriteId) {
        this.favoriteId = favoriteId;
    }

    @Column(name = "userId", nullable = false)
    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime", nullable = false, length = 19)
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "deleteFlag", nullable = false)
    public Boolean getDeleteFlag() {
        return this.deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "star")
    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    @Column(name = "author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Column(name = "days")
    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    @Column(name = "mainScenics")
    public String getMainScenics() {
        return mainScenics;
    }

    public void setMainScenics(String mainScenics) {
        this.mainScenics = mainScenics;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "score")
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Column(name = "scorePeopleNum")
    public Integer getScorePeopleNum() {
        return scorePeopleNum;
    }

    public void setScorePeopleNum(Integer scorePeopleNum) {
        this.scorePeopleNum = scorePeopleNum;
    }

    @Column(name = "taste")
    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    @Column(name = "adviceTime")
    public String getAdviceTime() {
        return adviceTime;
    }

    public void setAdviceTime(String adviceTime) {
        this.adviceTime = adviceTime;
    }

    @Column(name = "longitude")
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Column(name = "latitude")
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void completeFavorite(ScenicInfo scenicInfo) {
        this.title = scenicInfo.getName();
        this.imgPath = scenicInfo.getCover();
        this.score = scenicInfo.getScore();
        this.price = scenicInfo.getPrice() == null ? 0 : scenicInfo.getPrice().doubleValue();
        this.star = scenicInfo.getLevel() == null ? 0 : scenicInfo.getLevel().length();
        this.scorePeopleNum = scenicInfo.getScenicCommentList() == null ? 0 : scenicInfo.getScenicCommentList().size();
        if (scenicInfo.getScenicOther() != null) {
            this.content = scenicInfo.getScenicOther().getRecommendReason();
            this.address = scenicInfo.getScenicOther().getAddress();
        }
        if (scenicInfo.getScenicGeoinfo() != null) {
            this.longitude = scenicInfo.getScenicGeoinfo().getBaiduLng();
            this.latitude = scenicInfo.getScenicGeoinfo().getBaiduLat();
        }
    }

    public void completeFavorite(Hotel hotel) {
        this.title = hotel.getName();
        this.imgPath = hotel.getCover();
        this.price = hotel.getPrice() == null ? 0 : hotel.getPrice().doubleValue();
        this.star = hotel.getStar();
        this.content = hotel.getShortDesc();
        this.score = hotel.getScore() == null ? 0 : hotel.getScore().intValue();
        this.scorePeopleNum = hotel.getCommentList() == null ? 0 : hotel.getCommentList().size();
        if (hotel.getExtend() != null) {
            this.address = hotel.getExtend().getAddress();
            this.longitude = hotel.getExtend().getLongitude();
            this.latitude = hotel.getExtend().getLatitude();
        }
    }

    public void completeFavorite(Delicacy delicacy) {
        this.title = delicacy.getName();
        this.imgPath = delicacy.getCover();
        this.price = delicacy.getPrice();
        this.taste = delicacy.getTaste();
        if (delicacy.getExtend() != null) {
            this.content = delicacy.getExtend().getIntroduction();
        }
    }

    public void completeFavorite(Plan plan) {
        this.title = plan.getName();
        this.imgPath = plan.getCoverPath();
        this.content = plan.getDescription();
        this.days = plan.getPlanDays();
//        if (plan.getUser() != null && plan.getUser().getUserExinfo() != null) {
//            this.author = plan.getUser().getUserExinfo().getNickName();
//        }
        if (plan.getUser() != null && StringUtils.hasText(plan.getUser().getNickName())) {
            this.author = plan.getUser().getNickName();
        }
    }

    public void completeFavorite(RecommendPlan recommendPlan) {
        this.title = recommendPlan.getPlanName();
        this.imgPath = recommendPlan.getCoverPath();
        this.content = recommendPlan.getDescription();
        this.days = recommendPlan.getDays();
        this.mainScenics = recommendPlan.getPassScenics();
//        if (recommendPlan.getUser() != null && recommendPlan.getUser().getUserExinfo() != null) {
//            this.author = recommendPlan.getUser().getUserExinfo().getNickName();
//        }
        if (recommendPlan.getUser() != null && StringUtils.hasText(recommendPlan.getUser().getNickName())) {
            this.author = recommendPlan.getUser().getNickName();
        }
    }

    public void completeFavorite(Impression impression) {
        this.title = impression.getPlaceName();
        this.imgPath = impression.getCover();
        this.content = impression.getContent();
        if (impression.getUser() != null && StringUtils.hasText(impression.getUser().getNickName())) {
            this.author = impression.getUser().getNickName();
        }
    }

    public void completeFavorite(Line line) {
        this.title = "<" + line.getName() + ">" + line.getAppendTitle();
        this.imgPath = line.getCover();
        this.content = line.getShortDesc();
        this.days = line.getPlayDay();
        this.price = line.getPrice().doubleValue();
    }

    public void completeFavorite(CruiseShipDate cruiseShipDate) {
        CruiseShip cruiseShip = cruiseShipDate.getCruiseShip();
        this.title = cruiseShip.getName();
        this.imgPath = cruiseShip.getCoverImage();
        if (cruiseShip.getCruiseShipExtend() != null) {
            this.content = cruiseShip.getCruiseShipExtend().getIntroduction();
        }
    }

    public void completeFavorite(Ticket ticket) {
        this.imgPath = ticket.getTicketImgUrl();
        this.title = ticket.getName();
    }
}
