package com.hmlyinfo.app.soutu.weixin.domain;

import org.codehaus.jackson.annotate.JsonProperty;

import com.hmlyinfo.base.persistent.BaseEntity;

public class Qrcode extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3865039278606048834L;
	/**
	 * 二维码的地址
	 */
	private String name;
	/**
	 * 二维码的地址
	 */
	private String path;
	/**
	 * 二维码中含有的地址参数
	 */
	private Long sceneId;
	/**
	 * 二维码被扫描的次数
	 */
	private int scanCount;
	
	@JsonProperty
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@JsonProperty
	public Long getSceneId() {
		return sceneId;
	}
	public void setSceneId(Long scenicId) {
		this.sceneId = scenicId;
	}
	@JsonProperty
	public int getScanCount() {
		return scanCount;
	}
	public void setScanCount(int scanCount) {
		this.scanCount = scanCount;
	}
	@JsonProperty
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
