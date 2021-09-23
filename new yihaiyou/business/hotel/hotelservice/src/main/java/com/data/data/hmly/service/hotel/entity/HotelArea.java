package com.data.data.hmly.service.hotel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by guoshijie on 2015/12/2.
 */
@Entity
@Table(name = "hotel_area")
public class HotelArea extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;
    @Column(name = "hotel_id")
    private Long hotelId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private HotelRegion region;
    @Column(name = "hotel_name")
    private String hotelName;
    @Column(name = "area_name")
    private String areaName;
    @Column(name = "ranking")
    private Integer ranking;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HotelRegion getRegion() {
        return region;
    }

    public void setRegion(HotelRegion region) {
        this.region = region;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String scenicName) {
        this.hotelName = scenicName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
}
