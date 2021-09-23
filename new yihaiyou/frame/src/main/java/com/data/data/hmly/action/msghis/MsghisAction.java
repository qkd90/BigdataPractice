package com.data.data.hmly.action.msghis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.MsgHistoryService;
import com.data.data.hmly.service.SysActionLogService;
import com.data.data.hmly.service.entity.SendingMsgHis;
import com.data.data.hmly.service.entity.SysUser;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;

public class MsghisAction extends FrameBaseAction {
	
	
	/**@Todo 发送短信管理
	 * 
	 */
	private static final long serialVersionUID = 939657960445255969L;
	@Resource
	private MsgHistoryService	msgHistoryService;
	@Resource
	private SysActionLogService	sysActionLogService;
	private String				json;
	private Integer				page				= 1;
	private Integer				rows				= 10;
	private SendingMsgHis		msgHis				= new SendingMsgHis();
	private Map<String, Object>	map					= new HashMap<String, Object>();
	private SysUser				sysUser				= getLoginUser();
	private String				content				= "";
	String						account				= "";								// 用于记录当前登录用户账号
	String						name				= "";								// 用于记录当前登录用户姓名
	
	
	public Result manage(){
		return dispatch();
	}
	
	/**
	 * 功能描述：查询短信信息历史
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:52:04
	 * @return
	 */
	public Result searchMsgHis(){
		Page pager = new Page(page, rows);
		List<SendingMsgHis> msgs = msgHistoryService.findMsgHisList(msgHis, pager);
		return jsonResult(msgs, pager.getTotalCount());
		
	}


	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public SendingMsgHis getMsgHis() {
		return msgHis;
	}

	public void setMsgHis(SendingMsgHis msgHis) {
		this.msgHis = msgHis;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
