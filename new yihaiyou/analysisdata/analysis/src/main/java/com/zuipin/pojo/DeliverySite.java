package com.zuipin.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zuipin.service.entity.SysSite;

/**
 * @author cjj
 * @date 2015年5月13日
 * @TODO 配送点
 */
@Entity
@Table(name = "delivery_site")
public class DeliverySite extends com.framework.hibernate.util.Entity implements java.io.Serializable {
	
	// Fields
	
	private static final long	serialVersionUID	= 1L;
	private Long				id;
	private String				dsName;					// 配送点名称
	private String				dsAddress;					// 配送点地址
	private String				linkMan;					// 配送点联系人
	private String				linkPhone;					// 联系手机
	private String				linkTel;					// 联系电话
	private String				linkFax;					// 传真
	private Integer				isDefault;					// 是否为默认配送点
	private Integer				seq;						// 排序号
	private SysSite				sysSite;					// 所属站点
															
	public DeliverySite() {
		
	}
	
	public DeliverySite(Long id, String dsName) {
		this.id = id;
		this.dsName = dsName;
	}
	
	public DeliverySite(Long siteId) {
		this.id = siteId;
	}
	
	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "dsName")
	public String getDsName() {
		return dsName;
	}
	
	public void setDsName(String dsName) {
		this.dsName = dsName;
	}
	
	@Column(name = "dsAddress")
	public String getDsAddress() {
		return dsAddress;
	}
	
	public void setDsAddress(String dsAddress) {
		this.dsAddress = dsAddress;
	}
	
	@Column(name = "linkMan")
	public String getLinkMan() {
		return linkMan;
	}
	
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	
	@Column(name = "linkPhone")
	public String getLinkPhone() {
		return linkPhone;
	}
	
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	
	@Column(name = "linkTel")
	public String getLinkTel() {
		return linkTel;
	}
	
	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}
	
	@Column(name = "linkFax")
	public String getLinkFax() {
		return linkFax;
	}
	
	public void setLinkFax(String linkFax) {
		this.linkFax = linkFax;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "siteId")
	public SysSite getSysSite() {
		return sysSite;
	}
	
	public void setSysSite(SysSite sysSite) {
		this.sysSite = sysSite;
	}
	
	@Column(name = "seq")
	public Integer getSeq() {
		return seq;
	}
	
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	@Column(name = "isDefault")
	public Integer getIsDefault() {
		return isDefault;
	}
	
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	
}