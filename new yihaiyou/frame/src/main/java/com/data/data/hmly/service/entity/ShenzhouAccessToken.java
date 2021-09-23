package com.data.data.hmly.service.entity;

import com.data.data.hmly.enums.ShenzhouAccessTokenStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by huangpeijie on 2016-10-07,0007.
 */
@Entity
@Table(name = "shenzhouAccessToken")
@JsonIgnoreProperties
public class ShenzhouAccessToken extends com.framework.hibernate.util.Entity implements java.io.Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "accessToken")
    private String accessToken;
    @Column(name = "accessTokenDate")
    private Date accessTokenDate;
    @Column(name = "refreshToken")
    private String refreshToken;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ShenzhouAccessTokenStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getAccessTokenDate() {
        return accessTokenDate;
    }

    public void setAccessTokenDate(Date accessTokenDate) {
        this.accessTokenDate = accessTokenDate;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public ShenzhouAccessTokenStatus getStatus() {
        return status;
    }

    public void setStatus(ShenzhouAccessTokenStatus status) {
        this.status = status;
    }
}
