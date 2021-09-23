package com.data.data.hmly.service.wechat.dao;

import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.data.data.hmly.service.wechat.entity.WechatSupportAccount;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zzl on 2016/5/17.
 */
@Repository
public class WechatSupportAccountDao extends DataAccess<WechatSupportAccount> {

    /**
     * 查找多个指定公众号下的所有客服
     * @param accountList
     * @return
     */
    public List<WechatSupportAccount> getByAccountList(List<WechatAccount> accountList) {
        Criteria<WechatSupportAccount> criteria = new Criteria<WechatSupportAccount>(WechatSupportAccount.class);
        criteria.in("wechatAccount", accountList);
        return findByCriteria(criteria);
    }

    /**
     * 查找公众号下指定openId的客服
     * @param accountId
     * @param openId
     * @return
     */
    public List<WechatSupportAccount> getByAccountAndOpenId(Long accountId, String openId) {
        Criteria<WechatSupportAccount> criteria = new Criteria<WechatSupportAccount>(WechatSupportAccount.class);
        criteria.createCriteria("wechatAccount", "wechatAccount");
        criteria.eq("wechatAccount.id", accountId);
        criteria.eq("openId", openId);
        return findByCriteria(criteria);
    }

    /**
     * 是否已经是该公众号下客服检查
     * @param accountId
     * @param openId
     * @return
     */
    public Boolean checkSupporter(Long accountId, String openId) {
        Criteria<WechatSupportAccount> criteria = new Criteria<WechatSupportAccount>(WechatSupportAccount.class);
        criteria.createCriteria("wechatAccount", "wechatAccount");
        criteria.eq("wechatAccount.id", accountId);
        if (StringUtils.isNotBlank(openId)) {
            criteria.eq("openId", openId);
        }
        criteria.setProjection(Projections.groupProperty("id"));
        List<?> idList = findByCriteria(criteria);
        return !idList.isEmpty();
    }

    public void doDelSupporter(Long accountId, String openId) {
        Criteria<WechatSupportAccount> criteria = new Criteria<WechatSupportAccount>(WechatSupportAccount.class);
        criteria.createCriteria("wechatAccount", "wechatAccount");
        criteria.eq("wechatAccount.id", accountId);
        criteria.eq("openId", openId);
        WechatSupportAccount wechatSupportAccount = findUniqueByCriteria(criteria);
        if (wechatSupportAccount != null) {
            this.delete(wechatSupportAccount);
        }
    }


}
