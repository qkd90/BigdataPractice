package com.data.data.hmly.service.lxbcommon.entity;

import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.lxbcommon.entity.enums.UserCouponStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * Created by zzl on 2016/5/9.
 */
@Entity
@Table(name = "lxb_user_coupon")
public class UserCoupon extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserCouponStatus status;

    @Column(name = "valid_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validStart;

    @Column(name = "valid_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validEnd;

    @Column(name = "use_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date useTime;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Transient
    private String limitProductTypes;

    @Transient
    private List<UserCouponStatus> neededStatuses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public UserCouponStatus getStatus() {
        return status;
    }

    public void setStatus(UserCouponStatus status) {
        this.status = status;
    }

    public Date getValidStart() {
        return validStart;
    }

    public void setValidStart(Date validStart) {
        this.validStart = validStart;
    }

    public Date getValidEnd() {
        return validEnd;
    }

    public void setValidEnd(Date validEnd) {
        this.validEnd = validEnd;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLimitProductTypes() {
        return limitProductTypes;
    }

    public void setLimitProductTypes(String limitProductTypes) {
        this.limitProductTypes = limitProductTypes;
    }

    public List<UserCouponStatus> getNeededStatuses() {
        return neededStatuses;
    }

    public void setNeededStatuses(List<UserCouponStatus> neededStatuses) {
        this.neededStatuses = neededStatuses;
    }
}
