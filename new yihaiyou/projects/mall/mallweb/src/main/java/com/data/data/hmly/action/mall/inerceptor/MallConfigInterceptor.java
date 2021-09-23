package com.data.data.hmly.action.mall.inerceptor;

import com.data.data.hmly.action.mall.MallConstants;
import com.data.data.hmly.action.mall.util.MallUtil;
import com.data.data.hmly.action.mall.vo.MallConfig;
import com.data.data.hmly.action.user.UserConstans;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zuipin.util.FileUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MallConfigInterceptor extends AbstractInterceptor {
	/**
	 *
	 */
	private static final long serialVersionUID = 511623997635017942L;

	private Log log = LogFactory.getLog(this.getClass());

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		try {
			ActionContext actionContext = invocation.getInvocationContext();
			HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
			HttpSession session = request.getSession();
			MallConfig mallConfig = (MallConfig) session.getAttribute("mallConfig");
			String host = request.getHeader("Host");
			String url = request.getRequestURL().toString();
			host = url.substring(0, url.indexOf("//")+2)+host;
			if (mallConfig == null) {
				mallConfig = MallUtil.getConfig(host);
				session.setAttribute("mallConfig", mallConfig);
			} else if (!host.equals(mallConfig.getSite())) {
				mallConfig.setSite(host);
				changeSite(mallConfig);
			}
			session.setAttribute(MallConstants.GLOBAL_HEADER_KEY, FileUtil.loadHTML(MallConstants.GLOBAL_HEADER));
			session.setAttribute(MallConstants.GLOBAL_FOOTER_KEY, FileUtil.loadHTML(MallConstants.GLOBAL_FOOTER));
			request.setAttribute("mallConfig", mallConfig);
			session.setAttribute(UserConstans.SYSTEM_SITE_INFORMATION, mallConfig.getSysSite());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return invocation.invoke();
	}

	private void changeSite(MallConfig mallConfig) {
		MallUtil.changeSite(mallConfig);
	}
}
