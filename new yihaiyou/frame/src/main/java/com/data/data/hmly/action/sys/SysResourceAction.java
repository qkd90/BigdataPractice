package com.data.data.hmly.action.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.SysActionLogService;
import com.data.data.hmly.service.SysResourceService;
import com.data.data.hmly.service.entity.SysResource;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.util.WfConstants;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.Constants;
import com.zuipin.util.DateUtils;

/**
 * @author cjj
 * @date 2015年3月30日
 * @TODO 用户管理
 */
public class SysResourceAction extends FrameBaseAction implements ModelDriven<SysResource> {
	private static final long	serialVersionUID	= 1L;
	@Resource
	private SysResourceService	sysResourceService;
	@Resource
	private SysActionLogService	sysActionLogService;
	private String				json;
	private Integer				page				= 1;
	private Integer				rows				= 10;
	private SysResource			resource			= new SysResource();
	private Map<String, Object>	map					= new HashMap<String, Object>();
	private SysUser				sysUser				= getLoginUser();
	private String				content				= "";
	String						account				= "";								// 用于记录当前登录用户账号
	String						name				= "";								// 用于记录当前登录用户姓名
																						
	/**
	 * 功能描述：系统用户管理
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午3:24:14
	 * @return
	 */
	public Result manage() {
		return dispatch();
	}
	
	/**
	 * 功能描述：查询订单信息
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:52:04
	 * @return
	 */
	public Result searchResource() {
		Page pager = new Page(page, rows);
		List<SysResource> resources = sysResourceService.findResourceList(resource, pager);
		return jsonResult(resources, pager.getTotalCount());
	}
	
	/**
	 * 功能描述：打开模块选择框
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月3日下午6:15:45
	 * @return
	 */
	public Result menuSelect() {
		return dispatch();
	}
	
	/**
	 * 功能描述：保存资源信息
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午4:11:43
	 * @return
	 */
	public Result saveResource() {
		try {
			SysResource sr = sysResourceService.findSysResourceByNo(resource.getResourceNo(), resource.getId());
			if (sr != null) {
				simpleResult(map, false, "提交失败!资源编号已存在!");
			} else {
				sysResourceService.saveOrUpdateResource(resource);
				simpleResult(map, true, "");
				if (sysUser != null) {
					account = sysUser.getAccount();
					name = sysUser.getUserName();
				}
				content = "账号：" + account + "新增一个资源，资源ID为：" + resource.getId();
				sysActionLogService.addSysLog(account, name, "sysResource", "保存", content);
			}
		} catch (Exception e) {
			simpleResult(map, false, "保存资源出错!");
			slog(Constants.log_error, "保存资源出错!", e);
		}
		return jsonResult(map);
	}
	
	/**
	 * 功能描述：冻结资源
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午5:42:54
	 * @return
	 */
	public Result forzenResource() {
		try {
			resource = sysResourceService.findSysResourceById(resource.getId());
			if (resource.getStatus() != null && resource.getStatus().equals(1)) {
				simpleResult(map, false, "冻结失败,资源已冻结!");
			} else {
				resource.setStatus(1);
				sysResourceService.saveOrUpdateResource(resource);
				simpleResult(map, true, "");
			}
			if (sysUser != null) {
				account = sysUser.getAccount();
				name = sysUser.getUserName();
			}
			content = "账号：" + account + "在" + DateUtils.getCurrentSysDate() + "冻结一个资源，资源ID为：" + resource.getId();
			sysActionLogService.addSysLog(account, name, "sysResource", WfConstants.frozen, content);
		} catch (Exception e) {
			simpleResult(map, false, "冻结资源出错!");
			slog(Constants.log_error, "冻结资源出错!", e);
		}
		return jsonResult(map);
	}
	
	/**
	 * 功能描述：公开资源
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午5:42:54
	 * @return
	 */
	public Result publicResource() {
		try {
			resource = sysResourceService.findSysResourceById(resource.getId());
			if (resource == null) {
				simpleResult(map, false, "资源不存在!");
			} else {
				resource.setIsPublic(1);
				sysResourceService.saveOrUpdateResource(resource);
			}
			if (sysUser != null) {
				account = sysUser.getAccount();
				name = sysUser.getUserName();
			}
			content = "账号：" + account + "公开一个资源，资源ID为：" + resource.getId();
			sysActionLogService.addSysLog(account, name, "sysResource", "公开", content);
			simpleResult(map, true, "");
		} catch (Exception e) {
			simpleResult(map, false, "公开资源出错!");
			slog(Constants.log_error, "公开资源出错!", e);
		}
		return jsonResult(map);
	}
	
	/**
	 * 功能描述：私有资源
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午5:42:54
	 * @return
	 */
	public Result privateResource() {
		try {
			resource = sysResourceService.findSysResourceById(resource.getId());
			if (resource == null) {
				simpleResult(map, false, "资源不存在!");
			} else {
				resource.setIsPublic(0);
				sysResourceService.saveOrUpdateResource(resource);
			}
			if (sysUser != null) {
				account = sysUser.getAccount();
				name = sysUser.getUserName();
			}
			content = "账号：" + account + "私有一个资源，资源ID为：" + resource.getId();
			sysActionLogService.addSysLog(account, name, "sysResource", "私有", content);
			simpleResult(map, true, "");
		} catch (Exception e) {
			simpleResult(map, false, "私有资源出错!");
			slog(Constants.log_error, "私有资源出错!", e);
		}
		return jsonResult(map);
	}
	
	/**
	 * 功能描述：解结资源
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午5:42:54
	 * @return
	 */
	public Result unForzenResource() {
		try {
			resource = sysResourceService.findSysResourceById(resource.getId());
			if (resource.getStatus() != null && resource.getStatus().equals(0)) {
				simpleResult(map, false, "解冻失败,资源已解冻!");
			} else {
				resource.setStatus(0);
				sysResourceService.saveOrUpdateResource(resource);
				simpleResult(map, true, "");
			}
			if (sysUser != null) {
				account = sysUser.getAccount();
				name = sysUser.getUserName();
			}
			content = "账号：" + account + "解冻一个资源，资源ID为：" + resource.getId();
			sysActionLogService.addSysLog(account, name, "sysResource", WfConstants.remove_Frozen, content);
		} catch (Exception e) {
			simpleResult(map, false, "解冻资源出错!");
			slog(Constants.log_error, "解冻资源出错!", e);
		}
		return jsonResult(map);
	}
	
	public Result deleteResource() {
		try {
			resource = sysResourceService.findSysResourceById(resource.getId());
			if (resource.getDelFlag() != null && resource.getDelFlag()) {
				simpleResult(map, false, "删除失败,资源已删除!");
			} else {
				resource.setDelFlag(true);
				sysResourceService.saveOrUpdateResource(resource);
				simpleResult(map, true, "");
			}
			if (sysUser != null) {
				account = sysUser.getAccount();
				name = sysUser.getUserName();
			}
			content = "账号：" + account + "删除一个资源，资源ID为：" + resource.getId();
			sysActionLogService.addSysLog(account, name, "sysResource", WfConstants.deleteString, content);
		} catch (Exception e) {
			simpleResult(map, false, "删除资源出错!");
			slog(Constants.log_error, "删除资源出错!", e);
		}
		return jsonResult(map);
	}
	
	/**
	 * 功能描述：根据ID获取资源对象
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午4:53:29
	 * @return
	 */
	public Result getResourceById() {
		try {
			resource = sysResourceService.findSysResourceById(resource.getId());
			simpleResult(map, true, "");
			map.put("resource", resource);
		} catch (Exception e) {
			simpleResult(map, false, "读取资源出错!");
			slog(Constants.log_error, "读取资源出错!", e);
		}
		return jsonResult(map);
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
	
	@Override
	public SysResource getModel() {
		return resource;
	}
	
	public SysResource getResource() {
		return resource;
	}
	
	public void setResource(SysResource resource) {
		this.resource = resource;
	}
	
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	
	public Map<String, Object> getMap() {
		return map;
	}
	
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
}
