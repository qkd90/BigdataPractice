package com.hmlyinfo.app.soutu.delicacy.mapper;

import java.util.List;
import java.util.Map;

import com.hmlyinfo.app.soutu.delicacy.domain.Delicacy;
import com.hmlyinfo.base.persistent.BaseMapper;

public interface DelicacyMapper<T extends Delicacy> extends BaseMapper<T> {
	public void updateAllNum(Map<String, Object> paramMap);

	public List<Delicacy> listPrice(Map<String, Object> paramMap);
}
