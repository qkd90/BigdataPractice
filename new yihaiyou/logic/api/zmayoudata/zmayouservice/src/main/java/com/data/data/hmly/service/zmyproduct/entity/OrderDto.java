package com.data.data.hmly.service.zmyproduct.entity;

/**
 * Created by dy on 2016/5/9.
 */
public class OrderDto {

    private Long distributorId;
    private String outOrderNo;
    private Float total;
    private String buyerName;
    private String buyerIdNo;
    private String buyerMobile;

    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    public String getOutOrderNo() {
        return outOrderNo;
    }

    public void setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerIdNo() {
        return buyerIdNo;
    }

    public void setBuyerIdNo(String buyerIdNo) {
        this.buyerIdNo = buyerIdNo;
    }

    public String getBuyerMobile() {
        return buyerMobile;
    }

    public void setBuyerMobile(String buyerMobile) {
        this.buyerMobile = buyerMobile;
    }
}
