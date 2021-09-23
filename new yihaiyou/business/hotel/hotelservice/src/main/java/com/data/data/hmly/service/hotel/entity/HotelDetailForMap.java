package com.data.data.hmly.service.hotel.entity;

import com.data.data.hmly.service.common.entity.Product;

import java.util.List;

public class HotelDetailForMap extends Product implements java.io.Serializable {


    private Float score;
    private Integer commentsNum;
    private String address;
    private Integer star;
    private List<String> images;
    private List<HotelPrice> prices;

    public Integer getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(Integer commentsNum) {
        this.commentsNum = commentsNum;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public List<HotelPrice> getPrices() {
        return prices;
    }

    public void setPrices(List<HotelPrice> prices) {
        this.prices = prices;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }
}
