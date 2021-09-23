package com.data.data.hmly.action.mobile.response;

import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketExplain;

/**
 * Created by dy on 2016/9/19.
 */
public class SceneryResponse {

    private Long id;
    private String name;
    private String cover;
    private String introduction;
    private String address;
    private Float price;
    private String enterDesc;
    private String rule;
    private String privilege;
    private String proInfo;
    private Long cityId;
    private String openTime;
    private Double lat;
    private Double lng;
    private String trafficInfo;

    public SceneryResponse(Ticket ticket, TicketExplain explain) {
        this.id = ticket.getId();
        this.name = ticket.getName();
        this.cover = ticket.getTicketImgUrl();
        this.address = ticket.getAddress();
        this.price = ticket.getPrice();
        this.cityId = ticket.getCityId();
        if (explain != null) {
            this.enterDesc = explain.getEnterDesc();
            this.rule = explain.getRule();
            this.privilege = explain.getPrivilege();
            this.proInfo = explain.getProInfo();
        }
        if (ticket.getScenicInfo() != null && ticket.getScenicInfo().getScenicOther() != null) {
            this.openTime = ticket.getScenicInfo().getScenicOther().getOpenTime();
            this.trafficInfo = ticket.getScenicInfo().getScenicOther().getTrafficGuide();
        }
        if (ticket.getScenicInfo() != null && ticket.getScenicInfo().getScenicGeoinfo() != null) {
            this.lat = ticket.getScenicInfo().getScenicGeoinfo().getBaiduLat();
            this.lng = ticket.getScenicInfo().getScenicGeoinfo().getBaiduLng();
        }

    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getEnterDesc() {
        return enterDesc;
    }

    public void setEnterDesc(String enterDesc) {
        this.enterDesc = enterDesc;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getProInfo() {
        return proInfo;
    }

    public void setProInfo(String proInfo) {
        this.proInfo = proInfo;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getTrafficInfo() {
        return trafficInfo;
    }

    public void setTrafficInfo(String trafficInfo) {
        this.trafficInfo = trafficInfo;
    }
}
