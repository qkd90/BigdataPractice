package com.data.data.hmly.service.cruiseship;

import com.data.data.hmly.service.cruiseship.dao.CruiseShipDao;
import com.data.data.hmly.service.cruiseship.dao.CruiseShipPlanDao;
import com.data.data.hmly.service.cruiseship.entity.CruiseShip;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipPlan;
import com.data.data.hmly.service.entity.SysUser;
import com.framework.hibernate.util.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CruiseShipPlanService {
    @Resource
    private CruiseShipDao cruiseShipDao;
    @Resource
    private CruiseShipPlanDao cruiseShipPlanDao;

    public List<CruiseShipPlan> listByCruiseShipId(Long shipId) {
        Criteria<CruiseShipPlan> criteria = new Criteria<CruiseShipPlan>(CruiseShipPlan.class);
        criteria.eq("cruiseShip.id", shipId);
        criteria.orderBy(Order.asc("day"));
        return cruiseShipPlanDao.findByCriteria(criteria);
    }

    /**
     * 列表查询
     */
    public List<CruiseShipPlan> listCruiseShipPlans(CruiseShipPlan cruiseShipPlan, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin) {
        return cruiseShipPlanDao.listCruiseShipPlans(cruiseShipPlan, sysUser, isSiteAdmin, isSupperAdmin);
    }

    /**
     * 保存行程：先删除再插入，更新邮轮游玩天数
     */
    public void saveList(List<CruiseShipPlan> plans, Long cruiseShipId) {
        // 删除原有的行程
        cruiseShipPlanDao.delByCruiseShipId(cruiseShipId);
        // 保存新的行程
        CruiseShip cruiseShip = cruiseShipDao.load(cruiseShipId);
        for (CruiseShipPlan plan : plans) {
            plan.setCreateTime(new Date());
            plan.setUpdateTime(new Date());
            plan.setCruiseShip(cruiseShip);
        }
        cruiseShipPlanDao.save(plans);
        cruiseShip.setPlayDay(plans.size());
        cruiseShipDao.update(cruiseShip);
    }

}
