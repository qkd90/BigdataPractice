package com.zuipin.model;

public class CouponItem {
    private Long   couponId;
    private String name;
    
    public CouponItem() {
    }
    
    public CouponItem(Long couponId, String name) {
        this.couponId = couponId;
        this.name = name;
    }
    
    public Long getCouponId() {
        return couponId;
    }
    
    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
}
