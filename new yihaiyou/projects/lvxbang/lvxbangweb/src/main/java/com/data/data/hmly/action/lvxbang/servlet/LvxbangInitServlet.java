package com.data.data.hmly.action.lvxbang.servlet;

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
 * Created by guoshijie on 2015/10/21.
 */
public class LvxbangInitServlet extends HttpServlet {

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
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config.properties");
		Properties p = new Properties();
		try {
			p.load(inputStream);
			context.setAttribute("INDEX_PATH", p.getProperty("INDEX_PATH"));
			context.setAttribute("DESTINATION_PATH", p.getProperty("DESTINATION_PATH"));
			context.setAttribute("PLAN_PATH", p.getProperty("PLAN_PATH"));
			context.setAttribute("HANDDRAW_PATH", p.getProperty("HANDDRAW_PATH"));
			context.setAttribute("RECOMMENDPLAN_PATH", p.getProperty("RECOMMENDPLAN_PATH"));
			context.setAttribute("SCENIC_PATH", p.getProperty("SCENIC_PATH"));
			context.setAttribute("TRAFFIC_PATH", p.getProperty("TRAFFIC_PATH"));
			context.setAttribute("HOTEL_PATH", p.getProperty("HOTEL_PATH"));
			context.setAttribute("DELICACY_PATH", p.getProperty("DELICACY_PATH"));
			context.setAttribute("CUSTOM_PATH", p.getProperty("CUSTOM_PATH"));
			context.setAttribute("REQUIRE_PATH", p.getProperty("REQUIRE_PATH"));
			context.setAttribute("ZIZHU_PATH", p.getProperty("ZIZHU_PATH"));
			context.setAttribute("GENTUAN_PATH", p.getProperty("GENTUAN_PATH"));
			context.setAttribute("MOBILE_PATH", p.getProperty("MOBILE_PATH"));
		} catch (IOException e) {
			e.printStackTrace();
		}
//		ContextInitBeans contextInitBeans = (ContextInitBeans) ctx.getBean("contextInitBeans");
//		for (ContextInit init : contextInitBeans.getContextInitBeans()) {
//			init.init(context);
//		}
	}

}
