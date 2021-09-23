package com.data.data.hmly.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author cjj
 * @date 2015年3月28日
 * @TODO 系统资源表
 */
@Entity
@Table(name = "sys_resource")
public class SysResource extends com.framework.hibernate.util.Entity implements java.io.Serializable {
	
	// Fields
	
	private static final long	serialVersionUID	= 1L;
	
	private Long				id;
	private String				name;						// 资源名称
	private SysMenu				sysMenu;					// 资源对应模块
	private String				resourceUrl;				// 资源链接
	private String				resourceNo;				// 资源编号 如:product_add
	private Integer				isPublic;					// 是否为公共资源
	private Boolean				updater;					// 操作人
	private Boolean				delFlag;					// 是否删除 false 未删除 1已删除
	private Integer				status;					// 是否冻结 0:未冻结 1已冻结
	private Integer				seq;						// 排序值
	private String				remark;					// 描述
	private Integer				hasRight			= 0;	// 是否拥有该权限 VO字段不注入数据库 0:无权限 1:有权限 2:公开
															
	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "resource_url")
	public String getResourceUrl() {
		return resourceUrl;
	}
	
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	
	@Column(name = "resource_no")
	public String getResourceNo() {
		return resourceNo;
	}
	
	public void setResourceNo(String resourceNo) {
		this.resourceNo = resourceNo;
	}
	
	@Column(name = "is_public")
	public Integer getIsPublic() {
		return isPublic;
	}
	
	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}
	
	@Column(name = "del_flag")
	public Boolean getDelFlag() {
		return delFlag;
	}
	
	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}
	
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "seq")
	public Integer getSeq() {
		return seq;
	}
	
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "menu_id")
	public SysMenu getSysMenu() {
		return sysMenu;
	}
	
	public void setSysMenu(SysMenu sysMenu) {
		this.sysMenu = sysMenu;
	}
	
	@Column(name = "updater")
	public Boolean getUpdater() {
		return updater;
	}
	
	public void setUpdater(Boolean updater) {
		this.updater = updater;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Transient
	public Integer getHasRight() {
		return hasRight;
	}
	
	public void setHasRight(Integer hasRight) {
		this.hasRight = hasRight;
	}
	
}