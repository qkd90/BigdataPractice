package com.zuipin.action;

import com.framework.struts.ContentType;
import com.framework.struts.DefaultServletDispatcherResult;
import com.framework.struts.JSONResult;
import com.framework.struts.RedirectResult;
import com.framework.struts.TextResult;
import com.opensymphony.xwork2.ActionChainResult;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import com.zuipin.entity.Member;
import com.zuipin.util.Constants;
import com.zuipin.util.Jacksons;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.ServletDispatcherResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class JxmallAction extends ActionSupport {
	protected Map<String, Object> result = new HashMap<String, Object>();
	private Container container;
	private final static Log log = LogFactory.getLog(JxmallAction.class);

	public ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	protected Result json(JSON json) {
		return new JSONResult(json);
	}

	protected Result json(JSON json, JsonConfig config) {
		return new JSONResult(json, config);
	}

//	protected Result json(JSON json, String... excludes) {
//		JsonConfig config = JsonFilter.getFilterConfig(excludes);
//		return new JSONResult(json, config);
//	}

	public String getRealyPath(String path) {
		return getServletContext().getRealPath(path);
	}

	public ServletContext getServletContext(String path) {
		return getServletContext().getContext(path);
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

	protected void setSessionAttribute(String key, Object value) {
		getRequest().getSession().setAttribute(key, value);
	}

	@SuppressWarnings("unchecked")
	protected <T> T getSessionAttribute(String key) {
		return (T) getRequest().getSession().getAttribute(key);
	}

	protected Result dispatch(String location) {
		return new ServletDispatcherResult(location);
	}

	protected Result dispatch500() {
		return new ServletDispatcherResult("/WEB-INF/jsp/lvxbang/common/500.jsp");
	}

	protected Result dispatch404() {
		return new ServletDispatcherResult("/WEB-INF/jsp/lvxbang/common/404.jsp");
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

	@Override
	@Inject
	public void setContainer(Container container) {
		this.container = container;
	}

	public Member getSessionMember() {
		return (Member) getSession().getAttribute(Constants.MEMBER);
	}

	public Cookie getCookie(String name) {
		Cookie[] cookies = getRequest().getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().trim().equals(name)) {
				return cookie;
			}
		}
		return null;
	}

	/**
	 * 格式化通过ajax传输的中文
	 * 
	 * @param s
	 * @return
	 * @throws Exception
	 */
	protected String stringToString(String s) {
		try {
			return URLDecoder.decode(URLDecoder.decode(s, "utf-8"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			log.error("中文转换发生异常", e);
			return "";
		}
	}

	public String readJson(Object map) {
		String json = Jacksons.me().readAsString(map);
		return json;
	}

	public Result jsonResult(Map<String, Object> map) {
		String json = readJson(map);
		Result res = json(JSONObject.fromObject(json));
		return res;
	}

	/**
	 * @param map
	 * @简单json数据
	 */
	public void simpleResult(Map<String, Object> map, Boolean success, String msg) {
		map.put("success", success);
		map.put("errorMsg", msg);
	}
}
