package com.data.data.hmly.service.order.entity;

import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.entity.enums.OrderPayType;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderWay;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by kok on 2017/8/17.
 */
@Entity
public class LvjiOrder extends com.framework.hibernate.util.Entity implements java.io.Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String orderNo;//订单编号
    private String lvjiOrderNo;//驴迹订单编号
    @ManyToOne
    @JoinColumn(name = "userid", unique = true, nullable = false, updatable = false)
    private User user;//用户编号
    private String userName;//用户姓名
    private String mobile;//手机号
    private String tplId;//模板id
    private String scenicName;//景点名称
    private Integer num;//数量
    private Float price;//订单总金额
    private Date startDate;//有效期开始时间
    private Date endDate;//有效期结束时间
    private Integer effDays;//有效天数
    private String macAddr;//mac地址
    private String url;//链接地址
    private String code;//授权码
    @Column(name = "status", nullable = true, insertable = true, updatable = true, length = 10)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;//状态
    private String productNotice;//产品须知
    private Date createTime;//创建时间
    private Date modifyTime;//修改时间
    @Enumerated(EnumType.STRING)
    @Column(name = "payType")
    private OrderPayType payType;//支付方式：ZHAOH-招行,WECHAT-微信
    @Enumerated(EnumType.STRING)
    @Column(name = "orderWay")
    private OrderWay orderWay;
    private Boolean deleteFlag = false;//删除标识
    private Date cancelHandleTime;//退单处理时间
    private Date waitTime;//支付超时时间
    private String msg;//错误信息

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

    public String getLvjiOrderNo() {
        return lvjiOrderNo;
    }

    public void setLvjiOrderNo(String lvjiOrderNo) {
        this.lvjiOrderNo = lvjiOrderNo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getTplId() {
        return tplId;
    }

    public void setTplId(String tplId) {
        this.tplId = tplId;
    }

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public Integer getEffDays() {
        return effDays;
    }

    public void setEffDays(Integer effDays) {
        this.effDays = effDays;
    }

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getProductNotice() {
        return productNotice;
    }

    public void setProductNotice(String productNotice) {
        this.productNotice = productNotice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public OrderPayType getPayType() {
        return payType;
    }

    public void setPayType(OrderPayType payType) {
        this.payType = payType;
    }

    public OrderWay getOrderWay() {
        return orderWay;
    }

    public void setOrderWay(OrderWay orderWay) {
        this.orderWay = orderWay;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getCancelHandleTime() {
        return cancelHandleTime;
    }

    public void setCancelHandleTime(Date cancelHandleTime) {
        this.cancelHandleTime = cancelHandleTime;
    }

    public Date getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Date waitTime) {
        this.waitTime = waitTime;
    }
}
