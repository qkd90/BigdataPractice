package com.data.data.hmly.action.servlet;

import com.zuipin.util.ContextUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

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
    }
}
