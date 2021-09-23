package com.data.data.hmly.action.outOrder.dataEntities;

/**
 * Created by dy on 2016/3/16.
 */
public class OrderDetailEntity {


//    orderStartTime:startTime,
//    count:count,
//    ticketPriceId:perValue.id,
//    ticketPrice:perValue.discountPrice,
//    validay:$("#hipt_validDay").val()
//    type:perValue.type,
//    ticketName:perValue.name,


    private String orderStartTime;
    private Integer count;
    private Long ticketPriceId;
    private Float ticketPrice;
    private Integer validay;
    private String ticketName;
    private String type;
    private Float salesPrice;


    public String getOrderStartTime() {
        return orderStartTime;
    }

    public void setOrderStartTime(String orderStartTime) {
        this.orderStartTime = orderStartTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getTicketPriceId() {
        return ticketPriceId;
    }

    public void setTicketPriceId(Long ticketPriceId) {
        this.ticketPriceId = ticketPriceId;
    }

    public Float getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Float ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Integer getValiday() {
        return validay;
    }

    public void setValiday(Integer validay) {
        this.validay = validay;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(Float salesPrice) {
        this.salesPrice = salesPrice;
    }
}
