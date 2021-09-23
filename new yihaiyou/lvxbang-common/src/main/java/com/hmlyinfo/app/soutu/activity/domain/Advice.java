package com.hmlyinfo.app.soutu.activity.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

public class Advice extends BaseEntity {

	private static final long serialVersionUID = 1L;


	/**
	 * 用户id
	 */
	private long userId;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 内容
	 */
	private String content;

	/**
	 *
	 */
	private boolean reply;
	/**
	 * 采纳
	 */
	private boolean accept;


	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty
	public String getTitle() {
		return title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonProperty
	public String getContent() {
		return content;
	}

	public void setReply(boolean reply) {
		this.reply = reply;
	}

	@JsonProperty
	public boolean getReply() {
		return reply;
	}

	public boolean isAccept() {
		return accept;
	}

	public void setAccept(boolean accept) {
		this.accept = accept;
	}

	@JsonProperty
	public Date createtime() {
		return super.getCreateTime();
	}
}
