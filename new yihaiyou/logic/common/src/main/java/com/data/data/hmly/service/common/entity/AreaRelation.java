package com.data.data.hmly.service.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by vacuity on 15/12/31.
 */

@Entity
@Table(name = "tb_area_relation")
public class AreaRelation extends com.framework.hibernate.util.Entity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "ctrip_hotel_city")
    private Long ctripHotelCity;

    @Column(name = "ctrip_scenic_city")
    private Integer ctripScenicCity;

    @Column(name = "ctrip_flight_city")
    private String ctripFlightCity;

    @Column(name = "elong_hotel_city")
    private Integer elongHotelCity;
    @Column(name = "mfw_note_city")
    private Integer mfwNoteCity;

    @Column(name = "trip_advisor_city")
    private String tripAdvisorCity;

    @Column(name = "trip_advisor_used")
    private String tripAdvisorUsed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCtripHotelCity() {
        return ctripHotelCity;
    }

    public void setCtripHotelCity(Long ctripHotelCity) {
        this.ctripHotelCity = ctripHotelCity;
    }

    public Integer getCtripScenicCity() {
        return ctripScenicCity;
    }

    public void setCtripScenicCity(Integer ctripScenicCity) {
        this.ctripScenicCity = ctripScenicCity;
    }

    public String getCtripFlightCity() {
        return ctripFlightCity;
    }

    public void setCtripFlightCity(String ctripFlightCity) {
        this.ctripFlightCity = ctripFlightCity;
    }

    public Integer getElongHotelCity() {
        return elongHotelCity;
    }

    public void setElongHotelCity(Integer elongHotelCity) {
        this.elongHotelCity = elongHotelCity;
    }

    public Integer getMfwNoteCity() {
        return mfwNoteCity;
    }

    public void setMfwNoteCity(Integer mfwNoteCity) {
        this.mfwNoteCity = mfwNoteCity;
    }

    public String getTripAdvisorCity() {
        return tripAdvisorCity;
    }

    public void setTripAdvisorCity(String tripAdvisorCity) {
        this.tripAdvisorCity = tripAdvisorCity;
    }

    public String getTripAdvisorUsed() {
        return tripAdvisorUsed;
    }

    public void setTripAdvisorUsed(String tripAdvisorUsed) {
        this.tripAdvisorUsed = tripAdvisorUsed;
    }
}
