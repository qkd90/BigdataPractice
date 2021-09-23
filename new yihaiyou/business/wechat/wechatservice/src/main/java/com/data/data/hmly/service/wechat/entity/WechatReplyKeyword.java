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
@Table(name = "wx_reply_keyword")
public class WechatReplyKeyword extends Entity implements Serializable {
	private static final long serialVersionUID = 7120976931919333215L;
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "keyword")
    private String keyword;
    
	@Enumerated(EnumType.STRING)
    @Column(name = "matchType")
    private MatchType matchType;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruleId")
    private WechatReplyRule replyRule;

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

	public WechatReplyKeyword() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public MatchType getMatchType() {
		return matchType;
	}

	public void setMatchType(MatchType matchType) {
		this.matchType = matchType;
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

	public WechatReplyRule getReplyRule() {
		return replyRule;
	}

	public void setReplyRule(WechatReplyRule replyRule) {
		this.replyRule = replyRule;
	}
	
	
}
