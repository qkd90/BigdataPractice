package com.zuipin.model;

import java.util.ArrayList;
import java.util.List;

import com.zuipin.entity.TMemberSpecialCashback;
import com.zuipin.model.promotion.BuySendGood;
import com.zuipin.model.promotion.BuySendMoney;

@SuppressWarnings("serial")
public class MerchantStore implements java.io.Serializable {
    
    private Long                   storeId;
    /**
     * .店铺名称
     */
    private String                 storeName;
    /**
     * .服务QQ
     */
    private String                 serviceQq;
    /**
     * .店铺类型
     */
    private String                 storeType;
    /**
     * .店铺地址
     */
    private String                 storeAddress;
    
    /**
     * .店铺电话
     */
    private String                 storePhone;
    /**
     * .商品项
     */
    private List<CartItem>         cartItems      = new ArrayList<CartItem>();
    /**
     * 订单应付总金额
     */
    private double                 totalAmt       = 0.0;
    /**
     * .是否免运费
     */
    private boolean                freeCarry;
    /**
     * .满多少免运费
     */
    private double                 freeCarryCost;
    /**
     * .运费
     */
    private double                 carryCost;
    /**
     * .专用返现赠送比例
     */
    private double                 zyPresentCashback;
    /**
     * .专用返现抵扣比例
     */
    private double                 zyDeductionCashback;
    /**
     * .通用返现赠送比例
     */
    private double                 tyPresentCashback;
    /**
     * .通用返现抵扣比例
     */
    private double                 tyDeductionCashback;
    /**
     * 赠送专用返现总金额
     */
    private double                 totalZyGiftCashbackAmt;
    /**
     * 赠送通用返现总金额
     */
    private double                 totalTyGiftCashbackAmt;
    /**
     * .专用返现抵扣总金额
     */
    private double                 totalZyDeductibleCashbackAmt;
    /**
     * .通用返现抵扣总金额
     */
    private double                 totalTyDeductibleCashbackAmt;
    /**
     * .是否使用专返抵扣
     */
    private boolean                isDeductible   = false;
    /**
     * .配送方式
     */
    private int                    deliveryMode   = 1;
    /**
     * 买商品送商品促销
     */
    private List<BuySendGood>      buySendGoods   = new ArrayList<BuySendGood>();
    /**
     * 满足优惠券使用条件商品项
     */
    private List<CartItem>         couponItems    = new ArrayList<CartItem>();
    /**
     * 优惠券抵扣金额
     */
    private double                 totalCouponAmt = 0.0;
    /**
     * 使用的优惠券id
     */
    private Long                   couponMemberId;
    /**
     * 会员优惠券列表
     */
    private List<CouponItem>       couponList     = new ArrayList<CouponItem>();
    
    private TMemberSpecialCashback memberSpecialCashback;
    
    /**
     * .买多少返多少
     */
    private List<BuySendMoney>     buySendMoneys  = new ArrayList<BuySendMoney>();
    
    public Long getStoreId() {
        return storeId;
    }
    
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
    
    public String getStoreName() {
        return storeName;
    }
    
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    
    public String getStoreType() {
        return storeType;
    }
    
    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }
    
    public String getServiceQq() {
        return serviceQq;
    }
    
    public void setServiceQq(String serviceQq) {
        this.serviceQq = serviceQq;
    }
    
    public boolean isFreeCarry() {
        return freeCarry;
    }
    
    public void setFreeCarry(boolean freeCarry) {
        this.freeCarry = freeCarry;
    }
    
    public double getFreeCarryCost() {
        return freeCarryCost;
    }
    
    public void setFreeCarryCost(double freeCarryCost) {
        this.freeCarryCost = freeCarryCost;
    }
    
    public double getCarryCost() {
        return carryCost;
    }
    
    public void setCarryCost(double carryCost) {
        this.carryCost = carryCost;
    }
    
    public List<CartItem> getCartItems() {
        return cartItems;
    }
    
    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
    
    public List<BuySendGood> getBuySendGoods() {
        return buySendGoods;
    }
    
    public void setBuySendGoods(List<BuySendGood> buySendGoods) {
        this.buySendGoods = buySendGoods;
    }
    
    public double getTotalAmt() {
        return totalAmt;
    }
    
    public void setTotalAmt(double totalAmt) {
        this.totalAmt = totalAmt;
    }
    
    public List<BuySendMoney> getBuySendMoneys() {
        return buySendMoneys;
    }
    
    public void setBuySendMoneys(List<BuySendMoney> buySendMoneys) {
        this.buySendMoneys = buySendMoneys;
    }
    
    public double getZyPresentCashback() {
        return zyPresentCashback;
    }
    
    public void setZyPresentCashback(double zyPresentCashback) {
        this.zyPresentCashback = zyPresentCashback;
    }
    
    public double getZyDeductionCashback() {
        return zyDeductionCashback;
    }
    
    public void setZyDeductionCashback(double zyDeductionCashback) {
        this.zyDeductionCashback = zyDeductionCashback;
    }
    
    public double getTyPresentCashback() {
        return tyPresentCashback;
    }
    
    public void setTyPresentCashback(double tyPresentCashback) {
        this.tyPresentCashback = tyPresentCashback;
    }
    
    public double getTyDeductionCashback() {
        return tyDeductionCashback;
    }
    
    public void setTyDeductionCashback(double tyDeductionCashback) {
        this.tyDeductionCashback = tyDeductionCashback;
    }
    
    public boolean isDeductible() {
        return isDeductible;
    }
    
    public void setDeductible(boolean isDeductible) {
        this.isDeductible = isDeductible;
    }
    
    public int getDeliveryMode() {
        return deliveryMode;
    }
    
    public void setDeliveryMode(int deliveryMode) {
        this.deliveryMode = deliveryMode;
    }
    
    public List<CartItem> getCouponItems() {
        return couponItems;
    }
    
    public void setCouponItems(List<CartItem> couponItems) {
        this.couponItems = couponItems;
    }
    
    public double getTotalCouponAmt() {
        return totalCouponAmt;
    }
    
    public void setTotalCouponAmt(double totalCouponAmt) {
        this.totalCouponAmt = totalCouponAmt;
    }
    
    public Long getCouponMemberId() {
        return couponMemberId;
    }
    
    public void setCouponMemberId(Long couponMemberId) {
        this.couponMemberId = couponMemberId;
    }
    
    public double getTotalZyGiftCashbackAmt() {
        return totalZyGiftCashbackAmt;
    }
    
    public void setTotalZyGiftCashbackAmt(double totalZyGiftCashbackAmt) {
        this.totalZyGiftCashbackAmt = totalZyGiftCashbackAmt;
    }
    
    public double getTotalTyGiftCashbackAmt() {
        return totalTyGiftCashbackAmt;
    }
    
    public void setTotalTyGiftCashbackAmt(double totalTyGiftCashbackAmt) {
        this.totalTyGiftCashbackAmt = totalTyGiftCashbackAmt;
    }
    
    public double getTotalZyDeductibleCashbackAmt() {
        return totalZyDeductibleCashbackAmt;
    }
    
    public void setTotalZyDeductibleCashbackAmt(double totalZyDeductibleCashbackAmt) {
        this.totalZyDeductibleCashbackAmt = totalZyDeductibleCashbackAmt;
    }
    
    public double getTotalTyDeductibleCashbackAmt() {
        return totalTyDeductibleCashbackAmt;
    }
    
    public void setTotalTyDeductibleCashbackAmt(double totalTyDeductibleCashbackAmt) {
        this.totalTyDeductibleCashbackAmt = totalTyDeductibleCashbackAmt;
    }
    
    public List<CouponItem> getCouponList() {
        return couponList;
    }
    
    public void setCouponList(List<CouponItem> couponList) {
        this.couponList = couponList;
    }
    
    public TMemberSpecialCashback getMemberSpecialCashback() {
        return memberSpecialCashback;
    }
    
    public void setMemberSpecialCashback(TMemberSpecialCashback memberSpecialCashback) {
        this.memberSpecialCashback = memberSpecialCashback;
    }
    
    public String getStoreAddress() {
        return storeAddress;
    }
    
    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }
    
    public String getStorePhone() {
        return storePhone;
    }
    
    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }
    
}
