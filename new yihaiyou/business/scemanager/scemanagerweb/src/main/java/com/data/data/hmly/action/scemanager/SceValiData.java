package com.data.data.hmly.action.scemanager;

import java.util.Date;

public class SceValiData {
	
	private Long id;
	private String name;
	private String orderNo;
	private Date createTime;
	private String supUserName;
	private String usedStr;
	private String  buyerName;
	private String  buyerMobile;
	private Integer  used;
	private Integer orderCount;
	private String  code;
	private String  vaName;
	private Integer orderInitCount;
	private Integer refundCount;


	public Integer getUsed() {
		return used;
	}

	public void setUsed(Integer used) {
		this.used = used;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUsedStr() {
		return usedStr;
	}
	public void setUsedStr(String usedStr) {
		this.usedStr = usedStr;
	}
	public String getSupUserName() {
		return supUserName;
	}
	public void setSupUserName(String supUserName) {
		this.supUserName = supUserName;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getBuyerMobile() {
		return buyerMobile;
	}
	public void setBuyerMobile(String buyerMobile) {
		this.buyerMobile = buyerMobile;
	}
	public Integer getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getVaName() {
		return vaName;
	}
	public void setVaName(String vaName) {
		this.vaName = vaName;
	}

	public Integer getOrderInitCount() {
		return orderInitCount;
	}

	public void setOrderInitCount(Integer orderInitCount) {
		this.orderInitCount = orderInitCount;
	}

	public Integer getRefundCount() {
		return refundCount;
	}

	public void setRefundCount(Integer refundCount) {
		this.refundCount = refundCount;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
