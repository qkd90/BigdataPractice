package com.hmlyinfo.app.soutu.hotel.mapper;

import com.hmlyinfo.app.soutu.hotel.domain.CtripImage;
import com.hmlyinfo.base.persistent.BaseMapper;

import java.util.List;
import java.util.Map;

public interface CtripImageMapper<T extends CtripImage> extends BaseMapper<T> {
    public List<Map<String, Object>> countWithType(Map<String, Object> paramMap);

}
