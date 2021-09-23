package com.hmlyinfo.base;


public class AjaxList {
	private int counts;
	private String msg;
	private String errCode;
	private boolean success;
	private Object data;

	public AjaxList() {

	}

	public AjaxList(int counts, Object data) {
		this.counts = counts;
		this.data = data;
	}

	public AjaxList(int counts, String msg, boolean success, Object data) {
		this.counts = counts;
		this.msg = msg;
		this.success = success;
		this.data = data;
	}


	public static AjaxList createSuccess(Object data) {
		return new AjaxList(0, null, true, data);
	}

	public static AjaxList createFail() {
		return new AjaxList(0, null, false, null);
	}

	public int getCounts() {
		return counts;
	}

	public Object getData() {
		return data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}


}
