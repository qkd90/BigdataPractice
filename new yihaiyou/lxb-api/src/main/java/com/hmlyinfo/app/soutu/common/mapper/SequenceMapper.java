package com.hmlyinfo.app.soutu.common.mapper;

import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.app.soutu.common.domain.Sequence;

public interface SequenceMapper <T extends Sequence> extends BaseMapper<T>{
	
	int currentValue(String name);
	
	int nextValue(String name);
	
}
