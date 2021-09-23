package com.data.data.hmly.service.pay.util.httpclient;

import com.data.data.hmly.service.pay.util.UUIDUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.dom4j.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.List;

public class HttpUtil {
	
	public static final Logger logger = Logger.getLogger(HttpUtil.class);
	
	/**
	 * 获取请求参数
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getParamStr(HttpServletRequest request) {
		StringBuffer sbArgs = new StringBuffer();
		Enumeration<String> paramNames = request.getParameterNames();
		try {
			while (paramNames.hasMoreElements()) {
				String paramName = paramNames.nextElement();
//				String paramValue = request.getParameter(paramName);
				//解决参数中文乱码问题
				String paramValue = URLEncoder.encode(request.getParameter(paramName), "UTF-8");

				sbArgs.append(paramName);
				sbArgs.append("=");
				sbArgs.append(paramValue);
				sbArgs.append("&");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (sbArgs.length() > 0) {
			sbArgs.deleteCharAt(sbArgs.length() - 1);
		}

		return sbArgs.toString();
	}

	/**
	 * 添加session属性
	 *
	 * @param request
	 * @param key
	 * @param value
	 */
	public static void setSessionAttr(HttpServletRequest request, String key, Object value) {
		request.getSession().setAttribute(key, value);
	}

	/**
	 * 移除session属性
	 *
	 * @param request
	 * @param key
	 * @param value
	 */
	public static void removeSessionAttr(HttpServletRequest request, String key) {
		request.getSession().removeAttribute(key);
	}

	/**
	 * 添加session属性
	 *
	 * @param request
	 * @param key
	 * @param value
	 */
	public static Object getSessionAttr(HttpServletRequest request, String key) {
		return request.getSession().getAttribute(key);
	}

	/**
	 * 重定向
	 * 采用绝对路径，防止相对路径无法访问问题
	 *
	 * @param request
	 * @param url
	 */
	public static String getRedirectUrl(HttpServletRequest request, String url) {
		return getRedirectUrl(request, url, null);
	}

	/**
	 * 重定向
	 * 采用绝对路径，防止相对路径无法访问问题
	 *
	 * @param request
	 * @param url
	 */
	public static String getRedirectUrl(HttpServletRequest request,
	                                    String url, Map<String, Object> paramMap) {
		//项目名称
		String contextPath = request.getContextPath();
		StringBuilder path = new StringBuilder();
		path.append("redirect:/").append(contextPath).append(url);
		if (paramMap != null) {
			path.append("?");
			Iterator<String> keyIterator = paramMap.keySet().iterator();
			String key = "";
			try {
				while (keyIterator.hasNext()) {
					key = keyIterator.next();
					path.append(key).append("=")
						.append(URLEncoder.encode(paramMap.get(key).toString(), "UTF-8"))
						.append("&");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return path.substring(0, path.length() - 1);
		}


		return path.toString();
	}

	/**
	 * 返回ajax请求处理成功的响应
	 *
	 * @return
	 */
	public static Map getAjaxSuccessResult() {
		return getAjaxResult(true, null, null);
	}

	/**
	 * 返回ajax请求处理成功的响应
	 *
	 * @return
	 */
	public static Map getAjaxSuccessResult(Object data) {
		return getAjaxResult(true, data, null);
	}

	/**
	 * 返回ajax请求处理失败的响应
	 *
	 * @return
	 */
	public static Map getAjaxErrorResult(Object exception) {
		return getAjaxResult(false, null, exception);
	}

	/**
	 * 返回ajax结果
	 *
	 * @param result
	 * @param data
	 * @param exception
	 * @return
	 */
	public static Map getAjaxResult(boolean result, Object data, Object exception) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", result);
		resultMap.put("data", data);
		resultMap.put("exception", exception);

		return resultMap;
	}

	/**
	 * 往客户端推送消息
	 *
	 * @param response
	 * @param scriptHtml 自定义js脚本
	 */
	public static void pushInfo(HttpServletResponse response, String scriptHtml) {
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write("<script type=\"text/javascript\">\r\n");
			out.write(scriptHtml);
			out.write("\r\n</script>\r\n");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 往客户端推送消息
	 *
	 * @param response
	 * @param dialogMsg   弹出对话框信息
	 * @param winLocation 页面从定向，如果为null则不跳转
	 */
	public static void pushInfo(HttpServletResponse response, String dialogMsg, String winLocation) {
		StringBuilder scriptHtml = new StringBuilder();
		scriptHtml.append("alert('")
			.append(dialogMsg)
			.append("');");
		if (winLocation != null) {
			scriptHtml.append("window.location.href='")
				.append(winLocation)
				.append("';");
		}

		pushInfo(response, scriptHtml.toString());
	}

	/**
	 * 获取经过nginx转发后的用户真实ip
	 *
	 * @param request
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request) {
		String clientIp = request.getHeader("");
		return clientIp == null ? request.getRemoteAddr() : (String) clientIp;
	}



	public static String postStrFromUrl(String url, Map<String, String> paramMap) {
		HttpClient httpClient = HttpClientUtils.getHttpClient();
		HttpPost httpPost = new HttpPost(url);
		HttpResponse response = null;
		String resultStr = null;

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			if (entry.getValue() != null && StringUtils.isNotBlank(entry.getValue())) {
				NameValuePair nvp = new BasicNameValuePair(entry.getKey(), entry.getValue());
				nvps.add(nvp);
			}
		}

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			response = httpClient.execute(httpPost);
			resultStr = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return resultStr;
	}

	public static String getWithTimeout(String url, int timeout) {
		HttpClient httpClient = HttpClientUtils.getHttpClient(timeout);
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response;
		String resultStr;
		try {
			response = httpClient.execute(httpGet);
			resultStr = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return resultStr;
	}

	public static Cookie getCookie(HttpServletRequest request, String cookieName) {
		Cookie c = null;

		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(cookieName)) {
				c = cookie;
				break;
			}
		}

		return c;
	}

	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		Cookie c = getCookie(request, cookieName);
		if (c == null) {
			return null;
		}

		return c.getValue();
	}

	public static Map<String, String> parseXml(HttpServletRequest request) {

		try {
			request.setCharacterEncoding("UTF8");
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return readStringXmlOut(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, String> readStringXmlOut(String xml) {
		Map<String, String> map = Maps.newHashMap();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xml); // 将字符串转为XML

			Element rootElt = doc.getRootElement(); // 获取根节点

			Iterator iter = rootElt.elementIterator(); // 获取根节点下的子节点head

			// 遍历head节点

			while (iter.hasNext()) {
				Element recordEle = (Element) iter.next();
				map.put(recordEle.getName(), recordEle.getStringValue());
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static Map<String, Object> readStringXmlOutNull(String xml) {
        try {
            Document doc = DocumentHelper.parseText(xml); // 将字符串转为XML

            Element rootElt = doc.getRootElement(); // 获取根节点

            Iterator iter = rootElt.elementIterator(); // 获取根节点下的子节点head
            return toMap(iter);
        } catch (DocumentException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private static Map<String, Object> toMap(Iterator iter) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 遍历head节点
        while (iter.hasNext()) {
            Element recordEle = (Element) iter.next();
            Iterator iterator = recordEle.elementIterator();
            if (iterator.hasNext()) {
                Map<String, Object> subMap = toMap(iterator);
                List attributes = recordEle.attributes();
                for (Object attribute1 : attributes) {
                    Attribute attribute = (Attribute) attribute1;
                    subMap.put(attribute.getName(), attribute.getValue());
                }
                packMap(map, recordEle.getName(), subMap);

            } else {
                List attributes = recordEle.attributes();
                if (attributes.isEmpty()) {
                    packMap(map, recordEle.getName(), recordEle.getData());
                    continue;
                }
                Map<String, Object> subMap = new HashMap<String, Object>();
                subMap.put("value", recordEle.getData());
                for (Object attribute1 : attributes) {
                    Attribute attribute = (Attribute) attribute1;
                    subMap.put(attribute.getName(), attribute.getValue());
                }
                packMap(map, recordEle.getName(), subMap);
            }


        }
        return map;
    }

    @SuppressWarnings("unchecked")
    private static boolean packMap(Map<String, Object> map, String name, Object object) {
        if (!map.containsKey(name)) {
            map.put(name, object);
            return true;
        }
        if (map.get(name) instanceof List) {
            List list = (List) map.get(name);
            list.add(object);
        } else {
            List list = new ArrayList();
            list.add(map.get(name));
            list.add(object);
            map.put(name, list);
        }
        return false;
    }

}
