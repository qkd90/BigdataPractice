package com.data.data.hmly.service.order.entity;

import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * 仅用于查询使用
 * Created by caiys on 2017/1/17.
 */
@Entity
@Table(name = "v_order_all")
public class OrderAll extends com.framework.hibernate.util.Entity {
    @Id
    @Column(name = "orderNo")
    private String orderNo;
    @Column(name = "id")
    private Long id;
    @Column(name = "orderType")
    @Enumerated(EnumType.STRING)
    private OrderType orderType;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(name = "name")
    private String name;
    @Column(name = "num")
    private Integer num;
    @Column(name = "playDate")
    @Temporal(TemporalType.DATE)
    private Date playDate;
    @Column(name = "leaveDate")
    @Temporal(TemporalType.DATE)
    private Date leaveDate;
    @Column(name = "seatType")
    private String seatType;
    @Column(name = "price")
    private Float price;
    @Column(name = "hasComment")
    private Boolean hasComment;
    @Column(name = "startDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "endDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "createTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "deleteFlag")
    private Boolean deleteFlag;
    @Column(name = "userid")
    private Long userid;
    @Column(name = "subStatus")
    private String subStatus;   // 其他子状态
    @Column(name = "companyUnitId")
    private Long companyUnitId;
    @Column(name = "departTime")
    private String departTime;
    @Column(name = "flightNumber")
    private String flightNumber;
    @Column(name = "source")
    @Enumerated(EnumType.STRING)
    private ProductSource source;   // 产品来源
    @Column(name = "day")
    private Integer day;
    @Column(name = "startName")
    private String startName;
    @Column(name = "endName")
    private String endName;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "userName")
    private String userName;
    @Column(name = "companyUnitName")
    private String companyUnitName;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getPlayDate() {
        return playDate;
    }

    public void setPlayDate(Date playDate) {
        this.playDate = playDate;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Boolean getHasComment() {
        return hasComment;
    }

    public void setHasComment(Boolean hasComment) {
        this.hasComment = hasComment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ProductSource getSource() {
        return source;
    }

    public void setSource(ProductSource source) {
        this.source = source;
    }

    public Long getCompanyUnitId() {
        return companyUnitId;
    }

    public void setCompanyUnitId(Long companyUnitId) {
        this.companyUnitId = companyUnitId;
    }

    public String getDepartTime() {
        return departTime;
    }

    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getStartName() {
        return startName;
    }

    public void setStartName(String startName) {
        this.startName = startName;
    }

    public String getEndName() {
        return endName;
    }

    public void setEndName(String endName) {
        this.endName = endName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompanyUnitName() {
        return companyUnitName;
    }

    public void setCompanyUnitName(String companyUnitName) {
        this.companyUnitName = companyUnitName;
    }

    public String getStatusStr() {
        if (status != null) {
            return status.getDescription();
        }
        return null;
    }
}
