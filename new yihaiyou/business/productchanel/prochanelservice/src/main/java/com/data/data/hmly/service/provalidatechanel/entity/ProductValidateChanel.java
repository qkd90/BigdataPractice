package com.data.data.hmly.service.provalidatechanel.entity;

import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.provalidatechanel.entity.enums.ChanelType;
import com.framework.hibernate.util.Entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by dy on 2016/9/2.
 */
@javax.persistence.Entity
@Table(name = "product_validate_channel")
public class ProductValidateChanel extends Entity {

/*    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标识',
    `product_id` bigint(20) DEFAULT NULL COMMENT '产品标识',
    `proType` enum('plan','line','scenic','train','flight','hotel','recplan','restaurant','ship','delicacy') COLLATE utf8_bin DEFAULT NULL COMMENT '销售对象类型',
    `channel` enum('CTRIP','LXB','ZYB') COLLATE utf8_bin DEFAULT NULL COMMENT '验证通道：（CTRIP：携程，LXB：旅行帮，ZYB：智游宝）',
    `company_id` bigint(20) DEFAULT NULL COMMENT '所属公司',
    `user_id` bigint(20) DEFAULT NULL COMMENT '操作用户',
    `createTime` datetime DEFAULT NULL,
    `updateTime` datetime DEFAULT NULL,*/

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "proType")
    private ProductType proType;

    @Column(name = "channel")
    @Enumerated(EnumType.STRING)
    private ChanelType chanel;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private SysUnit company;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private SysUser user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime")
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime")
    private Date updateTime;


    @Transient
    private String updateTimeStartStr;
    @Transient
    private String updateTimeEndStr;


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

    public ProductType getProType() {
        return proType;
    }

    public void setProType(ProductType proType) {
        this.proType = proType;
    }

    public ChanelType getChanel() {
        return chanel;
    }

    public void setChanel(ChanelType chanel) {
        this.chanel = chanel;
    }

    public SysUnit getCompany() {
        return company;
    }

    public void setCompany(SysUnit company) {
        this.company = company;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
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

    public String getUpdateTimeStartStr() {
        return updateTimeStartStr;
    }

    public void setUpdateTimeStartStr(String updateTimeStartStr) {
        this.updateTimeStartStr = updateTimeStartStr;
    }

    public String getUpdateTimeEndStr() {
        return updateTimeEndStr;
    }

    public void setUpdateTimeEndStr(String updateTimeEndStr) {
        this.updateTimeEndStr = updateTimeEndStr;
    }
}
