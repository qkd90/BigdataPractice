package com.zuipin.solr.comparators;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.NumericUtils;

public class LongComparator extends NumberComparator {
	
	@Override
	public Boolean gt(Object num) {
		// TODO Auto-generated method stub
		return getValue(num).longValue() > getValue(val1).longValue();
	}
	
	@Override
	public Boolean lt(Object num) {
		// TODO Auto-generated method stub
		return getValue(num).longValue() < getValue(val1).longValue();
	}
	
	@Override
	public Boolean between(Object num) {
		// TODO Auto-generated method stub
		return getValue(num).longValue() >= getValue(val1).longValue() && getValue(num).longValue() <= getValue(val2).longValue();
	}
	
	@Override
	public Long getValue(Object num) {
		// TODO Auto-generated method stub
		return Long.parseLong(String.valueOf(num));
	}
	
	@Override
	public BytesRef getBytesRef(Object num) {
		BytesRef bytes = new BytesRef(NumericUtils.BUF_SIZE_LONG);
		NumericUtils.longToPrefixCoded(getValue(num), 0, bytes);
		return bytes;
	}
	
	@Override
	public StringBuilder gt() {
		// TODO Auto-generated method stub
		this.q = new StringBuilder(String.format("%s:[%s TO %d]", name, val1, Long.MAX_VALUE));
		return q;
	}
	
	@Override
	public StringBuilder lt() {
		// TODO Auto-generated method stub
		this.q = new StringBuilder(String.format("%s:[%d TO %s]", name, Long.MIN_VALUE, val1));
		return q;
	}
	
}
