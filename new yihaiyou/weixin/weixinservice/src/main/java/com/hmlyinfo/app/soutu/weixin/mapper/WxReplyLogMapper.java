package com.hmlyinfo.app.soutu.weixin.mapper;

import java.util.List;
import java.util.Map;
import com.hmlyinfo.app.soutu.weixin.domain.WxReplyLog;
import com.hmlyinfo.base.persistent.BaseMapper;

public interface WxReplyLogMapper <T extends WxReplyLog> extends BaseMapper<T>{
    //统计粉丝数
    public List<T> fansCountList(Map<String, Object> paramMap);
    //微信关键字回复集合
    public List<T> keyReplyList(Map<String, Object> paramMap);
}
