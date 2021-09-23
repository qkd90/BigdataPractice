package com.hmlyinfo.app.soutu.weixin.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class Graphic extends BaseEntity{

	// myltiple代表多图，single代表单图
	public static String TYPE_SINGLE ="1";
	public static String TYPE_MULTIPLE ="2";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 图片路径
	 */
	private String imagePath;
	/**
	 * 正文描述
	 */
	private String description;
	/**
	 * 链接
	 */
	private String url;
	/**
	 * 图文类型，单条/多条
	 */
	private String type;
	/**
	 * 如果是多条类型，其父ID
	 */
	private Long fatherId;
	/**
	 * 如果是多条类型，其副图文
	 */
	private String idStr;

	private List<Graphic> childGraphics;
	
	@JsonProperty
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@JsonProperty
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	@JsonProperty
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@JsonProperty
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@JsonProperty
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@JsonProperty
	public Long getFatherId() {
		return fatherId;
	}
	public void setFatherId(Long fatherId) {
		this.fatherId = fatherId;
	}
	@JsonProperty
	public List<Graphic> getChildGraphics() {
		return childGraphics;
	}
	public void setChildGraphics(List<Graphic> childGraphics) {
		this.childGraphics = childGraphics;
	}

	public String getIdStr() {
		return idStr;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}
}
