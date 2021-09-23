package com.data.data.hmly.service.order.entity;

import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.entity.enums.OrderLogLevel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by zzl on 2016/3/28.
 */
@Entity
@Table(name = "torderlog")
public class OrderLog extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, insertable = true, updatable = false)
    private Long id;

    @Column(name = "order_id", nullable = false, updatable = false)
    private Long orderId;

    @Column(name = "order_detail_id", nullable = true, updatable = false)
    private Long orderDetailId;

    @Column(name = "log_content", nullable = false, updatable = false)
    private String logContent;

    @Column(name = "record_time", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date recordTime;

    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    private OrderLogLevel level;

    @OneToOne
    @JoinColumn(name = "operator", unique = false, nullable = false, updatable = false)
    private User operator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public User getOperator() {
        return operator;
    }

    public void setOperator(User operator) {
        this.operator = operator;
    }

    public OrderLogLevel getLevel() {
        return level;
    }

    public void setLevel(OrderLogLevel level) {
        this.level = level;
    }
}
