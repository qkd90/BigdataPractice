package com.data.data.hmly.service.base;

import java.util.ArrayList;
import java.util.List;

public class ResultModel<T> {
	private int errorCode;
	private String errorMsg;
	private int total = 0;
	private List<T> rows = new ArrayList<T>();
	
	public ResultModel() {
	}

	public ResultModel(int total, List<T> rows) {
		this.total = total;
		this.rows = rows;
	}
	
	public ResultModel(int errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public ResultModel(int errorCode, String errorMsg, int total, List<T> rows) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.total = total;
		this.rows = rows;
	}
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
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
	
}
