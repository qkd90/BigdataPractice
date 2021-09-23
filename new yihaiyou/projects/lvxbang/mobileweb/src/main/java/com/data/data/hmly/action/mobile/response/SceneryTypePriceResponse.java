package com.data.data.hmly.action.mobile.response;


import com.data.data.hmly.service.ticket.entity.TicketPrice;

import java.util.Date;

/**
 * Created by dy on 2016/9/19.
 */
public class SceneryTypePriceResponse {

    private Long id;                                            // 主键ID
    private String name;                                        // 门票类型名称
    private String type;                                        // 票型
    private String getTicket;                                    // 取票方式
    private String orderKnow;                                    // 预定须知
    private Float commission;                                    // 佣金
    private Float discountPrice;                                // 分销价
    private Float maketPrice;                                   //原价
    private Long ticketId;                                        // 门票编号
    private Long userid;                                        // 创建人
    private Integer status;                                        // 状态
    private Date addTime;                                    // 创建时间
    private Float price; //分销价


    public SceneryTypePriceResponse(TicketPrice ticketPrice) {
        this.id = ticketPrice.getId();
        this.name = ticketPrice.getName();
        this.type = ticketPrice.getType();
        this.getTicket = ticketPrice.getGetTicket();
        this.orderKnow = ticketPrice.getOrderKnow();
        this.commission = ticketPrice.getCommission();
        this.discountPrice = ticketPrice.getDiscountPrice();
        this.maketPrice = ticketPrice.getMaketPrice();
        if (ticketPrice.getTicket() != null) {
            this.ticketId = ticketPrice.getTicket().getId();
        }
        this.userid = ticketPrice.getUserid();
//        this.status = ticketPrice.getStatus();
        this.addTime = ticketPrice.getAddTime();
        this.price = ticketPrice.getPrice();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGetTicket() {
        return getTicket;
    }

    public void setGetTicket(String getTicket) {
        this.getTicket = getTicket;
    }

    public String getOrderKnow() {
        return orderKnow;
    }

    public void setOrderKnow(String orderKnow) {
        this.orderKnow = orderKnow;
    }

    public Float getCommission() {
        return commission;
    }

    public void setCommission(Float commission) {
        this.commission = commission;
    }

    public Float getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Float discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Float getMaketPrice() {
        return maketPrice;
    }

    public void setMaketPrice(Float maketPrice) {
        this.maketPrice = maketPrice;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
