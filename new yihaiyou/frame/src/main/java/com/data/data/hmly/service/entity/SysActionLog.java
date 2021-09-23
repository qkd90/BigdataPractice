package com.data.data.hmly.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 操作日志表
 * 
 * @author Administrator
 */
@Entity
@Table(name = "sys_action_log")
public class SysActionLog extends com.framework.hibernate.util.Entity implements java.io.Serializable {
	
	private static final long	serialVersionUID	= 8103729902020450472L;
	
	private Long				logId;										// ID
	private String				opAccount;									// 操作者账号
	private String				opName;									// 操作者姓名
	private String				target;									// 目标表
	private String				actionType;								// 操作类型
	private String				actionTime;								// 操作时间
	private Boolean				delFlag;									// 删除标识
																			
	private String				actionContent;								// 操作内容
																			
	@Id
	@GeneratedValue
	@Column(name = "logId", unique = true, nullable = false)
	public Long getLogId() {
		return logId;
	}
	
	/**
	 * @param logId
	 *            the logId to set
	 */
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	
	@Column(name = "opAccount")
	public String getOpAccount() {
		return opAccount;
	}
	
	/**
	 * @param opAccount
	 *            the opAccount to set
	 */
	public void setOpAccount(String opAccount) {
		this.opAccount = opAccount;
	}
	
	@Column(name = "opName")
	public String getOpName() {
		return opName;
	}
	
	/**
	 * @param opName
	 *            the opName to set
	 */
	public void setOpName(String opName) {
		this.opName = opName;
	}
	
	@Column(name = "target")
	public String getTarget() {
		return target;
	}
	
	/**
	 * @param target
	 *            the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}
	
	@Column(name = "actionType")
	public String getActionType() {
		return actionType;
	}
	
	/**
	 * @param actionType
	 *            the actionType to set
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	@Column(name = "actionTime")
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
	 * @return the delFlag
	 */
	@Column(name = "delFlag")
	public Boolean getDelFlag() {
		return delFlag;
	}
	
	/**
	 * @param delFlag
	 *            the delFlag to set
	 */
	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}
	
	/**
	 * @return the actionContent
	 */
	@Column(name = "actionContent")
	public String getActionContent() {
		return actionContent;
	}
	
	/**
	 * @param actionContent
	 *            the actionContent to set
	 */
	public void setActionContent(String actionContent) {
		this.actionContent = actionContent;
	}
	
}
