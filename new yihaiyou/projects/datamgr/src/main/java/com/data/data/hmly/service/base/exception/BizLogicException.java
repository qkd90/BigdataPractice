package com.data.data.hmly.service.base.exception;

public class BizLogicException extends RuntimeException
{

	private int errorCode;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BizLogicException (int errorCode)
	{
		this.errorCode = errorCode;
	}
	
	public BizLogicException(String msg)
	{
		super(msg);
	}

	public int getErrorCode()
	{
		return errorCode;
	}
	

}
