package com.data.data.hmly.service.wechat.entity;

import com.data.data.hmly.service.wechat.entity.enums.ResType;
import com.framework.hibernate.util.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by vacuity on 15/11/19.
 */

@javax.persistence.Entity
@Table(name = "wx_resource")
public class WechatResource extends Entity implements Serializable {

    private static final long serialVersionUID = -1690906106930903058L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "resName")
    private String resName;

    @Column(name = "resType")
    @Enumerated(EnumType.STRING)
    private ResType resType;

    @Column(name = "content")
    private String content;

    @Column(name = "resObjectParam")
    private String resObjectParam;

    @Column(name = "validFlag")
    private Boolean validFlag;

    @Column(name = "createTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public ResType getResType() {
        return resType;
    }

    public void setResType(ResType resType) {
        this.resType = resType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResObjectParam() {
		return resObjectParam;
	}

	public void setResObjectParam(String resObjectParam) {
		this.resObjectParam = resObjectParam;
	}

	public Boolean getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(Boolean validFlag) {
        this.validFlag = validFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
