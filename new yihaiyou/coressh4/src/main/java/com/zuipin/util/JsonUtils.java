package com.zuipin.util;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JsonUtils {
	
	private final static Log	log			= LogFactory.getLog(JsonUtils.class);
	private final static String	resultFlag	= "result";
	
	public static void write(HttpServletResponse response, JSON json) {
		write(response, json.toString());
	}
	
	public static void write(HttpServletResponse response, String str) {
		response.setContentType("text/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		try {
			response.getWriter().println(str);
			response.getWriter().close();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public static JSONObject write(String key, Object value) {
		JSONObject o = new JSONObject();
		o.put(key, value);
		return o;
	}
	
	public static JSONObject write(Object value) {
		JSONObject o = new JSONObject();
		o.put(resultFlag, value);
		return o;
	}
	
	public static JSONObject getSuccess() {
		JSONObject o = new JSONObject();
		o.put(resultFlag, true);
		return o;
	}
	
	public static JSONObject getFailure() {
		JSONObject o = new JSONObject();
		o.put(resultFlag, false);
		return o;
	}
	
	public static JSONObject getFailure(String msg) {
		JSONObject o = getFailure();
		o.put("msg", msg);
		return o;
	}
	
	public static JSONObject getSuccess(String msg) {
		JSONObject o = getSuccess();
		o.put("msg", msg);
		return o;
	}
	
	public static JSONObject getJSONObject(Object object) {
		JSONObject o = JSONObject.fromObject(object);
		if (object != null)
			o.put("msg", true);
		else
			o.put("msg", false);
		return o;
	}
	
	public static JSONObject fromObject(Object object, final Boolean full) {
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
			
			@Override
			public Object processObjectValue(String arg0, Object value, JsonConfig arg2) {
				// TODO Auto-generated method stub
				if (value instanceof Date) {
					Date day = (Date) value;
					return full ? DateUtils.formatDate(day) : DateUtils.formatDate(day);
				}
				return null;
			}
			
			@Override
			public Object processArrayValue(Object arg0, JsonConfig arg1) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		return JSONObject.fromObject(object, config);
	}
	
	public static String getJsonpFailure(String callback) {
		JSONObject o = new JSONObject();
		o.put(resultFlag, false);
		return new StringBuilder().append(callback).append("(").append(o).append(")").toString();
	}
	
	public static String getJsonpSuccess(String callback) {
		JSONObject o = new JSONObject();
		o.put(resultFlag, true);
		return new StringBuilder().append(callback).append("(").append(o).append(")").toString();
	}
	
	public static String writeJsonp(String callback, Object value) {
		JSONObject o = new JSONObject();
		o.put(resultFlag, value);
		return new StringBuilder().append(callback).append("(").append(o).append(")").toString();
	}
}
