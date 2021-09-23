package com.data.data.hmly.service.ctriphotel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by vacuity on 15/12/4.
 */
@Entity
@Table(name = "ctrip_hotel")
public class CtripHotel extends com.framework.hibernate.util.Entity {
    private Long id;
    private Long hotelCityCode;
    private String hotelName;
    private Integer areaId;
    private String address;
    private Double longitude;
    private Double latitude;
    private String arrivalAndDeparture;
    private String cancel;
    private String depositAndPrepaid;
    private String pet;
    private String requirements;
    private Float hotelStarLicence;
    private Float hotelStarRate;
    private Float ctripStarRate;
    private Date createdTime;
    private Date updateTime;
    private Float ctripUserRate;
    private Float ctripCommRate;
    private Boolean valid;

    @Id
//    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    @Column(name = "hotelCityCode")
    public Long getHotelCityCode() {
        return hotelCityCode;
    }

    public void setHotelCityCode(Long hotelCityCode) {
        this.hotelCityCode = hotelCityCode;
    }

    
    @Column(name = "hotelName")
    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    
    @Column(name = "areaId")
    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }
    
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    
    @Column(name = "longitude")
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    
    @Column(name = "latitude")
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    
    @Column(name = "arrivalAndDeparture")
    public String getArrivalAndDeparture() {
        return arrivalAndDeparture;
    }

    public void setArrivalAndDeparture(String arrivalAndDeparture) {
        this.arrivalAndDeparture = arrivalAndDeparture;
    }

    
    @Column(name = "cancel")
    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    
    @Column(name = "depositAndPrepaid")
    public String getDepositAndPrepaid() {
        return depositAndPrepaid;
    }

    public void setDepositAndPrepaid(String depositAndPrepaid) {
        this.depositAndPrepaid = depositAndPrepaid;
    }

    
    @Column(name = "pet")
    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    @Column(name = "requirements")
    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    
    @Column(name = "hotelStarLicence")
    public Float getHotelStarLicence() {
        return hotelStarLicence;
    }

    public void setHotelStarLicence(Float hotelStarLicence) {
        this.hotelStarLicence = hotelStarLicence;
    }

    
    @Column(name = "hotelStarRate")
    public Float getHotelStarRate() {
        return hotelStarRate;
    }

    public void setHotelStarRate(Float hotelStarRate) {
        this.hotelStarRate = hotelStarRate;
    }

    
    @Column(name = "ctripStarRate")
    public Float getCtripStarRate() {
        return ctripStarRate;
    }

    public void setCtripStarRate(Float ctripStarRate) {
        this.ctripStarRate = ctripStarRate;
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

    @Column(name = "ctripUserRate")
    public Float getCtripUserRate() {
        return ctripUserRate;
    }

    public void setCtripUserRate(Float ctripUserRate) {
        this.ctripUserRate = ctripUserRate;
    }

    @Column(name = "ctripCommRate")
    public Float getCtripCommRate() {
        return ctripCommRate;
    }

    public void setCtripCommRate(Float ctripCommRate) {
        this.ctripCommRate = ctripCommRate;
    }

    @Column(name = "valid")
    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
