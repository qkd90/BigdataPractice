package com.data.data.hmly.service.cruiseship.entity;
// Generated 2016-9-13 14:06:21 by Hibernate Tools 3.2.0.CR1


import com.data.data.hmly.service.cruiseship.entity.enums.CruiseShipRoomType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "cruise_ship_room_date")
public class CruiseShipRoomDate extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "roomId")
    private CruiseShipRoom cruiseShipRoom;
    @ManyToOne
    @JoinColumn(name = "dateId")
    private CruiseShipDate cruiseShipDate;
    @Column(name = "cruiseShipId")
    private Long cruiseShipId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", length = 19)
    private Date date;
    @Column(name = "discountPrice")
    private Float discountPrice;
    @Column(name = "salePrice")
    private Float salePrice;
    @Column(name = "marketPrice")
    private Float marketPrice;
    @Column(name = "inventory")
    private Integer inventory;
    @Column(name = "childDiscountPrice")
    private Float childDiscountPrice;
    @Column(name = "childSalePrice")
    private Float childSalePrice;
    @Column(name = "childMarketPrice")
    private Float childMarketPrice;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime", length = 19)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime", length = 19)
    private Date updateTime;

    /**
     * 页面字段
     */
    @Transient
    private Long dateId;
    @Transient
    private String dateStr;
    @Transient
    private Long roomId;
    @Transient
    private String roomName;
    @Transient
    private CruiseShipRoomType roomType;

    public CruiseShipRoomDate() {
    }

    public CruiseShipRoomDate(Long id, Long roomId, String roomName, CruiseShipRoomType roomType, Float discountPrice, Float salePrice, Float marketPrice) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.discountPrice = discountPrice;
        this.salePrice = salePrice;
        this.marketPrice = marketPrice;
        this.id = id;
        this.roomType = roomType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CruiseShipRoom getCruiseShipRoom() {
        return cruiseShipRoom;
    }

    public void setCruiseShipRoom(CruiseShipRoom cruiseShipRoom) {
        this.cruiseShipRoom = cruiseShipRoom;
    }

    public CruiseShipDate getCruiseShipDate() {
        return cruiseShipDate;
    }

    public void setCruiseShipDate(CruiseShipDate cruiseShipDate) {
        this.cruiseShipDate = cruiseShipDate;
    }

    public Long getCruiseShipId() {
        return cruiseShipId;
    }

    public void setCruiseShipId(Long cruiseShipId) {
        this.cruiseShipId = cruiseShipId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Float discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Float salePrice) {
        this.salePrice = salePrice;
    }

    public Float getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Float marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Float getChildDiscountPrice() {
        return childDiscountPrice;
    }

    public void setChildDiscountPrice(Float childDiscountPrice) {
        this.childDiscountPrice = childDiscountPrice;
    }

    public Float getChildSalePrice() {
        return childSalePrice;
    }

    public void setChildSalePrice(Float childSalePrice) {
        this.childSalePrice = childSalePrice;
    }

    public Float getChildMarketPrice() {
        return childMarketPrice;
    }

    public void setChildMarketPrice(Float childMarketPrice) {
        this.childMarketPrice = childMarketPrice;
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

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public CruiseShipRoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(CruiseShipRoomType roomType) {
        this.roomType = roomType;
    }

    public Long getDateId() {
        return dateId;
    }

    public void setDateId(Long dateId) {
        this.dateId = dateId;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }
}


