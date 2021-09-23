package com.zuipin.solr.comparators;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.NumericUtils;

public class DoubleComparator extends NumberComparator {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5985393787954423745L;
	
	@Override
	public Boolean gt(Object num) {
		// TODO Auto-generated method stub
		return getValue(num).doubleValue() > getValue(val1).doubleValue();
	}
	
	@Override
	public Boolean lt(Object num) {
		// TODO Auto-generated method stub
		return getValue(num).doubleValue() < getValue(val1).doubleValue();
	}
	
	@Override
	public Boolean between(Object num) {
		// TODO Auto-generated method stub
		return getValue(num).doubleValue() >= getValue(val1).doubleValue() && getValue(num).doubleValue() <= getValue(val2).doubleValue();
	}
	
	@Override
	public Double getValue(Object num) {
		// TODO Auto-generated method stub
		return Double.parseDouble(String.valueOf(num));
	}
	
	@Override
	public StringBuilder gt() {
		// TODO Auto-generated method stub
		this.q = new StringBuilder(String.format("%s:[%s TO %d]", name, val1, Double.MAX_VALUE));
		return q;
	}
	
	@Override
	public StringBuilder lt() {
		// TODO Auto-generated method stub
		this.q = new StringBuilder(String.format("%s:[%d TO %s]", name, Double.MIN_VALUE, val1));
		return q;
	}
	
	@Override
	public BytesRef getBytesRef(Object num) {
		// TODO Auto-generated method stub
		BytesRef bytes = new BytesRef(NumericUtils.BUF_SIZE_LONG);
		long l = NumericUtils.doubleToSortableLong(getValue(num));
		NumericUtils.longToPrefixCoded(l, 0, bytes);
		return bytes;
	}
	
}
