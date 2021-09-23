package com.zuipin.solr.comparators;

import java.lang.reflect.Field;

import org.apache.lucene.util.BytesRef;

public abstract class NumberComparator extends BaseCompare {
	
	@Override
	public StringBuilder createQuery() throws Exception {
		if (ope.equals("等于")) {
			return eq();
		} else if (ope.equals("不等于")) {
			return neq();
		} else if (ope.equals("大于")) {
			return gt();
		} else if (ope.equals("小于")) {
			return lt();
		} else if (ope.equals("区间")) {
			return between();
		}
		return null;
	}
	
	@Override
	public Boolean compare(Object o) throws Exception {
		// TODO Auto-generated method stub
		Field[] fields = o.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.getName().equals(name)) {
				Object num = field.get(o);
				if (ope.equals("等于")) {
					return getValue(String.valueOf(num)) == getValue(val1);
				} else if (ope.equals("不等于")) {
					return getValue(String.valueOf(num)) != getValue(val1);
				} else if (ope.equals("大于")) {
					return gt(num);
				} else if (ope.equals("小于")) {
					return lt(num);
				} else if (ope.equals("区间")) {
					return between(num);
				} else {
					return false;
				}
			}
		}
		return false;
	}
	
	public StringBuilder eq() {
		this.q = new StringBuilder(String.format("%s:%s", name, val1));
		return q;
	}
	
	public StringBuilder neq() {
		this.fq = new StringBuilder(String.format("-%s:%s", name, val1));
		return fq;
	}
	
	public abstract Boolean gt(Object num);
	
	public abstract StringBuilder gt();
	
	public abstract Boolean lt(Object num);
	
	public abstract StringBuilder lt();
	
	public abstract Boolean between(Object num);
	
	public StringBuilder between() {
		this.q = new StringBuilder(String.format("%s:[%s TO %s]", name, val1, val2));
		return q;
	}
	
	public abstract Number getValue(Object num);
	
	public abstract BytesRef getBytesRef(Object num);
	
}
