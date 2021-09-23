package com.zuipin.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zuipin.util.StringUtils;

@SuppressWarnings("serial")
public class LogAdSrcInterceptor extends AbstractInterceptor {
	
	private Log	log	= LogFactory.getLog(this.getClass());
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String referer = request.getHeader("referer");
			// log.error(String.format("referer", referer));
			if (StringUtils.isNotBlank(referer)) {
				int index = 0;
				if (referer.startsWith("http://")) {
					index = 7;
				}
				int i = referer.indexOf("/", index);
				String search = "";
				if (i != -1) {
					search = referer.substring(index, i);
				} else {
					search = referer.substring(index);
				}
				// if (!search.contains("zuipin.cn")) {
				// CookieUtils.addCookie(request, ServletActionContext.getResponse(), Constants.COOKIE_AD_SRC, referer, Constants.EXPIRY_AD_SRC);
				// }
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return invocation.invoke();
	}
}
