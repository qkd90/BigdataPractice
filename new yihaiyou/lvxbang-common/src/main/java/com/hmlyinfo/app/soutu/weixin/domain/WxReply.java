package com.hmlyinfo.app.soutu.weixin.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class WxReply extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private String keyword;

	/**
	 * 1纯文字，2图文，3图文列表
	 */
	private int type;

	/**
	 *
	 */
	private String content;

	/**
	 *
	 */
	private String title;

	/**
	 *
	 */
	private String description;

	/**
	 * 完整图片地址
	 */
	private String img;

	/**
	 *
	 */
	private String url;

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@JsonProperty
	public String getKeyword() {
		return keyword;
	}

	public void setType(int type) {
		this.type = type;
	}

	@JsonProperty
	public int getType() {
		return type;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonProperty
	public String getContent() {
		return content;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty
	public String getTitle() {
		return title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty
	public String getDescription() {
		return description;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@JsonProperty
	public String getImg() {
		return img;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@JsonProperty
	public String getUrl() {
		return url;
	}
}
