package com.data.data.hmly.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by huangpeijie on 2017-03-03,0003.
 */
@Entity
@Table(name = "bankCard")
@JsonIgnoreProperties
public class BankCard extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue
    private Long id;
    private String bankNo;
    private String holderName;
    @ManyToOne
    @JoinColumn(name = "cityId")
    private TbArea city;
    private String bankName;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    private Boolean inUse;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public TbArea getCity() {
        return city;
    }

    public void setCity(TbArea city) {
        this.city = city;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getInUse() {
        return inUse;
    }

    public void setInUse(Boolean inUse) {
        this.inUse = inUse;
    }
}
