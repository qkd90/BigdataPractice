package com.data.data.hmly.action.order.vo;

import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.order.entity.enums.OrderCostPriceType;
import com.data.data.hmly.service.order.entity.enums.OrderCostType;

import java.util.Date;

/**
 * Created by guoshijie on 2015/11/10.
 */
public class OrderDetailVo {

	private Long               id;
	private Float              unitPrice;
	private Integer            num;
	private Float              totalPrice;
	private Float              promDiscount;
	private Float              discountPrice;
	private Float              yuePay;
	private Float              onlinePay;
	private Float              couponPay;
	private Float              jifenPay;
	private Float              finalPrice;
	private Long               promotionId;
	private Date               playDate;
	private ProductType        productType;
	private Long               costId;
	private OrderCostType      costType;
	private String             costName;
	private OrderCostPriceType priceType;
	private Product            product;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Float getPromDiscount() {
		return promDiscount;
	}

	public void setPromDiscount(Float promDiscount) {
		this.promDiscount = promDiscount;
	}

	public Float getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Float discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Float getYuePay() {
		return yuePay;
	}

	public void setYuePay(Float yuePay) {
		this.yuePay = yuePay;
	}

	public Float getOnlinePay() {
		return onlinePay;
	}

	public void setOnlinePay(Float onlinePay) {
		this.onlinePay = onlinePay;
	}

	public Float getCouponPay() {
		return couponPay;
	}

	public void setCouponPay(Float couponPay) {
		this.couponPay = couponPay;
	}

	public Float getJifenPay() {
		return jifenPay;
	}

	public void setJifenPay(Float jifenPay) {
		this.jifenPay = jifenPay;
	}

	public Float getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(Float finalPrice) {
		this.finalPrice = finalPrice;
	}

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

	public Date getPlayDate() {
		return playDate;
	}

	public void setPlayDate(Date playDate) {
		this.playDate = playDate;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public Long getCostId() {
		return costId;
	}

	public void setCostId(Long costId) {
		this.costId = costId;
	}

	public OrderCostType getCostType() {
		return costType;
	}

	public void setCostType(OrderCostType costType) {
		this.costType = costType;
	}

	public String getCostName() {
		return costName;
	}

	public void setCostName(String costName) {
		this.costName = costName;
	}

	public OrderCostPriceType getPriceType() {
		return priceType;
	}

	public void setPriceType(OrderCostPriceType priceType) {
		this.priceType = priceType;
	}

}
