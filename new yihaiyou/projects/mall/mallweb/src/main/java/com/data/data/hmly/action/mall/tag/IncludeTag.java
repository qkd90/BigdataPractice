package com.data.data.hmly.action.mall.tag;

import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class IncludeTag extends TagSupport {

    /**
     * .
     */
    private static final long serialVersionUID = -2165476717536407223L;

    private final Log log = LogFactory.getLog(IncludeTag.class);

    private Object fileAttr;
    private String encode = "utf-8";
    private Integer validDay = 7;
    private String targetObject;
    private String targetMethod;
    /**
     * 如果是空串，直接调用，如果是非空，且非json格式，则为long类型，否则从json中取对应的类型和数值
     */
    private String objs;

    @Override
    public int doStartTag() throws JspException {
        if (fileAttr == null || StringUtils.isBlank(fileAttr.toString())) {
            log.error("Tag attr 'fileAttr' is blank");
            return Tag.SKIP_BODY;
        }
        try {
            PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
            String filePath = propertiesManager.getString("HTML_DIR") + fileAttr;
            File file = new File(filePath);
            if (file.exists() && validDayFile(file)) {
                String html = FileUtils.readFileToString(file, encode);
                pageContext.getOut().write(html);
            } else {
                if (StringUtils.isNotBlank(targetMethod, targetObject)) {
                    Object object = SpringContextHolder.getBean(targetObject);
                    Method[] methods = object.getClass().getDeclaredMethods();
                    for (Method method : methods) {
                        if (method.getName().equals(targetMethod)) {
                            try {
                                if (StringUtils.isBlank(objs)) {
                                    method.invoke(object);
                                } else if (!objs.startsWith("{")) {
                                    method.invoke(object, Long.parseLong(objs.trim()));
                                }
                                if (file.exists()) {
                                    String html = FileUtils.readFileToString(file, encode);
                                    pageContext.getOut().write(html);
                                }
                            } catch (Exception e) {
                                log.error(e.getMessage(), e);
                            }
                        }
                    }
                } else {
                    pageContext.getOut().write(String.format("%s Not Found", fileAttr));
                }
            }
            return Tag.SKIP_BODY;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new JspException(e.getMessage(), e);
        }
    }

//    private void makeFile(String filePath) {
//        if (StringUtils.isNotBlank(targetMethod, targetObject)) {
//            Object object = SpringContextHolder.getBean(targetObject);
//            Method[] methods = object.getClass().getDeclaredMethods();
//            for (Method method : methods) {
//                if (method.getName().equals(targetMethod)) {
//                    try {
//                        if (StringUtils.isBlank(objs)) {
//                            method.invoke(object);
//                        } else if (!objs.startsWith("{")) {
//                            method.invoke(object, Long.parseLong(objs.trim()));
//                        }
//                    } catch (Exception e) {
//                        log.error(e.getMessage(), e);
//                    }
//                }
//            }
//        }
//    }

    private Boolean validDayFile(File file) {
        int validDayLong = validDay * 24 * 60 * 60 * 1000;
        long passTime = System.currentTimeMillis() - file.lastModified();
        return passTime > validDayLong ? false : true;
    }

    public Object getFileAttr() {
        return fileAttr;
    }

    public void setFileAttr(Object fileAttr) throws JspException {
        if (fileAttr != null) {
            this.fileAttr = ExpressionEvaluatorManager.evaluate("fileAttr", fileAttr.toString(), Object.class, this, pageContext);
        }
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public String getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(String targetObject) {
        this.targetObject = targetObject;
    }

    public String getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }

    public String getObjs() {
        return objs;
    }

    public void setObjs(String objs) {
        this.objs = objs;
    }

    public Integer getValidDay() {
        return validDay;
    }

    public void setValidDay(Integer validDay) {
        this.validDay = validDay;
    }
}
