package com.data.data.hmly.action.yhypc.response;

import com.data.data.hmly.service.scenic.vo.ScenicSolrEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-09-30,0030.
 */
public class OptimizeDetailDayResponse {
    private Integer day;
    private List<ScenicSolrEntity> scenics;
    private OptimizeDetailHotelResponse hotel;
    private Boolean inIsland;
    private Date date;
    private Float price;
    private Float playTime;
    private Boolean needShip;
    private Boolean needHotel;
    private Long coreScenic;
    private String startDate;
    private String endDate;
    private Map<String, Object> ferry;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public List<ScenicSolrEntity> getScenics() {
        return scenics;
    }

    public void setScenics(List<ScenicSolrEntity> scenics) {
        this.scenics = scenics;
    }

    public OptimizeDetailHotelResponse getHotel() {
        return hotel;
    }

    public void setHotel(OptimizeDetailHotelResponse hotel) {
        this.hotel = hotel;
    }

    public Boolean getInIsland() {
        return inIsland;
    }

    public void setInIsland(Boolean inIsland) {
        this.inIsland = inIsland;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Float playTime) {
        this.playTime = playTime;
    }

    public Boolean getNeedShip() {
        return needShip;
    }

    public void setNeedShip(Boolean needShip) {
        this.needShip = needShip;
    }

    public Long getCoreScenic() {
        return coreScenic;
    }

    public void setCoreScenic(Long coreScenic) {
        this.coreScenic = coreScenic;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Map<String, Object> getFerry() {
        return ferry;
    }

    public void setFerry(Map<String, Object> ferry) {
        this.ferry = ferry;
    }

    public Boolean getNeedHotel() {
        return needHotel;
    }

    public void setNeedHotel(Boolean needHotel) {
        this.needHotel = needHotel;
    }
}
