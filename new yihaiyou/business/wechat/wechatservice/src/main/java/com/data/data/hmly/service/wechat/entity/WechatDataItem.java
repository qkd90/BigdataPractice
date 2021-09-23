package com.data.data.hmly.service.wechat.entity;

import com.data.data.hmly.service.entity.SysUnit;
import com.framework.hibernate.util.Entity;
import com.gson.inf.MsgTypes;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "wx_data_item")
public class WechatDataItem extends Entity implements Serializable {

    private static final long	serialVersionUID	= -1690906106930903058L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    
    @Column(name = "type")
	@Enumerated(EnumType.STRING)
    private MsgTypes type;



    @Column(name = "updateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Column(name = "createTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyunit_id")
    private SysUnit company;

	@Column(name = "customServiceFlag")
	private Boolean customServiceFlag;
    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


	public SysUnit getCompany() {
		return company;
	}

	public void setCompany(SysUnit company) {
		this.company = company;
	}

	public MsgTypes getType() {
		return type;
	}

	public void setType(MsgTypes type) {
		this.type = type;
	}

    public Boolean getCustomServiceFlag() {
        return customServiceFlag;
    }

    public void setCustomServiceFlag(Boolean customServiceFlag) {
        this.customServiceFlag = customServiceFlag;
    }
}
