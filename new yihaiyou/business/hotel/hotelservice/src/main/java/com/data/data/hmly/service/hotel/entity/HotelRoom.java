package com.data.data.hmly.service.hotel.entity;

import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.hotel.entity.enums.HotelRoomStatus;

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

/**
 * Created by zzl on 2016/11/21.
 */
@Entity
@Table(name = "hotel_room")
public class HotelRoom extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "hotel_id")
    private Long hotelId;
    @Column(name = "hotel_price_id")
    private Long hotelPriceId;
    @Column(name = "room_num")
    private String roomNum;
    @Column(name = "room_status")
    @Enumerated(EnumType.STRING)
    private HotelRoomStatus roomStatus;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getHotelPriceId() {
        return hotelPriceId;
    }

    public void setHotelPriceId(Long hotelPriceId) {
        this.hotelPriceId = hotelPriceId;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public HotelRoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(HotelRoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HotelRoom) {
            HotelRoom r = (HotelRoom) obj;
            return this.id.equals(r.getId());
        } else {
            return false;
        }
    }
}
