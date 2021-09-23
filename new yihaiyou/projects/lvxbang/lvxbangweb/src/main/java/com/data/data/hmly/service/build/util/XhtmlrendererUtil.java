package com.data.data.hmly.service.build.util;

import com.lowagie.text.pdf.BaseFont;
import com.zuipin.util.Constants;
import com.zuipin.util.ContextUtil;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-07-27,0027.
 */
public class XhtmlrendererUtil {
    private static PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");

    public static void create(Map<Object, Object> data, String ftlName, String pdfName) {
        try {
            String pdfFullName = propertiesManager.getString("PDF_DIR") + pdfName;
            OutputStream out = new FileOutputStream(pdfFullName);
            String htmlStr = createHtml(data, ftlName);
            createPdf(htmlStr, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createPdf(String htmlStr, OutputStream out) throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(htmlStr.getBytes("UTF-8")));
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(doc, null);
        ITextFontResolver fontResolver = renderer.getFontResolver();
        fontResolver.addFont("C:/Windows/Fonts/ARIALUNI.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.layout();
        renderer.createPDF(out);
        out.close();
    }

    private static String createHtml(Map<Object, Object> data, String ftlName) throws Exception {
        Configuration cfg = new Configuration();
        cfg.setNumberFormat("#.##");
        cfg.setServletContextForTemplateLoading(ContextUtil.servletContext, File.separator);
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        cfg.setEncoding(Locale.getDefault(), Constants.UTF8);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        cfg.setClassicCompatible(true);
        Template tp = cfg.getTemplate(File.separator + ftlName);
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        tp.setEncoding("UTF-8");
        tp.process(data, writer);
        String htmlStr = stringWriter.toString();
        writer.flush();
        writer.close();
        return htmlStr;
    }
}
