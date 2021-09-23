package com.hmlyinfo.app.soutu.plan.mapper;

import java.util.List;
import java.util.Map;

import com.hmlyinfo.app.soutu.plan.domain.Plan;
import com.hmlyinfo.base.persistent.BaseMapper;

public interface PlanMapper <T extends Plan> extends BaseMapper<T>{
    public int addView(Map<String, Object> paramMap);
    public void updateAllNum(Map<String, Object> paramMap);
    public void delAllNum(Map<String, Object> paramMap);
    public List<Plan> listColumns(Map<String, Object> paramMap);
    public void updateStartTime(Plan plan);
    public List<Map<String, Object>> listids(Map<String, Object> paramMap);
}
