package com.data.data.hmly.action.other.interceptor;

import java.util.Date;
import java.util.concurrent.Callable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;

import com.data.data.hmly.action.other.util.VisitHistoryCookieUtils;
import com.data.data.hmly.action.user.UserConstans;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.other.OtherVisitHistoryService;
import com.data.data.hmly.service.other.entity.OtherVisitHistory;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zuipin.util.GlobalTheadPool;
import com.zuipin.util.PropertiesManager;

/**
 * 浏览历史拦截器
 * @author caiys
 * @date 2015年12月22日 下午6:08:32
 */
public class VisitHistoryInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 511623997635017942L;
	private final Log log = LogFactory.getLog(this.getClass());
	@Resource
	private OtherVisitHistoryService otherVisitHistoryService;
	@Resource
	private PropertiesManager propertiesManager;

	/* 
	 * 处理请求链接
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext actionContext = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
		HttpSession session = request.getSession();
		String uri = request.getRequestURI();
		String ip = com.zuipin.util.StringUtils.getIpAddr(request);
		log.info(" remote ip " + ip + " request uri " + uri);

		String interceptorProp = propertiesManager.getString(uri);
		if (StringUtils.isBlank(interceptorProp)) {	// 未配置需要缓存cookie
			return invocation.invoke();
		}
		// “类型,标识参数”，例如：scenic,scenicId
		String[] valueArray = interceptorProp.split(",");
		if (valueArray.length != 2) {
			log.error("配置文件interceptor.properties对应"+uri+"的值格式不正确");
			return invocation.invoke();
		}
		ProductType resType = null;
		String resObject = valueArray[1];
		Long resObjectId = null;
		for (ProductType rt : ProductType.values()) {
			if (rt.toString().equals(valueArray[0])) {
				resType = rt;
				break ;
			}
		}
		if (resType != null) {
			try {
				resObjectId = Long.valueOf(request.getParameter(resObject));
			} catch (Exception e) {
				log.error("配置文件interceptor.properties对应"+uri+"的标识参数"+resObject+"不合法");
				e.printStackTrace();
				return invocation.invoke();
			}
		} else {	
			log.error("配置文件interceptor.properties对应"+uri+"的值类型不存在");
			return invocation.invoke();
		}

		try {
			// 读取cookie，如果cookie失效需重写cookie
			String cookieId = VisitHistoryCookieUtils.getCookieId(request, response);

			// 保存浏览历史
			final OtherVisitHistory otherVisitHistory = new OtherVisitHistory();
			otherVisitHistory.setResType(resType);
			otherVisitHistory.setPath(uri);
			otherVisitHistory.setResObject(resObject);
			otherVisitHistory.setResObjectId(resObjectId);
			otherVisitHistory.setVisitIp(ip);
			otherVisitHistory.setCookieId(cookieId);
			User user = (User) session.getAttribute(UserConstans.CURRENT_LOGIN_USER);
			if (user != null) {
				otherVisitHistory.setUserId(user.getId());
			}
			otherVisitHistory.setCreateTime(new Date());
			otherVisitHistory.setDeleteFlag(false);

			// 更新操作
			GlobalTheadPool.instance.submit(new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					try {
						otherVisitHistoryService.doModifyOtherVisitHistory(otherVisitHistory);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return invocation.invoke();
	}

}
