package com.data.data.hmly.service.nctripticket.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "nctrip_order_contact_info")
public class CtripOrderContactInfo extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue
    @Column(name = "id", length = 20)
    private Long        id;		          // 标识
    @Column(name = "orderFormInfoId", length = 20)
    private Long        orderFormInfoId;		          // 订单标识
    @Column(name = "name", length = 32)
    private String name;        // （必填）联系人姓名
    @Column(name = "tel", length = 32)
    private String tel;         // 联系人电话
    @Column(name = "fax", length = 32)
    private String fax;	     // 联系人传真
    @Column(name = "mobile", length = 32)
    private String mobile;      // （必填）联系人手机
    @Column(name = "email", length = 256)
    private String email;       // 联系人邮箱
    @Column(name = "address", length = 256)
    private String address;     // 联系人地址

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderFormInfoId() {
        return orderFormInfoId;
    }

    public void setOrderFormInfoId(Long orderFormInfoId) {
        this.orderFormInfoId = orderFormInfoId;
    }

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("Tel")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @JsonProperty("Fax")
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @JsonProperty("Mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @JsonProperty("Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("Address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
