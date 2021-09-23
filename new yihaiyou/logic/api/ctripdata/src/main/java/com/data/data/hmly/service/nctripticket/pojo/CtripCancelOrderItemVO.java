package com.data.data.hmly.service.nctripticket.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by caiys on 2016/2/18.
 */
public class CtripCancelOrderItemVO {
    private Integer cancelItemStatus;   // 是否取消 0:不可取消 1:可取消 2 可取消退款金额无法计算
    private Integer itemType;           //	 可选项类型 0：可选项 1：保险 2：优惠券
    private Long orderItemId;           // 可选项ID
    private String resourceName;        // 可选项名称
    private Integer quantity;           //	 可选项份数
    private Double amount;              //	 可选项可退金额
    private Double refundCost;          // 扣损金额
    private Integer refundCostType;     // 扣损类型   0：无    1： %  2：元/张 3： 元
    private Double refundCostValue;     // 扣损值

    public Integer getCancelItemStatus() {
        return cancelItemStatus;
    }

    public void setCancelItemStatus(Integer cancelItemStatus) {
        this.cancelItemStatus = cancelItemStatus;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    @JsonProperty("OrderItemID")
    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @JsonProperty("Quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getRefundCost() {
        return refundCost;
    }

    public void setRefundCost(Double refundCost) {
        this.refundCost = refundCost;
    }

    public Integer getRefundCostType() {
        return refundCostType;
    }

    public void setRefundCostType(Integer refundCostType) {
        this.refundCostType = refundCostType;
    }

    public Double getRefundCostValue() {
        return refundCostValue;
    }

    public void setRefundCostValue(Double refundCostValue) {
        this.refundCostValue = refundCostValue;
    }
}
