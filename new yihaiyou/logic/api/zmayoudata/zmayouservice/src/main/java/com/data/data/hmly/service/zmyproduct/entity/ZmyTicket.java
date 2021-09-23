package com.data.data.hmly.service.zmyproduct.entity;

import com.framework.hibernate.util.Entity;

import javax.persistence.*;

/**
 * Created by dy on 2016/5/9.
 */

@javax.persistence.Entity
@Table(name = "zmy_ticket")
public class ZmyTicket extends Entity implements java.io.Serializable{

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private Long id;

    @Column(name = "zmy_distributorId")
    private Long distributorId;     	    //分销商编号

    @Column(name = "isTeam")
    private Integer isTeam;     			//是否团队票，1-是,2-否

    @Column(name = "marketPrice")
    private Float marketPrice;     	        //门市价

    @Column(name = "maxNum")
    private Integer maxNum;     			//最大购买量

    @Column(name = "minNum")
    private Integer minNum;     			//最小购买数量

    @Column(name = "name")
    private String name;    	            //产品名称

    @Column(name = "zmy_productId")
    private Long productId; 		        //产品编号

    @Column(name = "purchasePrice")
    private Float purchasePrice;	        //采购价

    @Column(name = "realname")
    private Integer realname; 			    //是否实名制，0-否,1-是

    @Column(name = "refundFactorage")
    private Integer refundFactorage;	    //退款手续费,单位为%

    @Column(name = "refundTimeLag")
    private Integer refundTimeLag; 		    //当refundType为3或4时才有值,若为当天则值为0

    @Column(name = "refundType")
    private Integer refundType;	            //退票类型：1-随时可退，2-不可

    @Column(name = "state")
    private Integer state;				    //状态 2-上架、3-下架

    @Column(name = "type")
    private Integer type;				    //产品类型 1-基础产品、2-高级产品

    @Column(name = "validType")
    private Integer validType;			    //生效类型	1、游玩时间生效 2、下单立即生效	3、生效时间延迟

    @Column(name = "validity")
    private Integer validity;	            //产品有效期


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    public Integer getIsTeam() {
        return isTeam;
    }

    public void setIsTeam(Integer isTeam) {
        this.isTeam = isTeam;
    }

    public Float getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Float marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public Integer getMinNum() {
        return minNum;
    }

    public void setMinNum(Integer minNum) {
        this.minNum = minNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getRealname() {
        return realname;
    }

    public void setRealname(Integer realname) {
        this.realname = realname;
    }

    public Integer getRefundFactorage() {
        return refundFactorage;
    }

    public void setRefundFactorage(Integer refundFactorage) {
        this.refundFactorage = refundFactorage;
    }

    public Integer getRefundTimeLag() {
        return refundTimeLag;
    }

    public void setRefundTimeLag(Integer refundTimeLag) {
        this.refundTimeLag = refundTimeLag;
    }

    public Integer getRefundType() {
        return refundType;
    }

    public void setRefundType(Integer refundType) {
        this.refundType = refundType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getValidType() {
        return validType;
    }

    public void setValidType(Integer validType) {
        this.validType = validType;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }
}
