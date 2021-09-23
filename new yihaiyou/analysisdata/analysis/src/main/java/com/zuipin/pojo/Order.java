package com.zuipin.pojo;

import java.util.Date;

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

import org.hibernate.annotations.Index;

import com.zuipin.pojo.enums.BillState;
import com.zuipin.pojo.enums.BusBillState;
import com.zuipin.pojo.enums.ImportStatus;
import com.zuipin.pojo.enums.OrderTBState;
import com.zuipin.pojo.enums.OrderType;
import com.zuipin.pojo.enums.PayMethod;

/**
 * Torder entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "torder")
public class Order extends com.framework.hibernate.util.Entity implements java.io.Serializable {
	
	private static final long	serialVersionUID	= -7561670861137817519L;
	
	// /////////////////////////////////目前可用的字段
	private Long				id;
	private String				orderNo;										// 内部单号
	private String				outOrderNo;									// 外部单号
	private Date				orderDate;										// 下单日期
	private BillState			state;											// 订单状态
	private WebStore			webStore;										// 订单所属网店
	private Express				express;										// 快递公司
	private Double				carryAmount;									// 运费
	private Double				totalAmount;									// 订单总额【应收金额，折后金额+运费+...】
	private Double				payAmount;										// 已付款【已结算】金额
	private Boolean				payMethod;										// 支付状态 是否支付
	private PayMethod			payMode;										// 支付方式
	private String				recName;										// 收货人姓名
	private String				address;										// 收货人地址
	private String				mobilePhone;									// 收货人手机
	private String				telPhone;										// 收货人电话
	private Member				member;										// 会员Id
	private String				remark;										// 订单备注
																				
	private Date				deliverDate;									// 发货时间
	private String				deliverNo;										// 快递单号
	private Date				payDate;										// 付款时间
	private Date				finishDate;									// 完成时间
	private String				invoiceType;									// 发票类型
	private String				invoiceName;									// 发票名称
	private String				invoiceContent;								// 发票内容
	private String				esqRemark;										// 客服留言
	private String				buyerMsg;										// 买家留言
	private Date				pendDate;										// 挂单时间
	private Date				endPendDate;									// 挂单截止日期
	private String				province;										// 省
	private String				city;											// 市
	private String				district;										// 区
	private Integer				sempSaleId;									// 对应赛普Id
	private Integer				importType;									// 导入类型 : 0或null为赛普 16为醉品商城
																				
	private Long				pickingOrderId;								// 总拣单Id
	private Boolean				isChange;										// 是否有退换货
																				
	private String				sempSaleState;									// semp订单变更状态
																				
	private String				isPrint;										// 是否打印
	private Integer				printNum;										// 打印次数，也就是匹配次数
																				
	private Long				creatorId;										// 制单人Id
	private String				creator;										// 制单人姓名
	private Date				createdTime;									// 创建时间
	private Long				updatorId;										// 修改人Id
	private String				upator;										// 修改人
	private Date				updateDate;									// 修改时间
	private Long				auditorId;										// 审核人Id
	private String				auditor;										// 审核人姓名
	private Date				auditDate;										// 审核时间
																				
	private Double				discountAmount;								// 商品折后总金额(不含运费)
	/** 分销 相关 ***/
	private DeliverySite		deliverySite;									// 订单的配送方,(例:龙岩仓库配送,深圳仓库配送,龙岩门店配送,供应商配送)
	private Long				supplierId;									// 由供应商配送时 配送方供应商ID
	private String				cbtOrderId;									// 茶帮通 子单ID
	private String				cbtMainOrderId;								// 茶帮通母单ID
	// ///////////////不持久化字段
	private Date				beginTime;
	private Date				endTime;
	private String				productName;
	private String				productNo;
	private int					searchType;									// 查询类别
	private OrderTBState		orderTBState;									// 订单颜色记录
	private Integer				platFormNum;									// 平台订单（1：醉品 2：T组）
																				
	// ////////////////////////////////以下字段暂时没用
	private Double				productAmount		= 0d;						// 商品总额[折前金额不含运费]
	private Double				tranAmount;									// ?
	private OrderType			freeSingleType;								// ?
																				
	private BusBillState		busState;										//
	private Boolean				carryFree;										// ?
	private Double				brforeAmount		= 0d;						// ?
	private Double				paidAmount			= 0d;						// ?
	private String				useBank;										// ?
	private Double				preferAmount;									// ?
	private Integer				offsetPoint;									// ?
	private Integer				giftPoint;										// ?
	private String				email;											// ?
	private String				zipCode;										// ?
	private Double				regionId;
	private Double				memberGradeId;
	private String				deliverPerson;									// ?
	private String				receiMode;										// ?
	private Date				receiDate;										// ?
	private Double				receiNo;										// ?
	private String				receiPerson;									// ?
	private Date				confirmDate;									// ?
	private Date				outstockDate;									// ?
	private Date				waitRcvDate;									// ?
	private String				deliverModeCom;								// ?
																				
	private String				tracker;										// ?
	private String				invoiceNeed;									// ?
	private String				invoiceWrite;									// ?
	private String				lgstState;										// ?
	private String				mainNo;										// ?
	private String				reckonFinishDate;								// ?
	private String				auditAddress;									// ?
	private String				itinerary;										// ?
	private String				ipAddress;										// ?
	private Date				assignDeliverDate;								// ?
	private String				superOrderNo;									// ?
																				
	// //////////////////////////店铺信息////////////////////////////////////////
	private Long				shopId;										// 店铺ID
	private String				startTime			= "";						// 店铺订单的下单时间
	private String				strEndTime			= "";						// 店铺订单的下单时间
	private Double				minAmount;										// 店铺订单的订单金额
	private Double				maxAmount;										// 店铺订单的订单金额
	private String				product_name		= "";						// 商品名称 不保存数据库
	// ///////////////////////////////订单状态/////////////////////////////////
	private ImportStatus		importStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "importStatus")
	public ImportStatus getImportStatus() {
		return importStatus;
	}
	
	public void setImportStatus(ImportStatus importStatus) {
		this.importStatus = importStatus;
	}
	
	// Constructors
	@Column(name = "PRINTNUM")
	public Integer getPrintNum() {
		return printNum;
	}
	
	public void setPrintNum(Integer printNum) {
		this.printNum = printNum;
	}
	
	/** default constructor */
	public Order() {
	}
	
	public Order(Long id) {
		this.id = id;
	}
	
	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "CREATED_DATE", length = 19)
	public Date getCreatedTime() {
		return createdTime;
	}
	
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	@Column(name = "ORDER_NO", length = 40)
	@Index(name = "order_orderNo")
	public String getOrderNo() {
		return this.orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Column(name = "ORDER_DATE", length = 19)
	@Index(name = "order_orderDate")
	public Date getOrderDate() {
		return this.orderDate;
	}
	
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	@Column(name = "STATE")
	@Enumerated(EnumType.STRING)
	@Index(name = "order_state")
	public BillState getState() {
		return this.state;
	}
	
	public void setState(BillState state) {
		this.state = state;
	}
	
	@Column(name = "FREE_SINGLE_TYPE")
	@Enumerated(EnumType.STRING)
	@Index(name = "order_free_single_type")
	public OrderType getFreeSingleType() {
		return this.freeSingleType;
	}
	
	public void setFreeSingleType(OrderType freeSingleType) {
		this.freeSingleType = freeSingleType;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EXPRESS_ID")
	public Express getExpress() {
		return this.express;
	}
	
	public void setExpress(Express express) {
		this.express = express;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STORE_ID")
	@Index(name = "order_webstore_name")
	public WebStore getWebStore() {
		return this.webStore;
	}
	
	public void setWebStore(WebStore storeId) {
		this.webStore = storeId;
	}
	
	@Column(name = "PRODUCT_AMOUNT", precision = 12)
	public Double getProductAmount() {
		return this.productAmount;
	}
	
	public void setProductAmount(Double productAmount) {
		this.productAmount = productAmount;
	}
	
	@Column(name = "CARRY_FREE", length = 10)
	public Boolean getCarryFree() {
		return this.carryFree;
	}
	
	public void setCarryFree(Boolean carryFree) {
		this.carryFree = carryFree;
	}
	
	@Column(name = "CARRY_AMOUNT", precision = 8)
	public Double getCarryAmount() {
		return this.carryAmount;
	}
	
	public void setCarryAmount(Double carryAmount) {
		this.carryAmount = carryAmount;
	}
	
	@Column(name = "TOTAL_AMOUNT", precision = 12)
	public Double getTotalAmount() {
		return this.totalAmount;
	}
	
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	@Column(name = "PAY_AMOUNT", precision = 12)
	public Double getPayAmount() {
		return this.payAmount;
	}
	
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	
	@Column(name = "BRFORE_AMOUNT", precision = 12)
	public Double getBrforeAmount() {
		return this.brforeAmount;
	}
	
	public void setBrforeAmount(Double brforeAmount) {
		this.brforeAmount = brforeAmount;
	}
	
	@Column(name = "PAID_AMOUNT", precision = 12)
	public Double getPaidAmount() {
		return this.paidAmount;
	}
	
	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}
	
	@Column(name = "PAY_METHOD")
	public Boolean getPayMethod() {
		return this.payMethod;
	}
	
	public void setPayMethod(Boolean payMethod) {
		this.payMethod = payMethod;
	}
	
	@Column(name = "PAY_MODE")
	@Enumerated(EnumType.STRING)
	@Index(name = "order_pay_mode")
	public PayMethod getPayMode() {
		return this.payMode;
	}
	
	public void setPayMode(PayMethod payMode) {
		this.payMode = payMode;
	}
	
	@Column(name = "USE_BANK", length = 1)
	public String getUseBank() {
		return this.useBank;
	}
	
	public void setUseBank(String useBank) {
		this.useBank = useBank;
	}
	
	@Column(name = "PREFER_AMOUNT", precision = 12)
	public Double getPreferAmount() {
		return this.preferAmount;
	}
	
	public void setPreferAmount(Double preferAmount) {
		this.preferAmount = preferAmount;
	}
	
	@Column(name = "OFFSET_POINT")
	public Integer getOffsetPoint() {
		return this.offsetPoint;
	}
	
	public void setOffsetPoint(Integer offsetPoint) {
		this.offsetPoint = offsetPoint;
	}
	
	@Column(name = "GIFT_POINT")
	public Integer getGiftPoint() {
		return this.giftPoint;
	}
	
	public void setGiftPoint(Integer giftPoint) {
		this.giftPoint = giftPoint;
	}
	
	@Column(name = "REC_NAME", length = 200)
	@Index(name = "order_recName")
	public String getRecName() {
		return this.recName;
	}
	
	public void setRecName(String recName) {
		this.recName = recName;
	}
	
	@Column(name = "ADDRESS", length = 300)
	@Index(name = "order_address")
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "MOBILE_PHONE", length = 50)
	@Index(name = "order_mobile_phone")
	public String getMobilePhone() {
		return this.mobilePhone;
	}
	
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	@Column(name = "TEL_PHONE", length = 50)
	public String getTelPhone() {
		return this.telPhone;
	}
	
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	
	@Column(name = "EMAIL", length = 50)
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MEMBER_ID")
	public Member getMember() {
		return this.member;
	}
	
	public void setMember(Member memberId) {
		this.member = memberId;
	}
	
	@Column(name = "ZIP_CODE", length = 20)
	public String getZipCode() {
		return this.zipCode;
	}
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	@Column(name = "REMARK", length = 400)
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "REGION_ID", precision = 18, scale = 0)
	public Double getRegionId() {
		return this.regionId;
	}
	
	public void setRegionId(Double regionId) {
		this.regionId = regionId;
	}
	
	@Column(name = "MEMBER_GRADE_ID", precision = 18, scale = 0)
	public Double getMemberGradeId() {
		return this.memberGradeId;
	}
	
	public void setMemberGradeId(Double memberGradeId) {
		this.memberGradeId = memberGradeId;
	}
	
	@Column(name = "UPDATE_DATE", length = 19)
	public Date getUpdateDate() {
		return this.updateDate;
	}
	
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	@Column(name = "DELIVER_DATE", length = 19)
	public Date getDeliverDate() {
		return this.deliverDate;
	}
	
	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}
	
	@Column(name = "DELIVER_NO")
	@Index(name = "order_deliveryNo")
	public String getDeliverNo() {
		return this.deliverNo;
	}
	
	public void setDeliverNo(String deliverNo) {
		this.deliverNo = deliverNo;
	}
	
	@Column(name = "DELIVER_PERSON", length = 50)
	public String getDeliverPerson() {
		return this.deliverPerson;
	}
	
	public void setDeliverPerson(String deliverPerson) {
		this.deliverPerson = deliverPerson;
	}
	
	/*
	 * @Column(name = "DELIVER_MODE")
	 * @Enumerated(EnumType.STRING) public DeliverMode getDeliverMode() { return this.deliverMode; } public void setDeliverMode(DeliverMode deliverMode) { this.deliverMode =
	 * deliverMode; }
	 */
	
	@Column(name = "RECEI_MODE", length = 2)
	public String getReceiMode() {
		return this.receiMode;
	}
	
	public void setReceiMode(String receiMode) {
		this.receiMode = receiMode;
	}
	
	@Column(name = "RECEI__DATE", length = 19)
	public Date getReceiDate() {
		return this.receiDate;
	}
	
	public void setReceiDate(Date receiDate) {
		this.receiDate = receiDate;
	}
	
	@Column(name = "RECEI_NO", precision = 18, scale = 0)
	public Double getReceiNo() {
		return this.receiNo;
	}
	
	public void setReceiNo(Double receiNo) {
		this.receiNo = receiNo;
	}
	
	@Column(name = "RECEI_PERSON", length = 50)
	public String getReceiPerson() {
		return this.receiPerson;
	}
	
	public void setReceiPerson(String receiPerson) {
		this.receiPerson = receiPerson;
	}
	
	@Column(name = "PAY_DATE", length = 19)
	public Date getPayDate() {
		return this.payDate;
	}
	
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	@Column(name = "CONFIRM_DATE", length = 19)
	public Date getConfirmDate() {
		return this.confirmDate;
	}
	
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	@Column(name = "OUTSTOCK_DATE", length = 19)
	public Date getOutstockDate() {
		return this.outstockDate;
	}
	
	public void setOutstockDate(Date outstockDate) {
		this.outstockDate = outstockDate;
	}
	
	@Column(name = "WAIT_RCV_DATE", length = 19)
	public Date getWaitRcvDate() {
		return this.waitRcvDate;
	}
	
	public void setWaitRcvDate(Date waitRcvDate) {
		this.waitRcvDate = waitRcvDate;
	}
	
	@Column(name = "FINISH_DATE", length = 19)
	public Date getFinishDate() {
		return this.finishDate;
	}
	
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	
	@Column(name = "DELIVER_MODE_COM", length = 200)
	public String getDeliverModeCom() {
		return this.deliverModeCom;
	}
	
	public void setDeliverModeCom(String deliverModeCom) {
		this.deliverModeCom = deliverModeCom;
	}
	
	@Column(name = "INVOICE_TYPE", length = 10)
	public String getInvoiceType() {
		return this.invoiceType;
	}
	
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	
	@Column(name = "INVOICE_NAME", length = 100)
	public String getInvoiceName() {
		return this.invoiceName;
	}
	
	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}
	
	@Column(name = "TRACKER", length = 200)
	public String getTracker() {
		return this.tracker;
	}
	
	public void setTracker(String tracker) {
		this.tracker = tracker;
	}
	
	@Column(name = "INVOICE_NEED", length = 10)
	public String getInvoiceNeed() {
		return this.invoiceNeed;
	}
	
	public void setInvoiceNeed(String invoiceNeed) {
		this.invoiceNeed = invoiceNeed;
	}
	
	@Column(name = "INVOICE_WRITE", length = 10)
	public String getInvoiceWrite() {
		return this.invoiceWrite;
	}
	
	public void setInvoiceWrite(String invoiceWrite) {
		this.invoiceWrite = invoiceWrite;
	}
	
	@Column(name = "LGST_STATE", length = 10)
	public String getLgstState() {
		return this.lgstState;
	}
	
	public void setLgstState(String lgstState) {
		this.lgstState = lgstState;
	}
	
	@Column(name = "MAIN_NO", length = 20)
	public String getMainNo() {
		return this.mainNo;
	}
	
	public void setMainNo(String mainNo) {
		this.mainNo = mainNo;
	}
	
	@Column(name = "RECKON_FINISH_DATE", length = 100)
	public String getReckonFinishDate() {
		return this.reckonFinishDate;
	}
	
	public void setReckonFinishDate(String reckonFinishDate) {
		this.reckonFinishDate = reckonFinishDate;
	}
	
	@Column(name = "AUDIT_ADDRESS", length = 10)
	public String getAuditAddress() {
		return this.auditAddress;
	}
	
	public void setAuditAddress(String auditAddress) {
		this.auditAddress = auditAddress;
	}
	
	@Column(name = "ITINERARY", length = 2)
	public String getItinerary() {
		return this.itinerary;
	}
	
	public void setItinerary(String itinerary) {
		this.itinerary = itinerary;
	}
	
	@Column(name = "IP_ADDRESS", length = 50)
	public String getIpAddress() {
		return this.ipAddress;
	}
	
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	@Column(name = "ASSIGN_DELIVER_DATE", length = 19)
	public Date getAssignDeliverDate() {
		return this.assignDeliverDate;
	}
	
	public void setAssignDeliverDate(Date assignDeliverDate) {
		this.assignDeliverDate = assignDeliverDate;
	}
	
	@Column(name = "INVOICE_CONTENT")
	public String getInvoiceContent() {
		return this.invoiceContent;
	}
	
	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}
	
	@Column(name = "SUPER_ORDER_NO", length = 50)
	public String getSuperOrderNo() {
		return this.superOrderNo;
	}
	
	public void setSuperOrderNo(String superOrderNo) {
		this.superOrderNo = superOrderNo;
	}
	
	@Column(name = "ESQ_REMARK", length = 400)
	public String getEsqRemark() {
		return esqRemark;
	}
	
	public void setEsqRemark(String esqRemark) {
		this.esqRemark = esqRemark;
	}
	
	@Column(name = "OUT_ORDER_NO")
	@Index(name = "order_outOrderNo")
	public String getOutOrderNo() {
		return outOrderNo;
	}
	
	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
	}
	
	@Column(name = "BUS_STATE")
	@Enumerated(EnumType.STRING)
	public BusBillState getBusState() {
		return busState;
	}
	
	public void setBusState(BusBillState busState) {
		this.busState = busState;
	}
	
	@Column(name = "TRAN_AMOUNT")
	public Double getTranAmount() {
		return tranAmount;
	}
	
	public void setTranAmount(Double tranAmount) {
		this.tranAmount = tranAmount;
	}
	
	@Column(name = "PEND_DATE")
	public Date getPendDate() {
		return pendDate;
	}
	
	public void setPendDate(Date pendDate) {
		this.pendDate = pendDate;
	}
	
	@Column(name = "END_PEND_DATE")
	public Date getEndPendDate() {
		return endPendDate;
	}
	
	public void setEndPendDate(Date endPendDate) {
		this.endPendDate = endPendDate;
	}
	
	@Column(name = "BUYER_MSG")
	public String getBuyerMsg() {
		return buyerMsg;
	}
	
	public void setBuyerMsg(String buyerMsg) {
		this.buyerMsg = buyerMsg;
	}
	
	@Column(name = "PROVINCE")
	public String getProvince() {
		return province;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}
	
	@Column(name = "CITY")
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "DISTRICT")
	public String getDistrict() {
		return district;
	}
	
	public void setDistrict(String district) {
		this.district = district;
	}
	
	@Column(name = "SEMP_SALE_ID")
	public Integer getSempSaleId() {
		return sempSaleId;
	}
	
	public void setSempSaleId(Integer sempSaleId) {
		this.sempSaleId = sempSaleId;
	}
	
	@Column(name = "PICKING_ORDER_ID")
	public Long getPickingOrderId() {
		return pickingOrderId;
	}
	
	public void setPickingOrderId(Long pickingOrderId) {
		this.pickingOrderId = pickingOrderId;
	}
	
	@Transient
	public Boolean getIsChange() {
		return isChange;
	}
	
	public void setIsChange(Boolean isChange) {
		this.isChange = isChange;
	}
	
	@Transient
	public OrderTBState getOrderTBState() {
		return orderTBState;
	}
	
	public void setOrderTBState(OrderTBState orderTBState) {
		this.orderTBState = orderTBState;
	}
	
	@Column(name = "SEMP_SALE_STATE")
	public String getSempSaleState() {
		return sempSaleState;
	}
	
	public void setSempSaleState(String sempSaleState) {
		this.sempSaleState = sempSaleState;
	}
	
	@Column(name = "SHOP_ID")
	public Long getShopId() {
		return shopId;
	}
	
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	
	@Transient
	public String getStartTime() {
		return startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	@Transient
	public Date getBeginTime() {
		return beginTime;
	}
	
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	@Transient
	public Double getMinAmount() {
		return minAmount;
	}
	
	public void setMinAmount(Double minAmount) {
		this.minAmount = minAmount;
	}
	
	@Transient
	public Double getMaxAmount() {
		return maxAmount;
	}
	
	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}
	
	@Transient
	public String getProduct_name() {
		return product_name;
	}
	
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	
	@Column(name = "IMPORT_TYPE")
	public Integer getImportType() {
		return importType;
	}
	
	public void setImportType(Integer importType) {
		this.importType = importType;
	}
	
	@Transient
	public Integer getPlatFormNum() {
		return platFormNum;
	}
	
	public void setPlatFormNum(Integer platFormNum) {
		this.platFormNum = platFormNum;
	}
	
	@Transient
	public String getIsPrint() {
		return isPrint;
	}
	
	public void setIsPrint(String isPrint) {
		this.isPrint = isPrint;
	}
	
	@Transient
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Transient
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Transient
	public String getProductNo() {
		return productNo;
	}
	
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	
	@Transient
	public int getSearchType() {
		return searchType;
	}
	
	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}
	
	@Column(name = "creatorId")
	public Long getCreatorId() {
		return creatorId;
	}
	
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	
	@Column(name = "creator")
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(name = "updatorId")
	public Long getUpdatorId() {
		return updatorId;
	}
	
	public void setUpdatorId(Long updatorId) {
		this.updatorId = updatorId;
	}
	
	@Column(name = "upator")
	public String getUpator() {
		return upator;
	}
	
	public void setUpator(String upator) {
		this.upator = upator;
	}
	
	@Column(name = "auditorId")
	public Long getAuditorId() {
		return auditorId;
	}
	
	public void setAuditorId(Long auditorId) {
		this.auditorId = auditorId;
	}
	
	@Column(name = "auditor")
	public String getAuditor() {
		return auditor;
	}
	
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	
	@Column(name = "auditDate")
	public Date getAuditDate() {
		return auditDate;
	}
	
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	
	@Transient
	public String getStrEndTime() {
		return strEndTime;
	}
	
	public void setStrEndTime(String strEndTime) {
		this.strEndTime = strEndTime;
	}
	
	@Column(name = "discount_amount")
	public Double getDiscountAmount() {
		return discountAmount;
	}
	
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "deliverySite")
	public DeliverySite getDeliverySite() {
		return deliverySite;
	}
	
	public void setDeliverySite(DeliverySite deliverySite) {
		this.deliverySite = deliverySite;
	}
	
	@Column(name = "supplierId")
	public Long getSupplierId() {
		return supplierId;
	}
	
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	
	@Column(name = "cbtOrderId")
	public String getCbtOrderId() {
		return cbtOrderId;
	}
	
	public void setCbtOrderId(String cbtOrderId) {
		this.cbtOrderId = cbtOrderId;
	}
	
	@Column(name = "cbtMainOrderId")
	public String getCbtMainOrderId() {
		return cbtMainOrderId;
	}
	
	public void setCbtMainOrderId(String cbtMainOrderId) {
		this.cbtMainOrderId = cbtMainOrderId;
	}
	
}