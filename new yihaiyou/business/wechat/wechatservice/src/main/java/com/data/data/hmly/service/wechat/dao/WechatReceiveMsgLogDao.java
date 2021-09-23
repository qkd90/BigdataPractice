package com.data.data.hmly.service.wechat.dao;

import com.data.data.hmly.service.wechat.entity.WechatQrcode;
import com.gson.inf.EventTypes;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Repository;

import com.data.data.hmly.service.wechat.entity.WechatReceiveMsgLog;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;

import java.util.List;


@Repository
public class WechatReceiveMsgLogDao extends DataAccess<WechatReceiveMsgLog> {

    public WechatReceiveMsgLog loadBy(Long msgId) {
        Criteria<WechatReceiveMsgLog> criteria = new Criteria<WechatReceiveMsgLog>(WechatReceiveMsgLog.class);
        criteria.eq("msgId", msgId);
        return findUniqueByCriteria(criteria);
    }

    public List<WechatReceiveMsgLog> findMsgLogsByEventKey(WechatQrcode qrcode, EventTypes eventType) {
        Criteria<WechatReceiveMsgLog> criteria = new Criteria<WechatReceiveMsgLog>(WechatReceiveMsgLog.class);
        if (StringUtils.isNotBlank(qrcode.getSceneStr())) {
            criteria.eq("eventKey", qrcode.getSceneStr());
        }
        if (eventType != null) {
            criteria.eq("event", eventType);
        }
        if (qrcode.getStartTime() != null) {
            criteria.ge("createTime", qrcode.getStartTime());
        }
        if (qrcode.getEndTime() != null) {
            criteria.le("createTime", qrcode.getEndTime());
        }
        return findByCriteria(criteria);
    }

    public List<WechatReceiveMsgLog> findMsgLogByOpenId(String openId) {
        Criteria<WechatReceiveMsgLog> criteria = new Criteria<WechatReceiveMsgLog>(WechatReceiveMsgLog.class);
        if (StringUtils.isNotBlank(openId)) {
            criteria.eq("openId", openId);
        }
        return findByCriteria(criteria);
    }
}
