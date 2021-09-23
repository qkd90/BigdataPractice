package com.data.data.hmly.service.outOrder.entity;

import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderDetailStatus;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderStatus;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderType;
import com.data.data.hmly.service.outOrder.entity.enums.SourceType;
import com.framework.hibernate.util.Entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2016/2/23.
 */
@javax.persistence.Entity
@Table(name = "jszx_order")
public class JszxOrder extends Entity implements java.io.Serializable{


//    `id` bigint(20) NOT NULL AUTO_INCREMENT,
//    `orderNo` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '订单编号',
//            `proType` enum('scenic','restaurant','hotel','line','train','recharge','flight') CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '销售对象类型',
//            `productId` bigint(20) DEFAULT NULL COMMENT '产品编号',
//            `total_price` float(10,0) DEFAULT NULL COMMENT '总价',
//            `count` int(11) DEFAULT NULL,
//    `source` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '产品来源',
//            `status` enum('CANCELED','UNPAY','PAYED','UNCANCEL') CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '''CANCELED-已取消'',''UNPAY-待付款'',''PAYED-已付款'',''UNCANCEL-待取消''',
//            `contact` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '联系人',
//            `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '联系手机',
//            `createTime` datetime DEFAULT NULL,
//            `updateTime` datetime DEFAULT NULL,
//            `createBy` bigint(255) DEFAULT NULL COMMENT '操作人',
//            `company_id` bigint(20) DEFAULT NULL COMMENT '公司',
//            `valid_day` int(11) DEFAULT NULL,


    public JszxOrder() {
    }

    public JszxOrder(String proName, Product product, Float actualPayPrice, Integer count) {
        this.proName = proName;
        this.product = product;
        this.actualPayPrice = actualPayPrice;
        this.count = count;
    }

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private Long id;

    @Column(name = "orderNo")
    private String orderNo;

    @Column(name = "order_source_id")
    private Long orderSourceId;

    @Column(name = "order_source_type")
    @Enumerated(EnumType.STRING)
    private SourceType sourceType;

    @Column(name = "proName")
    private String proName;

    @Column(name = "proType")
    @Enumerated(EnumType.STRING)
    private ProductType proType;

    @Column(name = "jszx_order_type")
    @Enumerated(EnumType.STRING)
    private JszxOrderType jszxOrderType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private JszxOrderStatus status;

    @Column(name = "source")
    private String source;

    @Column(name = "count")
    private Integer count;

    @Column(name = "total_price")
    private Float totalPrice;


    @Column(name = "quantity_total_price")
    private Float quantityTotalPrice;

    @Column(name = "actual_payment")
    private Float actualPayPrice;

    @Column(name = "contact")
    private String contact;

    @Column(name = "phone")
    private String phone;

    @Column(name = "idcard")
    private String idcard;

    @Column(name = "updateTime")
    private Date updateTime;

    @Column(name = "valid_day")
    private Integer validDay;

    @Column(name = "msg_count")
    private Integer msgCount;


    @Column(name = "confirm")
    private Integer isConfirm;

    @Column(name = "createTime")
    private Date createTime;

    @ManyToOne
    @JoinColumn(name = "createBy")
    private SysUser user;


    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private SysUnit supplierUnit;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private SysUnit companyUnit;


    @Transient
    private String code;

    @Transient
    private Long supplierId;

    @Transient
    private Long companyId;

    @Transient
    private String supplierName;

    @Transient
    private String supplierMobile;

    @Transient
    private String companyName;

    @Transient
    private String companyMobile;

    @Transient
    private String userAccount;

    @Transient
    private Integer btnStatus;

    @Transient
    private JszxOrderDetailStatus detailUseStatus;

    @Transient
    private String jszxOrderDetailName;

    @Transient
    private Integer showRefund;

    @Transient
    private List<JszxOrderDetail> jszxOrderDetailList;

    @Transient
    private Date endCreateTime;

    @Transient
    private Date startCreateTime;

    @Transient
    private Date endUseTime;

    @Transient
    private Date startUseTime;

    @Transient
    private Date startCheckoutTime;
    @Transient
    private Date endCheckoutTime;

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getBtnStatus() {
        return btnStatus;
    }

    public void setBtnStatus(Integer btnStatus) {
        this.btnStatus = btnStatus;
    }

    public JszxOrderStatus getStatus() {
        return status;
    }

    public void setStatus(JszxOrderStatus status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public ProductType getProType() {
        return proType;
    }

    public void setProType(ProductType proType) {
        this.proType = proType;
    }

    public JszxOrderType getJszxOrderType() {
        return jszxOrderType;
    }

    public void setJszxOrderType(JszxOrderType jszxOrderType) {
        this.jszxOrderType = jszxOrderType;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public SysUnit getCompanyUnit() {
        return companyUnit;
    }

    public void setCompanyUnit(SysUnit companyUnit) {
        this.companyUnit = companyUnit;
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

    public Integer getValidDay() {
        return validDay;
    }

    public void setValidDay(Integer validDay) {
        this.validDay = validDay;
    }

    public Integer getShowRefund() {
        return showRefund;
    }

    public void setShowRefund(Integer showRefund) {
        this.showRefund = showRefund;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public SysUnit getSupplierUnit() {
        return supplierUnit;
    }

    public void setSupplierUnit(SysUnit supplierUnit) {
        this.supplierUnit = supplierUnit;
    }

    public String getSupplierName() {
        if (getSupplierUnit() != null ) {
            return getSupplierUnit().getName();
        }
        return supplierName;
    }

    public Integer getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(Integer isConfirm) {
        this.isConfirm = isConfirm;
    }

    public Long getSupplierId() {

        if (getSupplierUnit() != null ) {
            return getSupplierUnit().getId();
        }

        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getCompanyId() {

        if (getCompanyUnit() != null ) {
            return getCompanyUnit().getId();
        }

        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {

        if (getCompanyUnit() != null ) {
            return getCompanyUnit().getName();
        }

        return companyName;
    }



    public String getUserAccount() {

        if (user != null ) {
            return user.getAccount();
        }

        return userAccount;
    }

    public String getSupplierMobile() {

        if (supplierUnit != null && supplierUnit.getSysUnitDetail() != null) {
            return supplierUnit.getSysUnitDetail().getMobile();
        }

        return supplierMobile;
    }

    public String getCompanyMobile() {

        if (companyUnit != null && companyUnit.getSysUnitDetail() != null) {
            return companyUnit.getSysUnitDetail().getMobile();
        }

        return companyMobile;
    }

    public List<JszxOrderDetail> getJszxOrderDetailList() {
        return jszxOrderDetailList;
    }

    public void setJszxOrderDetailList(List<JszxOrderDetail> jszxOrderDetailList) {
        this.jszxOrderDetailList = jszxOrderDetailList;
    }

    public Integer getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(Integer msgCount) {
        this.msgCount = msgCount;
    }


    public Float getActualPayPrice() {
        return actualPayPrice;
    }

    public void setActualPayPrice(Float actualPayPrice) {
        this.actualPayPrice = actualPayPrice;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }


    public Float getQuantityTotalPrice() {
        return quantityTotalPrice;
    }

    public void setQuantityTotalPrice(Float quantityTotalPrice) {
        this.quantityTotalPrice = quantityTotalPrice;
    }

    public JszxOrderDetailStatus getDetailUseStatus() {
        return detailUseStatus;
    }

    public void setDetailUseStatus(JszxOrderDetailStatus detailUseStatus) {
        this.detailUseStatus = detailUseStatus;
    }


    public String getJszxOrderDetailName() {
        return jszxOrderDetailName;
    }

    public void setJszxOrderDetailName(String jszxOrderDetailName) {
        this.jszxOrderDetailName = jszxOrderDetailName;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public Date getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(Date startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public Date getEndUseTime() {
        return endUseTime;
    }

    public void setEndUseTime(Date endUseTime) {
        this.endUseTime = endUseTime;
    }

    public Date getStartUseTime() {
        return startUseTime;
    }

    public void setStartUseTime(Date startUseTime) {
        this.startUseTime = startUseTime;
    }

    public Date getStartCheckoutTime() {
        return startCheckoutTime;
    }

    public void setStartCheckoutTime(Date startCheckoutTime) {
        this.startCheckoutTime = startCheckoutTime;
    }

    public Date getEndCheckoutTime() {
        return endCheckoutTime;
    }

    public void setEndCheckoutTime(Date endCheckoutTime) {
        this.endCheckoutTime = endCheckoutTime;
    }

    public Long getOrderSourceId() {
        return orderSourceId;
    }

    public void setOrderSourceId(Long orderSourceId) {
        this.orderSourceId = orderSourceId;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }
}

