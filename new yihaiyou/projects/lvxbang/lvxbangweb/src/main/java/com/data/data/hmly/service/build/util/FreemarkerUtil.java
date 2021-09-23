package com.data.data.hmly.service.build.util;

import com.zuipin.util.Constants;
import com.zuipin.util.ContextUtil;
import com.zuipin.util.FileUtil;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StaticConstants;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;


public class FreemarkerUtil {

    private static Log log = LogFactory.getLog(FreemarkerUtil.class);


    private static PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");

    /**
     * @param data     填充ftl数据Map
     * @param ftlDir   ftl模版根目录
     * @param ftlName  模版路径
     * @param htmlName 生成htm路径
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2012-8-7
     * @功能说明：
     */
    public static void createHtml(Map<Object, Object> data, String ftlDir, String ftlName, String htmlName) {
        try {
            PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
            data.put("path", propertiesManager.getString("path"));
            data.put("resPath", propertiesManager.getString("respath"));
            data.put("INDEX_PATH", ContextUtil.getAttribute("INDEX_PATH"));
            data.put("DESTINATION_PATH", ContextUtil.getAttribute("DESTINATION_PATH"));
            data.put("PLAN_PATH", ContextUtil.getAttribute("PLAN_PATH"));
            data.put("HANDDRAW_PATH", ContextUtil.getAttribute("HANDDRAW_PATH"));
            data.put("RECOMMENDPLAN_PATH", ContextUtil.getAttribute("RECOMMENDPLAN_PATH"));
            data.put("SCENIC_PATH", ContextUtil.getAttribute("SCENIC_PATH"));
            data.put("TRAFFIC_PATH", ContextUtil.getAttribute("TRAFFIC_PATH"));
            data.put("HOTEL_PATH", ContextUtil.getAttribute("HOTEL_PATH"));
            data.put("DELICACY_PATH", ContextUtil.getAttribute("DELICACY_PATH"));
            data.put("CUSTOM_PATH", ContextUtil.getAttribute("CUSTOM_PATH"));
            data.put("REQUIRE_PATH", ContextUtil.getAttribute("REQUIRE_PATH"));
            data.put("ZIZHU_PATH", ContextUtil.getAttribute("ZIZHU_PATH"));
            data.put("GENTUAN_PATH", ContextUtil.getAttribute("GENTUAN_PATH"));
            Configuration cfg = new Configuration();
            cfg.setNumberFormat("#.##");
            cfg.setServletContextForTemplateLoading(ContextUtil.servletContext, File.separator);
            cfg.setObjectWrapper(new DefaultObjectWrapper());
            cfg.setEncoding(Locale.getDefault(), Constants.UTF8);
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
            cfg.setClassicCompatible(true);
            Template template = cfg.getTemplate(File.separator + ftlDir + ftlName);
            template.setEncoding(Constants.UTF8);
            Writer out = new OutputStreamWriter(new FileOutputStream(htmlName), Constants.UTF8);
            template.process(data, out);
            out.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void create(String mainDir, Map<Object, Object> data, String ftlName, String htmlName) {
        try {
//            String fileName = new StringBuilder().append(ContextUtil.getRealPath()).append(StaticConstants.STATIC).append(mainDir).append(FileUtil.newFileName(htmlName)).toString();
            String fileName = new StringBuilder().append(propertiesManager.getString("HTML_DIR")).append(StaticConstants.STATIC).append(mainDir).append(FileUtil.newFileName(htmlName)).toString();
            File file = new File(fileName);
            if (!file.exists()) {
                FileUtil.createFile(fileName);
            }
            createHtml(data, FtlConstants.FTL, ftlName, fileName);
            FileUtil.rename(fileName);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * @param data     填充ftl数据Map
     * @param ftlName  模版路径
     * @param htmlName 生成htm路径
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2012-8-8
     * @功能说明：生成 backup、master 静态文件
     */
    public static void create(Map<Object, Object> data, String ftlName, String htmlName) {
        create(StaticConstants.BACKUP, data, ftlName, htmlName);
        create(StaticConstants.MASTER, data, ftlName, htmlName);
    }

    public static void createStaticPage(String mainDir, Map<Object, Object> data, String ftlName, String htmlName) {
        try {
            String fileName = new StringBuilder().append(ContextUtil.getRealPath()).append(FtlConstants.HTML).append(mainDir).append(FileUtil.newFileName(htmlName)).toString();
            File file = new File(fileName);
            if (!file.exists()) {
                FileUtil.createFile(fileName);
            }
            createHtml(data, FtlConstants.FTL, ftlName, fileName);
            FileUtil.rename(fileName);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void createStaticPage(Map<Object, Object> data, String ftlName, String htmlName) {
        createStaticPage(StaticConstants.BACKUP, data, ftlName, htmlName);
        createStaticPage(StaticConstants.MASTER, data, ftlName, htmlName);
    }
}
