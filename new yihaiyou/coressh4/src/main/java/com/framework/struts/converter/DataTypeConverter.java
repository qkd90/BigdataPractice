package com.framework.struts.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

public class DataTypeConverter extends StrutsTypeConverter {
	
	private static final SimpleDateFormat	FORMATDATE	= new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat	FORMATTIME	= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		// TODO Auto-generated method stub
		if (values == null || values.length == 0) {
			return null;
		}
		Date date = null;
		String dateString = values[0];
		if (dateString != null) {
			try {
				date = FORMATTIME.parse(dateString);
			} catch (ParseException e) {
				date = null;
			}
			if (date == null) {
				try {
					date = FORMATDATE.parse(dateString);
				} catch (ParseException e) {
					date = null;
				}
			}
		}
		return date;
	}
	
	@Override
	public String convertToString(Map map, Object o) {
		// TODO Auto-generated method stub
		Date date = (Date) o;
		return FORMATDATE.format(date);
	}
	
}
