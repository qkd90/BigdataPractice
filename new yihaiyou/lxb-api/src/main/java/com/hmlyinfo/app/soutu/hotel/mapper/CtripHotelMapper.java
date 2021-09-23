package com.hmlyinfo.app.soutu.hotel.mapper;

import com.hmlyinfo.app.soutu.hotel.domain.CtripHotel;
import com.hmlyinfo.base.persistent.BaseMapper;

import java.util.List;
import java.util.Map;

public interface CtripHotelMapper <T extends CtripHotel> extends BaseMapper<T>{
    public List<CtripHotel> listColumns(Map<String, Object> paramMap);
    public List<CtripHotel> listAndOrder(Map<String, Object> paramMap);
}
