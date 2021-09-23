package com.data.data.hmly.service.weixinh5.entity;

import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.weixinh5.entity.enums.ActivitySceneType;
import com.data.data.hmly.service.weixinh5.entity.enums.ActivityStatus;
import com.data.data.hmly.service.weixinh5.entity.enums.ActivityType;
import com.data.data.hmly.service.weixinh5.entity.enums.PromotWayType;
import com.framework.hibernate.util.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by dy on 2016/2/16.
 */
@javax.persistence.Entity
@Table(name = "activities")
public class Activities extends Entity implements Serializable{

    private static final long serialVersionUID = -1690906106930903058L;

//    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
//    `type` enum('flashsale','coupon') DEFAULT NULL COMMENT '活动类型（限时抢购，优惠券）',
//    `face_value` double(255,0) DEFAULT NULL COMMENT '优惠券面值',
//    `product_type` enum('line') DEFAULT NULL COMMENT '线路',
//    `lowest_price` double(255,0) DEFAULT NULL COMMENT '使用条件：满多少元使用，默认为空则无条件使用',
//    `number` int(11) DEFAULT NULL COMMENT '优惠券发行数量',
//    `instructions` varchar(500) DEFAULT NULL COMMENT '使用说明',
//    `status` enum('DOWN','UP') DEFAULT NULL COMMENT '状态：DOWN（下架）、UP（上架）',
//    `promotway` enum('sellersend','buyerget') DEFAULT NULL COMMENT '推广方式：卖家发放、买家领取',
//    `startTime` datetime DEFAULT NULL COMMENT '开始时间',
//    `scene` enum('attention_shop','old_buyer','ctrip_vip','customer_no_limit','buy_product','user_register','comment_product','index_self_get') DEFAULT NULL COMMENT '发放场景（关注过本店的客户、购买过本店商品的客户、携程会员、客户类型无限制、购买商品、新用户注册、点评商品、网店首页自主领取）',
//    `endTime` datetime DEFAULT NULL COMMENT '结束时间',
//    `createTime` datetime DEFAULT NULL COMMENT '创建时间',
//    `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
//    `createBy` bigint(20) DEFAULT NULL COMMENT '创建人',





    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;                                //编号

    @Column(name = "name")
    private String name;                            //活动名称

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ActivityType type;                      //活动类型

    @Column(name = "number")
    private Integer number;                         //优惠券发行数量

    @Column(name = "instructions", length= 500)
    private String instructions;                    //使用说明

    @Enumerated(EnumType.STRING)
    @Column(name = "promotway")
    private PromotWayType promotway;                //发放场景

    @Enumerated(EnumType.STRING)
    @Column(name = "scene")
    private ActivitySceneType sceneType;            //发放场景

    @Column(name = "face_value")
    private Double faceValue;                       //优惠券面值

    @Column(name = "perCounts")
    private Integer perCounts;                       //每人限领

    @Column(name = "lowest_price")
    private Double lowestPrice;                     //使用条件：满多少元使用，默认为空则无条件使用

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ActivityStatus status;                  //活动状态

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private ProductType productType;                //活动产品类型

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "startTime")
    private Date startTime;                         //活动开始时间

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "endTime")
    private Date endTime;                           //活动结束时间

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime")
    private Date createTime;                        //创建时间

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime")
    private Date updateTime;                        //修改时间


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createBy")
    private SysUser user;                           //创建人

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private SysUnit sysUnit;                        //公司

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SysUnit getSysUnit() {
        return sysUnit;
    }

    public void setSysUnit(SysUnit sysUnit) {
        this.sysUnit = sysUnit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public PromotWayType getPromotway() {
        return promotway;
    }

    public void setPromotway(PromotWayType promotway) {
        this.promotway = promotway;
    }

    public ActivitySceneType getSceneType() {
        return sceneType;
    }

    public void setSceneType(ActivitySceneType sceneType) {
        this.sceneType = sceneType;
    }

    public Double getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(Double faceValue) {
        this.faceValue = faceValue;
    }

    public Double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(Double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }


    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
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

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

    public Integer getPerCounts() {
        return perCounts;
    }

    public void setPerCounts(Integer perCounts) {
        this.perCounts = perCounts;
    }

}
