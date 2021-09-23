package com.data.data.hmly.service.balance.entity;

import com.data.data.hmly.service.balance.entity.enums.AccountStatus;
import com.data.data.hmly.service.balance.entity.enums.AccountType;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.framework.hibernate.util.Entity;

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
import javax.persistence.Transient;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by vacuity on 15/10/20.
 */

@javax.persistence.Entity
@Table(name = "account_log")
public class AccountLog extends Entity implements Serializable {
    private static final long	serialVersionUID	= -1690906106930903058L;
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createBy")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountUserId")
    private User accountUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private SysUnit companyUnit;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Column(name = "wechatCode")
    private String wechatCode;

    @Column(name = "wechatTime")
    private Date wechatTime;

    @Column(name = "money")
    private Double money;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "orderNo")
    private String orderNo;

    @Column(name = "description")
    private String description;

    @Column(name = "createTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "updateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Column(name = "sourceId")
    private Long sourceId;

    @Column(name = "auditTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date auditTime;

    @Column(name = "rejectReason")
    private String rejectReason;

    @Column(name = "orderType")
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Column(name = "detailType")
    @Enumerated(EnumType.STRING)
    private ProductType detailType;

    @Transient
    private List<AccountType> inType;
    @Transient
    private List<AccountStatus> inStatus;
    @Transient
    private Date createTimeStart;
    @Transient
    private Date createTimeEnd;
    @Transient
    private String name;
    @Transient
    private DecimalFormat df = new DecimalFormat("#,##0.00");

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public String getWechatCode() {
        return wechatCode;
    }

    public void setWechatCode(String wechatCode) {
        this.wechatCode = wechatCode;
    }

    public Date getWechatTime() {
        return wechatTime;
    }

    public void setWechatTime(Date wechatTime) {
        this.wechatTime = wechatTime;
    }

    public SysUnit getCompanyUnit() {
        return companyUnit;
    }

    public void setCompanyUnit(SysUnit companyUnit) {
        this.companyUnit = companyUnit;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getTypeStr() {
        if (type == null) {
            return "";
        }
        return type.getDescription();
    }

    public String getStatusStr() {
        if (status == null) {
            return "";
        }
        return status.getDescription();
    }

    /**
     * 转json时null被默认为0，所以增加此方法
     * @return
     */
    public String getBalanceStr() {
        if (balance == null) {
            return "----";
        }
        return df.format(balance);
    }

    public String getMoneyStr() {
        if (money == null) {
            return "----";
        }
        return df.format(money);
    }

    public List<AccountType> getInType() {
        return inType;
    }

    public void setInType(List<AccountType> inType) {
        this.inType = inType;
    }

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public String getCompanyUnitName() {
        if (companyUnit == null) {
            return null;
        }
        return companyUnit.getName();
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public User getAccountUser() {
        return accountUser;
    }

    public void setAccountUser(User accountUser) {
        this.accountUser = accountUser;
    }

    public List<AccountStatus> getInStatus() {
        return inStatus;
    }

    public void setInStatus(List<AccountStatus> inStatus) {
        this.inStatus = inStatus;
    }

    public ProductType getDetailType() {
        return detailType;
    }

    public void setDetailType(ProductType detailType) {
        this.detailType = detailType;
    }
}
