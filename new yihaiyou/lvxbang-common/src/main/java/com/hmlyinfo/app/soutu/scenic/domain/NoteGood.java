package com.hmlyinfo.app.soutu.scenic.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by guoshijie on 2014/8/25.
 */
public class NoteGood extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Long noteImageId;
	private Long userId;

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
}
