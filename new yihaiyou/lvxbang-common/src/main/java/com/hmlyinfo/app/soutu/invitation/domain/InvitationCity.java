package com.hmlyinfo.app.soutu.invitation.domain;

import com.hmlyinfo.base.persistent.BaseEntity;

public class InvitationCity extends BaseEntity {

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
	private long cityId;


	public void setInvitationId(long invitationId) {
		this.invitationId = invitationId;
	}

	public long getInvitationId() {
		return invitationId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public long getCityId() {
		return cityId;
	}
}
