package com.data.data.hmly.service.wechat.dao;

import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.UnitType;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by vacuity on 15/11/20.
 */

@Repository
public class WechatAccountDao extends DataAccess<WechatAccount> {

    /**
     * 返回站点对应的公众号信息，多条取其中一条
     * @param siteId
     * @return
     */
    public WechatAccount findSiteWechatAccount(Long siteId) {
        Criteria<WechatAccount> criteria = new Criteria<WechatAccount>(WechatAccount.class);
        criteria.createCriteria("companyUnit", "cp", JoinType.INNER_JOIN);
        criteria.eq("cp.sysSite.id", siteId);
        criteria.eq("cp.unitType", UnitType.site);
        criteria.eq("validFlag", true);
        criteria.isNotNull("appId");
        criteria.isNotNull("mchId");
        criteria.isNotNull("mchKey");
        List<WechatAccount> wechatAccounts = findByCriteria(criteria);
        if (!wechatAccounts.isEmpty()) {
            return wechatAccounts.get(0);
        }
        return null;
    }

    public WechatAccount get(long id) {
        Criteria<WechatAccount> criteria = new Criteria<WechatAccount>(WechatAccount.class);
        criteria.eq("id", id);
        return findUniqueByCriteria(criteria);
    }

    public List<WechatAccount> getAccountList(SysUnit companyUnit, Page page, String account, Boolean validFlag, Boolean isAdmin, Boolean isSiteAdmin) {
        Criteria<WechatAccount> criteria = new Criteria<WechatAccount>(WechatAccount.class);
        DetachedCriteria userCriteria = criteria.createCriteria("user", "user", JoinType.INNER_JOIN);
        if (!isAdmin) {
            if (!isSiteAdmin) {
                criteria.eq("companyUnit.id", companyUnit.getId());
            } else {
                DetachedCriteria unitCriteria = criteria.createCriteria("companyUnit", "companyUnit", JoinType.INNER_JOIN);
                DetachedCriteria siteCriteria = unitCriteria.createCriteria("sysSite", "sysSite", JoinType.INNER_JOIN);
                siteCriteria.add(Restrictions.eq("id", companyUnit.getSysSite().getId()));
            }

        }


        if (validFlag != null) {
            criteria.eq("validFlag", validFlag);
        }
        if (account != null && !"".equals(account)) {
            criteria.like("account", account);
        }
        return findByCriteria(criteria, page);
    }
    
    /**
     * 根据微信号或者原始ID查找
     * @author caiys
     * @date 2015年11月25日 下午2:43:54
     * @param account
     * @param originalId
     * @param validFlag
     * @return
     */
    public WechatAccount findUniqueBy(String account, String originalId, boolean validFlag) {
        Criteria<WechatAccount> criteria = new Criteria<WechatAccount>(WechatAccount.class);
        criteria.eq("validFlag", validFlag);
        if (StringUtils.isNotBlank(account)) {
        	criteria.eq("account", account);
        }
        if (StringUtils.isNotBlank(originalId)) {
        	criteria.eq("originalId", originalId);
        }
        List<WechatAccount> wechatAccounts = findByCriteria(criteria);
        if (wechatAccounts.size() > 0) {
        	return wechatAccounts.get(0);
        } else {
        	return null;
        }
    }

    public WechatAccount findUniqueByCompanyAndAccount(String accountName, Long companyId) {
        Criteria<WechatAccount> criteria = new Criteria<WechatAccount>(WechatAccount.class);
        criteria.createCriteria("companyUnit", "companyUnit");
        criteria.eq("account", accountName);
        criteria.eq("companyUnit.id", companyId);
        return findUniqueByCriteria(criteria);
    }
  /**
     * 通过多个公众号账户名和companyId查找有效公众号账户列表
     * @param accounts
     * @param companyIds
     * @return
     */
    public List<WechatAccount> findByCompanyAndAccount(String accounts, String companyIds) {
        Criteria<WechatAccount> criteria = new Criteria<WechatAccount>(WechatAccount.class);
        criteria.createCriteria("companyUnit", "companyUnit");
        criteria.in("account", accounts.split(","));
        String[] companyIdArr = companyIds.split(",");
        List<Long> companyIdList = ArrayUtils.stringArrayTOLongArray(companyIdArr);
        criteria.in("companyUnit.id", companyIdList);
        criteria.eq("validFlag", true);
        return findByCriteria(criteria);
    }

    /**
     * 公司获取该公众号
     * @param companyUnit
     * @param validFlag
     * @return
     */
	public List<WechatAccount> getAccountListAll(SysUnit companyUnit,
			Boolean validFlag) {
		 Criteria<WechatAccount> criteria = new Criteria<WechatAccount>(WechatAccount.class);
	     criteria.eq("validFlag", validFlag);
	     criteria.eq("companyUnit", companyUnit);
		return findByCriteria(criteria);
	}

	/**
	 * 全局管理员获取所有公众号
	 * @param validFlag
	 * @return
	 */
	public List<WechatAccount> getAccountListAll(Boolean validFlag) {
		 Criteria<WechatAccount> criteria = new Criteria<WechatAccount>(WechatAccount.class);
	     criteria.eq("validFlag", validFlag);
		return findByCriteria(criteria);
	}

	/**
	 * 站点管理员获取旗下的公司公众号
	 * @param sysUnList
	 * @param validFlag
	 * @return
	 */
	public List<WechatAccount> getAccountListAll(List<SysUnit> sysUnList,
			Boolean validFlag) {
		Criteria<WechatAccount> criteria = new Criteria<WechatAccount>(WechatAccount.class);
	     criteria.eq("validFlag", validFlag);
	     criteria.in("companyUnit", sysUnList);
		return findByCriteria(criteria);
	}

	public WechatAccount getAccountByOriId(String valiContent) {
		Criteria<WechatAccount> criteria = new Criteria<WechatAccount>(WechatAccount.class);
	     criteria.eq("originalId", valiContent);
		return findUniqueByCriteria(criteria);
	}

    public List<WechatAccount> findComboList(SysUser loginUser, SysUnit companyUnit, Boolean supperAdmin, Boolean siteAdmin) {

        Criteria<WechatAccount> criteria = new Criteria<WechatAccount>(WechatAccount.class);

        if (!supperAdmin) {
            if (!siteAdmin) {
                criteria.eq("companyUnit.id", companyUnit.getId());
            } else {
                DetachedCriteria unitCriteria = criteria.createCriteria("companyUnit", "companyUnit", JoinType.INNER_JOIN);
                DetachedCriteria siteCriteria = unitCriteria.createCriteria("sysSite", "sysSite", JoinType.INNER_JOIN);
                siteCriteria.add(Restrictions.eq("id", companyUnit.getSysSite().getId()));
            }
        }
        return findByCriteria(criteria);
    }

}

