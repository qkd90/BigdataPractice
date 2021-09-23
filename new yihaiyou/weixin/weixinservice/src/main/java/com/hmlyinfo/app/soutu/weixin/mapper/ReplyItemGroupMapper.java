package com.hmlyinfo.app.soutu.weixin.mapper;

import java.util.Map;
import com.hmlyinfo.app.soutu.weixin.domain.ReplyItemGroup;
import com.hmlyinfo.base.persistent.BaseMapper;

public interface ReplyItemGroupMapper<T extends ReplyItemGroup> extends BaseMapper<T> {
	public T selByName(String name);
	public int searchCount(Map<String, Object> paramMap);
}
