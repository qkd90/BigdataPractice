package com.data.data.hmly.service.nctripticket.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zuipin.util.DateUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "nctrip_order_form_resource_info")
public class CtripOrderFormResourceInfo extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue
    @Column(name = "id", length = 20)
    private Long        id;		          // 标识
    @Column(name = "orderFormInfoId", length = 20)
    private Long        orderFormInfoId;		          // 订单标识
    @Column(name = "resourceId", length = 20)
    private Long resourceId;	 // （必填）资源ID
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "useDate", length = 19)
    private Date useDate;	     // （必填）使用日期，不能带时间部分，否则订单处理失败
    @Column(name = "quantity", length = 11)
    private Integer quantity;  // （必填）使用数量
    @Column(name = "price", length = 20, precision = 2)
    private Float price;      // （必填）分销价

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderFormInfoId() {
        return orderFormInfoId;
    }

    public void setOrderFormInfoId(Long orderFormInfoId) {
        this.orderFormInfoId = orderFormInfoId;
    }

    @JsonProperty("ResourceID")
    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    @JsonProperty("Quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("Price")
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @JsonProperty("UseDate")
    public String getUseDateString() {
        return DateUtils.format(useDate, "yyyy-MM-dd'T'HH:mm:ss");
    }

}
