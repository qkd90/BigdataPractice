package com.data.data.hmly.service.common.entity;

import com.data.data.hmly.service.common.entity.enums.*;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "quantity_sales")
//@Polymorphism(type=PolymorphismType.EXPLICIT)
public class QuantitySales extends com.framework.hibernate.util.Entity implements java.io.Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -9215023448601457011L;
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;        //产品


    @Column(name = "pricetype_id")
    private Long priceTypeId;       //价格类型Id

//    @Column(name = "num_start", length = 10)
//    private Integer numStart;       //拱量起始数量
//
//    @Column(name = "num_end", length = 10)
//    private Integer numEnd;         //拱量截至数量

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private QuantityType type;            //金额、百分比


//    @Column(name = "percent", length = 10)
//    private Integer percent;       //拱量百分比
//
//    @Column(name = "money", length = 10)
//    private Integer money;         //拱量金额

    @Column(name = "flag")
    @Enumerated(EnumType.STRING)
    private QuantityFlagType flag;            //全局、公司

    @Column(name = "status", length = 10)
    private Integer status;      //有效状态

    @Column(name = "end_time", length = 10)
    private Date endTime;

    @Column(name = "start_time", length = 10)
    private Date startTime;

    @Column(name = "create_time", length = 10)
    private Date createTime;

    @Column(name = "update_time", length = 10)
    private Date updateTime;


    @Transient
    private List<QuantitySalesDetail> quantitySalesDetailList;



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


    public QuantityType getType() {
        return type;
    }

    public void setType(QuantityType type) {
        this.type = type;
    }

    public QuantityFlagType getFlag() {
        return flag;
    }

    public void setFlag(QuantityFlagType flag) {
        this.flag = flag;
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

    public QuantityType setQuantityType(String type) {

        if ("money".equals(type)) {
            return QuantityType.money;
        }
        return QuantityType.percent;
    }

    public QuantityFlagType setQuantityFlagType(String falg) {

        if ("global".equals(falg)) {
            return QuantityFlagType.global;
        }
        return QuantityFlagType.parts;
    }


    public Long getPriceTypeId() {
        return priceTypeId;
    }

    public void setPriceTypeId(Long priceTypeId) {
        this.priceTypeId = priceTypeId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public List<QuantitySalesDetail> getQuantitySalesDetailList() {
        return quantitySalesDetailList;
    }

    public void setQuantitySalesDetailList(List<QuantitySalesDetail> quantitySalesDetailList) {
        this.quantitySalesDetailList = quantitySalesDetailList;
    }
}
