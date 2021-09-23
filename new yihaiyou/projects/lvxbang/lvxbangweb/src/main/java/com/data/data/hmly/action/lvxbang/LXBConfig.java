package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.service.entity.SysSite;

import java.util.Map;

/**
 * Created by guoshijie on 2015/10/20.
 */
public class LXBConfig {

	private Long                siteId;
	private SysSite 			sysSite;
	private String              site;
	private String              title;
	private String              logoPath;
	private String              resourcePath;
	private String              resourceVersion;
	private Map<String, Object> others;


	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public SysSite getSysSite() {
		return sysSite;
	}

	public void setSysSite(SysSite sysSite) {
		this.sysSite = sysSite;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public String getResourceVersion() {
		return resourceVersion;
	}

	public void setResourceVersion(String resourceVersion) {
		this.resourceVersion = resourceVersion;
	}

	public Map<String, Object> getOthers() {
		return others;
	}

	public void setOthers(Map<String, Object> others) {
		this.others = others;
	}
}
