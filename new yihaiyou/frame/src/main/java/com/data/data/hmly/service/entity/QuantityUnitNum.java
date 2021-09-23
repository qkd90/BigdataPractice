package com.data.data.hmly.service.entity;

import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "quantity_unit_num")
//@Polymorphism(type=PolymorphismType.EXPLICIT)
public class QuantityUnitNum extends com.framework.hibernate.util.Entity implements java.io.Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -9215023448601457011L;
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supler_unit_id")
    private SysUnit suplerUnit; //拱量公司

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dealer_unit_id")
    private SysUnit dealerUnit; //被拱量公司


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_by")
    private SysUser user; //创建人


    @Column(name = "condition_nums", length = 10)
    private Integer conditionNum;       //拱量条件


    @Column(name = "create_time", length = 10)
    private Date createTime;

    @Column(name = "update_time", length = 10)
    private Date updateTime;



    @Transient
    private Long suplerUnitId; //拱量公司编号

    @Transient
    private Long dealerUnitId; //被拱量公司编号

    @Transient
    private String suplerUnitName; //拱量公司名称

    @Transient
    private String dealerUnitName; //被拱量公司名称

    @Transient
    private String suplerUnitIdentityCode; //拱量公司串码

    @Transient
    private String dealerUnitIdentityCode; //被拱量公司串码

    @Transient
    private Integer conditionNumStart;       //拱量条件起始值

    @Transient
    private Integer conditionNumEnd;       //拱量条件结束值

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SysUnit getSuplerUnit() {
        return suplerUnit;
    }

    public void setSuplerUnit(SysUnit suplerUnit) {
        this.suplerUnit = suplerUnit;
    }

    public SysUnit getDealerUnit() {
        return dealerUnit;
    }

    public void setDealerUnit(SysUnit dealerUnit) {
        this.dealerUnit = dealerUnit;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public Integer getConditionNum() {
        return conditionNum;
    }

    public void setConditionNum(Integer conditionNum) {
        this.conditionNum = conditionNum;
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


    public Long getSuplerUnitId() {
        if (suplerUnit != null) {
            return suplerUnit.getId();
        }
        return suplerUnitId;
    }

    public void setSuplerUnitId(Long suplerUnitId) {
        this.suplerUnitId = suplerUnitId;
    }

    public Long getDealerUnitId() {
        if (dealerUnit != null) {
            return dealerUnit.getId();
        }
        return dealerUnitId;
    }

    public void setDealerUnitId(Long dealerUnitId) {
        this.dealerUnitId = dealerUnitId;
    }

    public String getSuplerUnitName() {
        if (suplerUnit != null) {
            return suplerUnit.getName();
        }
        return suplerUnitName;
    }

    public void setSuplerUnitName(String suplerUnitName) {
        this.suplerUnitName = suplerUnitName;
    }

    public String getDealerUnitName() {
        if (dealerUnit != null) {
            return dealerUnit.getName();
        }
        return dealerUnitName;
    }

    public void setDealerUnitName(String dealerUnitName) {
        this.dealerUnitName = dealerUnitName;
    }

    public String getSuplerUnitIdentityCode() {

        if (suplerUnit != null) {
            return suplerUnit.getIdentityCode();
        }

        return suplerUnitIdentityCode;
    }

    public void setSuplerUnitIdentityCode(String suplerUnitIdentityCode) {
        this.suplerUnitIdentityCode = suplerUnitIdentityCode;
    }

    public String getDealerUnitIdentityCode() {
        if (dealerUnit != null) {
            return dealerUnit.getIdentityCode();
        }
        return dealerUnitIdentityCode;
    }

    public void setDealerUnitIdentityCode(String dealerUnitIdentityCode) {
        this.dealerUnitIdentityCode = dealerUnitIdentityCode;
    }

    public Integer getConditionNumStart() {
        return conditionNumStart;
    }

    public void setConditionNumStart(Integer conditionNumStart) {
        this.conditionNumStart = conditionNumStart;
    }

    public Integer getConditionNumEnd() {
        return conditionNumEnd;
    }

    public void setConditionNumEnd(Integer conditionNumEnd) {
        this.conditionNumEnd = conditionNumEnd;
    }
}
