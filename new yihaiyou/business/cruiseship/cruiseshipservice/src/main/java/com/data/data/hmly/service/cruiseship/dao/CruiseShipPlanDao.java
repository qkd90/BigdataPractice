package com.data.data.hmly.service.cruiseship.dao;

import com.data.data.hmly.service.cruiseship.entity.CruiseShipPlan;
import com.data.data.hmly.service.entity.SysUser;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CruiseShipPlanDao extends DataAccess<CruiseShipPlan> {

    /**
     * 列表查询
     */
    public List<CruiseShipPlan> listCruiseShipPlans(CruiseShipPlan cruiseShipPlan, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin) {
        Criteria<CruiseShipPlan> criteria = new Criteria<CruiseShipPlan>(CruiseShipPlan.class);
        // 邮轮标识
        if (cruiseShipPlan.getCruiseShipId() != null) {
            criteria.eq("cruiseShip.id", cruiseShipPlan.getCruiseShipId());
        }
        criteria.orderBy("day", "asc");
        return findByCriteria(criteria);
    }

    /**
     * 删除邮轮行程
     * @param cruiseShipId
     */
    public void delByCruiseShipId(Long cruiseShipId) {
        String hql = " delete CruiseShipPlan where cruiseShip.id = ? ";
        updateByHQL(hql, cruiseShipId);
    }

}
