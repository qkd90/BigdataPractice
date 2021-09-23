package com.zuipin.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.junit.Ignore;

import com.zuipin.pojo.enums.SaleType;

/**
 * OrderDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "order_detail")
public class OrderDetail extends com.framework.hibernate.util.Entity implements java.io.Serializable {
	
	// Fields
	
	private static final long	serialVersionUID	= 1L;
	// /////////////////////目前可用字段
	private Long				id;
	private Long				orderId;					// 订单Id
	private Product				product;					// 商品Id
	private Double				count;						// 购买数量
	private Double				unitPrice;					// 商品单价[原来单价](折前)
	private Double				originalTotalPrice;		// 商品总额(折前)
	private Double				price;						// 优惠价,折后单价=unitPrice*discount(折后)
	private Double				totalPrice;				// 折后总价=count*price(折后)
	private Double				averagePrice;				// 分摊成本价（核算后）
	private Double				costPrice;					// 成本价(取商品价格成本的值，用于财务计算)
	private Double				costTotalPrice;			// 商品成本总额(核算后：costPrice*count)
	private Double				discount;					// 折扣
	private Double				payAmt;					// 应收账款[跟totalPrice重复，可废弃]
	private String				orderItemId;				// 茶帮通 sub_order_Item 的ORDER_ITEM_ID;
	private String				remark;					// 备注
	// //////////////////////////////暂时没用到
	private String				orderNo;					// 订单号
	private Double				promDiscount;
	private Double				finalDiscount;
	
	private SaleType			saleType;					// 改为枚举
	private String				giftType;
	private Double				orderCount;
	private Double				couponDeductAmt;
	private Boolean				isGift;
	private Boolean				subState;
	private Boolean				isSign;
	private Boolean				isComm;
	private Boolean				isFreeOrder;
	
	// 赠送积分
	private Integer				giftPoint;
	// 抵扣积分
	private Integer				offsetPoint;
	
	private Double				giftRetAmt;
	private Double				LackGood;					// 是否是缺货 默认不缺货，1：缺货
															
	// Constructors
	@Column(name = "GIFT_RET_AMT", precision = 12)
	public Double getGiftRetAmt() {
		return this.giftRetAmt;
	}
	
	public void setGiftRetAmt(Double giftRetAmt) {
		this.giftRetAmt = giftRetAmt;
	}
	
	// Constructors
	@Column(name = "GIFT_POINT")
	public Integer getGiftPoint() {
		return giftPoint;
	}
	
	public void setGiftPoint(Integer giftPoint) {
		this.giftPoint = giftPoint;
	}
	
	@Column(name = "OFFSET_POINT")
	public Integer getOffsetPoint() {
		return offsetPoint;
	}
	
	public void setOffsetPoint(Integer offsetPoint) {
		this.offsetPoint = offsetPoint;
	}
	
	/** default constructor */
	public OrderDetail() {
	}
	
	/** minimal constructor */
	public OrderDetail(Long orderId, Product proId, Double count, String orderNo, Double unitPrice, Double discount, Double totalPrice, Double payAmt, Double promDiscount,
			Double finalDiscount, SaleType saleType, Boolean isSign, Boolean isComm) {
		this.orderId = orderId;
		this.product = proId;
		this.count = count;
		this.orderNo = orderNo;
		this.unitPrice = unitPrice;
		this.discount = discount;
		this.totalPrice = totalPrice;
		this.payAmt = payAmt;
		this.promDiscount = promDiscount;
		this.finalDiscount = finalDiscount;
		this.saleType = saleType;
		this.isSign = isSign;
		this.isComm = isComm;
	}
	
	/** full constructor */
	public OrderDetail(Long orderId, Product proId, Double count, String orderNo, Double unitPrice, Double discount, Double totalPrice, Double payAmt, Double promDiscount,
			Double finalDiscount, SaleType saleType, String remark, String giftType, Double orderCount, Double couponDeductAmt, Boolean isGift, Boolean subState, Boolean isSign,
			Boolean isComm, Boolean isFreeOrder) {
		this.orderId = orderId;
		this.product = proId;
		this.count = count;
		this.orderNo = orderNo;
		this.unitPrice = unitPrice;
		this.discount = discount;
		this.totalPrice = totalPrice;
		this.payAmt = payAmt;
		this.promDiscount = promDiscount;
		this.finalDiscount = finalDiscount;
		this.saleType = saleType;
		this.remark = remark;
		this.giftType = giftType;
		this.orderCount = orderCount;
		this.couponDeductAmt = couponDeductAmt;
		this.isGift = isGift;
		this.subState = subState;
		this.isSign = isSign;
		this.isComm = isComm;
		this.isFreeOrder = isFreeOrder;
	}
	
	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "ORDER_ID")
	public Long getOrderId() {
		return this.orderId;
	}
	
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	@Ignore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PRO_ID")
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	@Column(name = "COUNT", precision = 12)
	public Double getCount() {
		return this.count;
	}
	
	public void setCount(Double count) {
		this.count = count;
	}
	
	@Column(name = "ORDER_NO", length = 40)
	public String getOrderNo() {
		return this.orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Column(name = "UNIT_PRICE", precision = 18)
	public Double getUnitPrice() {
		return this.unitPrice;
	}
	
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	@Column(name = "DISCOUNT", precision = 12)
	public Double getDiscount() {
		return this.discount;
	}
	
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
	@Column(name = "TOTAL_PRICE", precision = 18)
	public Double getTotalPrice() {
		return this.totalPrice;
	}
	
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@Column(name = "PAY_AMT", precision = 18)
	public Double getPayAmt() {
		return this.payAmt;
	}
	
	public void setPayAmt(Double payAmt) {
		this.payAmt = payAmt;
	}
	
	@Column(name = "PROM_DISCOUNT", precision = 12)
	public Double getPromDiscount() {
		return this.promDiscount;
	}
	
	public void setPromDiscount(Double promDiscount) {
		this.promDiscount = promDiscount;
	}
	
	@Column(name = "FINAL_DISCOUNT", precision = 12)
	public Double getFinalDiscount() {
		return this.finalDiscount;
	}
	
	public void setFinalDiscount(Double finalDiscount) {
		this.finalDiscount = finalDiscount;
	}
	
	@Column(name = "SALE_TYPE")
	@Enumerated(EnumType.STRING)
	public SaleType getSaleType() {
		return this.saleType;
	}
	
	public void setSaleType(SaleType saleType) {
		this.saleType = saleType;
	}
	
	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "GIFT_TYPE", length = 1)
	public String getGiftType() {
		return this.giftType;
	}
	
	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}
	
	@Column(name = "ORDER_COUNT", precision = 12)
	public Double getOrderCount() {
		return this.orderCount;
	}
	
	public void setOrderCount(Double orderCount) {
		this.orderCount = orderCount;
	}
	
	@Column(name = "COUPON_DEDUCT_AMT", precision = 12)
	public Double getCouponDeductAmt() {
		return this.couponDeductAmt;
	}
	
	public void setCouponDeductAmt(Double couponDeductAmt) {
		this.couponDeductAmt = couponDeductAmt;
	}
	
	@Column(name = "IS_GIFT")
	public Boolean getIsGift() {
		return this.isGift;
	}
	
	public void setIsGift(Boolean isGift) {
		this.isGift = isGift;
	}
	
	@Column(name = "SUB_STATE")
	public Boolean getSubState() {
		return this.subState;
	}
	
	public void setSubState(Boolean subState) {
		this.subState = subState;
	}
	
	@Column(name = "IS_SIGN")
	public Boolean getIsSign() {
		return this.isSign;
	}
	
	public void setIsSign(Boolean isSign) {
		this.isSign = isSign;
	}
	
	@Column(name = "IS_COMM")
	public Boolean getIsComm() {
		return this.isComm;
	}
	
	public void setIsComm(Boolean isComm) {
		this.isComm = isComm;
	}
	
	@Column(name = "IS_FREE_ORDER")
	public Boolean getIsFreeOrder() {
		return this.isFreeOrder;
	}
	
	public void setIsFreeOrder(Boolean isFreeOrder) {
		this.isFreeOrder = isFreeOrder;
	}
	
	@Column(name = "PRICE")
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Column(name = "AVERAGE_PRICE")
	public Double getAveragePrice() {
		return averagePrice;
	}
	
	public void setAveragePrice(Double averagePrice) {
		this.averagePrice = averagePrice;
	}
	
	@Column(name = "COST_PRICE")
	public Double getCostPrice() {
		return costPrice;
	}
	
	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}
	
	@Transient
	public Double getLackGood() {
		return LackGood;
	}
	
	public void setLackGood(Double lackGood) {
		LackGood = lackGood;
	}
	
	@Column(name = "ORIGINAL_TOTAL_PRICE")
	public Double getOriginalTotalPrice() {
		return originalTotalPrice;
	}
	
	public void setOriginalTotalPrice(Double originalTotalPrice) {
		this.originalTotalPrice = originalTotalPrice;
	}
	
	@Column(name = "COST_TOTAL_PRICE")
	public Double getCostTotalPrice() {
		return costTotalPrice;
	}
	
	public void setCostTotalPrice(Double costTotalPrice) {
		this.costTotalPrice = costTotalPrice;
	}
	
	@Column(name = "orderItemId")
	public String getOrderItemId() {
		return orderItemId;
	}
	
	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}
	
}