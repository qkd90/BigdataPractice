//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.02 at 07:34:20 PM CST 
//


package com.data.data.hmly.service.ctripticket.entity;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

//@Entity
//@Table(name = "ctrip_ticket")
public class CtripTicket extends com.framework.hibernate.util.Entity {
	private static final long serialVersionUID = -2996522995238348090L;
	@Id
	@Column(name = "id", length = 11)
    private Integer id;
	@Column(name = "name", length = 256)
    private String name;
	@Column(name = "isReturnCash")
    private Boolean isReturnCash;
	@Column(name = "marketPrice", length = 11)
    private Integer marketPrice;
	@Column(name = "price", length = 11)
    private Integer price;
	@Column(name = "returnCashAmount", length = 11)
    private Integer returnCashAmount;
	@Column(name = "scenicSpotId", length = 11)
    private Integer scenicSpotId;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime", length = 19)
	private Date createTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateTime", length = 19)
	private Date updateTime;
	@Column(name = "rowStatus")
	@Enumerated(EnumType.STRING)
	private RowStatus rowStatus;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getIsReturnCash() {
		return isReturnCash;
	}
	public void setIsReturnCash(Boolean isReturnCash) {
		this.isReturnCash = isReturnCash;
	}
	public Integer getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Integer marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getReturnCashAmount() {
		return returnCashAmount;
	}
	public void setReturnCashAmount(Integer returnCashAmount) {
		this.returnCashAmount = returnCashAmount;
	}
	public Integer getScenicSpotId() {
		return scenicSpotId;
	}
	public void setScenicSpotId(Integer scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public RowStatus getRowStatus() {
		return rowStatus;
	}
	public void setRowStatus(RowStatus rowStatus) {
		this.rowStatus = rowStatus;
	}

}
