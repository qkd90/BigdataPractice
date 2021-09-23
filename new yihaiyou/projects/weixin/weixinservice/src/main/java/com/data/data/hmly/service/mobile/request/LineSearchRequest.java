package com.data.data.hmly.service.mobile.request;

import com.framework.hibernate.util.Page;
import org.apache.solr.client.solrj.SolrQuery;

/**
 * Created by guoshijie on 2015/11/19.
 */
public class LineSearchRequest {

	public String orderColumn;
	public SolrQuery.ORDER orderType;
	public String lowestPrice;
	public String highestPrice;
	public String cityNames;
	public String lowestDay;
	public String highestDay;
	public String planName;
	public Long lineId;

	public int page;
	public int pageSize;

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public SolrQuery.ORDER getOrderType() {
		return orderType;
	}

	public void setOrderType(SolrQuery.ORDER orderType) {
		this.orderType = orderType;
	}

	public String getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(String lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public String getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(String highestPrice) {
		this.highestPrice = highestPrice;
	}

	public String getCityNames() {
		return cityNames;
	}

	public void setCityNames(String cityNames) {
		this.cityNames = cityNames;
	}

	public String getLowestDay() {
		return lowestDay;
	}

	public void setLowestDay(String lowestDay) {
		this.lowestDay = lowestDay;
	}

	public String getHighestDay() {
		return highestDay;
	}

	public void setHighestDay(String highestDay) {
		this.highestDay = highestDay;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Page page() {
		return new Page(page, pageSize);
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Long getLineId() {
		return lineId;
	}

	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}
}
