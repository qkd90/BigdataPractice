package com.hmlyinfo.app.soutu.scenic.mapper;

import com.hmlyinfo.app.soutu.hotel.domain.CtripHotel;
import com.hmlyinfo.app.soutu.scenic.domain.ScenicInfo;
import com.hmlyinfo.base.persistent.BaseMapper;

import java.util.List;
import java.util.Map;

public interface ScenicInfoMapper <T extends ScenicInfo> extends BaseMapper<T>{

    public List<ScenicInfo> listBrief(Map<String, Object> paramMap);
    public void updateShareNum(List<Long> ids);
    public List<T> selectByPosition(Map<String, Object> params);
    public int countByPosition(Map<String, Object> params);
    public List<ScenicInfo> listColumns(Map<String,Object> paramMap);
    public List<ScenicInfo> addScennic(Map<String,Object> paramMap);
    public List<Map<String, Object>> listAndOrder(Map<String, Object> paramMap);
}
