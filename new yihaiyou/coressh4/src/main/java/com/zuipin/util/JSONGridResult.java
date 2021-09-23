/**
 * @(#)JSONGridResult.java 2011-5-18
 * Copyright 2000-2011 by Yinhoo Corporation.
 * All rights reserved.
 */
package com.zuipin.util;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.ValueStack;

public class JSONGridResult implements Result {
    private static final long serialVersionUID = 3675233089840896071L;
    private Object            responseObject;
    private String            expression;
    private String            columns;
    private String            datePattern      = "yyyy-MM-dd HH:mm:ss";
    private JsonConfig        config;
    
    public JSONGridResult() {
    }
    
    public JSONGridResult(Object responseObject) {
        this.responseObject = responseObject;
    }
    
    public JSONGridResult(Object responseObject, JsonConfig config) {
        this.responseObject = responseObject;
        this.config = config;
    }
    
    public JSONGridResult(Object responseObject, String columns) {
        this.responseObject = responseObject;
        this.columns = columns;
    }
    
    public JSONGridResult(Object responseObject, String columns, String datePattern) {
        this.responseObject = responseObject;
        this.columns = columns;
        this.datePattern = datePattern;
    }
    
    public void execute(ActionInvocation invocation) throws Exception {
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            if (this.config != null) {
                writer.print(JSONArray.fromObject(getResponseObject(invocation), config));
            } else {
                JsonConfig jsonConfig = new JsonConfig();
                // 处理日期在前台转换为object问题
                jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(datePattern));
                // 处理Boolean为null情况在前台显示false问题
                jsonConfig.registerJsonValueProcessor(Boolean.class, new JsonBooleanValueProcessor());
                if (!StringUtils.isEmpty(this.columns)) {
                    this.columns += ",error,page,rows,total";
                    jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
                        public boolean apply(Object source/* 属性的拥有者 */, String name /* 属性名字 */, Object value/* 属性值 */) {
                            // return true to skip name
                            return !getColumns().contains(name);
                        }
                    });
                }
                writer.print(JSONArray.fromObject(getResponseObject(invocation), jsonConfig));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    private Object getResponseObject(ActionInvocation invocation) {
        if (expression != null) {
            ValueStack stack = invocation.getStack();
            return stack.findValue(expression);
        }
        return responseObject;
    }
    
    public void setExpression(String expression) {
        this.expression = expression;
    }
    
    /**
     * @param columns
     * the columns to set
     */
    public void setColumns(String columns) {
        this.columns = columns;
    }
    
    /**
     * @return the columns
     */
    public String getColumns() {
        return columns;
    }
    
    /**
     * @param datePattern
     * the datePattern to set
     */
    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }
    
    /**
     * @return the datePattern
     */
    public String getDatePattern() {
        return datePattern;
    }
    
}
