package com.data.data.hmly.service.ctriphotel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by vacuity on 15/12/28.
 */

@Entity
@Table(name = "ctrip_hotel_ preferential")
public class CtripHotelPreferential extends com.framework.hibernate.util.Entity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "hotelId")
    private Long hotelId;
    @Column(name = "start")
    private Date start;
    @Column(name = "end")
    private Date end;
    @Column(name = "desc")
    private String desc;
    @Column(name = "createdTime")
    private Date createdTime;
    @Column(name = "ratePlanCode")
    private Long ratePlanCode;
    @Column(name = "nightsRequired")
    private Integer nightsRequired;
    @Column(name = "nightsDiscounted")
    private Integer nightsDiscounted;


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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Long getRatePlanCode() {
        return ratePlanCode;
    }

    public void setRatePlanCode(Long ratePlanCode) {
        this.ratePlanCode = ratePlanCode;
    }

    public Integer getNightsRequired() {
        return nightsRequired;
    }

    public void setNightsRequired(Integer nightsRequired) {
        this.nightsRequired = nightsRequired;
    }

    public Integer getNightsDiscounted() {
        return nightsDiscounted;
    }

    public void setNightsDiscounted(Integer nightsDiscounted) {
        this.nightsDiscounted = nightsDiscounted;
    }
}
