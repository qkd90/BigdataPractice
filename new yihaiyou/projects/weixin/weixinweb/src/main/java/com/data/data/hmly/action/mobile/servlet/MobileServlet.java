package com.data.data.hmly.action.mobile.servlet;

import com.zuipin.util.Constants;
import com.zuipin.util.ContextUtil;
import com.zuipin.util.DateUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.Date;

/**
 * Created by guoshijie on 2015/10/21.
 */
public class MobileServlet extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		this.initContext(config.getServletContext());
		super.init(config);
	}

	private void initContext(ServletContext context) {
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		ContextUtil.setWebApplicationContext(ctx);
		ContextUtil.setServletContext(context);
		context.setAttribute(Constants.RESOURCE_VERSION, DateUtils.format(new Date(), "MMddhhmm"));
//		ContextInitBeans contextInitBeans = (ContextInitBeans) ctx.getBean("contextInitBeans");
//		for (ContextInit init : contextInitBeans.getContextInitBeans()) {
//			init.init(context);
//		}
	}

}
