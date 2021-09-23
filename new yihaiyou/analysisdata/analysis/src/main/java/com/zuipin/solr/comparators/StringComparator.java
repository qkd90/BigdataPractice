package com.zuipin.solr.comparators;

import java.lang.reflect.Field;

import com.zuipin.util.StringUtils;

public class StringComparator extends BaseCompare {
	
	@Override
	public Boolean compare(Object o) throws Exception {
		// TODO Auto-generated method stub
		Field[] fields = o.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.getName().equals(name)) {
				String num = String.valueOf(field.get(o));
				if (ope.equals("等于")) {
					return num.equals(val1);
				} else if (ope.equals("不等于")) {
					return !num.equals(val1);
				} else if (ope.equals("为空")) {
					return StringUtils.isBlank(num);
				} else if (ope.equals("不为空")) {
					return StringUtils.isNotBlank(num);
				} else if (ope.equals("包含")) {
					return (num).contains(val1);
				} else {
					return false;
				}
			}
		}
		return false;
	}
	
	@Override
	public StringBuilder createQuery() throws Exception {
		// TODO Auto-generated method stub
		if (ope.equals("等于")) {
			this.q = new StringBuilder(String.format("%s:%s", name, val1));
			return q;
		} else if (ope.equals("不等于")) {
			this.fq = new StringBuilder(String.format("-%s:%s", name, val1));
			return fq;
		} else if (ope.equals("为空")) {
			this.q = new StringBuilder(String.format("%s:%s", name, ""));
			return q;
		} else if (ope.equals("不为空")) {
			this.fq = new StringBuilder(String.format("-%s:%s", name, ""));
			return fq;
		}
		return null;
	}
	
}
