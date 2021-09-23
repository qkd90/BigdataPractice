package com.spark.service.hbase.pojo;

import java.io.Serializable;
import java.util.Date;

public class Point implements Serializable {
	
	private Date			accessTime	= new Date();
	private Date			lastTime;
	private Integer			x;
	private Integer			y;
	private PointEventType	type		= PointEventType.Point;
	
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
	
	public PointEventType getType() {
		return type;
	}
	
	public void setType(PointEventType type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%d,%d,%d", type.name(), x, y);
	}
	
	public Date getLastTime() {
		return lastTime;
	}
	
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	
}
