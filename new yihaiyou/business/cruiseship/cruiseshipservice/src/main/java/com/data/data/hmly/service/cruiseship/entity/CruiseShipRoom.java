package com.data.data.hmly.service.cruiseship.entity;
// Generated 2016-9-13 14:06:21 by Hibernate Tools 3.2.0.CR1


import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.cruiseship.entity.enums.CruiseShipRoomType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "cruise_ship_room")
public class CruiseShipRoom extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cruiseShipId")
    private CruiseShip cruiseShip;
    @Column(name = "name")
    private String name;
    @Column(name = "roomType")
    @Enumerated(EnumType.STRING)
    private CruiseShipRoomType roomType;
    @Column(name = "deck")
    private String deck;
    @Column(name = "area")
    private String area;
    @Column(name = "facilities")
    private String facilities;
    @Column(name = "peopleNum")
    private Integer peopleNum;
    @Column(name = "forceFlag")
    private Boolean forceFlag;
    @Column(name = "coverImage")
    private String coverImage;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime", length = 19)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime", length = 19)
    private Date updateTime;

    @OneToMany(mappedBy = "cruiseShipRoom", fetch = FetchType.LAZY)
    private Set<CruiseShipRoomDate> cruiseShipRoomDates;

    /**
     * 页面字段
     */
    @Transient
    private Long cruiseShipId;
    @Transient
    private Float minPrice;
    @Transient
    private String roomTypeStr;
    @Transient
    private String roomTypeName;
    @Transient
    private List<Productimage> productimages;


    public CruiseShipRoom() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CruiseShip getCruiseShip() {
        return cruiseShip;
    }

    public void setCruiseShip(CruiseShip cruiseShip) {
        this.cruiseShip = cruiseShip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CruiseShipRoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(CruiseShipRoomType roomType) {
        this.roomType = roomType;
    }

    public String getDeck() {
        return deck;
    }

    public void setDeck(String deck) {
        this.deck = deck;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public Boolean getForceFlag() {
        return forceFlag;
    }

    public void setForceFlag(Boolean forceFlag) {
        this.forceFlag = forceFlag;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCruiseShipId() {
        return cruiseShipId;
    }

    public void setCruiseShipId(Long cruiseShipId) {
        this.cruiseShipId = cruiseShipId;
    }

    public Float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Float minPrice) {
        this.minPrice = minPrice;
    }

    public String getRoomTypeStr() {

        return this.roomType.getDescription();
    }

    public List<Productimage> getProductimages() {
        return productimages;
    }

    public void setProductimages(List<Productimage> productimages) {
        this.productimages = productimages;
    }

    public void setRoomTypeStr(String roomTypeStr) {
        if (CruiseShipRoomType.balcony.toString().equals(roomTypeStr)) {
            setRoomTypeName(CruiseShipRoomType.balcony.getDescription());
        }
        if (CruiseShipRoomType.inside.toString().equals(roomTypeStr)) {
            setRoomTypeName(CruiseShipRoomType.inside.getDescription());
        }
        if (CruiseShipRoomType.seascape.toString().equals(roomTypeStr)) {
            setRoomTypeName(CruiseShipRoomType.seascape.getDescription());
        }
        if (CruiseShipRoomType.suite.toString().equals(roomTypeStr)) {
            setRoomTypeName(CruiseShipRoomType.suite.getDescription());
        }
        this.roomTypeStr = roomTypeStr;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;

    }
}


