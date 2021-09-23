package com.data.data.hmly.service.order.entity;

import com.data.data.hmly.service.order.entity.enums.RefundChannel;
import com.data.data.hmly.service.order.entity.enums.RefundStatus;
import com.framework.hibernate.util.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by dy on 2016/5/30.
 */

@javax.persistence.Entity
@Table(name = "refund_log")
public class RefundLog extends Entity implements Serializable {
/*    CREATE TABLE `refund_log` (
        `id` bigint(20) NOT NULL,
        `trade_no` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '订单编号',
        `channel` enum('weixin','taobao') COLLATE utf8_bin DEFAULT NULL COMMENT '退款渠道',
        `order_id` bigint(20) DEFAULT NULL COMMENT '订单编号',
        `order_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '产品名称',
        `refund_result` enum('FAIL','SUCCESS') COLLATE utf8_bin DEFAULT NULL COMMENT '退款状态',
        `user_id` bigint(20) DEFAULT NULL,
        `total_refund` float(255,2) DEFAULT NULL COMMENT '退款金额',
        `retrun_msg` varchar(500) COLLATE utf8_bin DEFAULT NULL,
        `refund_desc` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '退款理由',
        `create_time` datetime DEFAULT NULL,
        `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;*/

    private static final long	serialVersionUID	= -1690906106930903058L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "trade_no")
    private String tradeNo;

    @Column(name = "channel")
    @Enumerated(EnumType.STRING)
    private RefundChannel channel;

    @Column(name = "refund_no")
    private String refundNo;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "order_name")
    private String orderName;

    @Column(name = "retrun_msg")
    private String retrunMsg;

    @Column(name = "refund_result")
    @Enumerated(EnumType.STRING)
    private RefundStatus result;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "total_refund")
    private Float totalRefund;

    @Column(name = "refund_desc")
    private String refundDesc;

    @Column(name = "requery_refund_desc")
    private String requeryRefundDesc;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Transient
    private Date startTime;

    @Transient
    private Date endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public RefundChannel getChannel() {
        return channel;
    }

    public void setChannel(RefundChannel channel) {
        this.channel = channel;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public RefundStatus getResult() {
        return result;
    }

    public void setResult(RefundStatus result) {
        this.result = result;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Float getTotalRefund() {
        return totalRefund;
    }

    public void setTotalRefund(Float totalRefund) {
        this.totalRefund = totalRefund;
    }

    public String getRefundDesc() {
        return refundDesc;
    }

    public void setRefundDesc(String refundDesc) {
        this.refundDesc = refundDesc;
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

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getRetrunMsg() {
        return retrunMsg;
    }

    public void setRetrunMsg(String retrunMsg) {
        this.retrunMsg = retrunMsg;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public RefundStatus getFmtResult(String resultStr) {

        if ("SUCCESS".equals(resultStr)) {
            return RefundStatus.SUCCESS;
        }

        if ("FAIL".equals(resultStr)) {
            return RefundStatus.FAIL;
        }

        if ("WAITING".equals(resultStr)) {
            return RefundStatus.WAITING;
        }

        return null;
    }

    public RefundChannel getFmtChannel(String channelStr) {

        if ("taobao".equals(channelStr)) {
            return RefundChannel.taobao;
        }

        if ("weixin".equals(channelStr)) {
            return RefundChannel.weixin;
        }

        return null;
    }

    public String getRequeryRefundDesc() {
        return requeryRefundDesc;
    }

    public void setRequeryRefundDesc(String requeryRefundDesc) {
        this.requeryRefundDesc = requeryRefundDesc;
    }

    public String getRefundNo() {
        return refundNo;
    }

    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo;
    }
}
