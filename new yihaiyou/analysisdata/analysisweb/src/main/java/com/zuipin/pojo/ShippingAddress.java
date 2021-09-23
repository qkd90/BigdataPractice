package com.zuipin.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ShippingAddress entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shipping_address")
public class ShippingAddress extends com.framework.hibernate.util.Entity implements java.io.Serializable {
	
	// Fields
	
	private static final long	serialVersionUID	= 3216783504192280430L;
	private Long	id;
	private Member	member;
	private String	consignee;
	private String	address;
	private String	postcode;
	private String	telephone;
	private String	mobilePhone;
	private Boolean	isDefaultAddress;
	private Long	sort;
	
	// Constructors
	
	/** default constructor */
	public ShippingAddress() {
	}
	
	/** full constructor */
	public ShippingAddress(Member member, String consignee, String address, String postcode, String telephone, String mobilePhone, Boolean isDefaultAddress, Long sort) {
		this.member = member;
		this.consignee = consignee;
		this.address = address;
		this.postcode = postcode;
		this.telephone = telephone;
		this.mobilePhone = mobilePhone;
		this.isDefaultAddress = isDefaultAddress;
		this.sort = sort;
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mem_ID")
	public Member getMember() {
		return this.member;
	}
	
	public void setMember(Member member) {
		this.member = member;
	}
	
	@Column(name = "CONSIGNEE", length = 500)
	public String getConsignee() {
		return this.consignee;
	}
	
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	
	@Column(name = "ADDRESS", length = 500)
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "POSTCODE", length = 500)
	public String getPostcode() {
		return this.postcode;
	}
	
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	@Column(name = "TELEPHONE", length = 500)
	public String getTelephone() {
		return this.telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Column(name = "MOBILE_PHONE", length = 500)
	public String getMobilePhone() {
		return this.mobilePhone;
	}
	
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	@Column(name = "IS_DEFAULT_ADDRESS")
	public Boolean getIsDefaultAddress() {
		return this.isDefaultAddress;
	}
	
	public void setIsDefaultAddress(Boolean isDefaultAddress) {
		this.isDefaultAddress = isDefaultAddress;
	}
	
	@Column(name = "SORT")
	public Long getSort() {
		return this.sort;
	}
	
	public void setSort(Long sort) {
		this.sort = sort;
	}
	
}