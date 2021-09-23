package com.data.data.hmly.service.ctriphotel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by vacuity on 15/12/4.
 */
@Entity
@Table(name = "ctrip_hotel_room")

public class CtripHotelRoom extends com.framework.hibernate.util.Entity {
    private Long id;
    private Long hotelId;
    private Long roomTypeCode;
    private Integer standardOccupancy;
    private String size;
    private String name;
    private String floor;
    private Integer invBlockCode;
    private Long bedTypeCode;
    private Integer quantity;
    private Boolean nonSmoking;
    private String roomSize;
    private Integer hasWindow;
    private Date createdTime;
    private Date updateTime;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    @Column(name = "hotelId")
    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    @Column(name = "roomTypeCode")
    public Long getRoomTypeCode() {
        return roomTypeCode;
    }

    public void setRoomTypeCode(Long roomTypeCode) {
        this.roomTypeCode = roomTypeCode;
    }

    @Column(name = "standardOccupancy")
    public Integer getStandardOccupancy() {
        return standardOccupancy;
    }

    public void setStandardOccupancy(Integer standardOccupancy) {
        this.standardOccupancy = standardOccupancy;
    }

    
    @Column(name = "size")
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    @Column(name = "floor")
    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    
    @Column(name = "invBlockCode")
    public Integer getInvBlockCode() {
        return invBlockCode;
    }

    public void setInvBlockCode(Integer invBlockCode) {
        this.invBlockCode = invBlockCode;
    }

    
    @Column(name = "bedTypeCode")
    public Long getBedTypeCode() {
        return bedTypeCode;
    }

    public void setBedTypeCode(Long bedTypeCode) {
        this.bedTypeCode = bedTypeCode;
    }

    
    @Column(name = "quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    
    @Column(name = "nonSmoking")
    public Boolean getNonSmoking() {
        return nonSmoking;
    }

    public void setNonSmoking(Boolean nonSmoking) {
        this.nonSmoking = nonSmoking;
    }

    
    @Column(name = "roomSize")
    public String getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(String roomSize) {
        this.roomSize = roomSize;
    }

    
    @Column(name = "hasWindow")
    public Integer getHasWindow() {
        return hasWindow;
    }

    public void setHasWindow(Integer hasWindow) {
        this.hasWindow = hasWindow;
    }

    
    @Column(name = "createdTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    
    @Column(name = "updateTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
}
