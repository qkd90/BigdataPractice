package com.data.data.hmly.service.line.entity.enums;

public enum ChildStandardType {
	desc("文本说明"), height("身高"), none("无"), age("年龄");
	String description;

	private ChildStandardType(String description) {
		this.description = description;
	}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
