package com.zuipin.action.session;

import java.util.Date;

public class SessionForm extends BaseForm {
	
	public String	uid, referer;
	private Date	startTime, endTime;
	private Integer	startPv, endPv, endCost, startCost;
	
	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Integer getStartPv() {
		return startPv;
	}
	
	public void setStartPv(Integer startPv) {
		this.startPv = startPv;
	}
	
	public Integer getEndPv() {
		return endPv;
	}
	
	public void setEndPv(Integer endPv) {
		this.endPv = endPv;
	}
	
	public String getReferer() {
		return referer;
	}
	
	public void setReferer(String referer) {
		this.referer = referer;
	}
	
	public Integer getEndCost() {
		return endCost;
	}
	
	public void setEndCost(Integer endCost) {
		this.endCost = endCost;
	}
	
	public Integer getStartCost() {
		return startCost;
	}
	
	public void setStartCost(Integer startCost) {
		this.startCost = startCost;
	}
	
}
