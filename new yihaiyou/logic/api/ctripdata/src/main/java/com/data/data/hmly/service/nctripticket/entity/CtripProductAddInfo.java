package com.data.data.hmly.service.nctripticket.entity;

import com.data.data.hmly.service.ctripcommon.enums.RowStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * Created by caiys on 2016/1/25.
 */
@Entity
@Table(name = "nctrip_product_addinfo")
public class CtripProductAddInfo extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue
    @Column(name = "id", length = 20, unique = true, nullable = false)
    private Long id;	            //  ID
    @Column(name = "productId", length = 20)
    private Long productId;	            //  产品ID
    @Column(name = "addInfoTitleName", length = 256)
    private String addInfoTitleName;		  //	产品附加信息标题名称
    @Column(name = "addInfoTitleCode", length = 256)
    private String addInfoTitleCode;		  //	产品附加信息标题名称ID
    @Column(name = "addInfoSubTitleName", length = 256)
    private String addInfoSubTitleName;		//	产品附加信息子名称
    @Column(name = "addInfoSubTitleCode", length = 256)
    private String addInfoSubTitleCode;		//	产品附加信息子标题ID
    @Column(name = "isRegOrder")
    private Boolean isRegOrder;		      //	是否记入订单
    @Column(name = "isShowAtReserve")
    private Boolean isShowAtReserve;		  //	是否预订时显示
//    @Column(name = "descDetail", length = 1024)
//    private String descDetail;              //    门票产品附加信息明细
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime", length = 19)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime", length = 19)
    private Date updateTime;
    @Column(name = "rowStatus")
    @Enumerated(EnumType.STRING)
    private RowStatus rowStatus;

    @Transient
    private List<CtripAddInfoDetail> productAddInfoDetailList;

    public String getAddInfoTitleName() {
        return addInfoTitleName;
    }

    public void setAddInfoTitleName(String addInfoTitleName) {
        this.addInfoTitleName = addInfoTitleName;
    }

    public String getAddInfoTitleCode() {
        return addInfoTitleCode;
    }

    public void setAddInfoTitleCode(String addInfoTitleCode) {
        this.addInfoTitleCode = addInfoTitleCode;
    }

    public String getAddInfoSubTitleName() {
        return addInfoSubTitleName;
    }

    public void setAddInfoSubTitleName(String addInfoSubTitleName) {
        this.addInfoSubTitleName = addInfoSubTitleName;
    }

    public String getAddInfoSubTitleCode() {
        return addInfoSubTitleCode;
    }

    public void setAddInfoSubTitleCode(String addInfoSubTitleCode) {
        this.addInfoSubTitleCode = addInfoSubTitleCode;
    }

    public Boolean getIsRegOrder() {
        return isRegOrder;
    }

    public void setIsRegOrder(Boolean isRegOrder) {
        this.isRegOrder = isRegOrder;
    }

    public Boolean getIsShowAtReserve() {
        return isShowAtReserve;
    }

    public void setIsShowAtReserve(Boolean isShowAtReserve) {
        this.isShowAtReserve = isShowAtReserve;
    }

    public List<CtripAddInfoDetail> getProductAddInfoDetailList() {
        return productAddInfoDetailList;
    }

    public void setProductAddInfoDetailList(List<CtripAddInfoDetail> productAddInfoDetailList) {
        this.productAddInfoDetailList = productAddInfoDetailList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public RowStatus getRowStatus() {
        return rowStatus;
    }

    public void setRowStatus(RowStatus rowStatus) {
        this.rowStatus = rowStatus;
    }
}
