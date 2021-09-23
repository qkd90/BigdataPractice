package com.data.data.hmly.service.weixinh5.dao;

import com.data.data.hmly.service.dao.SysSiteDao;
import com.data.data.hmly.service.dao.SysUnitDao;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.weixinh5.entity.Activities;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dy on 2016/2/16.
 */
@Repository
public class ActivitiesDao extends DataAccess<Activities>{

    @Resource
    private SysSiteDao sysSiteDao;
    @Resource
    private SysUnitDao sysUnitDao;
    /**
     * 查询线路列表
     *
     * @author caiys
     * @date 2015年10月16日 上午9:29:32
     * @param activities
     * @return
     */
    public List<Activities> findActivitiesList(Activities activities, Page page, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin) {
        Criteria<Activities> criteria = new Criteria<Activities>(Activities.class);
        criteria.createCriteria("sysUnit", "u", JoinType.INNER_JOIN);
        criteria.createCriteria("u.sysSite", "us", JoinType.INNER_JOIN);
        foramtCond(activities, criteria);

        // 数据过滤
        if (isSiteAdmin) {
            SysSite sysSite = sysSiteDao.load(sysUser.getSysSite().getId());
            List<SysUnit> units = sysUnitDao.findUnitBySite(sysSite);
            criteria.in("sysUnit",units);
        } else if (!isSupperAdmin && !isSiteAdmin){
            criteria.eq("sysUnit", sysUser.getSysUnit().getCompanyUnit());
        }

        criteria.orderBy("updateTime", "desc");
        return findByCriteria(criteria, page);
    }

    public void foramtCond(Activities activities, Criteria<Activities> criteria) {

        if (activities.getType() != null) {
            criteria.eq("type", activities.getType());
        }

        if (activities.getStatus() != null) {
            criteria.eq("status", activities.getStatus());
        }
        if (activities.getName() != null) {
            criteria.like("name", activities.getName());
        }

    }

}
