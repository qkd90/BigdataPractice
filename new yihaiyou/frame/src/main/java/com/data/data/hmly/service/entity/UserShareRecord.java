package com.data.data.hmly.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by caiys on 2016/7/21.
 */
@Entity
@Table(name = "user_share_record")
public class UserShareRecord extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue
    private Long				id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time")
    private Date               createdTime;
    private Long				userId;
    @Enumerated(EnumType.STRING)
    private ShareType		shareType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ShareType getShareType() {
        return shareType;
    }

    public void setShareType(ShareType shareType) {
        this.shareType = shareType;
    }
}
