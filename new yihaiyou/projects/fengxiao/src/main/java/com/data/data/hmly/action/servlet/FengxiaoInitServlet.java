package com.data.data.hmly.action.servlet;

import com.zuipin.util.ContextUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by zzl on 2016/1/20.
 */
public class FengxiaoInitServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.initContext(config.getServletContext());
        super.init(config);
    }

    private void initContext(ServletContext context) {
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        ContextUtil.setWebApplicationContext(ctx);
        ContextUtil.setServletContext(context);
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("config.properties");
        Properties p = new Properties();
        try {
            p.load(is);
            context.setAttribute("QINIU_BUCKET_URL", p.get("QINIU_BUCKET_URL"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
