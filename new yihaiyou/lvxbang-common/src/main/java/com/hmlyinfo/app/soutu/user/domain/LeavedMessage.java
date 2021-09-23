package com.hmlyinfo.app.soutu.user.domain;


import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class LeavedMessage extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 留言作者A
	 */
	private long firstUser;


	/**
	 * 留言对象B
	 */
	private long secondUser;


	/**
	 * 留言内容
	 */
	private String content;


	/**
	 * 是否已读
	 */
	private String readed;

	private String replied;


	@JsonProperty
	public long getFirstUser() {
		return firstUser;
	}

	public void setFirstUser(long firstUser) {
		this.firstUser = firstUser;
	}


	@JsonProperty
	public long getSecondUser() {
		return secondUser;
	}

	public void setSecondUser(long secondUser) {
		this.secondUser = secondUser;
	}


	@JsonProperty
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	@JsonProperty
	public String getReaded() {
		return readed;
	}

	public void setReaded(String readed) {
		this.readed = readed;
	}


	@JsonProperty
	public String getReplied() {
		return replied;
	}

	public void setReplied(String replied) {
		this.replied = replied;
	}

}

