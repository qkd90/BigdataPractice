package com.spark.service.hbase.pojo;

import java.io.Serializable;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import javax.persistence.Transient;

import org.apache.lucene.document.Document;
import org.apache.solr.common.SolrInputDocument;

public abstract class HBaseEntity implements Serializable {
	
	/**
	 * 
	 */
	@Transient
	private static final long	serialVersionUID	= -6693213140841268834L;
	
	protected abstract void makeKey();
	
	public static String getFamilyName() {
		return "default";
	}
	
	@Transient
	public Document getDoc() {
		return null;
	}
	
	@Transient
	public SolrInputDocument getSolrDoc() {
		return null;
	}
	
	public String getIndexPath() {
		return null;
	}
	
	@Transient
	public abstract WriteLock getWriteLock();
	
}
