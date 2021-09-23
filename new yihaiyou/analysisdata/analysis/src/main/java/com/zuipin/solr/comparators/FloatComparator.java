package com.zuipin.solr.comparators;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.NumericUtils;

public class FloatComparator extends NumberComparator {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3431034127592665161L;
	
	@Override
	public Boolean gt(Object num) {
		// TODO Auto-generated method stub
		return getValue(num).floatValue() > getValue(val1).floatValue();
	}
	
	@Override
	public Boolean lt(Object num) {
		// TODO Auto-generated method stub
		return getValue(num).floatValue() < getValue(val1).floatValue();
	}
	
	@Override
	public Boolean between(Object num) {
		// TODO Auto-generated method stub
		return getValue(num).floatValue() >= getValue(val1).floatValue() && getValue(num).floatValue() <= getValue(val2).floatValue();
	}
	
	@Override
	public Float getValue(Object num) {
		// TODO Auto-generated method stub
		return Float.parseFloat(String.valueOf(num));
	}
	
	@Override
	public StringBuilder gt() {
		// TODO Auto-generated method stub
		this.q = new StringBuilder(String.format("%s:[%s TO %d]", name, val1, Float.MAX_VALUE));
		return q;
	}
	
	@Override
	public StringBuilder lt() {
		// TODO Auto-generated method stub
		this.q = new StringBuilder(String.format("%s:[%d TO %s]", name, Float.MIN_VALUE, val1));
		return q;
	}
	
	@Override
	public BytesRef getBytesRef(Object num) {
		// TODO Auto-generated method stub
		BytesRef bytes = new BytesRef(NumericUtils.BUF_SIZE_INT);
		int a = NumericUtils.floatToSortableInt(getValue(num));
		NumericUtils.longToPrefixCoded(a, 0, bytes);
		return bytes;
	}
}
