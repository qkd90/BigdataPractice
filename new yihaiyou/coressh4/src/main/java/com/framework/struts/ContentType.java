package com.framework.struts;

public enum ContentType {
	HTML("jhtml"), XML("xml"), JSON("json");

	private String extension;

	ContentType(String extension) {
		this.extension = extension;
	}

	public String getExtension() {
		return extension;
	}
}
