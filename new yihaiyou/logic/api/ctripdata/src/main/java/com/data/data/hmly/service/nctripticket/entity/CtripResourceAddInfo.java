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
@Table(name = "nctrip_resource_addinfo")
public class CtripResourceAddInfo extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue
    @Column(name = "id", length = 20, unique = true, nullable = false)
    private Long    id;	          //  门票附加信息ID
    @Column(name = "resourceId", length = 20)
    private Long    resourceId;	  // 门票资源ID
    @Column(name = "titleCode", length = 256)
    private String  titleCode;	  //	标题code
    @Column(name = "title", length = 256)
    private String  title;		      //	描述标题
    @Column(name = "subTitleCode", length = 256)
    private String  subTitleCode;	  //	子标题code
    @Column(name = "subTitle", length = 256)
    private String  subTitle;		  //	描述子标题
    @Column(name = "isRegOrder")
    private Boolean isRegOrder;		//	是否记入订单
    @Column(name = "isShowAtReserve")
    private Boolean isShowAtReserve;	//	是否预订显示
//    @Column(name = "descDetail", length = 1024)
//    private String  descDetail;		//	描述信息
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
    private List<CtripAddInfoDetail> resourceAddInfoDetailList;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getTitleCode() {
        return titleCode;
    }

    public void setTitleCode(String titleCode) {
        this.titleCode = titleCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitleCode() {
        return subTitleCode;
    }

    public void setSubTitleCode(String subTitleCode) {
        this.subTitleCode = subTitleCode;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
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

    public List<CtripAddInfoDetail> getResourceAddInfoDetailList() {
        return resourceAddInfoDetailList;
    }

    public void setResourceAddInfoDetailList(List<CtripAddInfoDetail> resourceAddInfoDetailList) {
        this.resourceAddInfoDetailList = resourceAddInfoDetailList;
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
