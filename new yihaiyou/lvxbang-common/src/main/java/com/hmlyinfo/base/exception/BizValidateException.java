package com.hmlyinfo.base.exception;

public class BizValidateException extends RuntimeException {
	/**
	 *
	 */
	private static final long serialVersionUID = -7247658404070653458L;

	private int errorcode;
	private String msg;

	public BizValidateException(int errorcode) {
		super();
		this.errorcode = errorcode;
	}

	public BizValidateException(int errorcode, String msg) {
		super(msg);
		this.errorcode = errorcode;
		this.msg = msg;
	}


	public int getErrorcode() {
		return errorcode;
	}

	public String getMsg() {
		return msg;
	}


}
