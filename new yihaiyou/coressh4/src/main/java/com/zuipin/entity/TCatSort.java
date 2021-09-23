package com.zuipin.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CAT_SORT")
public class TCatSort implements java.io.Serializable {
	@Id
	@Column(name = "SORT_ID", precision = 18, scale = 0)
	private Long	sortId;
	@Column(name = "SORT_NAME", length = 20)
	private String	sortName;
	@Column(name = "CAT_ID", precision = 18, scale = 0)
	private Long	catId;
	@Column(name = "SHOW_ORDER", precision = 8, scale = 0)
	private Long	showOrder;
	@Column(name = "MOD_MAN", length = 40)
	private String	modMan;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MOD_DATE", length = 7)
	private Date	modDate;
	@Column(name = "FILTER_VALUE")
	private String	filterValue;
	
	public Long getSortId() {
		return sortId;
	}
	
	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}
	
	public String getSortName() {
		return sortName;
	}
	
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	
	public Long getCatId() {
		return catId;
	}
	
	public void setCatId(Long catId) {
		this.catId = catId;
	}
	
	public Long getShowOrder() {
		return showOrder;
	}
	
	public void setShowOrder(Long showOrder) {
		this.showOrder = showOrder;
	}
	
	public String getModMan() {
		return modMan;
	}
	
	public void setModMan(String modMan) {
		this.modMan = modMan;
	}
	
	public Date getModDate() {
		return modDate;
	}
	
	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}
	
	public String getFilterValue() {
		return filterValue;
	}
	
	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}
	
}
