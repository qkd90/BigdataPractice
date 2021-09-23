package com.data.data.hmly.service.wechat;

import com.data.data.hmly.service.wechat.dao.WechatAccountDao;
import com.data.data.hmly.service.wechat.dao.WechatFollowerDao;
import com.data.data.hmly.service.wechat.dao.WechatSupportAccountDao;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.data.data.hmly.service.wechat.entity.WechatFollower;
import com.data.data.hmly.service.wechat.entity.WechatSupportAccount;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/5/17.
 */
@Service
public class WechatSupportAccountService {

    @Resource
    private WechatSupportAccountDao wechatSupportAccountDao;
    @Resource
    private WechatAccountDao wechatAccountDao;
    @Resource
    private WechatFollowerDao wechatFollowerDao;


    public List<WechatSupportAccount> getByAccountList(String wxCompanyIds, String wxAccountNames) {
        List<WechatAccount> accountList = wechatAccountDao.findByCompanyAndAccount(wxAccountNames, wxCompanyIds);
        return wechatSupportAccountDao.getByAccountList(accountList);
    }

    public Map<String, Object> doSetSupporter(String accountIdStr, String openId) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isBlank(accountIdStr)) {
            result.put("success", false);
            result.put("msg", "公众号账户accountId不可为空!");
            return result;
        }
        if (StringUtils.isBlank(openId)) {
            result.put("success", false);
            result.put("msg", "微信用户openId不可为空!");
            return result;
        }

        Long accountId = Long.parseLong(accountIdStr);
        Boolean isSupporter = wechatSupportAccountDao.checkSupporter(accountId, openId);
        if (isSupporter) {
            result.put("success", false);
            result.put("msg", "已经是该公众号的客服了!");
            return result;
        }
        WechatFollower wechatFollower = wechatFollowerDao.getByOpenId(openId, accountId);
        WechatAccount wechatAccount = wechatFollower.getFollowAccount();
        // 保存该公众号客服信息
        WechatSupportAccount wechatSupportAccount = new WechatSupportAccount();
        wechatSupportAccount.setOpenId(openId);
        wechatSupportAccount.setWechatAccount(wechatAccount);
        wechatSupportAccount.setCreateTime(new Date());
        wechatSupportAccountDao.save(wechatSupportAccount);
        result.put("success", true);
        result.put("msg", "为公众号" + wechatAccount.getAccount() + "设置客服成功!");
        return result;
    }

    public Map<String, Object> doDelSupporter(String accountIdStr, String openId) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isBlank(accountIdStr)) {
            result.put("success", false);
            result.put("msg", "公众号账户accountId不可为空!");
            return result;
        }
        if (StringUtils.isBlank(openId)) {
            result.put("success", false);
            result.put("msg", "微信用户openId不可为空!");
            return result;
        }
        Long accountId = Long.parseLong(accountIdStr);
        wechatSupportAccountDao.doDelSupporter(accountId, openId);
        result.put("success", true);
        result.put("msg", "删除客服成功!");
        return result;
    }
}
