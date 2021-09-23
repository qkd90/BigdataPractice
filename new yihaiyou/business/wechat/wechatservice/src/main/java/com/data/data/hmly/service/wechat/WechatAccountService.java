package com.data.data.hmly.service.wechat;

import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.wechat.dao.WechatAccountDao;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by vacuity on 15/11/20.
 */

@Service
public class WechatAccountService {

    @Resource
    private WechatAccountDao dao;

    public WechatAccount get(long id) {
        return dao.get(id);
    }

    public List<WechatAccount> getAccountList(SysUnit companyUnit, Page page, String account, Boolean validFlag, Boolean isAdmin, Boolean isSiteAdmin) {
        return dao.getAccountList(companyUnit, page, account, validFlag, isAdmin, isSiteAdmin);
    }

    public void updateWechatAccount(WechatAccount wechatAccount) {
        dao.saveOrUpdate(wechatAccount, wechatAccount.getId());
    }

    /**
     * 根据微信号或者原始ID查找
     *
     * @param account
     * @param originalId
     * @param validFlag
     * @return
     * @author caiys
     * @date 2015年11月25日 下午2:43:54
     */
    public WechatAccount findUniqueBy(String account, String originalId, boolean validFlag) {
        return dao.findUniqueBy(account, originalId, validFlag);
    }

    /**
     * 通过多个公众号账户名和companyId查找有效公众号账户列表
     * @param accounts
     * @param companyIds
     * @return
     */
    public List<WechatAccount> findByCompanyAndAccount(String accounts, String companyIds) {
        return dao.findByCompanyAndAccount(accounts, companyIds);
    }

    public WechatAccount load(Long accId) {
        return dao.load(accId);
    }

    /**
     * 公司获取该公众号
     *
     * @param companyUnit
     * @param validFlag
     * @return
     */
    public List<WechatAccount> getAccountListAll(SysUnit companyUnit,
                                                 Boolean validFlag) {
        return dao.getAccountListAll(companyUnit, validFlag);
    }

    /**
     * 全局管理员获取所有公众号
     *
     * @param validFlag
     * @return
     */
    public List<WechatAccount> getAccountListAll(Boolean validFlag) {
        return dao.getAccountListAll(validFlag);
    }


    /**
     * 站点管理员获取旗下的公司公众号
     *
     * @param sysUnList
     * @param validFlag
     * @return
     */
    public List<WechatAccount> getAccountListAll(List<SysUnit> sysUnList,
                                                 Boolean validFlag) {
        return dao.getAccountListAll(sysUnList, validFlag);
    }

    public boolean validateOriId(String valiContent, long id) {

        WechatAccount account = dao.getAccountByOriId(valiContent);
        WechatAccount accountbyId = dao.load(id);
        boolean flag = true;
        if (account != accountbyId) {
            flag = false;
        }
        return flag;
    }

    public boolean validateOriId(String valiContent) {
        WechatAccount account = dao.getAccountByOriId(valiContent);
        boolean flag = true;
        if (account != null) {
            flag = false;
        }
        return flag;
    }


    public List<WechatAccount> findComboList(SysUser loginUser, SysUnit companyUnit, Boolean supperAdmin, Boolean siteAdmin) {
        return dao.findComboList(loginUser, companyUnit, supperAdmin, siteAdmin);
    }

    public WechatAccount findSiteWechatAccount(Long siteId) {
        return dao.findSiteWechatAccount(siteId);
    }

    public WechatAccount findUniqueSiteAccount(String account, Long siteId) {
        return dao.findUniqueByCompanyAndAccount(account, siteId);
    }
}
