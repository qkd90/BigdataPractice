package com.data.data.hmly.service.ctripcommon.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by caiys on 2016/2/18.
 */
@Entity
@Table(name = "nctrip_access_token")
public class CtripAccessToken extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 20, unique = true, nullable = false)
    private Long id;    // 标识
    @Column(name = "aid", length = 11)
    private Integer aid;    // 联盟ID
    @Column(name = "sid", length = 11)
    private Integer sid;    // 站点ID
    @Column(name = "accessToken", length = 256)
    private String accessToken; // 访问接口用授权
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expireDate", length = 19)
    private Date expireDate;    // 授权过期时间
    @Column(name = "refreshToken", length = 256)
    private String refreshToken;    // 刷新接口用授权
    @Column(name = "errcode", length = 11)
    private Integer errcode;    // 错误编号
    @Column(name = "errmsg", length = 256)
    private String errmsg;      // 错误信息
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "execTime", length = 19)
    private Date execTime;      // 执行时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Date getExecTime() {
        return execTime;
    }

    public void setExecTime(Date execTime) {
        this.execTime = execTime;
    }
}
