package com.data.data.hmly.action.msg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.SendingMsgService;
import com.data.data.hmly.service.SysActionLogService;
import com.data.data.hmly.service.entity.SendingMsg;
import com.data.data.hmly.service.entity.SysUser;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;

public class MsgAction extends FrameBaseAction {
	
	
	/**@Todo 发送短信管理
	 * 
	 */
	private static final long serialVersionUID = 939657960445255969L;
	@Resource
	private SendingMsgService	sendingMsgService;
	@Resource
	private SysActionLogService	sysActionLogService;
	private String				json;
	private Integer				page				= 1;
	private Integer				rows				= 10;
	private SendingMsg			msg					= new SendingMsg();
	private Map<String, Object>	map					= new HashMap<String, Object>();
	private SysUser				sysUser				= getLoginUser();
	private String				content				= "";
	String						account				= "";								// 用于记录当前登录用户账号
	String						name				= "";								// 用于记录当前登录用户姓名



	public Result manage(){
		return dispatch();
	}
	
	/**
	 * 功能描述：查询短信信息
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:52:04
	 * @return
	 */
	public Result searchMsg(){
		Page pager = new Page(page, rows);
		List<SendingMsg> msgs = sendingMsgService.findMsgList(msg, pager);
		return jsonResult(msgs, pager.getTotalCount());
		
	}
	
	
//	public Result deleteMsg() {
//		try {
//			msg = sendingMsgService.findSendingMsgById(msg.getId());
//			if (msg.getStatus() == SendStatus.done && msg.getStatus() == SendStatus.sending) {
//				simpleResult(map, false, "删除失败,信息已发送!");
//			} else {
//				msg.setStatus(SendStatus.newed);
//				sendingMsgService.update(msg);
//				simpleResult(map, true, "");
//				if (sysUser != null) {
//					account = sysUser.getAccount();
//					name = sysUser.getUserName();
//				}
//				content = "账号：" + account + "删除一条信息，客户ID为：" + msg.getId() + "，号码为：" + msg.getReceivernum();
//			}
//		} catch (Exception e) {
//			simpleResult(map, false, "删除信息出错!");
//			slog(Constants.log_error, "删除信息出错!", e);
//		}
//		return jsonResult(map);
//	}
//	
	
	
	/**
	 * 功能描述：根据ID获取角色对象
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午4:53:29
	 * @return
	 */

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

	public SendingMsg getMsg() {
		return msg;
	}

	public void setMsg(SendingMsg msg) {
		this.msg = msg;
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


