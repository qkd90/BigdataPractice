package com.data.data.hmly.action.wechat;

import java.util.Date;
import java.util.List;

import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.data.data.hmly.service.wechat.entity.WechatDataNews;
import com.data.data.hmly.service.wechat.entity.WechatDataText;
import com.data.data.hmly.service.wechat.entity.WechatReplyKeyword;
import com.gson.inf.MsgTypes;

public class RuleData {
	
	private Long id;
	private String name;
	private Long itemId;
	private MsgTypes msgTypes;
	private List<String> replyTitle;
	private List<WechatDataText> textList;
	private List<WechatDataNews> newsList;
	private List<WechatReplyKeyword> keywordList;
	private WechatAccount account;

	private Date createTime;
	private Date updateTime;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
//	public List<WechatDataNews> getNewsList() {
//		return newsList;
//	}
//	public void setNewsList(List<WechatDataNews> newsList) {
//		this.newsList = newsList;
//	}
//	public List<WechatDataText> getTextList() {
//		return textList;
//	}
//	public void setTextList(List<WechatDataText> textList) {
//		this.textList = textList;
//	}
	public List<WechatReplyKeyword> getKeywordList() {
		return keywordList;
	}
	public void setKeywordList(List<WechatReplyKeyword> keywordList) {
		this.keywordList = keywordList;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public MsgTypes getMsgTypes() {
		return msgTypes;
	}
	public void setMsgTypes(MsgTypes msgTypes) {
		this.msgTypes = msgTypes;
	}
	public List<String> getReplyTitle() {
		return replyTitle;
	}
	public void setReplyTitle(List<String> replyTitle) {
		this.replyTitle = replyTitle;
	}
	public List<WechatDataText> getTextList() {
		return textList;
	}
	public void setTextList(List<WechatDataText> textList) {
		this.textList = textList;
	}
	public List<WechatDataNews> getNewsList() {
		return newsList;
	}
	public void setNewsList(List<WechatDataNews> newsList) {
		this.newsList = newsList;
	}
	public WechatAccount getAccount() {
		return account;
	}
	public void setAccount(WechatAccount account) {
		this.account = account;
	}
	
	
	

}
