package com.zuipin.model;

import com.zuipin.entity.VProduct;

@SuppressWarnings("serial")
public class CartItem implements java.io.Serializable {
    /**
     * 商品
     */
    private VProduct product;
    /**
     * 商品数量
     */
    private double   prodCount;
    /**
     * 商品价格
     */
    private double   salePrice;
    /**
     * 商品折后小计
     */
    private double   totalAmt;
    /**
     * 商品未优惠小计
     */
    private double   totalSaleAmt;
    /**
     * 赠送专用返现金额
     */
    private double   zyGiftCashbackAmt;
    /**
     * 赠送通用返现金额
     */
    private double   tyGiftCashbackAmt;
    /**
     * .专用返现抵扣
     */
    private double   zyDeductibleCashbackAmt;
    /**
     * .通用返现抵扣
     */
    private double   tyDeductibleCashbackAmt;
    /**
     * 备注
     */
    private String   remark;
    
    /**
     * .
     */
    private double   giftRate;
    
    /**
     * .促销模版ID
     */
    private Long     modelId;
    
    /**
     * .促销模式
     */
    private String   promNo;
    
    /**
     * .促销类型
     */
    private String   promType;
    
    /**
     * .是否免运费
     */
    private boolean  freeCarry;
    /**
     * 优惠券总抵扣商品金额
     */
    private double   totalCouponAmt;
    
    public CartItem() {
        
    }
    
    public CartItem(VProduct product, double prodCount) {
        this.product = product;
        this.prodCount = prodCount;
    }
    
    public VProduct getProduct() {
        return product;
    }
    
    public void setProduct(VProduct product) {
        this.product = product;
    }
    
    public double getProdCount() {
        return prodCount;
    }
    
    public void setProdCount(double prodCount) {
        this.prodCount = prodCount;
    }
    
    public double getSalePrice() {
        return salePrice;
    }
    
    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }
    
    public double getTotalAmt() {
        return totalAmt;
    }
    
    public void setTotalAmt(double totalAmt) {
        this.totalAmt = totalAmt;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public Long getModelId() {
        return modelId;
    }
    
    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }
    
    public String getPromNo() {
        return promNo;
    }
    
    public void setPromNo(String promNo) {
        this.promNo = promNo;
    }
    
    public String getPromType() {
        return promType;
    }
    
    public void setPromType(String promType) {
        this.promType = promType;
    }
    
    public boolean isFreeCarry() {
        return freeCarry;
    }
    
    public void setFreeCarry(boolean freeCarry) {
        this.freeCarry = freeCarry;
    }
    
    public double getGiftRate() {
        return giftRate;
    }
    
    public void setGiftRate(double giftRate) {
        this.giftRate = giftRate;
    }
    
    public double getTotalCouponAmt() {
        return totalCouponAmt;
    }
    
    public void setTotalCouponAmt(double totalCouponAmt) {
        this.totalCouponAmt = totalCouponAmt;
    }
    
    public double getZyGiftCashbackAmt() {
        return zyGiftCashbackAmt;
    }
    
    public void setZyGiftCashbackAmt(double zyGiftCashbackAmt) {
        this.zyGiftCashbackAmt = zyGiftCashbackAmt;
    }
    
    public double getTyGiftCashbackAmt() {
        return tyGiftCashbackAmt;
    }
    
    public void setTyGiftCashbackAmt(double tyGiftCashbackAmt) {
        this.tyGiftCashbackAmt = tyGiftCashbackAmt;
    }
    
    public double getZyDeductibleCashbackAmt() {
        return zyDeductibleCashbackAmt;
    }
    
    public void setZyDeductibleCashbackAmt(double zyDeductibleCashbackAmt) {
        this.zyDeductibleCashbackAmt = zyDeductibleCashbackAmt;
    }
    
    public double getTyDeductibleCashbackAmt() {
        return tyDeductibleCashbackAmt;
    }
    
    public void setTyDeductibleCashbackAmt(double tyDeductibleCashbackAmt) {
        this.tyDeductibleCashbackAmt = tyDeductibleCashbackAmt;
    }
    
    public double getTotalSaleAmt() {
        return totalSaleAmt;
    }
    
    public void setTotalSaleAmt(double totalSaleAmt) {
        this.totalSaleAmt = totalSaleAmt;
    }
    
}
