package com.data.data.hmly.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author cjj
 * @date 2015年3月28日
 * @TODO 站点
 */
@Entity
@Table(name = "sys_site")
public class SysSite extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;

	private Long id;
	private String sitename; // 站点名称
	private String siteurl; // 站点地址
	private SiteStatus status;
	private TbArea area;
	private String logoImg;		//logo地址
	private String smsUid;
	private String smsKey;
	private String homePage;		// 首页地址

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "sitename")
	public String getSitename() {
		return sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}

	@Column(name = "siteurl")
	public String getSiteurl() {
		return siteurl;
	}

	public void setSiteurl(String siteurl) {
		this.siteurl = siteurl;
	}

	@Enumerated(EnumType.STRING)
	public SiteStatus getStatus() {
		return status;
	}

	public void setStatus(SiteStatus status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city")
	public TbArea getArea() {
		return area;
	}

	public void setArea(TbArea area) {
		this.area = area;
	}

	@Column(name = "logo_img")
	public String getLogoImg() {
		return logoImg;
	}

	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}

	@Column(name = "smsUid")
	public String getSmsUid() {
		return smsUid;
	}

	public void setSmsUid(String smsUid) {
		this.smsUid = smsUid;
	}

	@Column(name = "smsKey")
	public String getSmsKey() {
		return smsKey;
	}

	public void setSmsKey(String smsKey) {
		this.smsKey = smsKey;
	}

	@Column(name = "homePage")
	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}
}