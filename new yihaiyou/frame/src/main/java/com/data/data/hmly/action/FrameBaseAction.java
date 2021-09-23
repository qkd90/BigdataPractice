package com.data.data.hmly.action;

import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.UserType;
import com.data.data.hmly.util.Jacksons;
import com.data.data.hmly.util.JsonReader;
import com.data.data.hmly.util.JsonTabelDataReader;
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
import com.zuipin.action.JxmallAction;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.ServletDispatcherResult;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class FrameBaseAction extends JxmallAction {
	private Container container;
	public Page pager;
	private String json;
    protected final String loginCookieKey = "loginCookieKey";
    protected String loginLogoImg = "/images/lvxbang_logo_login.png";  // 登录默认logo
	protected String logoImg = "/images/lvxbang_logo.png";  // 首页默认logo
	protected String homePage = "/main/index/merchant.jhtml";   // 默认首页
	protected String systemTitle = "后台管理系统";  // 首页默认文字


	@Override
	public ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	@Override
	protected Result json(JSON json) {
		return new JSONResult(json);
	}

	@Override
	protected Result json(JSON json, JsonConfig config) {
		return new JSONResult(json, config);
	}

	@Override
	public String getRealyPath(String path) {
		return getServletContext().getRealPath(path);
	}

	@Override
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	@Override
	protected HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	@Override
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	@Override
	protected Object getParameter(String name) {
		return getRequest().getParameter(name);
	}

	@Override
	protected void setAttribute(String key, Object value) {
		getRequest().setAttribute(key, value);
	}
	protected Object getAttribute(String key) {
		return getRequest().getAttribute(key);
	}

	@Override
	protected Result dispatch(String location) {
		return new ServletDispatcherResult(location);
	}

	@Override
	protected Result dispatch500() {
		return new ServletDispatcherResult("/common/500.jsp");
	}

	@Override
	protected Result dispatch404() {
		return new ServletDispatcherResult("/commmon/404.jsp");
	}

	@Override
	protected Result dispatch() {
		return new DefaultServletDispatcherResult();
	}

	@Override
	protected Result redirect(String location) {
		return new RedirectResult(location);
	}

	@Override
	protected Result text(String text) {
		return new TextResult(text);
	}

	@Override
	protected Result text(String text, String charSet) {
		return new TextResult(text, charSet);
	}

	@Override
	protected Result forward(String actionName) {
		ActionChainResult result = new ActionChainResult("", String.format("%s", actionName), null);
		container.inject(result);
		return result;
	}

	@Override
	protected Result redirectAction(String actionName) {
		return new RedirectResult(String.format("%s.%s", actionName, ContentType.HTML.getExtension()));
	}

	@Override
	@Inject
	public void setContainer(Container container) {
		this.container = container;
	}

	public String getHex() {
		long time = new Date().getTime();
		String hex = Long.toHexString(time);
		return hex;
	}

	public String getHex(Date date) {
		long time = date.getTime();
		String hex = Long.toHexString(time);
		return hex;
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

	/**
	 * 功能描述：跳转登录页面
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年1月4日上午11:05:29
	 * @return
	 */
	protected Result dispatchlogin() {
		return new ServletDispatcherResult("/WEB-INF/jsp/sys/login/login.jsp");
	}

	/**
	 * 跳转一海游登录页面
	 * @return
	 */
	protected Result dispatchYhylogin() {
		return new ServletDispatcherResult("/WEB-INF/jsp/yhy/yhyLogin/login.jsp");
	}

	/**
	 * @param map
	 * @return 返回json
	 */


	public String readJsonMenu(Object map) {
		String json = Jacksons.me().filter(new String[] { "resource" }, new String[] { "sysMenu" }).readAsString(map);
		return json;
	}

	/**
	 * @param map
	 * @return 返回json
	 */
	public String readJsonFilter(Object map) {
		String json = Jacksons.me().filter(new String[] { "product" }, new String[] { "productCat", "supplier" }).readAsString(map);
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

	/**
	 * 功能描述：转化List 为json字符串,返回json Result
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年1月15日上午10:50:50
	 * @param rows
	 * @return
	 */
	public <T> Result jsonResult(List<T> rows) {
		JsonReader<T> jr = new JsonReader<T>();
		jr.setRows(rows);
		json = readJson(jr);
		Result result = json(JSONObject.fromObject(json,JsonFilter.getIncludeConfig()));
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

	public <T> Result jsonResult(List<T> rows, Integer count, JsonConfig config) {
		JsonReader<T> jr = new JsonReader<T>();
		jr.setRows(rows);
		jr.setTotal(count.longValue());
		json = readJson(jr);
		Result result = json(JSONObject.fromObject(json, config));
		return result;
	}

	public <T> Result jsonResult(List<T> rows, Integer count) {
		JsonReader<T> jr = new JsonReader<T>();
		jr.setRows(rows);
		jr.setTotal(count.longValue());
		JsonConfig jsonConfig = JsonFilter.getIncludeConfig(null ,null);
		json = readJson(jr);
		Result result = json(JSONObject.fromObject(json, jsonConfig));
		return result;
	}

	public <T> Result datagrid(List<T> rows, Integer count, JsonConfig config) {
		JsonReader<T> jr = new JsonReader<T>();
		jr.setRows(rows);
		jr.setTotal(count.longValue());
		JSONObject fromObject = JSONObject.fromObject(jr, config);
		return json(fromObject);
	}



	public <T> Result datagrid(List<T> rows, Integer count) {
		JsonReader<T> jr = new JsonReader<T>();
		jr.setRows(rows);
		jr.setTotal(count.longValue());
		JsonConfig jsonConfig = JsonFilter.getIncludeConfig(null, null);
		JSONObject fromObject = JSONObject.fromObject(jr, jsonConfig);
		return json(fromObject);
	}

	public <T> Result datagrid(List<T> rows, JsonConfig config) {
		JsonReader<T> jr = new JsonReader<T>();
		jr.setRows(rows);
		JSONObject fromObject = JSONObject.fromObject(jr, config);
		return json(fromObject);
	}

	public <T> Result datagrid(List<T> rows) {
		JsonReader<T> jr = new JsonReader<T>();
		jr.setRows(rows);
		JsonConfig jsonConfig = JsonFilter.getIncludeConfig(null, null);
		JSONObject fromObject = JSONObject.fromObject(jr, jsonConfig);
		return json(fromObject);
	}

	public <T> Result dataTabel(List<T> rows, Integer count, JsonConfig config) {
		JsonTabelDataReader<T> jtd = new JsonTabelDataReader<T>();
		jtd.setData(rows);
		jtd.setTotal(count.longValue());
		JSONObject fromObject = JSONObject.fromObject(jtd, config);
		return json(fromObject);
	}

	public <T> Result dataTabel(List<T> rows, Integer count) {
		JsonTabelDataReader<T> jtd = new JsonTabelDataReader<T>();
		jtd.setData(rows);
		jtd.setTotal(count.longValue());
		JsonConfig jsonConfig = JsonFilter.getIncludeConfig(null, null);
		JSONObject fromObject = JSONObject.fromObject(jtd, jsonConfig);
		return json(fromObject);
	}

	public <T> Result dataTabel(List<T> rows) {
		JsonTabelDataReader<T> jtd = new JsonTabelDataReader<T>();
		jtd.setData(rows);
		JsonConfig jsonConfig = JsonFilter.getIncludeConfig(null, null);
		JSONObject fromObject = JSONObject.fromObject(jtd, jsonConfig);
		return json(fromObject);
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


	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	/**
	 * 功能描述：记录log
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午4:14:49
	 * @param logtype
	 * @param logmsg
	 * @param e
	 */
	public void slog(Integer logtype, String logmsg, Exception e) {
		Logger.getLogger(getClass()).error(logmsg, e);
		switch (logtype) {
		case 1:
			Logger.getLogger(getClass()).info(logmsg, e);
			break;
		case 2:
			Logger.getLogger(getClass()).debug(logmsg, e);
			break;
		case 3:
			Logger.getLogger(getClass()).error(logmsg, e);
			break;
		case 4:
			Logger.getLogger(getClass()).warn(logmsg, e);
			break;
		default:
			break;
		}
	}

	public SysUser getLoginUser() {
		SysUser user = (SysUser) getSession().getAttribute("loginuser");
		if (user != null) {
			return user;
		} else {
			return null;
		}
	}

	public Boolean isSupperAdmin() {
		return UserType.AllSiteManage == getLoginUser().getUserType();
	}

	public Boolean isSiteAdmin() {
		return UserType.SiteManage == getLoginUser().getUserType();
	}

	public Boolean isCommpanyAdmin() {
		return UserType.CompanyManage == getLoginUser().getUserType();
	}

	public SysSite getSite() {
		return getLoginUser().getSysSite();
	}

	public SysUnit getCompanyUnit() {
		return getLoginUser().getSysUnit().getCompanyUnit();
	}

    public String getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(String logoImg) {
        this.logoImg = logoImg;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getLoginLogoImg() {
        return loginLogoImg;
    }

    public void setLoginLogoImg(String loginLogoImg) {
        this.loginLogoImg = loginLogoImg;
    }

    public String getSystemTitle() {
        return systemTitle;
    }

    public void setSystemTitle(String systemTitle) {
        this.systemTitle = systemTitle;
    }
}
