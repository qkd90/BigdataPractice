package com.zuipin.util;

@SuppressWarnings("serial")
public class DamallException extends RuntimeException {

	public DamallException() {
		super();
	}

	public DamallException(String message) {
		super(message);
	}

	public DamallException(String message, Throwable cause) {
		super(message, cause);
	}

	public DamallException(Throwable cause) {
		super(cause);
	}
}
