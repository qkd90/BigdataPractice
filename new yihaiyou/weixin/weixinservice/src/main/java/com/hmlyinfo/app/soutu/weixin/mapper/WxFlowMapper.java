package com.hmlyinfo.app.soutu.weixin.mapper;

import java.util.List;
import java.util.Map;

import com.hmlyinfo.app.soutu.weixin.domain.WxFlow;
import com.hmlyinfo.base.persistent.BaseMapper;

public interface WxFlowMapper <T extends WxFlow> extends BaseMapper<T>{
    //微信点击pv和uv的统计集合
    public List<T> wxFlowList(Map<String, Object> paramMap);
    //微信页面访问的pv和uv统计集合
    public List<T> wxFlowVistorList(Map<String, Object> paramMap);
    //获取最大的日期
    public List<T> maxDateList(Map<String, Object> paramMap);

    public List<T> maxDateList2(Map<String, Object> paramMap);

}
