package com.data.data.hmly.service.line.solr;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "products")
public class LineDayPriceSolrDocument {
	@Field
	private Date day;
	@Field
	private Float childPrice;
	@Field
	private Float discountPrice;
	@Field
	private String lineType;

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public Float getChildPrice() {
		return childPrice;
	}

	public void setChildPrice(Float childPrice) {
		this.childPrice = childPrice;
	}

	public Float getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Float discountPrice) {
		this.discountPrice = discountPrice;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

}
