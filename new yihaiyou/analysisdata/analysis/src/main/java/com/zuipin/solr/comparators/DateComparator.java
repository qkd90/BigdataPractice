package com.zuipin.solr.comparators;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.LogFactory;

import com.zuipin.util.StringUtils;

public class DateComparator extends BaseCompare {
	/**
	 * 
	 */
	private static final long		serialVersionUID	= -6793140190498760598L;
	private static SimpleDateFormat	sf					= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat	sdf					= new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public StringBuilder createQuery() throws Exception {
		// TODO Auto-generated method stub
		if (ope.equals("等于")) {
			return eq();
		} else if (ope.equals("不等于")) {
			return neq();
		} else if (ope.equals("晚于等于")) {
			return gt();
		} else if (ope.equals("早于等于")) {
			return lt();
		} else if (ope.equals("区间")) {
			return between();
		}
		return null;
	}
	
	private StringBuilder between() {
		// TODO Auto-generated method stub
		Calendar c = Calendar.getInstance();
		Date d1 = getValue(val1);
		c.setTime(d1);
		c.add(Calendar.HOUR_OF_DAY, -8);
		String day = sdf.format(c.getTime());
		
		Date d2 = getValue(val2);
		String day2 = sdf.format(d2);
		this.q = new StringBuilder(String.format("%s:[%sT16:00:00.000Z TO %sT15:59:59Z]", name, day, day2));
		return q;
	}
	
	private StringBuilder lt() {
		// TODO Auto-generated method stub
		Date d1 = getValue(val1);
		String day = sdf.format(d1);
		this.q = new StringBuilder(String.format("%s:[2014-01-01T16:00:00.000Z TO %sT15:59:59Z]", name, day));
		return q;
	}
	
	private StringBuilder gt() {
		// TODO Auto-generated method stub
		
		Calendar c = Calendar.getInstance();
		Date d1 = getValue(val1);
		c.setTime(d1);
		c.add(Calendar.HOUR_OF_DAY, -8);
		String day = sdf.format(c.getTime());
		
		String now = sdf.format(new Date());
		this.q = new StringBuilder(String.format("%s:[%sT16:00:00.000Z TO %sT15:59:59Z]", name, day, now));
		return q;
	}
	
	private StringBuilder neq() {
		// TODO Auto-generated method stub
		Calendar c = Calendar.getInstance();
		Date d1 = getValue(val1);
		c.setTime(d1);
		c.add(Calendar.HOUR_OF_DAY, -8);
		String day = sdf.format(c.getTime());
		this.q = new StringBuilder(String.format("-%s:[%sT16:00:00.000Z TO T15:59:59Z]", name, day, day));
		return q;
	}
	
	private StringBuilder eq() {
		// TODO Auto-generated method stub
		Calendar c = Calendar.getInstance();
		Date d1 = getValue(val1);
		c.setTime(d1);
		c.add(Calendar.HOUR_OF_DAY, -8);
		String day = sdf.format(c.getTime());
		this.q = new StringBuilder(String.format("%s:[%sT16:00:00.000Z TO %sT15:59:59Z]", name, day, day));
		return q;
	}
	
	@Override
	public Boolean compare(Object o) throws Exception {
		// TODO Auto-generated method stub
		Field[] fields = o.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.getName().equals(name)) {
				Date num = (Date) field.get(o);
				SimpleDateFormat sdf = getDateFormat(val1);
				if (ope.equals("等于")) {
					// return getTimeLong(num, sdf) == getValue(val1).longValue();
				} else if (ope.equals("不等于")) {
					// return getTimeLong(num, sdf) != getValue(val1).longValue();
				} else if (ope.equals("晚于等于")) {
					// return gt(num);
				} else if (ope.equals("早于等于")) {
					// return lt(num);
				} else if (ope.equals("区间")) {
					// return between(num);
				} else {
					return false;
				}
			}
		}
		return false;
	}
	
	/**
	 * @param num
	 * @param sdf
	 * @return
	 */
	private long getTimeLong(Date num, SimpleDateFormat sdf) {
		try {
			return sdf.parse(sdf.format(num)).getTime();
		} catch (ParseException e) {
			LogFactory.getLog(DateComparator.class).error(e.getMessage(), e);
			return 0;
		}
	}
	
	public Date getValue(Object o) {
		String str = String.valueOf(o);
		if (StringUtils.isNotBlank(String.valueOf(str))) {
			try {
				return sf.parse(str);
			} catch (Exception e) {
				try {
					return sdf.parse(str);
				} catch (ParseException e1) {
					LogFactory.getLog(DateComparator.class).error(e.getMessage(), e);
					return new Date();
				}
			}
		}
		return new Date();
	}
	
	public SimpleDateFormat getDateFormat(String str) {
		if (StringUtils.isNotBlank(String.valueOf(str))) {
			try {
				sf.parse(str);
				return sf;
			} catch (Exception e) {
				try {
					sdf.parse(str).getTime();
					return sdf;
				} catch (ParseException e1) {
					LogFactory.getLog(DateComparator.class).error(e.getMessage(), e);
					return null;
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws ParseException {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2014);
		c.set(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_YEAR, 1);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MILLISECOND, 999);
		SimpleDateFormat mysdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(mysdf.format(c.getTime()));
		System.out.println(sf.format(c.getTime()));
		System.out.println(sf.parse("2014-01-01 00:00:00"));
	}
}
