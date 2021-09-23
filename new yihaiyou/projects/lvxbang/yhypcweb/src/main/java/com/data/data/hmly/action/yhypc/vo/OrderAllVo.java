package com.data.data.hmly.action.yhypc.vo;

import java.util.List;

/**
 * Created by zzl on 2017/1/25.
 */
public class OrderAllVo {
    private Long id;
    private String name;
    private String fullName;
    private String orderNo;
    private Float unitPrice;
    private Float price;
    private String seatType;
    private String playDate;
    private String leaveDate;
    private String startDate;
    private String endDate;
    private Integer day;
    private Integer num;
    private String userName;
    private String mobile;
    private String createTime;
    private String type;
    private String status;
    private String source;
    private List<PvCodeVo> pvCodeVos;
    private List<OrderTouristVo> touristVos;
    private List<OrderAllVo> details;
    private String cover;
    private Long proId;
    private Long priceId;
    private Boolean hasComment;
    private String proType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getPlayDate() {
        return playDate;
    }

    public void setPlayDate(String playDate) {
        this.playDate = playDate;
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<PvCodeVo> getPvCodeVos() {
        return pvCodeVos;
    }

    public void setPvCodeVos(List<PvCodeVo> pvCodeVos) {
        this.pvCodeVos = pvCodeVos;
    }

    public List<OrderTouristVo> getTouristVos() {
        return touristVos;
    }

    public void setTouristVos(List<OrderTouristVo> touristVos) {
        this.touristVos = touristVos;
    }

    public List<OrderAllVo> getDetails() {
        return details;
    }

    public void setDetails(List<OrderAllVo> details) {
        this.details = details;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Long getProId() {
        return proId;
    }

    public void setProId(Long proId) {
        this.proId = proId;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public Boolean getHasComment() {
        return hasComment;
    }

    public void setHasComment(Boolean hasComment) {
        this.hasComment = hasComment;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }
}
