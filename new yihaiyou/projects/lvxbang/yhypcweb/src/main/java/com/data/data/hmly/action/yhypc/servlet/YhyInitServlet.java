package com.data.data.hmly.action.yhypc.servlet;

import com.zuipin.util.Constants;
import com.zuipin.util.ContextUtil;
import com.zuipin.util.DateUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 * Created by zzl on 2016/12/27.
 */
public class YhyInitServlet extends HttpServlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.initContext(servletConfig.getServletContext());
        super.init(servletConfig);
    }

    private void initContext(ServletContext servletContext) {
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        ContextUtil.setWebApplicationContext(applicationContext);
        ContextUtil.setServletContext(servletContext);
        servletContext.setAttribute(Constants.RESOURCE_VERSION, DateUtils.format(new Date(), "MMddhhmm"));
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("config.properties");
        Properties p = new Properties();
        try {
            p.load(is);
            servletContext.setAttribute("QINIU_BUCKET_URL", p.get("QINIU_BUCKET_URL"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
