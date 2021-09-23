package com.hmlyinfo.app.soutu.base;

import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.exception.BizValidateException;
import com.hmlyinfo.base.exception.SessionTimeOutException;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@Component
public class SimpleExceptionHandler implements HandlerExceptionResolver {
	private static final Log logs = LogFactory.getLog(SimpleExceptionHandler.class);

	public ModelAndView resolveException(HttpServletRequest request,
	                                     HttpServletResponse response, Object obj, Exception exception) {
		logs.info("processing request <" + request.getRequestURI() + "> fail, params:");
		StringBuffer sb = new StringBuffer();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			sb.append(paramName);
			sb.append("=");
			sb.append(request.getParameter(paramName));
			sb.append(", ");
		}
		logs.info(sb.toString());
		logs.info("detail:");
		exception.printStackTrace();
		ModelAndView mv = null;
		// 验证异常
		if (exception instanceof BizValidateException) {
			mv = new ModelAndView();
			BizValidateException be = (BizValidateException) exception;
			String msg = be.getMsg();
			if (StringUtils.isBlank(msg)) {
				msg = PropertiesUtils.getCfgInfo(be.getErrorcode() + "");
			}

			ActionResult ar = new ActionResult();
			ar.setErrorCode(be.getErrorcode());
			ar.setErrorMsg(msg);

			printActionResult(response, ar);
		}
		// 登录超时
		else if (exception instanceof SessionTimeOutException) {
			mv = new ModelAndView("redirect:/account/login");
			mv.addObject("f", getForwardUrl(request));
		} else {
			mv = new ModelAndView();

			ActionResult ar = new ActionResult();
			ar.setErrorCode(-1);
			ar.setErrorMsg(exception.getLocalizedMessage());

			printActionResult(response, ar);
		}

		return mv;
	}

	//获取登陆前url
	private String getForwardUrl(HttpServletRequest request) {
		StringBuilder url = new StringBuilder();
		url.append(request.getRequestURI());

		String paramInfo = HttpUtil.getParamStr(request);
		if (!StringUtil.isEmpty(paramInfo)) {
			url.append("?").append(HttpUtil.getParamStr(request));
		}

		return url.toString();
	}


	private void printActionResult(HttpServletResponse response, ActionResult ar) {
		String alStr = StringUtil.toJson(ar);
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=utf-8");

		try {
			PrintWriter printWriter = response.getWriter();
			printWriter.println(alStr);
			printWriter.flush();
		} catch (IOException e) {
			logs.error(e);
		}
	}
}