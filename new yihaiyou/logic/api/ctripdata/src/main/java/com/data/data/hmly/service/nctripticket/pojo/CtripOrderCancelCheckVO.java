package com.data.data.hmly.service.nctripticket.pojo;

import java.util.List;

/**
 * Created by caiys on 2016/2/18.
 */
public class CtripOrderCancelCheckVO {
    private Integer cancelStatus;       // 取消状态 0:不可取消 1:可取消 3 可见不可退
    private String payMode;              // 支付方式 O：现付 P:预付
    private Double totalAmount;          // 退款金额
    private List<CtripCancelOrderItemVO> cancelItems;

    public Integer getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(Integer cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<CtripCancelOrderItemVO> getCancelItems() {
        return cancelItems;
    }

    public void setCancelItems(List<CtripCancelOrderItemVO> cancelItems) {
        this.cancelItems = cancelItems;
    }
}
