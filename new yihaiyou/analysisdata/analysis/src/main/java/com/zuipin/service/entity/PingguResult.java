package com.zuipin.service.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.mahout.cf.taste.eval.RecommenderBuilder;

@Entity
@Table(name = "pingguresult")
public class PingguResult extends com.framework.hibernate.util.Entity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1852862911749265251L;
	@Id
	@GeneratedValue
	private Long				id;
	public Double				evaluaterScore, precisionScore, recall;
	private String				similarityName;
	private Integer				topn;
	private Boolean				booleanData;
	@Enumerated(EnumType.STRING)
	private BaseType			baseType;
	@Transient
	private RecommenderBuilder	rb;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getSimilarityName() {
		return similarityName;
	}
	
	public void setSimilarityName(String similarityName) {
		this.similarityName = similarityName;
	}
	
	public double getEvaluaterScore() {
		return evaluaterScore;
	}
	
	public void setEvaluaterScore(double evaluaterScore) {
		this.evaluaterScore = evaluaterScore;
	}
	
	public Double getPrecisionScore() {
		return precisionScore;
	}
	
	public void setPrecisionScore(Double precisionScore) {
		this.precisionScore = precisionScore;
	}
	
	public void setEvaluaterScore(Double evaluaterScore) {
		this.evaluaterScore = evaluaterScore;
	}
	
	public void setRecall(Double recall) {
		this.recall = recall;
	}
	
	public double getRecall() {
		return recall;
	}
	
	public void setRecall(double recall) {
		this.recall = recall;
	}
	
	public RecommenderBuilder getRb() {
		return rb;
	}
	
	public void setRb(RecommenderBuilder rb) {
		this.rb = rb;
	}
	
	public Integer getTopn() {
		return topn;
	}
	
	public void setTopn(Integer topn) {
		this.topn = topn;
	}
	
	public Boolean getBooleanData() {
		return booleanData;
	}
	
	public void setBooleanData(Boolean booleanData) {
		this.booleanData = booleanData;
	}
	
	public BaseType getBaseType() {
		return baseType;
	}
	
	public void setBaseType(BaseType baseType) {
		this.baseType = baseType;
	}
	
}
