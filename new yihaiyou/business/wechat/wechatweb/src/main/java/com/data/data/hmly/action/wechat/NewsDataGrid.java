package com.data.data.hmly.action.wechat;

import java.util.Date;
import java.util.List;

import com.data.data.hmly.service.wechat.entity.WechatDataNews;
import com.data.data.hmly.service.wechat.entity.WechatDataText;

public class NewsDataGrid {
	
	private String imgUrl;
	private List<WechatDataNews> newsList;
	private List<WechatDataText> textList;
	private Date updateTime;
	private Long id;		//itemId
	
	
	
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public List<WechatDataNews> getNewsList() {
		return newsList;
	}
	public void setNewsList(List<WechatDataNews> newsList) {
		this.newsList = newsList;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<WechatDataText> getTextList() {
		return textList;
	}
	public void setTextList(List<WechatDataText> textList) {
		this.textList = textList;
	}
	
	
	

}
