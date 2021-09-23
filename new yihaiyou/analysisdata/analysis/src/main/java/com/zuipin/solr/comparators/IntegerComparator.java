package com.zuipin.solr.comparators;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.NumericUtils;

public class IntegerComparator extends NumberComparator {
	
	@Override
	public StringBuilder gt() {
		// TODO Auto-generated method stub
		this.q = new StringBuilder(String.format("%s:[%s TO %d]", name, val1, Integer.MAX_VALUE));
		return q;
	}
	
	@Override
	public StringBuilder lt() {
		// TODO Auto-generated method stub
		this.q = new StringBuilder(String.format("%s:[%d TO %s]", name, Integer.MIN_VALUE, val1));
		return q;
	}
	
	@Override
	public Boolean gt(Object num) {
		// TODO Auto-generated method stub
		return getValue(num).intValue() > getValue(val1).intValue();
	}
	
	@Override
	public Boolean lt(Object num) {
		// TODO Auto-generated method stub
		return getValue(num).intValue() < getValue(val1).intValue();
	}
	
	@Override
	public Boolean between(Object num) {
		// TODO Auto-generated method stub
		return getValue(num).intValue() >= getValue(val1).intValue() && getValue(num).intValue() <= getValue(val2).intValue();
	}
	
	@Override
	public Integer getValue(Object num) {
		// TODO Auto-generated method stub
		return Integer.parseInt(String.valueOf(num));
	}
	
	@Override
	public BytesRef getBytesRef(Object num) {
		BytesRef bytes = new BytesRef(NumericUtils.BUF_SIZE_INT);
		NumericUtils.intToPrefixCoded(getValue(num), 0, bytes);
		return bytes;
	}
	
	public static void main(String[] args) {
		System.out.println(Integer.MIN_VALUE);
	}
}
