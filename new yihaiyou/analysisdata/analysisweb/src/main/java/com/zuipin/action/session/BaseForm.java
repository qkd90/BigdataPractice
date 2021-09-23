package com.zuipin.action.session;

public class BaseForm {
	
	private Integer	page;
	private Integer	rows;
	
	public BaseForm() {
		super();
	}
	
	public Integer getPage() {
		return page;
	}
	
	public void setPage(Integer page) {
		this.page = page;
	}
	
	public Integer getRows() {
		return rows;
	}
	
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	
}