package com.data.data.hmly.action.ticket;

import java.util.Date;

import com.data.data.hmly.service.common.entity.enums.ProductStatus;

public class TicketData {
	
	private Long id ;
	private String name;
	private ProductStatus status;
	private Date updateTime;
	private Integer showOrder;
	private Integer popCounts;
	private Integer orderCounts;
	private String cateName;
    private String ticketTypeName;
	private String ticketType;
	private String companyUnitName;
	private String supplierName;
	private String supplierMobile;
	private String address;
	private boolean agentFlag;
	private Long topId;
	private Long originId;
	private Integer imageTotalCount;
	
	
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
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}
	public Integer getPopCounts() {
		return popCounts;
	}
	public void setPopCounts(Integer popCounts) {
		this.popCounts = popCounts;
	}
	public Integer getOrderCounts() {
		return orderCounts;
	}
	public void setOrderCounts(Integer orderCounts) {
		this.orderCounts = orderCounts;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

    public String getTicketTypeName() {
        return ticketTypeName;
    }

    public void setTicketTypeName(String ticketTypeName) {
        this.ticketTypeName = ticketTypeName;
    }

    public ProductStatus getStatus() {
		return status;
	}
	public void setStatus(ProductStatus status) {
		this.status = status;
	}
	public Long getTopId() {
		return topId;
	}
	public void setTopId(Long topId) {
		this.topId = topId;
	}
	public String getCompanyUnitName() {
		return companyUnitName;
	}
	public void setCompanyUnitName(String companyUnitName) {
		this.companyUnitName = companyUnitName;
	}
	public boolean isAgentFlag() {
		return agentFlag;
	}
	public void setAgentFlag(boolean agentFlag) {
		this.agentFlag = agentFlag;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierMobile() {
		return supplierMobile;
	}

	public void setSupplierMobile(String supplierMobile) {
		this.supplierMobile = supplierMobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getOriginId() {
		return originId;
	}

	public void setOriginId(Long originId) {
		this.originId = originId;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public Integer getImageTotalCount() {
		return imageTotalCount;
	}

	public void setImageTotalCount(Integer imageTotalCount) {
		this.imageTotalCount = imageTotalCount;
	}
}
