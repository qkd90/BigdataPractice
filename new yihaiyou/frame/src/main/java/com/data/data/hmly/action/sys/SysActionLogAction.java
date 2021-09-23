package com.data.data.hmly.action.sys;

import java.util.List;

import javax.annotation.Resource;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.SysActionLogService;
import com.data.data.hmly.service.entity.SysActionLog;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;

public class SysActionLogAction extends FrameBaseAction implements ModelDriven<SysActionLog> {
	
	/**
	 * 用户动作日志
	 */
	private static final long	serialVersionUID	= 1L;
	@Resource
	private SysActionLogService	service;
	private SysActionLog		sysActionLog		= new SysActionLog();
	private Integer				page				= 1;
	private Integer				rows				= 20;
	private String				id;										// 传入的动作日志编号或机构编号
	private String				actionTime;								// 执行动作时间
	private List<SysActionLog>	sysActionLogList;							// 动作日志数据集合 为列表展示使用
	private String				jsonActionlogList;							// json格式字符串 定义方便界面调用
																			
	/**
	 * 模糊查询动作日志对象
	 */
	public Result search() {
		initPage();
		Page pager = new Page(page, rows);
		List<SysActionLog> logs = service.findSysActionLogList(sysActionLog, pager);
		return jsonResult(logs, pager.getTotalCount());
	}
	
	// 分页初始化
	public void initPage() {
		pager = new Page();
		pager.setPageIndex(page);
		pager.setPageSize(rows);
	}
	
	public Result manage() {
		return dispatch();
	}
	
	@Override
	public SysActionLog getModel() {
		return sysActionLog;
	}
	
	/**
	 * @return the sysActionLog
	 */
	public SysActionLog getSysActionLog() {
		return sysActionLog;
	}
	
	/**
	 * @param sysActionLog
	 *            the sysActionLog to set
	 */
	public void setSysActionLog(SysActionLog sysActionLog) {
		this.sysActionLog = sysActionLog;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return the actionTime
	 */
	public String getActionTime() {
		return actionTime;
	}
	
	/**
	 * @param actionTime
	 *            the actionTime to set
	 */
	public void setActionTime(String actionTime) {
		this.actionTime = actionTime;
	}
	
	/**
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}
	
	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(Integer page) {
		this.page = page;
	}
	
	/**
	 * @return the rows
	 */
	public Integer getRows() {
		return rows;
	}
	
	/**
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	
	/**
	 * @return the sysActionLogList
	 */
	public List<SysActionLog> getSysActionLogList() {
		return sysActionLogList;
	}
	
	/**
	 * @param sysActionLogList
	 *            the sysActionLogList to set
	 */
	public void setSysActionLogList(List<SysActionLog> sysActionLogList) {
		this.sysActionLogList = sysActionLogList;
	}
	
	/**
	 * @return the jsonActionlogList
	 */
	public String getJsonActionlogList() {
		return jsonActionlogList;
	}
	
	/**
	 * @param jsonActionlogList
	 *            the jsonActionlogList to set
	 */
	public void setJsonActionlogList(String jsonActionlogList) {
		this.jsonActionlogList = jsonActionlogList;
	}
	
}
