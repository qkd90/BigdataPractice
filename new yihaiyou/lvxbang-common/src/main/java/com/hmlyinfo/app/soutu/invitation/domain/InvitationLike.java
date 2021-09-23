package com.hmlyinfo.app.soutu.invitation.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class InvitationLike extends BaseEntity {

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
}
