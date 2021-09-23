package com.hmlyinfo.app.soutu.invitation.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class InvitationComment extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 *
	 */
	private long invitationId;

	/**
	 *
	 */
	private long userId;

	/**
	 * 结伴帖主人id
	 */
	private long invitationUserId;

	/**
	 * 回复帖对方id
	 */
	private long exUid;

	/**
	 *
	 */
	private String comment;

	/**
	 * 对方是否已读
	 */
	private boolean isReaded;

	/**
	 *
	 */
	private boolean isReplied;

	/**
	 * 帖子主人是否已读
	 */
	private boolean isInvitationReaded;

	/**
	 *
	 */
	private boolean isInvitationReplied;


	public void setInvitationId(long invitationId) {
		this.invitationId = invitationId;
	}

	@JsonProperty
	public long getInvitationId() {
		return invitationId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@JsonProperty
	public String getComment() {
		return comment;
	}

	@JsonProperty

	public long getInvitationUserId() {
		return invitationUserId;
	}

	public void setInvitationUserId(long invitationUserId) {
		this.invitationUserId = invitationUserId;
	}

	@JsonProperty
	public boolean getIsReaded() {
		return isReaded;
	}

	public void setIsReaded(boolean isReaded) {
		this.isReaded = isReaded;
	}

	@JsonProperty
	public long getExUid() {
		return exUid;
	}

	public void setExUid(long exUid) {
		this.exUid = exUid;
	}

	@JsonProperty
	public boolean getIsInvitationReaded() {
		return isInvitationReaded;
	}

	public void setIsInvitationReaded(boolean isInvitationReaded) {
		this.isInvitationReaded = isInvitationReaded;
	}

	@JsonProperty
	public boolean getIsReplied() {
		return isReplied;
	}

	public void setIsReplied(boolean isReplied) {
		this.isReplied = isReplied;
	}

	@JsonProperty
	public boolean getIsInvitationReplied() {
		return isInvitationReplied;
	}

	public void setIsInvitationReplied(boolean isInvitationReplied) {
		this.isInvitationReplied = isInvitationReplied;
	}
}
