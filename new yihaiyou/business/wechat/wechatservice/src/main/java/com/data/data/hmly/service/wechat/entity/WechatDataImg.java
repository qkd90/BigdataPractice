package com.data.data.hmly.service.wechat.entity;

import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.framework.hibernate.util.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by vacuity on 15/11/19.
 */

@javax.persistence.Entity
@Table(name = "wx_data_img")
public class WechatDataImg extends Entity implements Serializable {

    private static final long	serialVersionUID	= -1690906106930903058L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "childFolder")
    private String childFolder;

    @Column(name = "img_path")
    private String img_path;


    @Column(name = "updateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Column(name = "createTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private SysUser user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChildFolder() {
		return childFolder;
	}

	public void setChildFolder(String childFolder) {
		this.childFolder = childFolder;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}


	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}



}
