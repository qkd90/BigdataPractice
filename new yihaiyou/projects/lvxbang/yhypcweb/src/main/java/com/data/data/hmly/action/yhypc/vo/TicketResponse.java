package com.data.data.hmly.action.yhypc.vo;

import com.data.data.hmly.service.ticket.vo.TicketSolrEntity;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-12,0012.
 */
public class TicketResponse {

    private Long id;
    private Float discountPrice;
    private String name;
    private String address;
    private Integer productScore;
    private Integer orderNum;
    private String productImg;
    private Float stars;
    private List<TicketPriceResponse> priceList;



    public TicketResponse(TicketSolrEntity solrEntity) {
        this.id = solrEntity.getId();
        this.discountPrice = solrEntity.getDisCountPrice();
        this.name = solrEntity.getName();
        this.address = solrEntity.getAddress();
        this.productImg = solrEntity.getProductImg();
        this.productScore = solrEntity.getProductScore();
        this.orderNum = solrEntity.getOrderNum();
    }

    public List<TicketPriceResponse> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<TicketPriceResponse> priceList) {
        this.priceList = priceList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Float discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getProductScore() {
        return productScore;
    }

    public void setProductScore(Integer productScore) {
        this.productScore = productScore;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Float getStars() {
        return stars;
    }

    public void setStars(Float stars) {
        this.stars = stars;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }
}
