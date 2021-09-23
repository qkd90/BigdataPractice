package com.data.data.hmly.service.entity;

import com.framework.hibernate.util.Entity;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@javax.persistence.Entity
public class SendingMsg extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8979301394509748414L;
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
	private String statusCode;
	private Long siteId;

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

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
}
