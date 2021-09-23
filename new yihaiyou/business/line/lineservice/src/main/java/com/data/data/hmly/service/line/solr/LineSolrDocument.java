package com.data.data.hmly.service.line.solr;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "products")
public class LineSolrDocument {
	@Field
	private Long lineid;
	@Field
	private String supplierName;
	@Field
	private String name;
	@Field
	private String linePoint;
	@Field
	private Integer lineDay;
	@Field
	private String productAttr;

	private List<LineDayPriceSolrDocument> dayPrices = new ArrayList<LineDayPriceSolrDocument>();

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLinePoint() {
		return linePoint;
	}

	public void setLinePoint(String linePoint) {
		this.linePoint = linePoint;
	}

	public Integer getLineDay() {
		return lineDay;
	}

	public void setLineDay(Integer lineDay) {
		this.lineDay = lineDay;
	}

	public String getProductAttr() {
		return productAttr;
	}

	public void setProductAttr(String productAttr) {
		this.productAttr = productAttr;
	}

	public List<LineDayPriceSolrDocument> getDayPrices() {
		return dayPrices;
	}

	public void setDayPrices(List<LineDayPriceSolrDocument> dayPrices) {
		this.dayPrices = dayPrices;
	}

	public Long getLineid() {
		return lineid;
	}

	public void setLineid(Long lineid) {
		this.lineid = lineid;
	}

}
