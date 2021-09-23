package com.data.data.hmly.service.wechat;

import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.wechat.dao.WechatQrcodeDao;
import com.data.data.hmly.service.wechat.dao.WechatReceiveMsgLogDao;
import com.data.data.hmly.service.wechat.entity.EntityData.StatisticsQrcode;
import com.data.data.hmly.service.wechat.entity.WechatQrcode;
import com.data.data.hmly.service.wechat.entity.WechatReceiveMsgLog;
import com.framework.hibernate.util.Page;
import com.gson.inf.EventTypes;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class WechatReceiveMsgLogService {
    @Resource
    private WechatReceiveMsgLogDao wechatReceiveMsgLogDao;
    @Resource
    private WechatQrcodeDao wechatQrcodeDao;

    public void saveWechatReceiveMsgLog(WechatReceiveMsgLog wechatReceiveMsgLog) {
        wechatReceiveMsgLogDao.save(wechatReceiveMsgLog);
    }

    public void updateWechatReceiveMsgLog(WechatReceiveMsgLog wechatReceiveMsgLog) {
        wechatReceiveMsgLogDao.update(wechatReceiveMsgLog);
    }

    public WechatReceiveMsgLog load(Long id) {
        return wechatReceiveMsgLogDao.load(id);
    }

    public WechatReceiveMsgLog loadBy(Long msgId) {
        return wechatReceiveMsgLogDao.loadBy(msgId);
    }

    public List<StatisticsQrcode> getStatisticsQrcode(Long accountId, WechatQrcode qrcode, EventTypes eventType, SysUnit companyUnit, SysUser loginUser, Boolean supperAdmin, Boolean siteAdmin, Page pageInfo) {

        List<WechatQrcode> wechatQrcodes = wechatQrcodeDao.findQrcodeList(accountId, qrcode, eventType, companyUnit, supperAdmin, siteAdmin, pageInfo);
        List<StatisticsQrcode> statisticsQrcodes = new ArrayList<StatisticsQrcode>();
        for (WechatQrcode wechatQrcode : wechatQrcodes) {
            List<WechatReceiveMsgLog> wechatReceiveMsgLogs = wechatReceiveMsgLogDao.findMsgLogsByEventKey(wechatQrcode, eventType);

            StatisticsQrcode statisticsQrcode = new StatisticsQrcode();
            statisticsQrcode.setId(wechatQrcode.getId());
            statisticsQrcode.setName(wechatQrcode.getName());
            statisticsQrcode.setAccountId(wechatQrcode.getWechatAccount().getId());
            statisticsQrcode.setAccount(wechatQrcode.getWechatAccount().getAccount());
            statisticsQrcode.setSubCount(wechatReceiveMsgLogs.size());

            statisticsQrcodes.add(statisticsQrcode);
        }
        return statisticsQrcodes;
    }

    public List<StatisticsQrcode> statisticsQrcode(Long accountId, WechatQrcode qrcode, SysUnit companyUnit, Boolean supperAdmin, Boolean siteAdmin, Page pageInfo) {
        return wechatQrcodeDao.statisticsQrcode(accountId, qrcode, companyUnit, supperAdmin, siteAdmin, pageInfo);
    }
}
