package com.data.data.hmly.service.wechat.entity;

import com.framework.hibernate.util.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by vacuity on 15/11/19.
 */

@javax.persistence.Entity
@Table(name = "wx_visit_log")
public class WechatVisitLog extends Entity implements Serializable {

    private static final long serialVersionUID = -1690906106930903058L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "path")
    private String path;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resourceId")
    private WechatResource wechatResource;

    @Column(name = "resObjectValue")
    private String resObjectValue;

    @Column(name = "ip")
    private String ip;

    @Column(name = "openId")
    private String openId;
    
    @Column(name = "userId")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private WechatAccount wechatAccount;

    @Column(name = "visitTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date visitTime;


    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

	public WechatResource getWechatResource() {
		return wechatResource;
	}

	public void setWechatResource(WechatResource wechatResource) {
		this.wechatResource = wechatResource;
	}

	public String getResObjectValue() {
        return resObjectValue;
    }

    public void setResObjectValue(String resObjectValue) {
        this.resObjectValue = resObjectValue;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

	public WechatAccount getWechatAccount() {
        return wechatAccount;
    }

    public void setWechatAccount(WechatAccount wechatAccount) {
        this.wechatAccount = wechatAccount;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }
}
