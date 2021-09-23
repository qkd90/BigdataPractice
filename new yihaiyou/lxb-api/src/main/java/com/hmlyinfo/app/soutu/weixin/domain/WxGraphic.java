package com.hmlyinfo.app.soutu.weixin.domain;

import org.codehaus.jackson.annotate.JsonProperty;

import com.hmlyinfo.base.persistent.BaseEntity;

public class WxGraphic extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private long wxReplyId;
	
	/**
	 * 
	 */
	private String title;
	
	/**
	 * 
	 */
	private String description;
	
	/**
	 *  完整图片地址
	 */
	private String img;
	
	/**
	 * 
	 */
	private String url;
	
	/**
	 * 顺序编号
	 */
	private int orderNum;
	
	public void setWxReplyId(long wxReplyId){
		this.wxReplyId = wxReplyId;
	}
	
	@JsonProperty
	public long getWxReplyId(){
		return wxReplyId;
	}
	public void setTitle(String title){
		this.title = title;
	}
	
	@JsonProperty
	public String getTitle(){
		return title;
	}
	public void setDescription(String description){
		this.description = description;
	}
	
	@JsonProperty
	public String getDescription(){
		return description;
	}
	public void setImg(String img){
		this.img = img;
	}
	
	@JsonProperty
	public String getImg(){
		return img;
	}
	public void setUrl(String url){
		this.url = url;
	}
	
	@JsonProperty
	public String getUrl(){
		return url;
	}
	public void setOrderNum(int orderNum){
		this.orderNum = orderNum;
	}
	
	@JsonProperty
	public int getOrderNum(){
		return orderNum;
	}
}
