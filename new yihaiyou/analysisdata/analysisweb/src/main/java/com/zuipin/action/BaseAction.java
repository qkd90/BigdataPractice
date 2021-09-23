package com.zuipin.action;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.ServletDispatcherResult;

import com.framework.hibernate.util.Entity;
import com.framework.hibernate.util.Page;
import com.framework.struts.ContentType;
import com.framework.struts.DefaultServletDispatcherResult;
import com.framework.struts.JSONResult;
import com.framework.struts.RedirectResult;
import com.framework.struts.TextResult;
import com.opensymphony.xwork2.ActionChainResult;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import com.zuipin.service.entity.SysUser;
import com.zuipin.util.Constants;
import com.zuipin.util.ConvertUtils;
import com.zuipin.util.Jacksons;
import com.zuipin.util.JsonReader;
import com.zuipin.zuipinentity.Paylog;

@SuppressWarnings("serial")
public class BaseAction extends JxmallAction {
	private Container			container;
	public Page					pager;
	public String				json;
	
	protected String			page;
	protected String			rows;
	
	private static final int	pageIndex	= 1;							// 默认开始页面索引
	private static final int	pageSize	= 20;							// 默认页面大小
																			
	/**
	 * 日志记录， 子类可以直接调用该对象
	 */
	protected final Logger		log			= Logger.getLogger(getClass());
	
	public ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}
	
	protected Result json(JSON json) {
		return new JSONResult(json);
	}
	
	protected Result json(JSON json, JsonConfig config) {
		return new JSONResult(json, config);
	}
	
	public String getRealyPath(String path) {
		return getServletContext().getRealPath(path);
	}
	
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	
	protected HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}
	
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	
	protected Object getParameter(String name) {
		return getRequest().getParameter(name);
	}
	
	protected void setAttribute(String key, Object value) {
		getRequest().setAttribute(key, value);
	}
	
	protected Result dispatch(String location) {
		return new ServletDispatcherResult(location);
	}
	
	protected Result dispatch500() {
		return new ServletDispatcherResult("/common/500.jsp");
	}
	
	protected Result dispatch404() {
		return new ServletDispatcherResult("/commmon/404.jsp");
	}
	
	protected Result dispatch() {
		return new DefaultServletDispatcherResult();
	}
	
	protected Result redirect(String location) {
		return new RedirectResult(location);
	}
	
	protected Result text(String text) {
		return new TextResult(text);
	}
	
	protected Result text(String text, String charSet) {
		return new TextResult(text, charSet);
	}
	
	protected Result forward(String actionName) {
		ActionChainResult result = new ActionChainResult("", String.format("%s", actionName), null);
		container.inject(result);
		return result;
	}
	
	protected Result redirectAction(String actionName) {
		return new RedirectResult(String.format("%s.%s", actionName, ContentType.HTML.getExtension()));
	}
	
	@Inject
	public void setContainer(Container container) {
		this.container = container;
	}
	
	public Paylog getSessionMember() {
		return (Paylog) getSession().getAttribute(Constants.MEMBER);
	}
	
	/**
	 * 功能描述：根据session的key返回字符串形式的session对象
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2014年11月8日上午9:24:50
	 * @param attr
	 * @return
	 */
	public String getSessionAttr(String attr) {
		return (String) getSession().getAttribute(attr);
	}
	
	/**
	 * @param map
	 * @简单json数据
	 */
	public void simpleResult(Map<String, Object> map, Boolean success, String msg) {
		map.put("success", success);
		map.put("errorMsg", msg);
	}
	
	public Result simpleJsonResult(Map<String, Object> map, Boolean success, String msg) {
		simpleResult(map, success, msg);
		String json = readJsonFilter(map);
		return json(JSONObject.fromObject(json));
	}
	
	public Result messToLogin(Map<String, Object> map, String json) {
		simpleResult(map, false, "请登录!");
		json = readJson(map);
		return json(JSONObject.fromObject(json));
	}
	
	public Result messToLogin(String json) {
		json = "{\"errorMsg\":\"请登录！\",\"success\":false}";
		return json(JSONObject.fromObject(json));
	}
	
	public String getJson() {
		return json;
	}
	
	public void setJson(String json) {
		this.json = json;
	}
	
	/**
	 * @return the pager
	 */
	public Page getPager() {
		if (pager == null) {
			pager = new Page();
			pager.setPageIndex((page == null || "".equals(page)) ? pageIndex : Integer.parseInt(page));
			pager.setPageSize((rows == null || "".equals(rows)) ? pageSize : Integer.parseInt(rows));
		}
		return pager;
	}
	
	/**
	 * @param pager
	 *            the pager to set
	 */
	public void setPager(Page pager) {
		this.pager = pager;
	}
	
	/**
	 * @param map
	 * @return 返回json
	 */
	public String readJson(Object map) {
		String json = Jacksons.me().readAsString(map);
		return json;
	}
	
	/**
	 * @param map
	 * @return 返回json
	 */
	public String readJsonFilter(Object map) {
		String json = Jacksons.me().filter(new String[] { "product" }, new String[] { "productCat" }).readAsString(map);
		return json;
	}
	
	/**
	 * @param map
	 * @return 返回json
	 */
	public String readJsonFilter(Object map, String[] target, String[] arr) {
		String json = Jacksons.me().filter(target, arr).readAsString(map);
		return json;
	}
	
	public <T extends Entity> String initJsonList(List<T> list, Long count) {
		JsonReader<T> jr = new JsonReader<T>();
		jr.setRows(list);
		jr.setTotal(count);
		return readJson(jr);
	}
	
	public String initJsonMapList(List<Map<?, ?>> list, Long count) {
		JsonReader<Map<?, ?>> jr = new JsonReader<Map<?, ?>>();
		jr.setRows(list);
		jr.setTotal(count);
		return readJson(jr);
	}
	
	public <T extends Entity> String initJsonListFilter(List<T> list, Long count) {
		JsonReader<T> jr = new JsonReader<T>();
		jr.setRows(list);
		jr.setTotal(count);
		return readJsonFilter(jr);
	}
	
	public <T> Result jsonResult(List<T> rows) {
		JsonReader<T> jr = new JsonReader<T>();
		jr.setRows(rows);
		json = readJson(jr);
		Result result = json(JSONObject.fromObject(json));
		return result;
	}
	
	/**
	 * 功能描述：返回struts结果集
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2014年12月19日上午10:02:52
	 * @param rows
	 *            内容
	 * @param count
	 *            记录数
	 * @return
	 */
	public <T> Result jsonResult(List<T> rows, Long count) {
		JsonReader<T> jr = new JsonReader<T>();
		jr.setRows(rows);
		jr.setTotal(count);
		json = readJson(jr);
		Result result = json(JSONObject.fromObject(json));
		return result;
	}
	
	/**
	 * 功能描述：返回struts结果集
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2014年12月19日上午10:02:52
	 * @param rows
	 *            内容
	 * @param count
	 *            记录数
	 * @return
	 */
	public <T> Result jsonResult(List<T> rows, Integer count) {
		JsonReader<T> jr = new JsonReader<T>();
		jr.setRows(rows);
		jr.setTotal(ConvertUtils.objectToLong(count));
		json = readJsonFilter(jr);
		Result result = json(JSONObject.fromObject(json));
		return result;
	}
	
	public <T> Result jsonResultFilter(List<T> rows, Long count) {
		JsonReader<T> jr = new JsonReader<T>();
		jr.setRows(rows);
		jr.setTotal(count);
		json = readJsonFilter(jr);
		Result result = json(JSONObject.fromObject(json));
		return result;
	}
	
	/**
	 * 功能描述：返回struts结果集
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2014年12月19日上午10:02:52
	 * @param rows
	 *            内容
	 * @param count
	 *            记录数
	 * @param footers
	 *            页脚数据
	 * @return
	 */
	public <T> Result jsonResult(List<T> rows, Long count, List<T> footers) {
		JsonReader<T> jr = new JsonReader<T>();
		jr.setRows(rows);
		jr.setTotal(count);
		jr.setFooter(footers);
		json = readJson(jr);
		Result result = json(JSONObject.fromObject(json));
		return result;
	}
	
	public Result jsonResult(Map<String, Object> map) {
		json = readJsonFilter(map);
		Result res = json(JSONObject.fromObject(json));
		return res;
	}
	
	public SysUser getLoginUser() {
		SysUser user = (SysUser) getSession().getAttribute("loginuser");
		if (user != null) {
			return user;
		} else {
			return new SysUser();
		}
	}
	
	public String getPage() {
		return page;
	}
	
	public void setPage(String page) {
		this.page = page;
	}
	
	public String getRows() {
		return rows;
	}
	
	public void setRows(String rows) {
		this.rows = rows;
	}
	
}
