package com.hmlyinfo.app.soutu.invitation.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class InvitationImg extends BaseEntity {

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
	private String urlSmall;

	/**
	 *
	 */
	private String urlBig;


	public void setInvitationId(long invitationId) {
		this.invitationId = invitationId;
	}

	@JsonProperty
	public long getInvitationId() {
		return invitationId;
	}

	public void setUrlSmall(String urlSmall) {
		this.urlSmall = urlSmall;
	}

	@JsonProperty
	public String getUrlSmall() {
		return urlSmall;
	}

	public void setUrlBig(String urlBig) {
		this.urlBig = urlBig;
	}

	@JsonProperty
	public String getUrlBig() {
		return urlBig;
	}
}
