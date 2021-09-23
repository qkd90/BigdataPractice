package com.zuipin.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TimeUtils {
	
	private static SimpleDateFormat	datePattern		= new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat	dateTimePattern	= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat	dfyyyyMMdd		= new SimpleDateFormat("yyyyMMdd");
	
	public static Map<Integer, String> buildTimeOptionsMap(int max) {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		for (int i = 0; i < max; i++) {
			map.put(i, StringUtils.paddingLeft(String.valueOf(i), '0', 2));
		}
		return map;
	}
	
	public static List<String> buildTimeOptions(int max) {
		List<String> hours = new ArrayList<String>();
		for (int i = 0; i < max; i++) {
			hours.add(StringUtils.paddingLeft(String.valueOf(i), '0', 2));
		}
		return hours;
	}
	
	public static String formatDateTime(Date date) {
		return dateTimePattern.format(date);
	}
	
	public static String formatyyyyMMdd(Date date) {
		return dfyyyyMMdd.format(date);
	}
	
	public static String formatDate(Date date) {
		if (date == null) {
			return "";
		}
		return datePattern.format(date);
	}
	
	public static Date parseDate(String date) {
		try {
			return datePattern.parse(date);
		} catch (Exception ex) {
			return null;
		}
	}
	
	public static Date parseDateTime(String date) {
		try {
			return dateTimePattern.parse(date);
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * 根据传入的Date对象构??附着于父Message后面的字符串 用于构?子Message的名?
	 * 
	 * @param date
	 *            时间日期对象
	 * @return 后缀的字符串
	 */
	public static String buildMessageNamesuffix(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return "_" + sdf.format(date);
	}
}