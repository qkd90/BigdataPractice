package com.hmlyinfo.app.soutu.common;

import com.hmlyinfo.app.soutu.base.properties.Config;
import org.springframework.web.servlet.FrameworkServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import java.util.HashMap;
import java.util.Map;

public class ApplicationListener implements ServletContextAttributeListener {

	private static String dispatherName = FrameworkServlet.SERVLET_CONTEXT_PREFIX + "spring";

	public void attributeAdded(ServletContextAttributeEvent event) {

		String atrrName = event.getName();
		if (atrrName.equals(dispatherName)) {
			Map<String, String> cfgMap = new HashMap<String, String>();
			cfgMap.put("SRV_ADDR_WWW", Config.get("SRV_ADDR_WWW"));
			ServletContext application = event.getServletContext();
			application.setAttribute("CFG", cfgMap);
		}
	}


	public void attributeRemoved(
		ServletContextAttributeEvent servletcontextattributeevent) {
	}

	public void attributeReplaced(
		ServletContextAttributeEvent servletcontextattributeevent) {

	}

}