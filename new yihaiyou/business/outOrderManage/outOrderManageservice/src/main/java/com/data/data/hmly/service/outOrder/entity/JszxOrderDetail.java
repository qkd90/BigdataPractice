package com.data.data.hmly.service.outOrder.entity;


import com.data.data.hmly.service.common.entity.ProductValidateCode;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderDetailPriceType;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderDetailStatus;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.framework.hibernate.util.Entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by dy on 2016/2/24.
 */
@javax.persistence.Entity
@Table(name = "jszx_order_detail")
public class JszxOrderDetail extends Entity implements java.io.Serializable {
//    `id` bigint(20) NOT NULL AUTO_INCREMENT,
//    `order_id` bigint(20) DEFAULT NULL,
//    `type` enum('adult','student','child','oldman','taopiao','other','team') CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '票型：-1其他；1成人票；2学生票；3儿童票；4老人票；5团队票；6套票',
//            `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
//            `ticketNo` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
//    `outTime` datetime DEFAULT NULL,
//            `useStatus` enum('UNUSED','CANCEL','REFUNDING','USED') CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '''UNUSED-未使用'',''CANCEL-已取消'',''REFUNDING-退款中-'',''USED-已使用''',
//            `useTime` datetime DEFAULT NULL,
//            `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
//            `ticket_price_id` bigint(20) DEFAULT NULL,
//    `valid_endTime` datetime DEFAULT NULL,
//            `valid_startTime` datetime DEFAULT NULL,
//
//    `create_time` datetime DEFAULT NULL,
//            `update_time` datetime DEFAULT NULL,
//    `price` float(11,2) DEFAULT NULL,
//    `total_price` float(11,2) DEFAULT NULL,
//    `count` int(11) DEFAULT NULL,


    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private Long id;

    @Column(name = "name")
    private String ticketName;

    @Column(name = "ticketNo")
    private String ticketNo;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private JszxOrderDetailPriceType type;

    @Column(name = "refund_status")
    private String refundStatus;

    @Column(name = "description")
    private String description;

    @Column(name = "total_price")
    private Float totalPrice;


    @Column(name = "actual_payment")
    private Float actualPay;

    @Column(name = "sales_price")
    private Float salesPrice;

    @Column(name = "count")
    private Integer count;

    @Column(name = "rest_count")
    private Integer restCount;

    @Column(name = "refund_count")
    private Integer refundCount;

    @Column(name = "price")
    private Float price;


    @Column(name = "quantity_price")
    private Float quantityPrice;


    @Column(name = "ticket_price_id")
    private Long typePriceId;

    @Column(name = "useStatus")
    @Enumerated(EnumType.STRING)
    private JszxOrderDetailStatus useStatus;


    @ManyToOne
    @JoinColumn(name = "order_id")
    private JszxOrder jszxOrder;

    @Column(name = "useTime")
    private Date useTime;

    @Column(name = "valid_endTime")
    private Date endTime;

    @Column(name = "valid_startTime")
    private Date startTime;


    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Transient
    private String code;

    @Transient
    private String detailStatus;

    @Transient
    private String playStatus;


    public Integer getRestCount() {
        return restCount;
    }

    public void setRestCount(Integer restCount) {
        this.restCount = restCount;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String description) {
        this.description = description;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public JszxOrderDetailStatus getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(JszxOrderDetailStatus useStatus) {
        this.useStatus = useStatus;
    }

    public JszxOrder getJszxOrder() {
        return jszxOrder;
    }

    public void setJszxOrder(JszxOrder jszxOrder) {
        this.jszxOrder = jszxOrder;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public JszxOrderDetailPriceType getType() {
        return type;
    }

    public void setType(JszxOrderDetailPriceType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public Integer getRefundCount() {
        return refundCount;
    }
    public void setRefundCount(Integer refundCount) {
        this.refundCount = refundCount;
    }

    public Long getTypePriceId() {
        return typePriceId;
    }

    public void setTypePriceId(Long typePriceId) {
        this.typePriceId = typePriceId;
    }

    public String getRefundStatus() {
//        'confirm','noconfirm'
        if ("noconfirm".equals(refundStatus)) {
            return "待审核";
        }
        if ("confirm".equals(refundStatus)) {
            return "已审核";
        }
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }


    public Float getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(Float salesPrice) {
        this.salesPrice = salesPrice;
    }

    public String getPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(String playStatus) {
        this.playStatus = playStatus;
    }

    public Float getActualPay() {
        return actualPay;
    }

    public void setActualPay(Float actualPay) {
        this.actualPay = actualPay;
    }

    public String getDetailStatus() {
        return detailStatus;
    }

    public void setDetailStatus(String detailStatus) {
        this.detailStatus = detailStatus;
    }


    public Float getQuantityPrice() {
        return quantityPrice;
    }

    public void setQuantityPrice(Float quantityPrice) {
        this.quantityPrice = quantityPrice;
    }

    public void syncUseStatus(JszxOrderDetail orderDetail, ProductValidateCode productValidateCode) {

        if (productValidateCode.getUsed() >= 1) {
            orderDetail.setUseStatus(JszxOrderDetailStatus.USED);
        } else if (productValidateCode.getUsed() == 0) {
            orderDetail.setUseStatus(JszxOrderDetailStatus.UNUSED);
        } else {
            orderDetail.setUseStatus(JszxOrderDetailStatus.CANCEL);
        }

    }

    public void fmtTicketDetailUseStatus(JszxOrderDetail jszxOrderDetail, ProductValidateCode productValidateCode) {
        String useStatusStr = "";   //使用状态
        Integer totalCount = 0;
        if (productValidateCode != null && productValidateCode.getOrderInitCount() != null) {
            totalCount = productValidateCode.getOrderInitCount();  //总数量
        }
        Integer orderCount = 0;
        if (productValidateCode != null && productValidateCode.getOrderCount() != null) {
            orderCount = productValidateCode.getOrderCount();    //可验票数量
        }
        Integer refundCount = 0;
        if (productValidateCode != null && productValidateCode.getRefundCount() != null) {
            refundCount = productValidateCode.getRefundCount();     //退款数量
        }
        String refundCountStr = "";
        if (refundCount != null && refundCount != 0) {
            refundCountStr = "退款x" + refundCount + "，";
        }
        String orderCountStr = "";
        if (orderCount != null && orderCount != 0) {
            orderCountStr = "未验票x" + orderCount + "，";
        }
        String hasedCheckStr = "";
        int hasedCount = totalCount - orderCount - refundCount;     //已验票数量
        if (hasedCount != 0) {
            hasedCheckStr = "已验票x" + hasedCount + "，";
        }
        if (jszxOrderDetail.getUseStatus() == JszxOrderDetailStatus.UNUSED
                || jszxOrderDetail.getUseStatus() == JszxOrderDetailStatus.USED) {
            useStatusStr = useStatusStr + hasedCheckStr;
            useStatusStr = useStatusStr + orderCountStr;
            useStatusStr = useStatusStr + refundCountStr;
        } else if (jszxOrderDetail.getUseStatus() == JszxOrderDetailStatus.CANCEL) {
            useStatusStr = "已取消，";
        }
        if (useStatusStr.length() > 0 ) {
            useStatusStr = useStatusStr.substring(0, useStatusStr.length() - 1);
        }
        jszxOrderDetail.setDetailStatus(useStatusStr);
    }




}
