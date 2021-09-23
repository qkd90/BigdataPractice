package com.data.data.hmly.service.entity;

import com.data.data.hmly.enums.FerryIdType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framework.hibernate.util.Entity;

import javax.persistence.*;

/**
 * Created by huangpeijie on 2016-11-21,0021.
 */
@javax.persistence.Entity
@Table(name = "ferryMember")
@JsonIgnoreProperties
public class FerryMember extends Entity implements java.io.Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String password;
    private String trueName;
    @Enumerated(EnumType.STRING)
    private FerryIdType idTypeName;
    private String idnumber;
    private String email;
    private String mobile;
    private String bankNo;
    private Boolean isReal;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public FerryIdType getIdTypeName() {
        return idTypeName;
    }

    public void setIdTypeName(FerryIdType idTypeName) {
        this.idTypeName = idTypeName;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public Boolean getIsReal() {
        return isReal;
    }

    public void setIsReal(Boolean isReal) {
        this.isReal = isReal;
    }
}
