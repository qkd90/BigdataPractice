package com.hmlyinfo.app.soutu.bargain.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;
import java.util.List;

public class BargainPlanOrder extends BaseEntity {
	private static final long serialVersionUID = 1L;

	public static final int STATUS_INVALID = 0;
	public static final int STATUS_EDITING = 1;
	public static final int STATUS_WAIT_PAY = 2;
	public static final int STATUS_PAY_SUCCESS = 3;
	public static final int STATUS_PAY_FAIL = 11;
	// 还未推送
	public static int PUSH_NO = 1;
	// 已经通过微信推送购买成功
	public static int PUSH_YES = 2;


	private long bargainPlanId;
	private long userId;
	private Long payOrder;
	private String orderName;
	private double amount;
	private String contact;
	private String mobile;
	private String idNo;
	private int sex;
	private Date playDate;
	private int adultCount;
	private int kidCount;
	private int status;
	private transient List<BargainPlanOrderItem> itemList;

	private int pushFlag;

	public void setBargainPlanId(long bargainPlanId) {
		this.bargainPlanId = bargainPlanId;
	}

	@JsonProperty
	public long getBargainPlanId() {
		return bargainPlanId;
	}

	public Long getPayOrder() {
		return payOrder;
	}

	public void setPayOrder(Long payOrder) {
		this.payOrder = payOrder;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	@JsonProperty
	public String getOrderName() {
		return orderName;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@JsonProperty
	public double getAmount() {
		return amount;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@JsonProperty
	public String getContact() {
		return contact;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@JsonProperty
	public String getMobile() {
		return mobile;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	@JsonProperty
	public String getIdNo() {
		return idNo;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	@JsonProperty
	public int getSex() {
		return sex;
	}

	public void setPlayDate(Date playDate) {
		this.playDate = playDate;
	}

	@JsonProperty
	public Date getPlayDate() {
		return playDate;
	}

	public void setAdultCount(int adultCount) {
		this.adultCount = adultCount;
	}

	@JsonProperty
	public int getAdultCount() {
		return adultCount;
	}

	public void setKidCount(int kidCount) {
		this.kidCount = kidCount;
	}

	@JsonProperty
	public int getKidCount() {
		return kidCount;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@JsonProperty
	public int getStatus() {
		return status;
	}

	@JsonProperty
	public List<BargainPlanOrderItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<BargainPlanOrderItem> itemList) {
		this.itemList = itemList;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public int getPushFlag() {
		return pushFlag;
	}

	public void setPushFlag(int pushFlag) {
		this.pushFlag = pushFlag;
	}
}
