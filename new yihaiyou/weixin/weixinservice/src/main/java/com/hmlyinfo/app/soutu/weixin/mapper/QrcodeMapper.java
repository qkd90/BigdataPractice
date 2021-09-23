package com.hmlyinfo.app.soutu.weixin.mapper;

import java.util.List;
import java.util.Map;
import com.hmlyinfo.app.soutu.weixin.domain.Qrcode;
import com.hmlyinfo.base.persistent.BaseMapper;

public interface QrcodeMapper <T extends Qrcode> extends BaseMapper<T>{
    public List<T> channelsOfOriginCount(Map<String, Object> paramMap);
    public int countOrigin(Map<String, Object> paramMap);
}
