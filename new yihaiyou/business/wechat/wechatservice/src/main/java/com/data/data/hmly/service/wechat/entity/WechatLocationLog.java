package com.data.data.hmly.service.wechat.entity;

import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.user.entity.ThirdPartyUser;
import com.framework.hibernate.util.Entity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by vacuity on 15/11/19.
 */

@javax.persistence.Entity
@Table(name = "wx_location_log")
public class WechatLocationLog extends Entity implements Serializable {

    private static final long serialVersionUID = -1690906106930903058L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thirdpartUserId")
    private ThirdPartyUser thirdPartyUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "areaid")
    private TbArea tbArea;

    private String latitude;

    private String longitude;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public ThirdPartyUser getThirdPartyUser() {
        return thirdPartyUser;
    }

    public void setThirdPartyUser(ThirdPartyUser thirdPartyUser) {
        this.thirdPartyUser = thirdPartyUser;
    }

    public TbArea getTbArea() {
        return tbArea;
    }

    public void setTbArea(TbArea tbArea) {
        this.tbArea = tbArea;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
