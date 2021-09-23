package com.data.data.hmly.service.wechat;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.data.data.hmly.service.wechat.dao.WechatVisitLogDao;
import com.data.data.hmly.service.wechat.entity.WechatVisitLog;

@Service
public class WechatVisitLogService {
    @Resource
    private WechatVisitLogDao wechatVisitLogDao;
	
	public void save(WechatVisitLog wechatVisitLog) {
		wechatVisitLogDao.save(wechatVisitLog);
	}
}
