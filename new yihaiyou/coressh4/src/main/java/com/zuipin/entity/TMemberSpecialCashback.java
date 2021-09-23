package com.zuipin.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_MEMBER_SPECIAL_CASHBACK")
public class TMemberSpecialCashback implements java.io.Serializable {
	@Id
	@Column(name = "ID", precision = 18, scale = 0)
	private Long	id;
	@Column(name = "MEMBER_ID", precision = 18, scale = 0)
	private Long	memberId;
	@Column(name = "STORE_ID", precision = 18, scale = 0)
	private Long	storeId;
	@Column(name = "CARD_BALANCE", precision = 23, scale = 3)
	private Double	cardBalance;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INPUT_DATE")
	private Date	inputDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_DATE")
	private Date	updateDate;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getMemberId() {
		return memberId;
	}
	
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
	public Long getStoreId() {
		return storeId;
	}
	
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}
	
	public Date getInputDate() {
		return inputDate;
	}
	
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	
	public Date getUpdateDate() {
		return updateDate;
	}
	
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public Double getCardBalance() {
		return cardBalance;
	}
	
	public void setCardBalance(Double cardBalance) {
		this.cardBalance = cardBalance;
	}
	
}
