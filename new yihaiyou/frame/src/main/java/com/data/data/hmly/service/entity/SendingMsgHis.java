package com.data.data.hmly.service.entity;

import com.framework.hibernate.util.Entity;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
@javax.persistence.Entity
@Table(name = "sendingMsg_his")
public class SendingMsgHis extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	// @ManyToOne
	// private SysUser sysUser;
	@Temporal(TemporalType.TIMESTAMP)
	private Date sendtime;
	private String context;
	private String receivernum;
	@Enumerated(EnumType.STRING)
	private SendStatus status;
	private Integer retry;


	private String orderNo;
	private Long siteId;
	private String realContext;


	
	public SendingMsgHis() {
		super();
	}

	public SendingMsgHis(Long id, Date sendtime, String context,
			String receivernum, SendStatus status, Integer retry) {
		super();
		this.id = id;
		this.sendtime = sendtime;
		this.context = context;
		this.receivernum = receivernum;
		this.status = status;
		this.retry = retry;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public SendStatus getStatus() {
		return status;
	}

	public void setStatus(SendStatus status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getReceivernum() {
		return receivernum;
	}

	public void setReceivernum(String receivernum) {
		this.receivernum = receivernum;
	}

	public Integer getRetry() {
		return retry;
	}

	public void setRetry(Integer retry) {
		this.retry = retry;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getRealContext() {
		return realContext;
	}

	public void setRealContext(String realContext) {
		this.realContext = realContext;
	}
}
