package com.data.data.hmly.service.ctripticket.entity;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

//@Entity
//@Table(name = "ctrip_ticket_preorder")
public class CtripTicketPreOrder extends com.framework.hibernate.util.Entity {
	private static final long serialVersionUID = -4721924498569505170L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 20)
	private Long	id;
	@Column(name = "adultNumber", length = 11)
    private Integer adultNumber;
	@Column(name = "amount", length = 20, precision=2)
    private Integer amount;
	@Column(name = "childNumber", length = 11)
    private Integer childNumber;
	@Column(name = "distributorOrderId", length = 256)
    private String distributorOrderId;
	@Column(name = "contactEmail", length = 256)
    private String contactEmail;
	@Column(name = "contactMobile", length = 256)
    private String contactMobile;
	@Column(name = "contactName", length = 256)
    private String contactName;
	@Column(name = "isBackCash")
    private Boolean isBackCash;
	@Column(name = "productId", length = 11)
    private Integer productId;
	@Column(name = "remark", length = 256)
    private String remark;
	@Column(name = "salesCity", length = 11)
    private Integer salesCity;
	@Column(name = "uid", length = 256)
    private String uid;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime", length = 19)
	private Date createTime;
	@Column(name = "orderStatus")
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	@Column(name = "ctripOrderId", length = 256)
    private String ctripOrderId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getAdultNumber() {
		return adultNumber;
	}
	public void setAdultNumber(Integer adultNumber) {
		this.adultNumber = adultNumber;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getChildNumber() {
		return childNumber;
	}
	public void setChildNumber(Integer childNumber) {
		this.childNumber = childNumber;
	}
	public String getDistributorOrderId() {
		return distributorOrderId;
	}
	public void setDistributorOrderId(String distributorOrderId) {
		this.distributorOrderId = distributorOrderId;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getContactMobile() {
		return contactMobile;
	}
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public Boolean getIsBackCash() {
		return isBackCash;
	}
	public void setIsBackCash(Boolean isBackCash) {
		this.isBackCash = isBackCash;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getSalesCity() {
		return salesCity;
	}
	public void setSalesCity(Integer salesCity) {
		this.salesCity = salesCity;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getCtripOrderId() {
		return ctripOrderId;
	}
	public void setCtripOrderId(String ctripOrderId) {
		this.ctripOrderId = ctripOrderId;
	}

}
