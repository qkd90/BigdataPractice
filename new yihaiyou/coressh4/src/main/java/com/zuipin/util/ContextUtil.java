package com.zuipin.util;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;

public class ContextUtil {
    
    /**
     * Spring工具类
     */
    public static WebApplicationContext webApplicationContext;
    
    /**
     * 容器的上下文
     */
    public static ServletContext        servletContext;
    
    /**
     * 取Spring配制文件中的Bean
     * 
     * @param name bean 名称
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) webApplicationContext.getBean(name);
    }
    
    /**
     * 取容器上下文中的内容
     * 
     * @param attribute
     * @return
     */
    public static Object getAttribute(String key) {
        return servletContext.getAttribute(key);
    }
    
    /**
     * @return
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2012-8-11
     * @功能说明：获取webapps路径
     */
    public static String getRealPath() {
        return servletContext.getRealPath("");
    }
    
    /**
     * 设置上下文内容
     * 
     * @param attributeKey
     * @param object
     */
    public static void setAttribute(String key, Object value) {
        servletContext.setAttribute(key, value);
    }
    
    public static void setWebApplicationContext(WebApplicationContext webApplicationContext) {
        ContextUtil.webApplicationContext = webApplicationContext;
    }
    
    public static void setServletContext(ServletContext servletContext) {
        ContextUtil.servletContext = servletContext;
    }
    
}
