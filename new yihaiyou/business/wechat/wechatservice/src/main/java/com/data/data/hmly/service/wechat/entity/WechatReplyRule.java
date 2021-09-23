package com.data.data.hmly.service.wechat.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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

import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.wechat.entity.enums.MatchType;
import com.framework.hibernate.util.Entity;

@javax.persistence.Entity
@Table(name = "wx_reply_rule")
public class WechatReplyRule extends Entity implements Serializable {
	private static final long serialVersionUID = 7120976931919333215L;
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dataItemId")
    private WechatDataItem dataItem;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private WechatAccount wechatAccount;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId")
    private SysUnit sysUnit;

    @Column(name = "modifyTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyTime;

    @Column(name = "createTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    
    @Column(name = "userId")
    private Long userId;

	public WechatReplyRule() {
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

	public WechatDataItem getDataItem() {
		return dataItem;
	}

	public void setDataItem(WechatDataItem dataItem) {
		this.dataItem = dataItem;
	}

	public WechatAccount getWechatAccount() {
		return wechatAccount;
	}

	public void setWechatAccount(WechatAccount wechatAccount) {
		this.wechatAccount = wechatAccount;
	}

	public SysUnit getSysUnit() {
		return sysUnit;
	}

	public void setSysUnit(SysUnit sysUnit) {
		this.sysUnit = sysUnit;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
}
