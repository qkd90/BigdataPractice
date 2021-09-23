package com.data.data.hmly.util;

import com.zuipin.util.StringUtils;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.StringWriter;
import java.util.Map;

/**
 * Created by zzl on 2016/4/29.
 * DO NOT ! modify this file
 * Notify zzl before modify this file
 * 使用Freemarker, 文本模板加载数据, 最终生成消息内容工具类
 */
public class MsgTemplateUtil {

    private static Log log = LogFactory.getLog(MsgTemplateUtil.class);

    public static String createContent(Map<String, Object> data, String msgTemplate) {
        if (data == null) {
            log.error("消息数据map不能为null!");
            return null;
        }
        if (!StringUtils.hasText(msgTemplate)) {
            log.error("模板内容为空!");
            return null;
        }
        try {
            Configuration cfg = new Configuration();
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            stringTemplateLoader.putTemplate("msgTemplate", msgTemplate);
            cfg.setTemplateLoader(stringTemplateLoader);
            Template template = cfg.getTemplate("msgTemplate");
            StringWriter writer = new StringWriter();
            template.process(data, writer);
            String msgContent = writer.toString();
            msgContent.replace("&nbsp;", "");
            return msgContent;
        } catch (Exception e) {
            log.error("通过模板生成消息失败!", e);
            return null;
        }
    }
}
