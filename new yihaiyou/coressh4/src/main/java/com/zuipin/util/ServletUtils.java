package com.zuipin.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;

/**
 * 功能描述：servlet获取相应的请求
 * 
 * @author : Teny_lu 刘鹰
 * @E_Mail : liuying5590@163.com
 * @CreatedTime : 2014年11月17日-上午09:49:32
 * @return
 */
public class ServletUtils {
	public static final String	TEXT_TYPE				= "text/plain";
	public static final String	JSON_TYPE				= "application/json";
	public static final String	XML_TYPE				= "text/xml";
	public static final String	HTML_TYPE				= "text/html";
	public static final String	JS_TYPE					= "text/javascript";
	public static final String	EXCEL_TYPE				= "application/vnd.ms-excel";
	public static final String	AUTHENTICATION_HEADER	= "Authorization";
	public static final long	ONE_YEAR_SECONDS		= 31536000L;
	
	public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
		response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000L);
		
		response.setHeader("Cache-Control", "private, max-age=" + expiresSeconds);
	}
	
	public static void setDisableCacheHeader(HttpServletResponse response) {
		response.setDateHeader("Expires", 1L);
		response.addHeader("Pragma", "no-cache");
		
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
	}
	
	public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
		response.setDateHeader("Last-Modified", lastModifiedDate);
	}
	
	public static void setEtag(HttpServletResponse response, String etag) {
		response.setHeader("ETag", etag);
	}
	
	public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response, long lastModified) {
		long ifModifiedSince = request.getDateHeader("If-Modified-Since");
		if ((ifModifiedSince != -1L) && (lastModified < ifModifiedSince + 1000L)) {
			response.setStatus(304);
			return false;
		}
		return true;
	}
	
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
		String headerValue = request.getHeader("If-None-Match");
		if (headerValue != null) {
			boolean conditionSatisfied = false;
			if (!"*".equals(headerValue)) {
				StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");
				do {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(etag))
						conditionSatisfied = true;
					if (conditionSatisfied)
						break;
				} while (commaTokenizer.hasMoreTokens());
			} else {
				conditionSatisfied = true;
			}
			
			if (conditionSatisfied) {
				response.setStatus(304);
				response.setHeader("ETag", etag);
				return false;
			}
		}
		return true;
	}
	
	public static void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
		try {
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setCharacterEncoding("UTF-8");
			response.setBufferSize(4000);
			
			String browserType = request.getHeader("User-Agent").toLowerCase();
			if ((browserType.toUpperCase().indexOf("FIREFOX") > 0) || (browserType.toUpperCase().indexOf("CHROME") > 0))
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			else if (browserType.toUpperCase().indexOf("MSIE") > 0)
				fileName = URLEncoder.encode(fileName, "UTF-8");
			else {
				fileName = new String(fileName.getBytes());
			}
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
		}
	}
	
	public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
		Assert.notNull(request, "Request must not be null");
		
		Enumeration paramNames = request.getParameterNames();
		Map params = new TreeMap();
		if (prefix == null) {
			prefix = "";
		}
		while ((paramNames != null) && (paramNames.hasMoreElements())) {
			String paramName = (String) paramNames.nextElement();
			if (("".equals(prefix)) || (paramName.startsWith(prefix))) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if ((values == null) || (values.length == 0))
					continue;
				if (values.length > 1)
					params.put(unprefixed, values);
				else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}
	
	public static String getContentType(String fileName) {
		String fileNameTmp = fileName.toLowerCase();
		String ret = "";
		if (fileNameTmp.endsWith("txt")) {
			ret = "text/plain";
		}
		if (fileNameTmp.endsWith("gif")) {
			ret = "image/gif";
		}
		if (fileNameTmp.endsWith("jpg")) {
			ret = "image/jpeg";
		}
		if (fileNameTmp.endsWith("jpeg")) {
			ret = "image/jpeg";
		}
		if (fileNameTmp.endsWith("jpe")) {
			ret = "image/jpeg";
		}
		if (fileNameTmp.endsWith("zip")) {
			ret = "application/zip";
		}
		if (fileNameTmp.endsWith("rar")) {
			ret = "application/rar";
		}
		if (fileNameTmp.endsWith("doc")) {
			ret = "application/msword";
		}
		if (fileNameTmp.endsWith("ppt")) {
			ret = "application/vnd.ms-powerpoint";
		}
		if (fileNameTmp.endsWith("xls")) {
			ret = "application/vnd.ms-excel";
		}
		if (fileNameTmp.endsWith("html")) {
			ret = "text/html";
		}
		if (fileNameTmp.endsWith("htm")) {
			ret = "text/html";
		}
		if (fileNameTmp.endsWith("tif")) {
			ret = "image/tiff";
		}
		if (fileNameTmp.endsWith("tiff")) {
			ret = "image/tiff";
		}
		if (fileNameTmp.endsWith("pdf")) {
			ret = "application/pdf";
		}
		return ret;
	}
	
	public String getBrowseType(HttpServletRequest request) {
		return request.getHeader("user-agent");
	}
	
	public String getLocationHead(HttpServletRequest request) {
		return request.getHeader("accept-language");
	}
}