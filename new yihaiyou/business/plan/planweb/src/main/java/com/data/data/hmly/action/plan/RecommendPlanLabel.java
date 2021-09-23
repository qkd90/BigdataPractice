package com.data.data.hmly.action.plan;

import java.util.List;

public class RecommendPlanLabel {
	
	private Long id;
	private String name;
	private String cityName;
	private Long cityId;
	private Integer sort;
    private String updateTime;
	private List<Long> labelItems;
	private List<Long> labelIds;
	private List<String> labelNames;
	private List<Integer> itemSort;
//	private List<Label> labels;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<Long> getLabelItems() {
		return labelItems;
	}
	public void setLabelItems(List<Long> labelItems) {
		this.labelItems = labelItems;
	}
	public List<Long> getLabelIds() {
		return labelIds;
	}
	public void setLabelIds(List<Long> labelIds) {
		this.labelIds = labelIds;
	}
	public List<String> getLabelNames() {
		return labelNames;
	}
	public void setLabelNames(List<String> labelNames) {
		this.labelNames = labelNames;
	}
	public List<Integer> getItemSort() {
		return itemSort;
	}
	public void setItemSort(List<Integer> itemSort) {
		this.itemSort = itemSort;
	}

}
