package com.data.data.hmly.service.wechat.entity;

import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.framework.hibernate.util.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by vacuity on 15/11/19.
 */

@javax.persistence.Entity
@Table(name = "wx_account_menu")
public class WechatAccountMenu extends Entity implements Serializable {

    private static final long serialVersionUID = -1690906106930903058L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "menuName")
    private String menuName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resourceId")
    private WechatResource wechatResource;

    @Column(name = "level")
    private Integer level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private WechatAccountMenu parentMenu;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private WechatAccount wechatAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId")
    private SysUnit companyUnit;

    @Column(name = "createTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private SysUser user;

    @Column(name = "syncTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date syncTime;

    @Column(name = "orderNo")
    private Integer orderNo;

    @Column(name = "url")
    private String url;


    @Transient
    private List<WechatAccountMenu> children;
    @Transient
    private String text;
    @Transient
    private Long resourceId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public WechatResource getWechatResource() {
        return wechatResource;
    }

    public void setWechatResource(WechatResource wechatResource) {
        this.wechatResource = wechatResource;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public WechatAccountMenu getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(WechatAccountMenu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public WechatAccount getWechatAccount() {
        return wechatAccount;
    }

    public void setWechatAccount(WechatAccount wechatAccount) {
        this.wechatAccount = wechatAccount;
    }

    public SysUnit getCompanyUnit() {
        return companyUnit;
    }

    public void setCompanyUnit(SysUnit companyUnit) {
        this.companyUnit = companyUnit;
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

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public List<WechatAccountMenu> getChildren() {
        return children;
    }

    public void setChildren(List<WechatAccountMenu> children) {
        this.children = children;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getResourceId() {
        if (wechatResource != null) {
            return wechatResource.getId();
        } else {
            return null;
        }
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
}
