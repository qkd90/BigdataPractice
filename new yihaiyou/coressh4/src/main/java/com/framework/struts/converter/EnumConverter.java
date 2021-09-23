package com.framework.struts.converter;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.zuipin.util.StringUtils;

public class EnumConverter extends StrutsTypeConverter {
	@SuppressWarnings("unchecked")
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (!StringUtils.hasText(values[0]))
			return null;
		return Enum.valueOf(toClass, values[0]);
	}
	
	@SuppressWarnings("unchecked")
	public String convertToString(Map context, Object object) {
		if (object == null)
			return "";
		return object.toString();
	}
}
