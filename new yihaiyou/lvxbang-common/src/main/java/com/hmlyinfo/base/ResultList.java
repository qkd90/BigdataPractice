package com.hmlyinfo.base;

import java.util.List;

public class ResultList<T> {

	private List<T> data;
	private int counts;


	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getCounts() {
		return counts;
	}

	public void setCounts(int counts) {
		this.counts = counts;
	}

}
