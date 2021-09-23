package com.data.data.hmly.service.order.entity;

import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.lxbcommon.entity.UserCoupon;
import com.data.data.hmly.service.order.entity.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by guoshijie on 2015/10/13.
 */
@Entity
@Table(name = "torder")
@JsonIgnoreProperties
public class Order extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false, insertable = true, updatable = true)
	private Long id;
	@Basic
	@Column(name = "orderNo", nullable = false, insertable = true, updatable = true, length = 50)
	private String orderNo;
	@ManyToOne
	@JoinColumn(name = "userid", unique = true, nullable = false, updatable = false)
	private User user;
	@Basic
	@Column(name = "recName", nullable = true, insertable = true, updatable = true, length = 50)
	private String recName;
	@Basic
	@Column(name = "address", nullable = true, insertable = true, updatable = true, length = 200)
	private String address;
	@Basic
	@Column(name = "mobile", nullable = true, insertable = true, updatable = true, length = 50)
	private String mobile;
	@Basic
	@Column(name = "payType", nullable = true, insertable = true, updatable = true, length = 7)
	@Enumerated(EnumType.STRING)
	private OrderPayType payType;
	@Basic
	@Column(name = "receiveGoodType", nullable = true, insertable = true, updatable = true, length = 10)
	@Enumerated(EnumType.STRING)
	private OrderReceiveType receiveGoodType;
	@Basic
	@Column(name = "remark", nullable = true, insertable = true, updatable = true, length = 1000)
	private String remark;
	@Basic
	@Column(name = "modifyTime", nullable = true, insertable = true, updatable = true, length = 1000)
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyTime;
	@Basic
	@Column(name = "createTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Basic
	@Column(name = "status", nullable = true, insertable = true, updatable = true, length = 10)
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
    @Column(name = "msg")
    private String msg;
	@Basic
	@Column(name = "operationDesc", nullable = true, insertable = true, updatable = true, length = 1000)
	private String operationDesc;
	// private OrderDetail orderDetail;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	private List<OrderDetail> orderDetails;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	private List<OrderAlias> orderAliases;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderTourist> orderTourists;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	private List<OrderInsurance> orderInsurances;
	@OneToOne
	@JoinColumn(name = "invoice")
	private OrderInvoice invoice;

	@Basic
	@Column(name = "orderType")
	@Enumerated(EnumType.STRING)
	private OrderType orderType;

	@Basic
	@Column(name = "insurancePrice")
	private Float insurancePrice;

	@Basic
	@Column(name = "name")
	private String name;

	@Basic
	@Column(name = "price")
	private Float price;

	@OneToOne
	@JoinColumn(name = "userCoupon")
	private UserCoupon userCoupon;

	@Basic
	@Column(name = "wechatCode")
	private String wechatCode;

	@Basic
	@Column(name = "alipayCode")
	private String alipayCode;

	@Basic
	@Column(name = "trade_no")
	private String tradeNo;

	@Basic
	@Column(name = "wechatTime")
	private Date wechatTime;

	@Basic
	@Column(name = "playDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date playDate;

	@Basic
	@Column(name = "hasComment")
	private Boolean hasComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_unit_id")
    private SysUnit companyUnit;

    @Basic
    @Column(name = "ip_addr")
    private String ipAddr;

	@Basic
	@Column(name = "day")
	private Integer day;

	@Basic
	@Column(name = "hasOld")
	private Boolean hasOld;

	@Basic
	@Column(name = "hasForeigner")
	private Boolean hasForeigner;

	@Basic
	@Column(name = "departureInfoId")
	private Long departureInfoId;

    @Basic
    @Column(name = "jszxOrderId")
    private Long jszxOrderId;

    @Basic
    @Column(name = "confirmTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date confirmTime;

    @Basic
    @Column(name = "waitTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date waitTime;

	@Basic
	@Column(name = "isCombineLine")
	private Boolean isCombineLine;

	@Basic
	@Column(name = "billNo")
	private String billNo;

	@Basic
	@Column(name = "refNo")
	private String refNo;

	@Basic
	@Column(name = "cmbStatus")
	@Enumerated(EnumType.STRING)
	private CmbOrderStatus cmbStatus;

	@Basic
	@Column(name = "cmbTime")
	private String cmbTime;

	@Column(name = "deleteFlag")
	private Boolean deleteFlag = false;

	@Enumerated(EnumType.STRING)
	@Column(name = "orderWay")
	private OrderWay orderWay;

    @Transient
    @JsonIgnore
    private List<OrderType> filterOrderTypes;

	@Transient
	@JsonIgnore
	private List<OrderType> includeOrderTypes;

    @Transient
    @JsonIgnore
    private List<ProductSource> thirdOrderSources;

    @Transient
    @JsonIgnore
    private List<OrderStatus> neededStatuses;

	@Transient
	private Float priceStart;

	@Transient
	private Float priceEnd;

	@Transient
	private Date startTime;

	@Transient
	private Date endTime;

	@Transient
	private Date 		playTime;
	@Transient
	private Date 		leaveTime;

	@Transient
	private String 		playTimeStr;
	@Transient
	private String 		leaveTimeStr;

	@Transient
	private String 		searchKeyword;

	@Transient
	private String 		statusStr;
	@Transient
	private Integer 	count;

	@Transient
	private Float 	perPrice;

	@Transient
	private String departTime;

	@Transient
	private List<OrderTourist> touristList;

	@Transient
	private Integer waitSeconds;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRecName() {
		return recName;
	}

	public void setRecName(String recName) {
		this.recName = recName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public OrderPayType getPayType() {
		return payType;
	}

	public void setPayType(OrderPayType payType) {
		this.payType = payType;
	}

	public OrderReceiveType getReceiveGoodType() {
		return receiveGoodType;
	}

	public void setReceiveGoodType(OrderReceiveType receiveGoodType) {
		this.receiveGoodType = receiveGoodType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOperationDesc() {
		return operationDesc;
	}

	public void setOperationDesc(String operationDesc) {
		this.operationDesc = operationDesc;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public boolean canChangeToValid() {
		return status == OrderStatus.WAIT || status == OrderStatus.UNCONFIRMED;
	}

	public List<OrderAlias> getOrderAliases() {
		return orderAliases;
	}

	public void setOrderAliases(List<OrderAlias> orderAliases) {
		this.orderAliases = orderAliases;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

    public List<OrderTourist> getOrderTourists() {
        return orderTourists;
    }

    public void setOrderTourists(List<OrderTourist> orderTourists) {
        this.orderTourists = orderTourists;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getWechatCode() {
		return wechatCode;
	}

	public void setWechatCode(String wechatCode) {
		this.wechatCode = wechatCode;
	}

	public Date getWechatTime() {
		return wechatTime;
	}

	public void setWechatTime(Date wechatTime) {
		this.wechatTime = wechatTime;
	}

	public Date getPlayDate() {
		return playDate;
	}

	public void setPlayDate(Date playDate) {
		this.playDate = playDate;
	}

	public Boolean getHasComment() {
		return hasComment;
	}

	public void setHasComment(Boolean hasComment) {
		this.hasComment = hasComment;
	}

    public SysUnit getCompanyUnit() {
        return companyUnit;
    }

    public void setCompanyUnit(SysUnit companyUnit) {
        this.companyUnit = companyUnit;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public List<OrderType> getFilterOrderTypes() {
        return filterOrderTypes;
    }

    public void setFilterOrderTypes(List<OrderType> filterOrderTypes) {
        this.filterOrderTypes = filterOrderTypes;
    }

    public List<ProductSource> getThirdOrderSources() {
        return thirdOrderSources;
    }

    public void setThirdOrderSources(List<ProductSource> thirdOrderSources) {
        this.thirdOrderSources = thirdOrderSources;
    }

    public List<OrderStatus> getNeededStatuses() {
        return neededStatuses;
    }

    public void setNeededStatuses(List<OrderStatus> neededStatuses) {
        this.neededStatuses = neededStatuses;
    }

    public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public UserCoupon getUserCoupon() {
		return userCoupon;
	}

	public void setUserCoupon(UserCoupon userCoupon) {
		this.userCoupon = userCoupon;
	}

	public OrderInvoice getInvoice() {
		return invoice;
	}

	public void setInvoice(OrderInvoice invoice) {
		this.invoice = invoice;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Boolean getHasOld() {
		return hasOld;
	}

	public void setHasOld(Boolean hasOld) {
		this.hasOld = hasOld;
	}

	public Boolean getHasForeigner() {
		return hasForeigner;
	}

	public void setHasForeigner(Boolean hasForeigner) {
		this.hasForeigner = hasForeigner;
	}

	public Long getDepartureInfoId() {
		return departureInfoId;
	}

	public void setDepartureInfoId(Long departureInfoId) {
		this.departureInfoId = departureInfoId;
	}

    public Long getJszxOrderId() {
        return jszxOrderId;
    }

    public void setJszxOrderId(Long jszxOrderId) {
        this.jszxOrderId = jszxOrderId;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Date getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Date waitTime) {
        this.waitTime = waitTime;
    }

	public List<OrderInsurance> getOrderInsurances() {
		return orderInsurances;
	}

	public void setOrderInsurances(List<OrderInsurance> orderInsurances) {
		this.orderInsurances = orderInsurances;
	}

	public Float getInsurancePrice() {
		return insurancePrice;
	}

	public void setInsurancePrice(Float insurancePrice) {
		this.insurancePrice = insurancePrice;
	}

	public Boolean getIsCombineLine() {
		return isCombineLine;
	}

	public void setIsCombineLine(Boolean isCombineLine) {
		this.isCombineLine = isCombineLine;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public CmbOrderStatus getCmbStatus() {
		return cmbStatus;
	}

	public void setCmbStatus(CmbOrderStatus cmbStatus) {
		this.cmbStatus = cmbStatus;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getCmbTime() {
		return cmbTime;
	}

	public void setCmbTime(String cmbTime) {
		this.cmbTime = cmbTime;
	}

	public Float getPriceStart() {
		return priceStart;
	}

	public void setPriceStart(Float priceStart) {
		this.priceStart = priceStart;
	}

	public Float getPriceEnd() {
		return priceEnd;
	}

	public void setPriceEnd(Float priceEnd) {
		this.priceEnd = priceEnd;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getStatusStr() {
		return getStatus().getDescription();
		/*if (getStatus() == OrderStatus.SUCCESS) {
			return OrderStatus.SUCCESS.getDescription();
		}
		if (getStatus() == OrderStatus.CONFIRMED) {
			return "交易完成";
		}
		if (getStatus() == OrderStatus.FAILED) {
			return "失败";
		}
		if (getStatus() == OrderStatus.PARTIAL_FAILED) {
			return "部分失败";
		}
		if (getStatus() == OrderStatus.UNCONFIRMED) {
			return "等待确认";
		}
		if (getStatus() == OrderStatus.WAIT) {
			return "待支付";
		}
		if (getStatus() == OrderStatus.PAYED) {
			return "已支付";
		}
		if (getStatus() == OrderStatus.REFUND) {
			return "已退款";
		}
		if (getStatus() == OrderStatus.CANCELED) {
			return "已取消";
		}
		if (getStatus() == OrderStatus.CANCELING) {
			return "取消中";
		}
		if (getStatus() == OrderStatus.DELETED) {
			return "已删除";
		}
		if (getStatus() == OrderStatus.CLOSED) {
			return "已关闭";
		}
		if (getStatus() == OrderStatus.INVALID) {
			return "无效订单";
		}
		if (getStatus() == OrderStatus.PROCESSING) {
			return "处理中";
		}
		if (getStatus() == OrderStatus.PROCESSED) {
			return "已处理";
		}
		return statusStr;*/
	}

	public Float getPerPrice() {
		return perPrice;
	}

	public void setPerPrice(Float perPrice) {
		this.perPrice = perPrice;
	}

	public void setStatusStr(String statusStr) {
		
		
		this.statusStr = statusStr;
	}

	public List<OrderType> getIncludeOrderTypes() {
		return includeOrderTypes;
	}

	public void setIncludeOrderTypes(List<OrderType> includeOrderTypes) {
		this.includeOrderTypes = includeOrderTypes;
	}

	public String getPlayTimeStr() {
		return playTimeStr;
	}

	public void setPlayTimeStr(String playTimeStr) {
		this.playTimeStr = playTimeStr;
	}

	public String getLeaveTimeStr() {
		return leaveTimeStr;
	}

	public void setLeaveTimeStr(String leaveTimeStr) {
		this.leaveTimeStr = leaveTimeStr;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getDepartTime() {
		return departTime;
	}

	public void setDepartTime(String departTime) {
		this.departTime = departTime;
	}

	public OrderWay getOrderWay() {
		return orderWay;
	}

	public void setOrderWay(OrderWay orderWay) {
		this.orderWay = orderWay;
	}

	public String getAlipayCode() {
		return alipayCode;
	}

	public void setAlipayCode(String alipayCode) {
		this.alipayCode = alipayCode;
	}

	public List<OrderTourist> getTouristList() {
		return touristList;
	}

	public void setTouristList(List<OrderTourist> touristList) {
		this.touristList = touristList;
	}

	public Integer getWaitSeconds() {
		return waitSeconds;
	}

	public void setWaitSeconds(Integer waitSeconds) {
		this.waitSeconds = waitSeconds;
	}
}
