package com.spark.service.hbase.pojo;

import java.util.Date;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.lucene.document.Document;

import com.zuipin.util.DateUtils;

public class LoadProduct extends HBaseEntity {
	
	@Id
	private String							id;
	private String							productId;
	private String							webRequestId;
	private String							url;
	private Long							userId;
	private Integer							type;
	private Date							accessTime	= new Date();
	private Integer							x;
	private Integer							y;
	@Transient
	private static ReentrantReadWriteLock	lock		= new ReentrantReadWriteLock();
	@Transient
	public static ReadLock					readLock	= lock.readLock();
	@Transient
	public static WriteLock					writeLock	= lock.writeLock();
	
	@Override
	public WriteLock getWriteLock() {
		// TODO Auto-generated method stub
		return writeLock;
	}
	
	public String getProductId() {
		return productId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getWebRequestId() {
		return webRequestId;
	}
	
	public void setWebRequestId(String webRequestId) {
		this.webRequestId = webRequestId;
	}
	
	public Date getAccessTime() {
		return accessTime;
	}
	
	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}
	
	public Integer getX() {
		return x;
	}
	
	public void setX(Integer x) {
		this.x = x;
	}
	
	public Integer getY() {
		return y;
	}
	
	public void setY(Integer y) {
		this.y = y;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Override
	public void makeKey() {
		// TODO Auto-generated method stub
		id = String.format("%s%20d", DateUtils.formatDate(accessTime), productId);
	}
	
	@Override
	public Document getDoc() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getIndexPath() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}
	
}
