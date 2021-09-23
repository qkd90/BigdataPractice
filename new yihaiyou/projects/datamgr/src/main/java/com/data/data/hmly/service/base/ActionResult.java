package com.data.data.hmly.service.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActionResult implements Serializable
{
	
	private static final long serialVersionUID = 285169851424218073L;
	
	private int errorCode;
	private String errorMsg;
	private ResultList<Object> resultList;
	
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) { 
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public ResultList<Object> getResultList() {
		return resultList;
	}
	
	public void setResultList(ResultList<Object> resultList) {
		this.resultList = resultList;
	}
	
	public void setRecordList(List<Object> recordList) {
		
		resultList = new ResultList<Object>();
		resultList.setData(recordList);
		resultList.setCounts(recordList.size());
	}
	
	public static ActionResult createSuccess(Object data)
	{
		ActionResult result = new ActionResult();
		result.setErrorCode(0);
		
		if (data instanceof ResultList)
		{
			result.setResultList((ResultList)data);
		}
		else if (data instanceof List)
		{
			result.setRecordList((List)data);
		}
		else
		{
			List<Object> rList = new ArrayList<Object>();
			rList.add(data);
			
			result.setRecordList(rList);
		}
		
		return result;
	}

	public static ActionResult createSuccess() {
		return createSuccess(new ArrayList());
	}

	public static ActionResult createFail(int errorCode) {
		ActionResult result = new ActionResult();
		result.setErrorCode(errorCode);
		return result;
	}

}
