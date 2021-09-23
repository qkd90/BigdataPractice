package com.hmlyinfo.app.soutu.delicacy.mapper;

import com.hmlyinfo.app.soutu.delicacy.domain.Restaurant;
import com.hmlyinfo.base.persistent.BaseMapper;

import java.util.List;
import java.util.Map;

public interface RestaurantMapper<T extends Restaurant> extends BaseMapper<T> {
    public List<Restaurant> listColumns(Map<String,Object> paramMap);
}
