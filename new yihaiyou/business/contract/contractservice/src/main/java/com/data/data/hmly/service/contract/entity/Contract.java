package com.data.data.hmly.service.contract.entity;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.contract.entity.nums.ContractStatus;
import com.data.data.hmly.service.contract.entity.nums.SettlementType;
import com.data.data.hmly.service.contract.entity.nums.ValuationModels;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.framework.hibernate.util.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2016/9/1.
 */
@javax.persistence.Entity
@Table(name = "contract")
public class Contract extends Entity {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "partyAunit")
    private SysUnit partyAunit;

    @Column(name = "partyAnum")
    private String partyAnum;

    @ManyToOne
    @JoinColumn(name = "partyBunit")
    private SysUnit partyBunit;

    @Column(name = "partyBnum")
    private String partyBnum;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "signTime")
    private Date signTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "effectiveTime")
    private Date effectiveTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expirationTime")
    private Date expirationTime;

    @Column(name = "settlementType")
    @Enumerated(EnumType.STRING)
    private SettlementType settlementType;

    @Column(name = "settlementValue")
    private Integer settlementValue;


    @Column(name = "valuationModels")
    @Enumerated(EnumType.STRING)
    private ValuationModels valuationModels;

    @Column(name = "valuationValue")
    private Integer valuationValue;


    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "createBy")
    private SysUser user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime")
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime")
    private Date updateTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contract")
    private List<ContractAppendices> appendicesList = new ArrayList<ContractAppendices>();

    @Transient
    private String signTimeStr;

    @Transient
    private String qrySignTimeStart;

    @Transient
    private String qrySignTimeEnd;

    @Transient
    private String effectiveTimeStr;

    @Transient
    private String expirationTimeStr;

    @Transient
    private String qryExpiTimeStart;

    @Transient
    private String qryExpiTimeEnd;


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

    public String getPartyAnum() {
        return partyAnum;
    }

    public void setPartyAnum(String partyAnum) {
        this.partyAnum = partyAnum;
    }

    public String getPartyBnum() {
        return partyBnum;
    }

    public void setPartyBnum(String partyBnum) {
        this.partyBnum = partyBnum;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public SettlementType getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(SettlementType settlementType) {
        this.settlementType = settlementType;
    }

    public ContractStatus getStatus() {
        return status;
    }

    public void setStatus(ContractStatus status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public SysUnit getPartyAunit() {
        return partyAunit;
    }

    public void setPartyAunit(SysUnit partyAunit) {
        this.partyAunit = partyAunit;
    }

    public SysUnit getPartyBunit() {
        return partyBunit;
    }

    public void setPartyBunit(SysUnit partyBunit) {
        this.partyBunit = partyBunit;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public String getSignTimeStr() {
        return signTimeStr;
    }

    public void setSignTimeStr(String signTimeStr) {
        this.signTimeStr = signTimeStr;
    }

    public String getEffectiveTimeStr() {
        return effectiveTimeStr;
    }

    public void setEffectiveTimeStr(String effectiveTimeStr) {
        this.effectiveTimeStr = effectiveTimeStr;
    }

    public String getExpirationTimeStr() {
        return expirationTimeStr;
    }

    public void setExpirationTimeStr(String expirationTimeStr) {
        this.expirationTimeStr = expirationTimeStr;
    }

    public String getQrySignTimeStart() {
        return qrySignTimeStart;
    }

    public void setQrySignTimeStart(String qrySignTimeStart) {
        this.qrySignTimeStart = qrySignTimeStart;
    }

    public String getQrySignTimeEnd() {
        return qrySignTimeEnd;
    }

    public void setQrySignTimeEnd(String qrySignTimeEnd) {
        this.qrySignTimeEnd = qrySignTimeEnd;
    }

    public String getQryExpiTimeStart() {
        return qryExpiTimeStart;
    }

    public void setQryExpiTimeStart(String qryExpiTimeStart) {
        this.qryExpiTimeStart = qryExpiTimeStart;
    }

    public String getQryExpiTimeEnd() {
        return qryExpiTimeEnd;
    }

    public void setQryExpiTimeEnd(String qryExpiTimeEnd) {
        this.qryExpiTimeEnd = qryExpiTimeEnd;
    }

    public Integer getSettlementValue() {
        return settlementValue;
    }

    public void setSettlementValue(Integer settlementValue) {
        this.settlementValue = settlementValue;
    }

    public ValuationModels getValuationModels() {
        return valuationModels;
    }

    public void setValuationModels(ValuationModels valuationModels) {
        this.valuationModels = valuationModels;
    }

    public Integer getValuationValue() {
        return valuationValue;
    }

    public void setValuationValue(Integer valuationValue) {
        this.valuationValue = valuationValue;
    }

    public List<ContractAppendices> getAppendicesList() {
        return appendicesList;
    }

    public void setAppendicesList(List<ContractAppendices> appendicesList) {
        this.appendicesList = appendicesList;
    }
}
