package com.data.data.hmly.service.order.entity;

import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.order.entity.enums.OrderBillTarget;
import com.data.data.hmly.service.order.entity.enums.OrderBillType;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.zuipin.util.StringUtils;

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

/**
 * Created by zzl on 2016/10/27.
 */
@Entity
@Table(name = "order_bill_summary")
public class OrderBillSummary extends com.framework.hibernate.util.Entity implements java.io.Serializable {


    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "bill_no")
    private String billNo;

    @ManyToOne
    @JoinColumn(name = "company_unit_id")
    private SysUnit companyUnit;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "total_order_count")
    private Integer totalOrderCount;

    @Column(name = "total_order_price")
    private Float totalOrderPrice;

    @Column(name = "total_bill_price")
    private Float totalBillPrice;

    @Column(name = "already_bill_price")
    private Float alreadyBillPrice;

    @Column(name = "not_bill_price")
    private Float notBillPrice;

    @Column(name = "bill_summary_date")
    @Temporal(TemporalType.DATE)
    private Date billSummaryDate;

    @Column(name = "bill_type")
    @Enumerated(EnumType.STRING)
    private OrderBillType billType;

    @Column(name = "bill_days")
    private Integer billDays;

    @Column(name = "bill_date")
    @Temporal(TemporalType.DATE)
    private Date billDate;

    @Column(name = "status")
    private Integer status;

    @Column(name = "confirm_status")
    private Integer confirmStatus;

    @ManyToOne
    @JoinColumn(name = "site_confirmor")
    private SysUser siteConfirmor;

    @ManyToOne
    @JoinColumn(name = "unit_confirmor")
    private SysUser unitConfirmor;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "remark")
    private String remark;

    @Column(name = "bill_target")
    @Enumerated(EnumType.STRING)
    private OrderBillTarget billTarget;

    @Column(name = "refund_price")
    private Float refundPrice;  // 退款金额
    @Column(name = "refund_count")
    private Integer refundCount;  // 退款订单数
    @Column(name = "refund_fee")
    private Float refundFee;  // 退款手续费
    @Column(name = "sale_count")
    private Integer saleCount;  // 销售票数（轮渡统计）
    @Column(name = "sale_amount")
    private Float saleAmount;  // 销售金额（轮渡统计）
    @Column(name = "return_count")
    private Integer returnCount;  // 退款票数（轮渡统计）
    @Column(name = "return_amount")
    private Float returnAmount;  // 销售金额（轮渡统计）
    @Column(name = "poundage_amount")
    private Float poundageAmount;  // 退款手续费（轮渡统计）

    @Transient
    private Date billDateStart;
    @Transient
    private Date billDateEnd;
    @Transient
    private String billDateStartStr;
    @Transient
    private String billDateEndStr;
    @Transient
    private Integer orderNum;
    @Transient
    private OrderDetailStatus orderDetailStatus;
    @Transient
    private Long companyUnitId;
    @Transient
    private String companyUnitName;
    @Transient
    private boolean notBillSummary = false; // 是否是人工生成
    @Transient
    private Long refundBillSummaryId;

    public OrderBillSummary() {
    }

    /**
     * 此构造方法用于
     * com.data.data.hmly.service.order.dao.OrderBillSummaryDao#summaryOrderDetail(com.data.data.hmly.service.order.entity.OrderBillSummary, com.framework.hibernate.util.Page)
     * @param companyUnitId
     * @param totalBillPrice
     * @param totalOrderCount
     */
    public OrderBillSummary(Long companyUnitId, Double totalOrderPrice, Double totalBillPrice, Long totalOrderCount) {
        this.companyUnitId = companyUnitId;
        this.totalOrderPrice = totalOrderPrice == null ? 0f : totalOrderPrice.floatValue();
        this.totalBillPrice = totalBillPrice == null ? 0f : totalBillPrice.floatValue();
        this.totalOrderCount = totalOrderCount == null ? 0 : totalOrderCount.intValue();
    }

    /**
     * 此构造方法用于
     * com.data.data.hmly.service.order.dao.OrderBillSummaryDao#summaryOrderDetailShenzhou(com.data.data.hmly.service.order.entity.OrderBillSummary, com.framework.hibernate.util.Page)
     * com.data.data.hmly.service.order.dao.OrderBillSummaryDao#summaryOrderDetailFerry(com.data.data.hmly.service.order.entity.OrderBillSummary, com.framework.hibernate.util.Page)
     * @param totalBillPrice
     * @param totalOrderCount
     */
    public OrderBillSummary(Double totalBillPrice, Long totalOrderCount) {
        this.totalBillPrice = totalBillPrice == null ? 0f : totalBillPrice.floatValue();
        this.totalOrderCount = totalOrderCount == null ? 0 : totalOrderCount.intValue();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SysUnit getCompanyUnit() {
        return companyUnit;
    }

    public void setCompanyUnit(SysUnit companyUnit) {
        this.companyUnit = companyUnit;
    }

    public Float getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(Float totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }

    public Float getTotalBillPrice() {
        return totalBillPrice;
    }

    public void setTotalBillPrice(Float totalBillPrice) {
        this.totalBillPrice = totalBillPrice;
    }

    public Float getAlreadyBillPrice() {
        return alreadyBillPrice;
    }

    public void setAlreadyBillPrice(Float alreadyBillPrice) {
        this.alreadyBillPrice = alreadyBillPrice;
    }

    public Float getNotBillPrice() {
        return notBillPrice;
    }

    public void setNotBillPrice(Float notBillPrice) {
        this.notBillPrice = notBillPrice;
    }

    public Date getBillSummaryDate() {
        return billSummaryDate;
    }

    public void setBillSummaryDate(Date billSummaryDate) {
        this.billSummaryDate = billSummaryDate;
    }

    public OrderBillType getBillType() {
        return billType;
    }

    public void setBillType(OrderBillType billType) {
        this.billType = billType;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public SysUser getSiteConfirmor() {
        return siteConfirmor;
    }

    public void setSiteConfirmor(SysUser siteConfirmor) {
        this.siteConfirmor = siteConfirmor;
    }

    public SysUser getUnitConfirmor() {
        return unitConfirmor;
    }

    public void setUnitConfirmor(SysUser unitConfirmor) {
        this.unitConfirmor = unitConfirmor;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getBillDateStart() {
        return billDateStart;
    }

    public void setBillDateStart(Date billDateStart) {
        this.billDateStart = billDateStart;
    }

    public Date getBillDateEnd() {
        return billDateEnd;
    }

    public void setBillDateEnd(Date billDateEnd) {
        this.billDateEnd = billDateEnd;
    }

    public String getBillDateStartStr() {
        return billDateStartStr;
    }

    public void setBillDateStartStr(String billDateStartStr) {
        if (StringUtils.isNotBlank(billDateStartStr)) {
            this.billDateStart = DateUtils.toDate(billDateStartStr);
        }
        this.billDateStartStr = billDateStartStr;
    }

    public String getBillDateEndStr() {
        return billDateEndStr;
    }

    public void setBillDateEndStr(String billDateEndStr) {
        if (StringUtils.isNotBlank(billDateEndStr)) {
            this.billDateEnd = DateUtils.toDate(billDateEndStr);
        }
        this.billDateEndStr = billDateEndStr;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getTotalOrderCount() {
        return totalOrderCount;
    }

    public void setTotalOrderCount(Integer totalOrderCount) {
        this.totalOrderCount = totalOrderCount;
    }

    public OrderDetailStatus getOrderDetailStatus() {
        return orderDetailStatus;
    }

    public void setOrderDetailStatus(OrderDetailStatus orderDetailStatus) {
        this.orderDetailStatus = orderDetailStatus;
    }

    public Long getCompanyUnitId() {
        return companyUnitId;
    }

    public void setCompanyUnitId(Long companyUnitId) {
        this.companyUnitId = companyUnitId;
    }

    public String getCompanyUnitName() {
        return companyUnitName;
    }

    public void setCompanyUnitName(String companyUnitName) {
        this.companyUnitName = companyUnitName;
    }

    public boolean getNotBillSummary() {
        return notBillSummary;
    }

    public void setNotBillSummary(boolean notBillSummary) {
        this.notBillSummary = notBillSummary;
    }

    public Integer getBillDays() {
        return billDays;
    }

    public void setBillDays(Integer billDays) {
        this.billDays = billDays;
    }

    public Float getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(Float refundPrice) {
        this.refundPrice = refundPrice;
    }

    public OrderBillTarget getBillTarget() {
        return billTarget;
    }

    public void setBillTarget(OrderBillTarget billTarget) {
        this.billTarget = billTarget;
    }

    public Long getRefundBillSummaryId() {
        return refundBillSummaryId;
    }

    public void setRefundBillSummaryId(Long refundBillSummaryId) {
        this.refundBillSummaryId = refundBillSummaryId;
    }

    public Float getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Float refundFee) {
        this.refundFee = refundFee;
    }

    public Float getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(Float saleAmount) {
        this.saleAmount = saleAmount;
    }

    public Float getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(Float returnAmount) {
        this.returnAmount = returnAmount;
    }

    public Float getPoundageAmount() {
        return poundageAmount;
    }

    public void setPoundageAmount(Float poundageAmount) {
        this.poundageAmount = poundageAmount;
    }

    public boolean isNotBillSummary() {
        return notBillSummary;
    }

    public Integer getRefundCount() {
        return refundCount;
    }

    public void setRefundCount(Integer refundCount) {
        this.refundCount = refundCount;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public Integer getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }

}
