package com.data.data.hmly.service.order.entity;

import com.data.data.hmly.service.order.entity.enums.OrderTouristIdType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by zzl on 2016/12/9.
 */
@Entity
@Table(name = "hotel_room_member")
public class HotelRoomMember extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "hotel_room_state_id")
    private HotelRoomState hotelRoomState;
    @Column(name = "name")
    private String name;
    @Column(name = "id_type")
    @Enumerated(EnumType.STRING)
    private OrderTouristIdType idType;
    @Column(name = "id_number")
    private String idNumber;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HotelRoomState getHotelRoomState() {
        return hotelRoomState;
    }

    public void setHotelRoomState(HotelRoomState hotelRoomState) {
        this.hotelRoomState = hotelRoomState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrderTouristIdType getIdType() {
        return idType;
    }

    public void setIdType(OrderTouristIdType idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
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
}
