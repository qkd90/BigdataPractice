package com.data.data.hmly.util;

import java.util.List;

public class JsonReader<T> {
	
	private Long	total;	// 总页数
							
	private List<T>	rows;	// 包含实际数组的对象
							
	private List<T>	footer; // 页面数组对象
							
	public Long getTotal() {
		return total;
	}
	
	public void setTotal(Long total) {
		this.total = total;
	}
	
	public List<T> getRows() {
		return rows;
	}
	
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
	public List<T> getFooter() {
		return footer;
	}
	
	public void setFooter(List<T> footer) {
		this.footer = footer;
	}


}
