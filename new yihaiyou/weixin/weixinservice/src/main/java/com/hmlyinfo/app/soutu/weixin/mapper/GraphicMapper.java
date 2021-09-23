package com.hmlyinfo.app.soutu.weixin.mapper;

import java.util.List;
import java.util.Map;
import com.hmlyinfo.app.soutu.weixin.domain.Graphic;
import com.hmlyinfo.base.persistent.BaseMapper;

public interface GraphicMapper <T extends Graphic> extends BaseMapper<T>{
	public List<Graphic> ascList(Map<String, Object> paramMap);
	public int searchCount(Map<String, Object> paramMap);
	public List<Graphic> searchList(Map<String, Object> paramMap);
}
