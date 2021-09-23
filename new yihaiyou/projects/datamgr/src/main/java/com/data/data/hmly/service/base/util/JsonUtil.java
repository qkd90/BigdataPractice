package com.data.data.hmly.service.base.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.data.data.hmly.service.base.exception.BizLogicException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonUtil {
	
	/**
	 * json对象转为json字符串
	 * @param jsonObject
	 * @param response
	 * @throws IOException
	 */
	public static void jsonToResponse(Object jsonObject, HttpServletResponse response) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(jsonObject);// 把map或者是list转换成json字符串
			response.getWriter().println(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BizLogicException(-1);
		}
	}

}
