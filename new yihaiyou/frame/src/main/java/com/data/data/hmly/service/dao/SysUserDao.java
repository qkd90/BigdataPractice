package com.data.data.hmly.service.dao;

import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.UserType;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SysUserDao extends DataAccess<SysUser> {

	@Override
	public void save(Object entity) {
		// TODO Auto-generated method stub
		super.save(entity);
	}

	@Override
	public void update(Object entity) {
		// TODO Auto-generated method stub
		super.update(entity);
	}

    /**
     * 查询公司管理员
     * @param companyUnitId
     * @return
     */
	public SysUser findCompanyManager(Long companyUnitId) {
		Criteria<SysUser> criteria = new Criteria<SysUser>(SysUser.class);
		criteria.createCriteria("sysUnit", "su", JoinType.INNER_JOIN);
		criteria.createCriteria("su.companyUnit", "cu", JoinType.INNER_JOIN);
        criteria.eq("cu.id", companyUnitId);
        criteria.add(Restrictions.or(Restrictions.eq("userType", UserType.CompanyManage), Restrictions.or(Restrictions.eq("userType", UserType.SiteManage))));
        List<SysUser> users = findByCriteria(criteria);
        if (users.size() > 0) {
            return users.get(0);
        }
        return null;
	}
	
}
