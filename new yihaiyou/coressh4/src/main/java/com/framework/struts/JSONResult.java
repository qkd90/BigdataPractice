package com.framework.struts;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;

public class JSONResult implements Result {
	private static final long	serialVersionUID	= 3675233089840896071L;
	private final Log			log					= LogFactory.getLog(getClass());
	private final Object		json;
	private JsonConfig			config;

	public JSONResult(Object json) {
		this.json = json;
	}

	public JSONResult(Object json, JsonConfig config) {
		this.json = json;
		this.config = config;
	}

	@Override
	public void execute(ActionInvocation invocation) throws Exception {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			String agent = ServletActionContext.getRequest().getHeader("USER-AGENT").toLowerCase(); // 判断浏览器
			if (agent.indexOf("ie") > -1) {
				response.setContentType("text/plain;charset=UTF-8");
			} else {
				response.setContentType("application/json;charset=UTF-8");
			}
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			if (config != null) {
				writer.print(JSONObject.fromObject(json, config));
			} else {
				if (json instanceof JSON) {
					writer.print(json.toString());
				} else {
					JsonConfig jsonConfig = new JsonConfig();
					// 处理日期在前台转换为object问题
					jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
					// 处理Boolean为null情况在前台显示false问题
					jsonConfig.registerJsonValueProcessor(Boolean.class, new JsonBooleanValueProcessor());
					writer.print(JSONObject.fromObject(json, jsonConfig));
				}
			}
			writer.close();
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

}
