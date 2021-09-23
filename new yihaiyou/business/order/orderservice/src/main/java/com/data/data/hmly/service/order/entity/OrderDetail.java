package com.data.data.hmly.service.order.entity;

import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.order.entity.enums.OrderBillType;
import com.data.data.hmly.service.order.entity.enums.OrderCostPriceType;
import com.data.data.hmly.service.order.entity.enums.OrderCostType;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * Created by guoshijie on 2015/10/14.
 */
@Entity
@Table(name = "torderdetail")
@JsonIgnoreProperties
public class OrderDetail extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 9153056917765595961L;
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false, insertable = true, updatable = true)
	private Long                 id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderId")
	private Order                order;
	@OneToOne
	@JoinColumn(name = "proId")
	private Product              product;
	@Basic
	@Column(name = "unitPrice", nullable = true, insertable = true, updatable = true, precision = 2)
	private Float                unitPrice;
	@Basic
	@Column(name = "num", nullable = true, insertable = true, updatable = true)
	private Integer              num;
	@Basic
	@Column(name = "totalPrice", nullable = true, insertable = true, updatable = true, precision = 2)
	private Float                totalPrice;
	@Basic
	@Column(name = "promDiscount", nullable = true, insertable = true, updatable = true, precision = 2)
	private Float promDiscount = 0f;
	@Basic
	@Column(name = "discountPrice", nullable = true, insertable = true, updatable = true, precision = 2)
	private Float discountPrice = 0f;
	@Basic
	@Column(name = "yuePay", nullable = true, insertable = true, updatable = true, precision = 2)
	private Float yuePay = 0f;
	@Basic
	@Column(name = "onlinePay", nullable = true, insertable = true, updatable = true, precision = 2)
	private Float onlinePay = 0f;
	@Basic
	@Column(name = "couponPay", nullable = true, insertable = true, updatable = true, precision = 2)
	private Float couponPay = 0f;
	@Basic
	@Column(name = "jifenPay", nullable = true, insertable = true, updatable = true, precision = 2)
	private Float jifenPay = 0f;
	@Basic
	@Column(name = "finalPrice", nullable = true, insertable = true, updatable = true, precision = 2)
	private Float                finalPrice;
    @Basic
    @Column(name = "combinePay", nullable = true, insertable = true, updatable = true, precision = 2)
    private Float combinePay = 0f;
    @Basic
    @Column(name = "refund")
    private Float refund;

    @ManyToOne
    @JoinColumn(name = "cardId")
    private CreditCard creditCard;

	@Basic
	@Column(name = "promotionId", nullable = true, insertable = true, updatable = true)
	private Long                 promotionId;
	@Basic
	@Column(name = "playDate", nullable = true, insertable = true, updatable = true)
    @Temporal(TemporalType.DATE)
	private Date                 playDate;
	@Basic
	@Column(name = "productType", nullable = true, insertable = true, updatable = true)
	@Enumerated(EnumType.STRING)
	private ProductType          productType;
	@Basic
	@Column(name = "costId", nullable = true, insertable = true, updatable = true)
	private Long                 costId;
	@Basic
	@Column(name = "costType", nullable = true, insertable = true, updatable = true)
	@Enumerated(EnumType.STRING)
	private OrderCostType        costType;
	@Basic
	@Column(name = "costName", nullable = true, insertable = true, updatable = true)
	private String               costName;
	@OneToOne(mappedBy = "orderDetail")
	private OrderDetailFlattened orderDetailFlattened;
	@Transient
	private OrderCostPriceType   priceType;
	@OneToMany(mappedBy = "orderDetail")
	private List<Commission> commissions;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cityId")
	private TbArea city;
	@Column(name = "day")
	private Integer day;

	@Basic
	@Column(name = "days")
	private Integer days;

    @Basic
    @Column(name = "leaveDate")
    @Temporal(TemporalType.DATE)
    private Date leaveDate;

    @Basic
    @Column(name = "seatType")
    private String seatType;

    @Basic
    @Column(name = "ratePlanCode")
    private String ratePlanCode;

    @Basic
    @Column(name = "realOrderId")
    private String realOrderId;

    @Basic
    @Column(name = "apiResult")
    private String apiResult;

    @Column(name = "msg")
    private String msg;

    @Basic
    @Column(name = "status", nullable = true, insertable = true, updatable = true, length = 10)
    @Enumerated(EnumType.STRING)
    private OrderDetailStatus status;

    @Column(name = "needConfirm")
    private Boolean needConfirm;

	@Column(name = "singleRoomPrice")
	private Float singleRoomPrice;

    @OneToMany(mappedBy = "orderDetail")
    private List<OrderTourist> orderTouristList;

    @Column(name = "billType")
    @Enumerated(EnumType.STRING)
    private OrderBillType orderBillType;
    @Column(name = "billDays")
    private Integer orderBillDays;
    @Column(name = "billPrice")
    private Float orderBillPrice;
    @Column(name = "billDate")
    private Date orderBillDate;
    @Column(name = "billStatus")
    private Integer orderBillStatus;
	@Column(name = "billSummaryId")
	private Long billSummaryId;
	@Column(name = "refundBillAmount")
	private Float refundBillAmount;
	@Column(name = "refundDate")
	private Date refundDate;
	@Column(name = "refundBillSummaryId")
	private Long refundBillSummaryId;

	@Column(name = "hasComment")
	private Boolean hasComment;

	@ManyToOne
	@JoinColumn(name = "operator")
	private SysUser operator;

	@Column(name = "operationDesc", nullable = true, insertable = true, updatable = true, length = 1000)
	private String operationDesc;

	@Column(name = "remark")
	private String remark;


    @Transient
    @JsonIgnore
    private List<OrderDetailStatus> neededStatuses;

    @Transient
    @JsonIgnore
    private List<ProductType> filterProType;

    @Transient
    @JsonIgnore
    private List<ProductSource> thirdOrderSources;

    @Transient
    @JsonIgnore
    private Date minPlayDate;

    @Transient
    @JsonIgnore
    private Date maxPlayDate;

    @Transient
    private HotelPrice hotelPrice;

	@Transient
	private Date 		playTime;
	@Transient
	private Date 		leaveTime;
	@Transient
	private String 		orderNo;
	@Transient
	private String 		productName;

	@Transient
    private String roomNums;

	public OrderDetail() {
	}

    public OrderDetail(Long id, Float totalPrice, Float orderBillPrice, String orderNo, String productName) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.orderBillPrice = orderBillPrice;
        this.orderNo = orderNo;
        this.productName = productName;
    }

	public OrderDetail(Long id, Float totalPrice, Float orderBillPrice, Float refundBillAmount, String orderNo, String productName, Date refundDate) {
		this.id = id;
		this.totalPrice = totalPrice;
		this.orderBillPrice = orderBillPrice;
		this.refundBillAmount = refundBillAmount;
		this.orderNo = orderNo;
		this.productName = productName;
		this.refundDate = refundDate;
	}

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

    public Float getCombinePay() {
        return combinePay;
    }

    public void setCombinePay(Float combinePay) {
        this.combinePay = combinePay;
    }

    public Float getRefund() {
        return refund;
    }

    public void setRefund(Float refund) {
        this.refund = refund;
    }

    public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
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

	public OrderDetailFlattened getOrderDetailFlattened() {
		return orderDetailFlattened;
	}

	public void setOrderDetailFlattened(OrderDetailFlattened orderDetailFlattened) {
		this.orderDetailFlattened = orderDetailFlattened;
	}

	public OrderCostPriceType getPriceType() {
		return priceType;
	}

	public void setPriceType(OrderCostPriceType priceType) {
		this.priceType = priceType;
	}

	public List<Commission> getCommissions() {
		return commissions;
	}

	public void setCommissions(List<Commission> commissions) {
		this.commissions = commissions;
	}

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getRatePlanCode() {
        return ratePlanCode;
    }

    public void setRatePlanCode(String ratePlanCode) {
        this.ratePlanCode = ratePlanCode;
    }

    public String getRealOrderId() {
        return realOrderId;
    }

    public void setRealOrderId(String realOrderId) {
        this.realOrderId = realOrderId;
    }

    public String getApiResult() {
        if (this.apiResult == null || "".equals(this.apiResult)) {
            return "";
        }
        return apiResult;
    }

    public void setApiResult(String apiResult) {
        this.apiResult = apiResult;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<OrderTourist> getOrderTouristList() {
        return orderTouristList;
    }

    public void setOrderTouristList(List<OrderTourist> orderTouristList) {
        this.orderTouristList = orderTouristList;
    }

    public OrderDetailStatus getStatus() {
        return status;
    }

    public void setStatus(OrderDetailStatus status) {
        this.status = status;
    }

    public Boolean getNeedConfirm() {
        return needConfirm;
    }

    public void setNeedConfirm(Boolean needConfirm) {
        this.needConfirm = needConfirm;
    }

    public List<OrderDetailStatus> getNeededStatuses() {
        return neededStatuses;
    }

    public void setNeededStatuses(List<OrderDetailStatus> neededStatuses) {
        this.neededStatuses = neededStatuses;
    }

    public List<ProductType> getFilterProType() {
        return filterProType;
    }

    public void setFilterProType(List<ProductType> filterProType) {
        this.filterProType = filterProType;
    }

	public TbArea getCity() {
		return city;
	}

	public void setCity(TbArea city) {
		this.city = city;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Float getSingleRoomPrice() {
		return singleRoomPrice;
	}

	public void setSingleRoomPrice(Float singleRoomPrice) {
		this.singleRoomPrice = singleRoomPrice;
	}

    public List<ProductSource> getThirdOrderSources() {
        return thirdOrderSources;
    }

    public void setThirdOrderSources(List<ProductSource> thirdOrderSources) {
        this.thirdOrderSources = thirdOrderSources;
    }

    public Date getMinPlayDate() {
        return minPlayDate;
    }

    public void setMinPlayDate(Date minPlayDate) {
        this.minPlayDate = minPlayDate;
    }

    public Date getMaxPlayDate() {
        return maxPlayDate;
    }

    public void setMaxPlayDate(Date maxPlayDate) {
        this.maxPlayDate = maxPlayDate;
    }

    public HotelPrice getHotelPrice() {
        return hotelPrice;
    }

    public void setHotelPrice(HotelPrice hotelPrice) {
        this.hotelPrice = hotelPrice;
    }

    public OrderBillType getOrderBillType() {
        return orderBillType;
    }

    public void setOrderBillType(OrderBillType orderBillType) {
        this.orderBillType = orderBillType;
    }

    public Integer getOrderBillDays() {
        return orderBillDays;
    }

    public void setOrderBillDays(Integer orderBillDays) {
        this.orderBillDays = orderBillDays;
    }

    public Float getOrderBillPrice() {
        return orderBillPrice;
    }

    public void setOrderBillPrice(Float orderBillPrice) {
        this.orderBillPrice = orderBillPrice;
    }

    public Date getOrderBillDate() {
        return orderBillDate;
    }

    public void setOrderBillDate(Date orderBillDate) {
        this.orderBillDate = orderBillDate;
    }

    public Integer getOrderBillStatus() {
        return orderBillStatus;
    }

    public void setOrderBillStatus(Integer orderBillStatus) {
        this.orderBillStatus = orderBillStatus;
    }

	public Boolean getHasComment() {
		return hasComment;
	}

	public void setHasComment(Boolean hasComment) {
		this.hasComment = hasComment;
	}

	public SysUser getOperator() {
		return operator;
	}

	public void setOperator(SysUser operator) {
		this.operator = operator;
	}

	public Long getBillSummaryId() {
		return billSummaryId;
	}

	public void setBillSummaryId(Long billSummaryId) {
		this.billSummaryId = billSummaryId;
	}

	public Float getRefundBillAmount() {
		return refundBillAmount;
	}

	public void setRefundBillAmount(Float refundBillAmount) {
		this.refundBillAmount = refundBillAmount;
	}

	public Long getRefundBillSummaryId() {
		return refundBillSummaryId;
	}

	public void setRefundBillSummaryId(Long refundBillSummaryId) {
		this.refundBillSummaryId = refundBillSummaryId;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getPlayTime() {
		return playTime;
	}

	public void setPlayTime(Date playTime) {
		this.playTime = playTime;
	}

	public Date getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRoomNums() {
        return roomNums;
    }

    public void setRoomNums(String roomNums) {
        this.roomNums = roomNums;
    }

    public String getOperationDesc() {
		return operationDesc;
	}

	public void setOperationDesc(String operationDesc) {
		this.operationDesc = operationDesc;
	}
}
