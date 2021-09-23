package com.data.data.hmly.service.hotel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by caiys on 2016/10/28.
 */
@Entity
@Table(name = "hotel_elong_static_info")
public class HotelElongStaticInfo extends com.framework.hibernate.util.Entity {
    @Id
    @Column(name = "elongHotelId")
    private Long elongHotelId;
    @Column(name = "name")
    private String name;
    @Column(name = "line")
    private String line;
    @Column(name = "cityId")
    private Integer cityId;

    public Long getElongHotelId() {
        return elongHotelId;
    }

    public void setElongHotelId(Long elongHotelId) {
        this.elongHotelId = elongHotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }
}
