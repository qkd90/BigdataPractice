package com.data.spider.service.pojo;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.data.spider.process.entity.MakeBy;

@Entity//注解为实体
@Table(name = "data_tasks")//对应数据库中哪一张表
public class Datatask extends com.framework.hibernate.util.Entity {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5020517186323824656L;
	@Id//注解为 id主键?
	@GeneratedValue//注解为自动增长?
	private Long				id;
	private String				url, tag, classname, html, xml, info, name;
	@Enumerated(EnumType.STRING)//注解为枚举数据类型,采用枚举类型与数据库进行交互
	private DatataskStatus		status;
	@Enumerated(EnumType.STRING)
	private MakeBy				madeby;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s %s %s", this.id, this.url, this.xml);
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public DatataskStatus getStatus() {
		return status;
	}

	public void setStatus(DatataskStatus status) {
		this.status = status;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MakeBy getMadeby() {
		return madeby;
	}

	public void setMadeby(MakeBy madeby) {
		this.madeby = madeby;
	}

}
