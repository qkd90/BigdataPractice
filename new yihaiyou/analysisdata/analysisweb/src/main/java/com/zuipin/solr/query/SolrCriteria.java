package com.zuipin.solr.query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.framework.hibernate.util.Page;
import com.zuipin.util.DateUtils;

public class SolrCriteria {
	
	private StringBuilder		query		= new StringBuilder();
	private StringBuilder		orderBy		= new StringBuilder();
	private List<String>		fl			= new ArrayList<String>();
	private Boolean				isAnd		= true;
	private final static String	AND			= " AND ";
	private Page				page;
	private Boolean				ISPARENT	= true;
	
	public SolrCriteria eq(String property, String value) {
		if (query.length() > 0) {
			query.append(AND);
		}
		query.append(String.format("%s:%s", property, value));
		return this;
	}
	
	public SolrCriteria flAppend(String flStr) {
		fl.add(flStr);
		return this;
	}
	
	public SolrCriteria setFl(String... fls) {
		for (String flItem : fls) {
			fl.add(flItem);
		}
		return this;
	}
	
	public SolrCriteria ge(String property, String value) {
		if (query.length() > 0) {
			query.append(AND);
		}
		query.append(String.format("%s:[%s TO *]", property, value));
		return this;
	}
	
	public SolrCriteria le(String property, String value) {
		if (query.length() > 0) {
			query.append(AND);
		}
		query.append(String.format("%s:(* TO %s]", property, value));
		return this;
	}
	
	public SolrCriteria gt(String property, String value) {
		if (query.length() > 0) {
			query.append(AND);
		}
		query.append(String.format("%s:(%s TO *]", property, value));
		return this;
	}
	
	public SolrCriteria lt(String property, String value) {
		if (query.length() > 0) {
			query.append(AND);
		}
		query.append(String.format("%s:(* TO %s)", property, value));
		return this;
	}
	
	public SolrCriteria isNotNull(String property) {
		return eq(property, "*");
	}
	
	public SolrCriteria ne(String property, String value) {
		if (query.length() > 0) {
			query.append(AND);
		}
		query.append(String.format("NOT %s:%s", property, value));
		return this;
	}
	
	public SolrCriteria between(String property, Object minValue, Object maxValue) {
		if (query.length() > 0) {
			query.append(AND);
		}
		query.append(String.format("%s:[%s TO %s]", property, minValue, maxValue));
		return this;
	}
	
	public SolrCriteria between(String property, Date minValue, Date maxValue) {
		if (query.length() > 0) {
			query.append(AND);
		}
		Calendar minCalen = Calendar.getInstance();
		minCalen.setTime(minValue);
		minCalen.add(Calendar.HOUR_OF_DAY, -8);
		String minDay = DateUtils.format(minCalen.getTime(), "yyyy-MM-dd'T'HH:mm:ss.000'Z'");
		
		Calendar maxCalen = Calendar.getInstance();
		maxCalen.setTime(maxValue);
		maxCalen.add(Calendar.HOUR_OF_DAY, -8);
		String maxDay = DateUtils.format(maxCalen.getTime(), "yyyy-MM-dd'T'HH:mm:ss.999'Z'");
		query.append(String.format("%s:[%s TO %s]", property, minDay, maxDay));
		return this;
	}
	
	public SolrCriteria orderBy(String property, Boolean ifAsc) {
		if (orderBy.length() > 0) {
			orderBy.append(AND);
		}
		orderBy.append(String.format("%s:%s", property, ifAsc ? " ASC " : " DESC "));
		return this;
	}
	
	public Boolean getIsAnd() {
		return isAnd;
	}
	
	public void setIsAnd(Boolean isAnd) {
		this.isAnd = isAnd;
	}
	
	public Page getPage() {
		return page;
	}
	
	public void setPage(Page page) {
		this.page = page;
	}
	
	public String getQuery() {
		return query.toString();
	}
	
	public String[] getFl() {
		String[] a = new String[fl.size()];
		return fl.toArray(a);
	}
	
	public void setISPARENT(Boolean iSPARENT) {
		ISPARENT = iSPARENT;
	}
	
	public Boolean getISPARENT() {
		return ISPARENT;
	}
	
	public SolrCriteria eq(String property, boolean bool) {
		return eq(property, String.valueOf(bool));
	}
}
