package com.data.data.hmly.service.hotel.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Created by guoshijie on 2015/12/21.
 */
@Entity
@Table(name = "hotel_extend")
public class HotelExtend extends com.framework.hibernate.util.Entity {

    @Id
    @Column(name = "id")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Hotel hotel;
    @Column(name = "address")
    private String address;
    @Column(name = "tel")
    private String tel;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "description")
    private String description;
    @Column(name = "gcjLng")
    private Double gcjLng;
    @Column(name = "gcjLat")
    private Double gcjLat;
    @Column(name = "collect_num")
    private Integer collectNum;
    @Column(name = "comment_num")
    private Integer commentNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getGcjLng() {
        return gcjLng;
    }

    public void setGcjLng(Double gcjLng) {
        this.gcjLng = gcjLng;
    }

    public Double getGcjLat() {
        return gcjLat;
    }

    public void setGcjLat(Double gcjLat) {
        this.gcjLat = gcjLat;
    }

    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }
}
