package com.data.data.hmly.service.user.entity;

import com.data.data.hmly.enums.ThirdPartyUserType;
import com.data.data.hmly.service.entity.TbArea;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "third_party_user")
public class ThirdPartyUser extends com.framework.hibernate.util.Entity implements java.io.Serializable {
	private static final long serialVersionUID = 7055097129219806071L;
	@Id
	@GeneratedValue
	private Long				id;
	@Column(name = "openId")
	private String				openId;
	@Column(name = "userId")
	private Long				userId;
	@Column(name = "accountId")
	private Long				accountId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "areaid")
	private TbArea tbArea;
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private ThirdPartyUserType type;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime")
	private Date				createdTime;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public ThirdPartyUserType getType() {
		return type;
	}
	public void setType(ThirdPartyUserType type) {
		this.type = type;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public TbArea getTbArea() {
		return tbArea;
	}

	public void setTbArea(TbArea tbArea) {
		this.tbArea = tbArea;
	}
}
