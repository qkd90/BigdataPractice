package com.hmlyinfo.app.soutu.scenic.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by guoshijie on 2014/8/25.
 */
public class NoteComment extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Long noteId;
	private Long noteImageId;
	private Long userId;
	private Long replyTo;
	private String content;

	@JsonProperty
	public Long getNoteId() {
		return noteId;
	}

	public void setNoteId(Long noteId) {
		this.noteId = noteId;
	}

	@JsonProperty
	public Long getNoteImageId() {
		return noteImageId;
	}

	public void setNoteImageId(Long noteImageId) {
		this.noteImageId = noteImageId;
	}

	@JsonProperty
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public Long getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(Long replyTo) {
		this.replyTo = replyTo;
	}

	@JsonProperty
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
