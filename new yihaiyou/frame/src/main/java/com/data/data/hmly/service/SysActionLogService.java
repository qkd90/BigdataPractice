package com.data.data.hmly.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.data.data.hmly.service.dao.SysActionLogDao;
import com.data.data.hmly.service.entity.SysActionLog;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.DateUtils;
import com.zuipin.util.StringUtils;

/**
 * 系统操作日志
 * 
 * @author Administrator
 */
@Service
public class SysActionLogService {
	@Resource
	private SysActionLogDao	dao;
	
	public void saveSysActionLog(SysActionLog sysActionLog) {
		if (sysActionLog != null) {
			if (sysActionLog.getLogId() == null) {
				dao.save(sysActionLog);
			} else {
				dao.update(sysActionLog);
			}
		}
	}
	
	/**
	 * 获得一个日志对象
	 * 
	 * @param Clazz
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public SysActionLog getEntityByID(Class Clazz, Serializable id) {
		
		return dao.get(SysActionLog.class, id);
	}
	
	public List<SysActionLog> findSysActionLogList(SysActionLog sysActionLog, Page page) {
		Criteria<SysActionLog> c = new Criteria<SysActionLog>(SysActionLog.class);
		foramtCond(sysActionLog, c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false)));
		return dao.findByCriteria(c, page);
	}
	
	public void foramtCond(SysActionLog sysActionLog, Criteria<SysActionLog> c) {
		if (sysActionLog != null) {
			if (StringUtils.isNotBlank(sysActionLog.getOpAccount())) {
				c.like("opAccount", sysActionLog.getOpAccount());
			}
			if (StringUtils.isNotBlank(sysActionLog.getOpName())) {
				c.like("account", sysActionLog.getOpName());
			}
			if (StringUtils.isNotBlank(sysActionLog.getActionTime())) {
				c.like("actionTime", sysActionLog.getActionTime());
			}
			
		}
	}
	
	/**
	 * 插入一条日志记录信息
	 * 
	 * @param account
	 *            操作账号
	 * @param userName
	 *            操作人姓名
	 * @param actiontable
	 *            操作表
	 * @param actionType
	 *            操作动作 如：新增、更新、删除 等
	 * @param content
	 *            操作内容
	 */
	public void addSysLog(String account, String name, String actiontable, String actionType, String content) {
		SysActionLog log = new SysActionLog();
		log.setOpAccount(account);
		log.setOpName(name);
		log.setTarget(actiontable);
		log.setActionType(actionType);
		log.setActionTime(DateUtils.getCurrentSysDate());
		log.setActionContent(content);
		log.setDelFlag(false);
		dao.save(log);
	}
	
}
