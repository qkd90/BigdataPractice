package com.data.data.hmly.service.pay.entity;

import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.pay.entity.enums.PayAction;
import com.data.data.hmly.service.pay.entity.enums.PayTongdao;
import com.framework.hibernate.util.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by vacuity on 15/10/20.
 */

@javax.persistence.Entity
@Table(name = "paylog")
public class PayLog extends Entity implements Serializable {

    private static final long	serialVersionUID	= -1690906106930903058L;

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    private long id;

    @Column(name = "proId")
    private Long orderId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @ManyToOne
    @JoinColumn(name = "USERID", unique = true, nullable = false, updatable = false)
    private User user;

//    @Column(name = "PROID")
//    private long proId;
//    @Column(name = "USERID")
//    private long userid;

    @Column(name = "ACTION")
    @Enumerated(EnumType.STRING)
    private PayAction action;

    @Column(name = "COST")
    private float cost;

    @Column(name = "REQUESTTIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestTime;

    @Column(name = "TONGDAO")
    @Enumerated(EnumType.STRING)
    private PayTongdao tongdao;

    @Column(name = "payAccount")
    private String payAccount;

    @Column(name = "subject")
    private String subject;

    @Column(name = "tradeNo")
    private String tradeNo;

    @Column(name = "notifyId")
    private String notifyId;

    @Column(name = "notifyType")
    private String notifyType;

    @Column(name = "notifyTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date notifyTime;



    /**
     *
     * @return
     */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public PayAction getAction() {
        return action;
    }

    public void setAction(PayAction action) {
        this.action = action;
    }


    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }


    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }


    public PayTongdao getTongdao() {
        return tongdao;
    }

    public void setTongdao(PayTongdao tongdao) {
        this.tongdao = tongdao;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public Date getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
    }
}
