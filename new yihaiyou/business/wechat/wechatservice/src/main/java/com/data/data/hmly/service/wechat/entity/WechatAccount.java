package com.data.data.hmly.service.wechat.entity;

import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.framework.hibernate.util.Entity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by vacuity on 15/11/19.
 */

@javax.persistence.Entity
@Table(name = "wx_account")
public class WechatAccount extends Entity implements Serializable {
    private static final long	serialVersionUID	= -1690906106930903058L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "account")
    private String account;

    @Column(name = "originalId")
    private String originalId;

    @Column(name = "appId")
    private String appId;

    @Column(name = "appSecret")
    private String appSecret;

    @Column(name = "app_appId")
    private String appAppId;

    @Column(name = "app_appSecret")
    private String appAppSecret;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId")
    private SysUnit companyUnit;

    @Column(name = "token")
    private String token;

    @Column(name = "tokenExpired")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tokenExpired;

    @Column(name = "createTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private SysUser user;

    @Column(name = "validFlag")
    private Boolean validFlag;

    @Column(name = "mchKey")
    private String mchKey;

    @Column(name = "mchId")
    private String mchId;
    @Column(name = "notifyUrl")
    private String notifyUrl;

    @Column(name = "defaultTplId")
    private String defaultTplId;

    @Column(name = "jsapiTicket")
    private String jsapiTicket;

    @Column(name = "ticketExpired")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ticketExpired;

    @Column(name = "qrcode")
    private String qrcode;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public SysUnit getCompanyUnit() {
        return companyUnit;
    }

    public void setCompanyUnit(SysUnit companyUnit) {
        this.companyUnit = companyUnit;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTokenExpired() {
        return tokenExpired;
    }

    public void setTokenExpired(Date tokenExpired) {
        this.tokenExpired = tokenExpired;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public boolean getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(Boolean validFlag) {
        this.validFlag = validFlag;
    }

    public String getMchKey() {
        return mchKey;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

	public String getDefaultTplId() {
		return defaultTplId;
	}

	public void setDefaultTplId(String defaultTplId) {
		this.defaultTplId = defaultTplId;
	}

    public Date getTicketExpired() {
        return ticketExpired;
    }

    public void setTicketExpired(Date ticketExpired) {
        this.ticketExpired = ticketExpired;
    }

    public String getJsapiTicket() {
        return jsapiTicket;
    }

    public void setJsapiTicket(String jsapiTicket) {
        this.jsapiTicket = jsapiTicket;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getAppAppId() {
        return appAppId;
    }

    public void setAppAppId(String appAppId) {
        this.appAppId = appAppId;
    }

    public String getAppAppSecret() {
        return appAppSecret;
    }

    public void setAppAppSecret(String appAppSecret) {
        this.appAppSecret = appAppSecret;
    }
}
