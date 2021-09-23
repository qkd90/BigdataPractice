package com.data.data.hmly.service.dao;

import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.entity.UserStatus;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.Projections;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vacuity on 15/11/3.
 */
@Repository
public class UserDao extends DataAccess<User> {

	public List<SysUser> getUserList(String sceId, String status ,String cityIdStr, Page pageInfo, SysUnit companyUnit, Boolean isSupperAdmin, Boolean isSiteAdmin) {
		Criteria<SysUser> criteria = new Criteria<SysUser>(SysUser.class);
		criteria.createCriteria("sysUnit", "su", JoinType.INNER_JOIN);
		criteria.createCriteria("su.companyUnit", "cu", JoinType.INNER_JOIN);
		criteria.createCriteria("cu.sysUnitDetail", "csud", JoinType.INNER_JOIN);
		if (StringUtils.isNotBlank(sceId)) {
			criteria.eq("csud.scenicid", Long.valueOf(sceId));
		} else {
			criteria.isNotNull("csud.scenicid");
		}
		if (StringUtils.isNotBlank(status)) {
			criteria.eq("status", UserStatus.valueOf(status));
		}
		if (StringUtils.isNotBlank(cityIdStr)) {
			criteria.eq("cu.area.id", Long.valueOf(cityIdStr));
		}
		// 数据过滤
		if (!isSupperAdmin) {
			criteria.eq("cu.sysSite.id", companyUnit.getSysSite().getId());
			if (!isSiteAdmin) {
				criteria.eq("cu.id", companyUnit.getId());
			}
		}
		criteria.orderBy("updateTime", "desc");
		return findByCriteria(criteria, pageInfo);
	}

    /**
     * 查询账户是否已经存在
     * @param account
     * @param neUserId
     * @return
     */
    public boolean isExistsAccount(String account, String neUserId) {
        Criteria<User> c = new Criteria<User>(User.class);
        c.eq("account", account);
        if (StringUtils.isNotBlank(neUserId)) {
            c.ne("id", Long.valueOf(neUserId));
        }
        c.setProjection(Projections.rowCount());
        Long count = (Long) findUniqueValue(c);
        if (count != null && count > 0) {
            return true;
        }
        return false;
    }

	/**
	 * 查询账户是否已经存在
	 * @param user
	 * @param neUserId
	 * @return
	 */
	public boolean isExistsAccount(User user, String neUserId) {
		Criteria<User> c = new Criteria<User>(User.class);
		if (StringUtils.isNotBlank(user.getAccount())) {
			c.eq("account", user.getAccount());
		}

		if (StringUtils.isNotBlank(user.getMobile())) {
			c.eq("mobile", user.getMobile());
		}
//		if (StringUtils.isNotBlank(user.getPassword())) {
//			c.eq("password", Encryption.encry(user.getPassword()));
//		}
		if (user.getId() != null) {
			c.eq("id", user.getId());
		}
		if (StringUtils.isNotBlank(neUserId)) {
			c.ne("id", Long.valueOf(neUserId));
		}
		c.setProjection(Projections.rowCount());
		Long count = (Long) findUniqueValue(c);
		if (count != null && count > 0) {
			return true;
		}
		return false;
	}
}
