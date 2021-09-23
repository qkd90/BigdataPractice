package com.data.data.hmly.service.common.entity;

import com.data.data.hmly.service.entity.User;
import com.framework.hibernate.util.Entity;
import com.zuipin.util.DateUtils;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2016/12/22.
 */
@javax.persistence.Entity
@Table(name = "product_validate_code")
public class ProValidCode extends Entity implements Serializable {
    /**
     * `id` bigint(255) NOT NULL AUTO_INCREMENT,
     `order_id` bigint(20) DEFAULT NULL COMMENT '订单标识ID',
     `order_detail_id` bigint(20) DEFAULT NULL COMMENT '订单详情标识ID',
     `code` varchar(255) DEFAULT NULL COMMENT '验证码',
     `used` int(10) DEFAULT NULL COMMENT '使用情况：0->未使用，1->已使用，-1->无效',
     `buyer_name` varchar(255) DEFAULT NULL COMMENT '使用者姓名',
     `buyer_mobile` varchar(255) DEFAULT NULL COMMENT '使用者手机',
     `buyer_id` bigint(20) DEFAULT NULL COMMENT '使用者标识ID',
     `order_num` varchar(255) DEFAULT NULL COMMENT '订单编号',
     `count` int(11) DEFAULT NULL COMMENT '数量',
     `supplier_id` bigint(20) DEFAULT NULL COMMENT '供应商标识ID',
     `supplier_name` varchar(255) DEFAULT NULL COMMENT '供应商名称',
     `product_name` varchar(255) DEFAULT NULL COMMENT '产品名称',
     `product_type_name` varchar(255) DEFAULT NULL COMMENT '产品类型名称',
     `validate_by` bigint(20) DEFAULT NULL COMMENT '验证人标识ID',
     `valid_time` datetime DEFAULT NULL COMMENT '有效时间始',
     `invalid_time` datetime DEFAULT NULL COMMENT '有效时间末',
     `create_time` datetime DEFAULT NULL,
     `update_time` datetime DEFAULT NULL,
     */

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "order_detail_id")
    private Long orderDetailId;
    @Column(name = "code")
    private String code;
    @Column(name = "used")
    private Integer used;
    @Column(name = "buyer_name")
    private String buyerName;
    @Column(name = "buyer_mobile")
    private String buyerMobile;
    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;
    @Column(name = "order_no")
    private String orderNo;
    @Column(name = "count")
    private Integer count;
    @Column(name = "supplier_id")
    private Long supplierId;
    @Column(name = "supplier_name")
    private String supplierName;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_type_name")
    private String productTypeName;
    @ManyToOne
    @JoinColumn(name = "validate_by")
    private User validateUser;

    @Column(name = "valid_time")
    private Date validTime;
    @Column(name = "invalid_time")
    private Date invalidTime;

    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;


    @Transient
    private List<ProValidCode> proValidCodeList;

    @Transient
    private String orderDetailStatus;
    @Transient
    private Float totalPrice;

    @Transient
    private Integer unUsedNum;
    @Transient
    private Integer totalNum;

    @Transient
    private Date orderCreateTime;

    @Transient
    private String searchKeyword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerMobile() {
        return buyerMobile;
    }

    public void setBuyerMobile(String buyerMobile) {
        this.buyerMobile = buyerMobile;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public User getValidateUser() {
        return validateUser;
    }

    public void setValidateUser(User validateUser) {
        this.validateUser = validateUser;
    }

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
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

    public List<ProValidCode> getProValidCodeList() {
        return proValidCodeList;
    }

    public void setProValidCodeList(List<ProValidCode> proValidCodeList) {
        this.proValidCodeList = proValidCodeList;
    }


    public String getOrderDetailStatus() {
        return orderDetailStatus;
    }

    public void setOrderDetailStatus(String orderDetailStatus) {
        this.orderDetailStatus = orderDetailStatus;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getUnUsedNum() {
        return unUsedNum;
    }

    public void setUnUsedNum(Integer unUsedNum) {
        this.unUsedNum = unUsedNum;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    /**
     * 根据数量字段同步验证码状态：1已使用，0未使用，-1无效
     * 1.有效期不为空且已过期：如果可验票数为零且有使用，状态为已使用；否则，状态为无效
     * 1.可验票数为零时：如果全部退款，状态为无效；如果有使用，状态为已使用
     * 2.可验票数不为零时：状态为未使用
     * @param endTime
     */
    public void syncUsed(Date endTime) {
        if (endTime != null) {
            Long endLong = DateUtils.getDateDiffLong(endTime, new Date());
            if (endLong > 0 || endLong == 0) {
                this.setUsed(1);
            } else {
                this.setUsed(-1);
            }

        }
    }

}
