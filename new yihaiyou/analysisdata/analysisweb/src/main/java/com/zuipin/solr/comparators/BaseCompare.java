package com.zuipin.solr.comparators;

import java.io.Serializable;

public abstract class BaseCompare implements Serializable {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -600050654213574573L;
	public Class<Object>		clazz;
	public String				name;
	public String				type;
	public String				ope;
	public String				con;
	public String				val1, val2;
	public Boolean				isParent;
	protected StringBuilder		q					= new StringBuilder();
	protected StringBuilder		fq					= new StringBuilder();
	
	public Class<Object> getClazz() {
		return clazz;
	}
	
	public void setClazz(Class<Object> clazz) {
		this.clazz = clazz;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name.toUpperCase();
	}
	
	public String getOpe() {
		return ope;
	}
	
	public void setOpe(String ope) {
		this.ope = ope;
	}
	
	public String getVal1() {
		return val1;
	}
	
	public void setVal1(String val1) {
		this.val1 = val1;
	}
	
	public String getVal2() {
		return val2;
	}
	
	public void setVal2(String val2) {
		this.val2 = val2;
	}
	
	public abstract Boolean compare(Object o) throws Exception;
	
	public abstract StringBuilder createQuery() throws Exception;
	
	public String getCon() {
		return con;
	}
	
	public void setCon(String con) {
		this.con = con;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public StringBuilder getQ() {
		return q;
	}
	
	public void setQ(StringBuilder q) {
		this.q = q;
	}
	
	public StringBuilder getFq() {
		return fq;
	}
	
	public void setFq(StringBuilder fq) {
		this.fq = fq;
	}
	
	public Boolean getIsParent() {
		return isParent;
	}
	
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	
}
