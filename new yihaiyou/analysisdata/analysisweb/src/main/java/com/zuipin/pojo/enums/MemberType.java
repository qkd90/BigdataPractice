package com.zuipin.pojo.enums;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author cjj
 * @date 2014年8月20日
 * @TODO 用户来源/用户类别
 */
@Entity
@Table(name = "member_type")
public class MemberType extends com.framework.hibernate.util.Entity implements java.io.Serializable {
	
	// Fields
	
	private static final long	serialVersionUID	= -6421897910894894286L;
	private Long				id;
	private String				typeCode;
	private String				typeName;
	private Long				parentId;
	private Boolean				isClosed;
	private Long				opid;
	private Date				opdate;
	private Long				modid;
	private Date				moddate;
	private Boolean				isClient;
	private Integer				sempId;
	
	public MemberType() {
		super();
	}
	
	public MemberType(Long id) {
		super();
		this.id = id;
	}
	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "TYPE_CODE")
	public String getTypeCode() {
		return typeCode;
	}
	
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
	@Column(name = "TYPE_NAME")
	public String getTypeName() {
		return typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	@Column(name = "PARENT_ID")
	public Long getParentId() {
		return parentId;
	}
	
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	@Column(name = "IS_CLOSED")
	public Boolean getIsClosed() {
		return isClosed;
	}
	
	public void setIsClosed(Boolean closed) {
		this.isClosed = closed;
	}
	
	@Column(name = "OP_ID")
	public Long getOpid() {
		return opid;
	}
	
	public void setOpid(Long opid) {
		this.opid = opid;
	}
	
	@Column(name = "OP_DATE")
	public Date getOpdate() {
		return opdate;
	}
	
	public void setOpdate(Date opdate) {
		this.opdate = opdate;
	}
	
	@Column(name = "MOD_ID")
	public Long getModid() {
		return modid;
	}
	
	public void setModid(Long modid) {
		this.modid = modid;
	}
	
	@Column(name = "MOD_DATE")
	public Date getModdate() {
		return moddate;
	}
	
	public void setModdate(Date moddate) {
		this.moddate = moddate;
	}
	
	@Column(name = "IS_CLIENT")
	public Boolean getIsClient() {
		return isClient;
	}
	
	public void setIsClient(Boolean isclient) {
		this.isClient = isclient;
	}
	
	@Column(name = "SEMP_ID")
	public Integer getSempId() {
		return sempId;
	}
	
	public void setSempId(Integer sempId) {
		this.sempId = sempId;
	}
	
}