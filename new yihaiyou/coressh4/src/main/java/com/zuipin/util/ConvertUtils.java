package com.zuipin.util;

public class ConvertUtils {
	
	public static String objectToString(Object object) {
		if (object != null) {
			return String.valueOf(object);
		} else {
			return "";
		}
	}
	
	public static Long objectToLong(Object object) {
		if (object != null) {
			return Long.valueOf(String.valueOf(object));
		} else {
			return 0l;
		}
	}
	
	public static Integer objectToInteger(Object object) {
		if (object != null) {
			return Integer.valueOf(String.valueOf(object));
		} else {
			return 0;
		}
	}
	
	public static Double objectToDouble(Object object) {
		if (object != null) {
			return Double.valueOf(String.valueOf(object));
		} else {
			return 0d;
		}
	}
	
	public static Object convertStringToObject(String value, Class<?> toType) {
		try {
			return org.apache.commons.beanutils.ConvertUtils.convert(value, toType);
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}
	}
	
}
