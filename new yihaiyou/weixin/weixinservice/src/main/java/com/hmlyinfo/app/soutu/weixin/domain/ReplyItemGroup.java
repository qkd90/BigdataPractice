package com.hmlyinfo.app.soutu.weixin.domain;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

import com.hmlyinfo.base.persistent.BaseEntity;
/**
 * 自动回复分组类
 * @author DELL
 *
 */
public class ReplyItemGroup extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 自动回复消息的组名
	 */
	private String name;
	
	/**
	 * 自动回复组的编号
	 */
	private int orderNum;
	/**
	 * 自动回复组中包含的关键词
	 * @return
	 */
	private List<Reply> replys;
	/**
	 * 自动回复组中包含的回复消息
	 * @return
	 */
	private Map<Integer,ReplyModel> replyItems;

	@JsonProperty
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@JsonProperty
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	@JsonProperty
	public List<Reply> getReplys() {
		return replys;
	}
	public void setReplys(List<Reply> replys) {
		this.replys = replys;
	}
	@JsonProperty
	public Map<Integer, ReplyModel> getReplyItems() {
		return replyItems;
	}
	public void setReplyItems(Map<Integer, ReplyModel> replyItems2) {
		this.replyItems = replyItems2;
	}
	
}
