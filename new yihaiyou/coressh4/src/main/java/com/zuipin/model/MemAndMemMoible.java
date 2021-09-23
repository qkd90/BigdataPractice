package com.zuipin.model;

import com.zuipin.entity.Member;
import com.zuipin.entity.TMemberMobile;

public class MemAndMemMoible {

	private Member			member;

	private TMemberMobile	memberMobile;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public TMemberMobile getMemberMobile() {
		return memberMobile;
	}

	public void setMemberMobile(TMemberMobile memberMobile) {
		this.memberMobile = memberMobile;
	}

}
