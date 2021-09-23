package com.data.data.hmly.action.yhypc.response;

import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelExtend;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;

/**
 * Created by huangpeijie on 2016-09-30,0030.
 */
public class OptimizeDetailHotelResponse {
    private Long id;
    private String name;
    private String cover;
    private Long priceId;
    private Float price;
    private String priceName;
    private String address;
    private String description;
    private Double lng;
    private Double lat;
    private String startDate;
    private String endDate;

    public OptimizeDetailHotelResponse() {
    }

    public OptimizeDetailHotelResponse(HotelPrice hotelPrice) {
        Hotel hotel = hotelPrice.getHotel();
        this.id = hotel.getId();
        this.name = hotel.getName();
        this.cover = hotel.getCover();
        HotelExtend extend = hotel.getExtend();
        if (extend != null) {
            this.address = extend.getAddress();
            this.description = extend.getDescription();
            this.lng = extend.getLongitude();
            this.lat = extend.getLatitude();
        }
        this.priceId = hotelPrice.getPriceId();
        this.priceName = hotelPrice.getRoomName();
    }

    private String cover(String cover) {
        if (StringUtils.isBlank(cover)) {
            return QiniuUtil.URL + "jiudian.png";
        }
        if (cover.startsWith("http")) {
            return cover;
        }
        return QiniuUtil.URL + cover;
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

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
