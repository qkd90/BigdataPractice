package com.data.spider.process.entity;

public enum MfwLabelenum {

	电话("telephone"), 交通("guide"), 门票("ticket"), 开放时间("open_time"), 用时参考("advice_time"), 网址("website");

	private String	field;

	private MfwLabelenum(String field) {
		// TODO Auto-generated constructor stub
		this.field = field;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public static String find(String name) {
		for (MfwLabelenum la : values()) {
			if (name.contains(la.name())) {
				return la.getField();
			}
		}
		return null;
	}

}
